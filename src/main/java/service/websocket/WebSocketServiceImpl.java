package service.websocket;

import entity.game.Player;
import game.websocket.GameWebSocket;

import java.util.HashMap;
import java.util.Map;

public class WebSocketServiceImpl implements WebSocketService {
    private Map<String, GameWebSocket> userSockets = new HashMap<>();

    @Override
    public void addPlayer(GameWebSocket user) {
        userSockets.put(user.getMyName(), user);
    }

    @Override
    public void notifyMyNewScore(Player user) {
        userSockets.get(user.getMyName()).setMyScore(user);
    }

    @Override
    public void notifyEnemyNewScore(Player user) {
        userSockets.get(user.getMyName()).setEnemyScore(user);
    }

    @Override
    public void notifyStartGame(Player user) {
        GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
        gameWebSocket.startGame(user);
    }

    @Override
    public void notifyGameOver(Player user, boolean win) {
        userSockets.get(user.getMyName()).gameOver(user, win);
    }
}
