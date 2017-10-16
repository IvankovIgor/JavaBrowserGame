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
import service.database.DAOFactory;
import service.websocket.WebSocketService;
import service.websocket.WebSocketServiceImpl;
import servlet.*;

import javax.servlet.Servlet;
import java.lang.invoke.MethodHandles;

public class Main {
    @SuppressWarnings("ConstantNamingConvention")
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

//    public static final int DEFAULT_PORT = 8888;
    public static final String STATIC_BASE = "static";

    @SuppressWarnings("OverlyBroadThrowsClause")
    public static void main(String[] args) throws Exception {
        Context context = new Context();

        WebSocketService webSocketService = new WebSocketServiceImpl();
        context.add(WebSocketService.class, webSocketService);

        DatabaseSettings databaseSettings = (DatabaseSettings) ResourceSingleton.getInstance().getResource(ResourceSingleton.MYSQL_PROPERTIES);
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.JPA_MYSQL, databaseSettings);
        if (daoFactory != null) {
            context.add(DAOFactory.class, daoFactory);
        } else {
            logger.error("DAO factory is null");
        }

        AccountService accountService = new AccountServiceImpl(context);
        context.add(AccountService.class, accountService);

        GameSettings gameSettings = (GameSettings) ResourceSingleton.getInstance().getResource(ResourceSingleton.GAME_PROPERTIES);
        context.add(GameSettings.class, gameSettings);

        GameMechanics gameMechanics = new GameMechanicsImpl(context);
        context.add(GameMechanics.class, gameMechanics);

        Servlet signUpServlet = new SignUpServlet(context);
        Servlet signInServlet = new SignInServlet(context);
        Servlet adminServlet = new AdminServlet(context);
        Servlet chatWebSocketServlet = new ChatWebSocketServlet(context);
        Servlet gameServlet = new GameServlet(context);
        Servlet gameWebSocketServlet = new GameWebSocketServlet(context);
        Servlet userPageServlet = new UserPageServlet(context);

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(signUpServlet), SignUpServlet.SIGN_UP_PAGE_URL);
        servletContextHandler.addServlet(new ServletHolder(signInServlet), SignInServlet.SIGN_IN_PAGE_URL);
        servletContextHandler.addServlet(new ServletHolder(adminServlet), AdminServlet.ADMIN_PAGE_URL);
        servletContextHandler.addServlet(new ServletHolder(chatWebSocketServlet), ChatWebSocketServlet.CHAT_WEB_SOCKET_URL);
        servletContextHandler.addServlet(new ServletHolder(gameServlet), GameServlet.GAME_PAGE_URL);
        servletContextHandler.addServlet(new ServletHolder(gameWebSocketServlet), GameWebSocketServlet.GAME_WEB_SOCKET_URL);
        servletContextHandler.addServlet(new ServletHolder(userPageServlet), UserPageServlet.USER_PAGE_URL);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase(STATIC_BASE);

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{resourceHandler, servletContextHandler});

        ServerSettings serverSettings = (ServerSettings) ResourceSingleton.getInstance().getResource(ResourceSingleton.SERVER_PROPERTIES);
//        String host = serverSettings.getHost();
        int port = serverSettings.getPort();
//        int port = args.length == 1 ? Integer.valueOf(args[0]) : DEFAULT_PORT;
//        InetSocketAddress socketAddress = new InetSocketAddress(host, port);

        Server server = new Server(port);
        server.setHandler(handlerList);
//        ServerConnector connector = null;
//        try (ServerConnector connector = new ServerConnector(server)) {
//            connector.setPort(port);
//            connector.setHost(host);
//            server.setConnectors(new ServerConnector[] {connector});
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//        }
        logger.info("Starting jetty");
        server.start();

//        server.join();
        gameMechanics.run();
    }

    // MESSAGE SYSTEM ----------------------
//        MessageSystem messageSystem = new MessageSystem();
//        final Thread accountServiceThread = new Thread(new AccountServiceImpl(messageSystem));
//        accountServiceThread.setDaemon(true);
//        accountServiceThread.setName("AccountService1");
//        accountServiceThread.start();
//        final Thread accountServiceThread2 = new Thread(new AccountServiceImpl(messageSystem));
//        accountServiceThread2.setDaemon(true);
//        accountServiceThread2.setName("AccountService2");
//        accountServiceThread2.start();
//        final Thread frontEndServiceThread = new Thread(new FrontendImpl(messageSystem));
//        frontEndServiceThread.setDaemon(true);
//        frontEndServiceThread.setName("FrontendService");
//        frontEndServiceThread.start();
//        final Thread databaseServiceThread = new Thread()
    // ENDS MESSAGE SYSTEM -----------------
}
