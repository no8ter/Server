package ru.rgrtu.pahomova.Utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


public class UserToolsTests {

    @BeforeAll
    public static void init() {
        ru.rgrtu.pahomova.Utils.Connect.connect();
    }

    @Test
    public void testCheckLoginFunc() {
        String login = "admin";
        String pass  = "admin";
        assertTrue(UserTools.checkLoginAndPass(login, pass));
        assertFalse(UserTools.checkLoginAndPass(null, null));
        assertFalse(UserTools.checkLoginAndPass(login, "null"));
    }

    @Test
    public void testGetUserData() throws SQLException {
        assertEquals(UserTools.getUserData(1).firstName, "Петр");
    }
}
