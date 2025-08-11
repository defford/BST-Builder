package com.example.bstdb.service;

import com.example.bstdb.dto.TreeDto;
import com.example.bstdb.service.model.BstNode;

public interface BSTLayoutService {
    TreeDto layout(BstNode root, int hSpacing, int vSpacing);
}


