package main;

import entity.resource.DatabaseSettings;
import entity.resource.GameSettings;
import entity.resource.ServerSettings;
import game.mechanics.GameMechanics;
import game.mechanics.GameMechanicsImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.auth.AuthService;
import service.auth.AuthServiceImpl;
import service.database.DatabaseService;
import service.database.DatabaseServiceMySqlImpl;
import service.websocket.WebSocketService;
import service.websocket.WebSocketServiceImpl;
import servlet.*;

import javax.servlet.Servlet;
import java.lang.invoke.MethodHandles;

/**
 * @author Igor Ivankov
 */
public class Main {
    @SuppressWarnings("ConstantNamingConvention")
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

//    public static final int DEFAULT_PORT = 8888;
    public static final String RESOURCE_BASE = "static";
    private static final String PATH_TO_PROPERTIES = "server.properties";

    @SuppressWarnings("OverlyBroadThrowsClause")
    public static void main(String[] args) throws Exception {
        // TODO
        // use context in constructors
        Context context = new Context();

        WebSocketService webSocketService = new WebSocketServiceImpl();
        context.add(webSocketService);
        ResourceSingleton.getInstance().loadAllResources("src/main/res");

        DatabaseSettings databaseSettings = (DatabaseSettings) ResourceSingleton.getInstance().getResource("DatabaseSettings");
        DatabaseService databaseService = new DatabaseServiceMySqlImpl(databaseSettings);
        context.add(databaseService);

        GameSettings gameSettings = (GameSettings) ResourceSingleton.getInstance().getResource("GameSettings");
        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService, gameSettings);

        AccountService accountService = new AccountServiceImpl();
        context.add(accountService);

        AuthService authService = new AuthServiceImpl();
        context.add(authService);

        Servlet signUpServlet = new SignUpServlet(accountService);
        Servlet signInServlet = new SignInServlet(accountService);
        Servlet adminServlet = new AdminServlet(accountService);
        Servlet chatWebSocketServlet = new ChatWebSocketServlet();
        Servlet gameServlet = new GameServlet(gameMechanics, authService);
        Servlet gameWebSocketServlet = new GameWebSocketServlet(authService, gameMechanics, webSocketService);

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(signUpServlet), SignUpServlet.SIGN_UP_PAGE_URL);
        servletContextHandler.addServlet(new ServletHolder(signInServlet), SignInServlet.SIGN_IN_PAGE_URL);
        servletContextHandler.addServlet(new ServletHolder(adminServlet), AdminServlet.ADMIN_PAGE_URL);
        // TODO
        // hardcode url
        servletContextHandler.addServlet(new ServletHolder(chatWebSocketServlet), "/chat");
        servletContextHandler.addServlet(new ServletHolder(gameServlet), GameServlet.GAME_PAGE_URL);
        servletContextHandler.addServlet(new ServletHolder(gameWebSocketServlet), GameWebSocketServlet.GAME_WEB_SOCKET_URL);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase(RESOURCE_BASE);

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{resourceHandler, servletContextHandler});


        ServerSettings serverSettings = (ServerSettings) ResourceSingleton.getInstance().getResource("ServerSettings");
        int port = serverSettings.getPort();
//        int port = args.length == 1 ? Integer.valueOf(args[0]) : DEFAULT_PORT;
        Server server = new Server(port);
        server.setHandler(handlerList);

        server.start();

//        server.join();
        gameMechanics.run();
    }
}
