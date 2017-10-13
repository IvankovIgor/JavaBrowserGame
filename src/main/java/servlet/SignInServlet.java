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

public class SignInServlet extends HttpServlet {
    public static final String SIGN_IN_PAGE_URL = "/signin";
    AccountService accountService;

    public SignInServlet(Context context) {
        this.accountService = (AccountService) context.get(AccountService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        response.getWriter().println(PageGenerator.getPage("signinform.html", pageVariables));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Set<AccountStatus> accountStatuses = accountService.signIn(login, password);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<>();
        if (accountStatuses.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_OK);
            accountService.saveUserSession(login, request.getSession().getId());
            response.sendRedirect("/userpage");
//            HttpServletRequestWrapper newReq = new HttpServletRequestWrapper(request);
//            newReq.set

//            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/userpage");
//            dispatcher.forward(request, response);
//            pageVariables.put("login", login == null ? "" : login);
//            response.getWriter().println(PageGenerator.getPage("userpage.html", pageVariables));
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
            pageVariables.put("error", "failed");
            response.getWriter().println(PageGenerator.getPage("signin.html", pageVariables));
        }
    }
}
