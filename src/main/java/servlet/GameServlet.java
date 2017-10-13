package servlet;

import game.mechanics.GameMechanics;
import main.Context;
import service.account.AccountService;
import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameServlet extends HttpServlet {
    public static final String GAME_PAGE_URL = "/game";

    private GameMechanics gameMechanics;
    private AccountService accountService;

    public GameServlet(Context context) {
        this.gameMechanics = (GameMechanics) context.get(GameMechanics.class);
        this.accountService = (AccountService) context.get(AccountService.class);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String safeLogin = login == null ? "Unnamed" : login;
        accountService.saveUserSession(safeLogin, request.getSession().getId());
//        gameMechanics.addPlayer(safeLogin);
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("myName", safeLogin);
        response.getWriter().println(PageGenerator.getPage("game.html", pageVariables));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
