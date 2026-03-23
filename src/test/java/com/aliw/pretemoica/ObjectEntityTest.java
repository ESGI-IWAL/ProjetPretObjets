package com.aliw.pretemoica;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.aliw.pretemoica.entity.ObjectEntity;
import com.aliw.pretemoica.entity.UserEntity;

public class ObjectEntityTest {

    @Test
    public void testDefaultConstructor() {
        ObjectEntity obj = new ObjectEntity();
        assertNotNull(obj);
        assertNull(obj.getId());
        assertNull(obj.getName());
        assertNull(obj.getOwnedBy());
        assertEquals(ObjectEntity.ObjectStatus.AVAILABLE, obj.getStatus());
        assertNull(obj.getState());
    }

    @Test
    public void testSettersAndGetters() {
        ObjectEntity obj = new ObjectEntity();
        UserEntity owner = new UserEntity();
        owner.setEmail("owner@example.com");

        obj.setName("Test Object");
        obj.setOwnedBy(owner);
        obj.setStatus(ObjectEntity.ObjectStatus.LENT);
        obj.setState(ObjectEntity.ObjectState.GOOD);

        assertEquals("Test Object", obj.getName());
        assertEquals(owner, obj.getOwnedBy());
        assertEquals(ObjectEntity.ObjectStatus.LENT, obj.getStatus());
        assertEquals(ObjectEntity.ObjectState.GOOD, obj.getState());
    }

    @Test
    public void testObjectStatusEnum() {
        // Just to ensure enums are accessible
        assertNotNull(ObjectEntity.ObjectStatus.AVAILABLE);
        assertNotNull(ObjectEntity.ObjectStatus.LENT);
        assertNotNull(ObjectEntity.ObjectStatus.RESERVED);
        assertNotNull(ObjectEntity.ObjectStatus.UNAVAILABLE);
    }

    @Test
    public void testObjectStateEnum() {
        assertNotNull(ObjectEntity.ObjectState.NEW);
        assertNotNull(ObjectEntity.ObjectState.GOOD);
        assertNotNull(ObjectEntity.ObjectState.WORN);
        assertNotNull(ObjectEntity.ObjectState.DAMAGED);
    }
}
