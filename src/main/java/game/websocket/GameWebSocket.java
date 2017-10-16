package game.websocket;

import entity.game.Player;
import game.mechanics.GameMechanics;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.simple.JSONObject;
import service.websocket.WebSocketService;

import java.io.IOException;

@WebSocket
public class GameWebSocket {
    private String myName;
    private Session session;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public GameWebSocket(String myName, GameMechanics gameMechanics, WebSocketService webSocketService) {
        this.myName = myName;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    public String getMyName() {
        return myName;
    }

    @SuppressWarnings("unchecked")
    public void startGame(Player user) {
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("status", "start");
            jsonStart.put("enemyName", user.getEnemyName());
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.out.print(e.toString());
        }
    }

    @SuppressWarnings("unchecked")
    public void gameOver(Player user, boolean win) {
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("status", "finish");
            jsonStart.put("win", win);
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.out.print(e.toString());
        }
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        gameMechanics.incrementScore(myName);
    }

    @OnWebSocketConnect
    public void onOpen(Session s) {
        this.session = s;
        webSocketService.addPlayer(this);
        gameMechanics.addPlayer(myName);
    }

    @SuppressWarnings("unchecked")
    public void setMyScore(Player user) {
        JSONObject jsonStart = new JSONObject();
        jsonStart.put("status", "increment");
        jsonStart.put("name", myName);
        jsonStart.put("score", user.getMyScore());
        try {
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.out.print(e.toString());
        }
    }

    @SuppressWarnings("unchecked")
    public void setEnemyScore(Player user) {
        JSONObject jsonStart = new JSONObject();
        jsonStart.put("status", "increment");
        jsonStart.put("name", user.getEnemyName());
        jsonStart.put("score", user.getEnemyScore());
        try {
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.out.print(e.toString());
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {

    }
}
