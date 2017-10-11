package game.mechanics;

import entity.game.GameSession;
import entity.game.Player;
import entity.resource.GameSettings;
import messagesystem.Abonent;
import messagesystem.MessageSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.websocket.WebSocketService;
import util.TimeHelper;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameMechanicsImpl extends Abonent implements GameMechanics {
    @SuppressWarnings("ConstantNamingConvention")
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static final int STEP_TIME = 100;
    private static final int GAME_TIME = 15 * 1000;

    private GameSettings gameSettings;
    private WebSocketService webSocketService;
    private Map<String, GameSession> nameToGame = new HashMap<>();
    private Set<GameSession> allSessions = new HashSet<>();
    private String waiter;

    public GameMechanicsImpl(MessageSystem messageSystem, WebSocketService webSocketService, GameSettings gameSettings) {
        super(messageSystem);
        this.webSocketService = webSocketService;
        this.gameSettings = gameSettings;
    }

    @Override
    public void addPlayer(String user) {
        if (waiter != null) {
            starGame(user);
            waiter = null;
        } else {
            waiter = user;
        }
    }

    @Override
    public void incrementScore(String userName) {
        GameSession myGameSession = nameToGame.get(userName);
        Player myUser = myGameSession.getSelf(userName);
        myUser.incrementMyScore();
        Player enemyUser = myGameSession.getEnemy(userName);
        enemyUser.incrementEnemyScore();
        webSocketService.notifyMyNewScore(myUser);
        webSocketService.notifyEnemyNewScore(enemyUser);
        if (myUser.getMyScore() == gameSettings.getScoreToWin()) {
            logger.debug("winner!");
        }
    }

    @Override
    public void run() {
        while (true) {
            gmStep();
            TimeHelper.sleep(STEP_TIME);
        }
    }

    private void gmStep() {
        for (GameSession session : allSessions) {
            if (session.getSessionTime() > GAME_TIME) {
                boolean firstWin = session.isFirstWin();
                webSocketService.notifyGameOver(session.getFirst(), firstWin);
                webSocketService.notifyGameOver(session.getSecond(), !firstWin);
            }
        }
    }

    private void starGame(String first) {
        String second = waiter;
        GameSession gameSession = new GameSession(first, second);
        allSessions.add(gameSession);
        nameToGame.put(first, gameSession);
        nameToGame.put(second, gameSession);

        webSocketService.notifyStartGame(gameSession.getSelf(first));
        webSocketService.notifyStartGame(gameSession.getSelf(second));
    }
}
