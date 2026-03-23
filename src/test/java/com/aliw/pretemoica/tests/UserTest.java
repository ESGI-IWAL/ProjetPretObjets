package com.aliw.pretemoica.tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testDefaultConstructor() {
        User user = new User();
        assertNotNull(user);
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getEmail());
    }

    @Test
    public void testParameterizedConstructor() {
        User user = new User("testuser", "test@example.com");
        assertNotNull(user);
        assertNull(user.getId()); // id is generated
        assertEquals("testuser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    public void testSettersAndGetters() {
        User user = new User();
        user.setUsername("newuser");
        user.setEmail("new@example.com");
        user.setId(1L);

        assertEquals(1L, user.getId());
        assertEquals("newuser", user.getUsername());
        assertEquals("new@example.com", user.getEmail());
    }
}
