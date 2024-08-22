/*
Question 7

Route Optimization for Delivery Service (Java GUI)
This scenario explores a Java GUI application using graph algorithms to optimize delivery routes for a courier
service.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class RouteOptimizerGUI {
    
    // Graph class to handle routing and distances
    static class Graph {
        private final Map<String, Map<String, Integer>> adjList = new HashMap<>();
        
        public void addEdge(String from, String to, int weight) {
            adjList.computeIfAbsent(from, k -> new HashMap<>()).put(to, weight);
            adjList.computeIfAbsent(to, k -> new HashMap<>()).put(from, weight); // Undirected graph
        }
        
        public Map<String, Integer> dijkstra(String start) {
            Map<String, Integer> distances = new HashMap<>();
            PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
            Map<String, String> previous = new HashMap<>();
            
            for (String node : adjList.keySet()) {
                distances.put(node, Integer.MAX_VALUE);
                previous.put(node, null);
            }
            distances.put(start, 0);
            pq.add(new AbstractMap.SimpleEntry<>(start, 0));
            
            while (!pq.isEmpty()) {
                String current = pq.poll().getKey();
                
                for (Map.Entry<String, Integer> neighbor : adjList.getOrDefault(current, Collections.emptyMap()).entrySet()) {
                    String neighborNode = neighbor.getKey();
                    int newDist = distances.get(current) + neighbor.getValue();
                    
                    if (newDist < distances.get(neighborNode)) {
                        distances.put(neighborNode, newDist);
                        previous.put(neighborNode, current);
                        pq.add(new AbstractMap.SimpleEntry<>(neighborNode, newDist));
                    }
                }
            }
            
            return distances;
        }
    }

    public static void main(String[] args) {
        // Create the Graph instance
        final Graph graph = new Graph();
        
        // Create the JFrame and its components
        JFrame frame = new JFrame("Delivery Route Optimizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        
        JPanel panel = new JPanel(new GridLayout(6, 2));
        
        JTextField fromField = new JTextField();
        JTextField toField = new JTextField();
        JTextField weightField = new JTextField();
        JButton addEdgeButton = new JButton("Add Route");
        
        JTextField startField = new JTextField();
        JButton findRouteButton = new JButton("Find Shortest Route");
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        
        panel.add(new JLabel("From:"));
        panel.add(fromField);
        panel.add(new JLabel("To:"));
        panel.add(toField);
        panel.add(new JLabel("Weight:"));
        panel.add(weightField);
        panel.add(addEdgeButton);
        
        panel.add(new JLabel("Start Node:"));
        panel.add(startField);
        panel.add(findRouteButton);
        
        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        
        // Add action listener for the "Add Route" button
        addEdgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String from = fromField.getText();
                String to = toField.getText();
                int weight;
                
                try {
                    weight = Integer.parseInt(weightField.getText());
                    graph.addEdge(from, to, weight);
                    fromField.setText("");
                    toField.setText("");
                    weightField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid weight.");
                }
            }
        });
        
        // Add action listener for the "Find Shortest Route" button
        findRouteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start = startField.getText();
                Map<String, Integer> distances = graph.dijkstra(start);
                StringBuilder result = new StringBuilder();
                
                for (Map.Entry<String, Integer> entry : distances.entrySet()) {
                    result.append("Distance from ").append(start).append(" to ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                }
                
                resultArea.setText(result.toString());
                startField.setText("");
            }
        });
        
        frame.setVisible(true);
    }
}
