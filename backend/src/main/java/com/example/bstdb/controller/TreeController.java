package com.example.bstdb.controller;

import com.example.bstdb.dto.ProcessNumbersRequest;
import com.example.bstdb.dto.TreeDto;
import com.example.bstdb.dto.TreeRecordResponse;
import com.example.bstdb.entity.TreeRecord;
import com.example.bstdb.repo.TreeRecordRepo;
import com.example.bstdb.service.BSTLayoutService;
import com.example.bstdb.service.BSTService;
import com.example.bstdb.service.model.BstNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;

@RestController
@RequestMapping("/api")
public class TreeController {

    private final BSTService bstService;
    private final BSTLayoutService layoutService;
    private final TreeRecordRepo repo;
    private final ObjectMapper objectMapper;

    public TreeController(BSTService bstService, BSTLayoutService layoutService, TreeRecordRepo repo, ObjectMapper objectMapper) {
        this.bstService = bstService;
        this.layoutService = layoutService;
        this.repo = repo;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/process-numbers")
    public ResponseEntity<TreeRecordResponse> processNumbers(@RequestParam(name = "balanced", defaultValue = "false") boolean balanced,
                                                             @RequestBody ProcessNumbersRequest request) {
        try {
            List<Integer> numbers = bstService.parseNumbers(request.getNumbers());
            BstNode root = balanced ? bstService.buildAvl(numbers) : bstService.buildBst(numbers);
            TreeDto tree = layoutService.layout(root, 80, 80);
            String treeJson = objectMapper.writeValueAsString(tree);

            TreeRecord entity = new TreeRecord();
            entity.setInputNumbers(request.getNumbers());
            entity.setTreeJson(treeJson);
            entity.setBalanced(balanced);
            TreeRecord saved = repo.save(entity);

            TreeRecordResponse resp = new TreeRecordResponse();
            resp.setId(saved.getId());
            resp.setInputNumbers(saved.getInputNumbers());
            resp.setBalanced(saved.isBalanced());
            resp.setCreatedAt(saved.getCreatedAt());
            resp.setTree(tree);
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/previous-trees")
    public ResponseEntity<List<TreeRecordResponse>> previousTrees(@RequestParam(name = "limit", defaultValue = "50") int limit) {
        List<TreeRecord> all = repo.findAllByOrderByCreatedAtDesc(PageRequest.of(0, Math.max(1, limit))).getContent();
        List<TreeRecordResponse> result = all.stream()
                .map(tr -> {
                    TreeRecordResponse r = new TreeRecordResponse();
                    r.setId(tr.getId());
                    r.setInputNumbers(tr.getInputNumbers());
                    r.setBalanced(tr.isBalanced());
                    r.setCreatedAt(tr.getCreatedAt());
                    try {
                        r.setTree(objectMapper.readValue(tr.getTreeJson(), TreeDto.class));
                    } catch (Exception e) {
                        r.setTree(null);
                    }
                    return r;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}


