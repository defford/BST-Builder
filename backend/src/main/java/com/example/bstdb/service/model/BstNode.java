package com.example.bstdb.service.model;

public class BstNode {
    public int value;
    public BstNode left;
    public BstNode right;
    public int height = 1;

    public BstNode(int value) {
        this.value = value;
    }
}


