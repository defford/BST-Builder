package com.example.bstdb.dto;

import java.util.List;

public class TreeDto {
    private List<TreeNodeDto> nodes;
    private List<TreeEdgeDto> edges;
    private TreeLayoutDto layout;

    public List<TreeNodeDto> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeNodeDto> nodes) {
        this.nodes = nodes;
    }

    public List<TreeEdgeDto> getEdges() {
        return edges;
    }

    public void setEdges(List<TreeEdgeDto> edges) {
        this.edges = edges;
    }

    public TreeLayoutDto getLayout() {
        return layout;
    }

    public void setLayout(TreeLayoutDto layout) {
        this.layout = layout;
    }
}


