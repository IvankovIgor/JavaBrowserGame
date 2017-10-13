package servlet;

import chat.websocket.ChatWebSocketCreator;
import main.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class ChatWebSocketServlet extends WebSocketServlet {
    public static final String CHAT_WEB_SOCKET_URL = "/chat";
    static final Logger logger = LogManager.getLogger(ChatWebSocketServlet.class);
    private final static int LOGOUT_TIME = 10 * 60 * 1000;

    public ChatWebSocketServlet(Context context) {
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator(new ChatWebSocketCreator());
        logger.info("Socket servlet configured");
    }
}
