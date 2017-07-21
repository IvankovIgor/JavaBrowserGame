package servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.account.AccountServiceImpl;
import util.PageGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by ivankov on 17.07.2017.
 */
public class SignUpServletTest {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDoGet() throws Exception {
        when(request.getParameter("login")).thenReturn("User1");
        when(request.getParameter("password")).thenReturn("Password1");
        when(request.getParameter("email")).thenReturn("");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);

        SignUpServlet signUpServlet = new SignUpServlet(new AccountServiceImpl());
        signUpServlet.doGet(request, response);
        String result = stringWriter.getBuffer().toString().trim();

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("lastLogin", "User1");
        assertEquals(result, PageGenerator.getPage("authform.html", pageVariables));
    }

    @Test
    public void testDoPost() throws Exception {
        // TODO
    }

}