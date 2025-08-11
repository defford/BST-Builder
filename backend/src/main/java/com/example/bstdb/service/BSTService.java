package com.example.bstdb.service;

import com.example.bstdb.service.model.BstNode;

import java.util.List;

public interface BSTService {
    List<Integer> parseNumbers(String input);
    BstNode buildBst(List<Integer> numbers);
    BstNode buildAvl(List<Integer> numbers);
}


