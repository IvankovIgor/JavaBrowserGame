package game.mechanics;

import entity.game.GameSession;
import entity.game.Player;
import entity.resource.GameSettings;
import main.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.websocket.WebSocketService;
import util.TimeHelper;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameMechanicsImpl implements GameMechanics {
    @SuppressWarnings("ConstantNamingConvention")
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static final int STEP_TIME = 1000;
    private static long GAME_TIME;
    private static final long DEFAULT_GAME_TIME = 10000;

    private GameSettings gameSettings;
    private WebSocketService webSocketService;
    private Map<String, GameSession> nameToGame = new HashMap<>();
    private Set<GameSession> allSessions = new HashSet<>();
    private String waiter;

    public GameMechanicsImpl(Context context) {
        this.webSocketService = (WebSocketService) context.get(WebSocketService.class);
        this.gameSettings = (GameSettings) context.get(GameSettings.class);
        GAME_TIME = gameSettings == null ? DEFAULT_GAME_TIME : gameSettings.getMaxTime();
    }

    @Override
    public void addPlayer(String user) {
        if (waiter != null) {
            startGame(user);
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
                allSessions.remove(session);
            }
        }
    }

    private void startGame(String first) {
        String second = waiter;
        GameSession gameSession = new GameSession(first, second);
        allSessions.add(gameSession);
        nameToGame.put(first, gameSession);
        nameToGame.put(second, gameSession);

        webSocketService.notifyStartGame(gameSession.getSelf(first));
        webSocketService.notifyStartGame(gameSession.getSelf(second));
    }
}
