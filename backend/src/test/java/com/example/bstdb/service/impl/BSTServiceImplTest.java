package com.example.bstdb.service.impl;

import com.example.bstdb.service.model.BstNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BSTServiceImplTest {

    private final BSTServiceImpl service = new BSTServiceImpl();

    @Test
    void parseNumbers_parsesValidInput() {
        List<Integer> nums = service.parseNumbers("7, 3 10 -1,5");
        assertEquals(List.of(7, 3, 10, -1, 5), nums);
    }

    @Test
    void parseNumbers_throwsOnInvalidToken() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.parseNumbers("7,a,3"));
        assertTrue(ex.getMessage().toLowerCase().contains("invalid"));
    }

    @Test
    void buildAvl_performsRotationForAscendingSequence() {
        BstNode root = service.buildAvl(List.of(10, 20, 30));
        // Classic LL/RR rotation case should produce 20 as new root in AVL
        assertNotNull(root);
        assertEquals(20, root.value);
        assertNotNull(root.left);
        assertNotNull(root.right);
        assertEquals(10, root.left.value);
        assertEquals(30, root.right.value);
    }
}


