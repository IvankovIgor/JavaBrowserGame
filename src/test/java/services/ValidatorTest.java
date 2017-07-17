package services;

import enums.AccountStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by ivankov on 17.07.2017.
 */

public class ValidatorTest {
    private Validator validator;
    private String login;
    private String password;
    private String email;

    @Before
    public void initialize() {
        Set<AccountStatus> statuses = new HashSet<>();
        validator = new Validator(statuses);
    }

    @Test
    public void testValidate() throws Exception {
        // TODO
        validator.validate("admin", "adminadmin");
        assertTrue(true);
    }
}