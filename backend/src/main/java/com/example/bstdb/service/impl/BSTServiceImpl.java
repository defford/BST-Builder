package com.example.bstdb.service.impl;

import com.example.bstdb.service.BSTService;
import com.example.bstdb.service.model.BstNode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BSTServiceImpl implements BSTService {

    @Override
    public List<Integer> parseNumbers(String input) {
        if (input == null) {
            throw new IllegalArgumentException("numbers is required");
        }
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("numbers is empty");
        }
        String[] tokens = trimmed.split("[^\\d-]+");
        List<Integer> numbers = new ArrayList<>();
        for (String token : tokens) {
            if (token == null || token.isEmpty()) {
                continue;
            }
            try {
                numbers.add(Integer.parseInt(token));
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Invalid integer: " + token);
            }
        }
        if (numbers.isEmpty()) {
            throw new IllegalArgumentException("no valid integers found");
        }
        return numbers;
    }

    @Override
    public BstNode buildBst(List<Integer> numbers) {
        BstNode root = null;
        for (Integer value : numbers) {
            root = insertBst(root, value);
        }
        return root;
    }

    private BstNode insertBst(BstNode node, int value) {
        if (node == null) return new BstNode(value);
        if (value < node.value) {
            node.left = insertBst(node.left, value);
        } else { // duplicates go to the right
            node.right = insertBst(node.right, value);
        }
        return node;
    }

    @Override
    public BstNode buildAvl(List<Integer> numbers) {
        BstNode root = null;
        for (Integer value : numbers) {
            root = insertAvl(root, value);
        }
        return root;
    }

    private int height(BstNode n) { return n == null ? 0 : n.height; }

    private int balance(BstNode n) { return n == null ? 0 : height(n.left) - height(n.right); }

    private void updateHeight(BstNode n) { n.height = Math.max(height(n.left), height(n.right)) + 1; }

    private BstNode rotateRight(BstNode y) {
        BstNode x = y.left;
        BstNode T2 = x.right;
        x.right = y;
        y.left = T2;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private BstNode rotateLeft(BstNode x) {
        BstNode y = x.right;
        BstNode T2 = y.left;
        y.left = x;
        x.right = T2;
        updateHeight(x);
        updateHeight(y);
        return y;
    }

    private BstNode insertAvl(BstNode node, int value) {
        if (node == null) return new BstNode(value);
        if (value < node.value) {
            node.left = insertAvl(node.left, value);
        } else { // duplicates to the right
            node.right = insertAvl(node.right, value);
        }
        updateHeight(node);
        int bf = balance(node);
        // LL
        if (bf > 1 && value < node.left.value) {
            return rotateRight(node);
        }
        // RR
        if (bf < -1 && value >= node.right.value) {
            return rotateLeft(node);
        }
        // LR
        if (bf > 1 && value >= node.left.value) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        // RL
        if (bf < -1 && value < node.right.value) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

}


