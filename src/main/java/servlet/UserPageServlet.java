package servlet;

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

public class UserPageServlet extends HttpServlet {
    public static final String USER_PAGE_URL = "/userpage";
    private final AccountService accountService;

    public UserPageServlet(Context context) {
        this.accountService = (AccountService) context.get(AccountService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = accountService.getLoginBySession(request.getSession().getId());
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("login", login == null ? "" : login);
        response.getWriter().println(PageGenerator.getPage("userpage.html", pageVariables));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
