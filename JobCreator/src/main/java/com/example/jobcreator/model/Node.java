package com.example.jobcreator.model;

import lombok.Data;

@Data
public class Node implements Comparable<Node>{

    char data;
    int cost;
    Node left;
    Node right;

    public Node(char data, int cost){
        this.data = data;
        this.cost = cost;

        left = null;
        right = null;
    }

    @Override
    public int compareTo(Node o) {
        return this.cost - o.cost;
    }
}
