package servlet;

import game.mechanics.GameMechanics;
import game.websocket.GameWebSocketCreator;
import main.Context;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import service.account.AccountService;
import service.websocket.WebSocketService;

public class GameWebSocketServlet extends WebSocketServlet {
    public static final String GAME_WEB_SOCKET_URL = "/gameplay";

    private static final int IDLE_TIME = 60 * 1000;
    private AccountService accountService;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public GameWebSocketServlet(Context context) {
            this.accountService = (AccountService) context.get(AccountService.class);
            this.gameMechanics = (GameMechanics) context.get(GameMechanics.class);
            this.webSocketService = (WebSocketService) context.get(WebSocketService.class);
        }

        @Override
        public void configure(WebSocketServletFactory factory) {
            factory.getPolicy().setIdleTimeout(IDLE_TIME);
            factory.setCreator(new GameWebSocketCreator(accountService, gameMechanics, webSocketService));
        }
}
