package com.example.bstdb.dto;

import java.time.Instant;

public class TreeRecordResponse {
    private Long id;
    private String inputNumbers;
    private boolean balanced;
    private Instant createdAt;
    private TreeDto tree;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInputNumbers() {
        return inputNumbers;
    }

    public void setInputNumbers(String inputNumbers) {
        this.inputNumbers = inputNumbers;
    }


    public boolean isBalanced() {
        return balanced;
    }

    public void setBalanced(boolean balanced) {
        this.balanced = balanced;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public TreeDto getTree() {
        return tree;
    }

    public void setTree(TreeDto tree) {
        this.tree = tree;
    }
}


