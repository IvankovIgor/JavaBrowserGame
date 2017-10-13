package servlet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.when;

public class SignInServletTest {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testDoGet() throws Exception {
        thrown.expect(NullPointerException.class);
        when(request.getParameter("login")).thenReturn("User1");
        when(request.getParameter("password")).thenReturn(null);

//        SignInServlet signInServlet = new SignInServlet(new AccountServiceImpl());
//        signInServlet.doGet(request, response);
    }
}