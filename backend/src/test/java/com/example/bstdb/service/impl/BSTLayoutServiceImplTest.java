package com.example.bstdb.service.impl;

import com.example.bstdb.dto.TreeDto;
import com.example.bstdb.service.model.BstNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BSTLayoutServiceImplTest {

    @Test
    void layout_assignsCoordinates_andEdges() {
        // Build a minimal tree:   2
        //                        / \
        //                       1   3
        BstNode root = new BstNode(2);
        root.left = new BstNode(1);
        root.right = new BstNode(3);

        BSTLayoutServiceImpl layout = new BSTLayoutServiceImpl();
        TreeDto dto = layout.layout(root, 80, 80);

        assertNotNull(dto);
        assertEquals(3, dto.getNodes().size());
        assertEquals(2, dto.getEdges().size());
        // All nodes should have positive coordinates with the chosen spacing margins
        assertTrue(dto.getNodes().stream().allMatch(n -> n.getX() > 0 && n.getY() > 0));
        assertEquals(80, dto.getLayout().getHSpacing());
        assertEquals(80, dto.getLayout().getVSpacing());
    }
}


