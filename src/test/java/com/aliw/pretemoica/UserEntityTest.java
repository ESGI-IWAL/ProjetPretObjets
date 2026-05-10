package com.aliw.pretemoica;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import com.aliw.pretemoica.entity.UserEntity;
import com.aliw.pretemoica.entity.ObjectEntity;

class UserEntityTest {

    @Test
    void testDefaultConstructor() {
        UserEntity user = new UserEntity();
        assertNotNull(user);
        assertNull(user.getId());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNotNull(user.getObjects());
        assertNotNull(user.getLinkedUsers());
        assertEquals(0, user.getRating());
    }

    @Test
    void testSettersAndGetters() {
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRating(5);

        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals(5, user.getRating());
    }

    @Test
    void testAddObject() {
        UserEntity user = new UserEntity();
        ObjectEntity obj = new ObjectEntity();
        obj.setName("Test Object");

        user.addObject(obj);

        List<ObjectEntity> objects = user.getObjects();
        assertEquals(1, objects.size());
        assertEquals("Test Object", objects.get(0).getName());
    }

    @Test
    void testAddLinkedUser() {
        UserEntity user1 = new UserEntity();
        user1.setEmail("user1@example.com");

        UserEntity user2 = new UserEntity();
        user2.setEmail("user2@example.com");

        user1.addLinkedUser(user2);

        List<UserEntity> linkedUsers = user1.getLinkedUsers();
        assertEquals(1, linkedUsers.size());
        assertEquals("user2@example.com", linkedUsers.get(0).getEmail());
    }

    @Test
    void testGetRatingThrowsExceptionWhenNull() {
        UserEntity user = new UserEntity();
        user.setRating(null);

        IllegalStateException exception = assertThrows(IllegalStateException.class, user::getRating);
        assertTrue(exception.getMessage().contains("Le rating n'a pas été initialisé"));
    }

    @Test
    void testGetRatingReturnsValue() {
        UserEntity user = new UserEntity();
        user.setRating(10);

        assertEquals(10, user.getRating());
    }

    @Test
    void testAuthentificationSuccess() {
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        user.setPassword("password123");

        assertTrue(user.authentification("test@example.com", "password123"));
    }

    @Test
    void testAuthentificationFailureWrongEmail() {
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        user.setPassword("password123");

        assertFalse(user.authentification("wrong@example.com", "password123"));
    }

    @Test
    void testAuthentificationFailureWrongPassword() {
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        user.setPassword("password123");

        assertFalse(user.authentification("test@example.com", "wrongpassword"));
    }

    @Test
    void testAuthentificationFailureNullEmail() {
        UserEntity user = new UserEntity();
        user.setPassword("password123");

        assertFalse(user.authentification("test@example.com", "password123"));
    }

    @Test
    void testAuthentificationFailureNullPassword() {
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");

        assertFalse(user.authentification("test@example.com", "password123"));
    }
}
