package com.example.jobcreator.utils;

import com.example.jobcreator.model.EncoderData;
import com.example.jobcreator.model.Node;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


@NoArgsConstructor
public class HuffmanCoder {

    HashMap<Character,String> encoder;
    HashMap<String,Character> decoder;
    Node root;

    public HuffmanCoder(String feeder) throws Exception {
        // Count frequency
        HashMap<Character, Integer> fmap = new HashMap<>();
        for (int i = 0; i < feeder.length(); i++) {
            char cc = feeder.charAt(i);
            fmap.put(cc, fmap.getOrDefault(cc, 0) + 1);
        }

        PriorityQueue<Node> minHeap = new PriorityQueue<>();

        // Put the base characters in minheap in ascending order
        for (Map.Entry<Character, Integer> mp : fmap.entrySet()) {
            Node node = new Node(mp.getKey(), mp.getValue());
            minHeap.add(node);
        }

        while (minHeap.size() > 1) {
            Node first = minHeap.poll();
            Node second = minHeap.poll();

            Node newNode = new Node('\0', first.getCost() + second.getCost());
            newNode.setLeft(first);
            newNode.setRight(second);

            minHeap.add(newNode);
        }

        // Take the root node
        root = minHeap.poll();
        this.encoder = new HashMap<>();
        this.decoder = new HashMap<>();
        this.initEncoderDecode(root, "");
    }

    private void initEncoderDecode(Node node, String osf) {
        if (node == null) return;

        if (node.getLeft() == null && node.getRight() == null) {
            this.encoder.put(node.getData(), osf);
            this.decoder.put(osf, node.getData());
        }

        initEncoderDecode(node.getLeft(), osf + "0");
        initEncoderDecode(node.getRight(), osf + "1");
    }

    public EncoderData encode(String src) {
        BitSet bitSet = new BitSet();

        StringBuilder encodedString = new StringBuilder();
        for (char c : src.toCharArray()) {
            encodedString.append(encoder.get(c));
        }

        for (int i = 0; i < encodedString.length(); i++) {
            if (encodedString.charAt(i) == '1') {
                bitSet.set(i);
            }
        }

        System.out.println("length " + bitSet.toByteArray().length);

        return new EncoderData(bitSet.toByteArray(),decoder);
    }

    public String decode(BitSet set, Map<String,Character> decoder1) {
        StringBuilder decodedString = new StringBuilder();

        StringBuilder currentCode = new StringBuilder();
        for (int i = 0; i < set.length(); i++) {
            currentCode.append(set.get(i) ? '1' : '0');
            if (decoder1.containsKey(currentCode.toString())) {
                decodedString.append(decoder1.get(currentCode.toString()));
                currentCode = new StringBuilder();
            }
        }
        return decodedString.toString();
    }
}
