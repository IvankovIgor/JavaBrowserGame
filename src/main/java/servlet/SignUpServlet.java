package servlet;

import entity.account.AccountStatus;
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
import java.util.Set;

/**
 * @author Igor Ivankov
 */
public class SignUpServlet extends HttpServlet {
    public static final String SIGN_UP_PAGE_URL = "/signup";

    private final AccountService accountService;

    public SignUpServlet(Context context) {
        this.accountService = (AccountService) context.get(AccountService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("login", "");
        pageVariables.put("error", "");
        response.getWriter().println(PageGenerator.getPage("signupform.html", pageVariables));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        Set<AccountStatus> accountStatuses = accountService.signUp(login, password, email);
        response.setContentType("text/html;charset=utf-8");
        Map<String, Object> pageVariables = new HashMap<>();
        if (accountStatuses.size() == 1 && accountStatuses.contains(AccountStatus.CREATED)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            pageVariables.put("login", login == null ? "" : login);
//            response.getWriter().println(PageGenerator.getPage("userpage.html", pageVariables));
            response.sendRedirect("/signin");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
            pageVariables.put("error", accountStatuses.toArray()[1].toString());
            pageVariables.put("login", "");
            response.getWriter().println(PageGenerator.getPage("signupform.html", pageVariables));
        }
    }
}
