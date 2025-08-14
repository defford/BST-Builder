package com.example.bstdb.controller;

import com.example.bstdb.dto.ProcessNumbersRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TreeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void processNumbers_and_fetchPreviousTrees() throws Exception {
        ProcessNumbersRequest req = new ProcessNumbersRequest();
        req.setNumbers("10, 5, 20");
        String body = objectMapper.writeValueAsString(req);

        mockMvc.perform(post("/api/process-numbers")
                        .param("balanced", "false")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.inputNumbers").value("10, 5, 20"))
                .andExpect(jsonPath("$.tree.nodes").isArray())
                .andExpect(jsonPath("$.tree.layout.hSpacing").value(80));

        mockMvc.perform(get("/api/previous-trees").param("limit", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].tree").exists());
    }
}


