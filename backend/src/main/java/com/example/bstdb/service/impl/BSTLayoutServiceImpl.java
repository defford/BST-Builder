package com.example.bstdb.service.impl;

import com.example.bstdb.dto.TreeDto;
import com.example.bstdb.dto.TreeEdgeDto;
import com.example.bstdb.dto.TreeLayoutDto;
import com.example.bstdb.dto.TreeNodeDto;
import com.example.bstdb.service.BSTLayoutService;
import com.example.bstdb.service.model.BstNode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BSTLayoutServiceImpl implements BSTLayoutService {

    // removed unused IndexedNode helper to simplify implementation

    @Override
    public TreeDto layout(BstNode root, int hSpacing, int vSpacing) {
        List<TreeNodeDto> nodes = new ArrayList<>();
        List<TreeEdgeDto> edges = new ArrayList<>();

        // In-order indexing for x; BFS/DFS to collect edges
        int[] index = new int[] {0};
        assignCoordinates(root, 0, hSpacing, vSpacing, index, nodes, edges, -1);

        TreeLayoutDto layout = new TreeLayoutDto();
        layout.setHSpacing(hSpacing);
        layout.setVSpacing(vSpacing);

        TreeDto dto = new TreeDto();
        dto.setNodes(nodes);
        dto.setEdges(edges);
        dto.setLayout(layout);
        return dto;
    }

    private long nodeIdSeq = 1;

    private long nextId() { return nodeIdSeq++; }

    private long assignCoordinates(BstNode node,
                                   int depth,
                                   int hSpacing,
                                   int vSpacing,
                                   int[] inorderIndex,
                                   List<TreeNodeDto> nodes,
                                   List<TreeEdgeDto> edges,
                                   long parentId) {
        if (node == null) return -1;
        long leftId = assignCoordinates(node.left, depth + 1, hSpacing, vSpacing, inorderIndex, nodes, edges, -1);
        long id = nextId();
        int x = inorderIndex[0] * hSpacing + hSpacing; // margin = hSpacing
        int y = depth * vSpacing + vSpacing; // margin = vSpacing
        inorderIndex[0]++;
        TreeNodeDto n = new TreeNodeDto();
        n.setId(id);
        n.setValue(node.value);
        n.setX(x);
        n.setY(y);
        nodes.add(n);

        long rightId = assignCoordinates(node.right, depth + 1, hSpacing, vSpacing, inorderIndex, nodes, edges, -1);

        if (leftId != -1) {
            TreeEdgeDto e = new TreeEdgeDto();
            e.setFrom(id);
            e.setTo(leftId);
            edges.add(e);
        }
        if (rightId != -1) {
            TreeEdgeDto e = new TreeEdgeDto();
            e.setFrom(id);
            e.setTo(rightId);
            edges.add(e);
        }
        return id;
    }
}


