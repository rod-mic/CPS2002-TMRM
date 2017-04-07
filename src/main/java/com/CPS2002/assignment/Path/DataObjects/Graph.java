package com.CPS2002.assignment.Path.DataObjects;

/**
 * Created by thoma on 07/04/2017.
 */

import com.CPS2002.assignment.Objects.Map;
import com.CPS2002.assignment.Objects.Position;

import java.util.Vector;

public class Graph {
    private char[][] map;
    private Vector<Node> listOfNodes;
    private Node rootNode;
    private Node goal;

    public Graph(Map m, Position position, Position treasure) {
        this.map = m.getMap();
        listOfNodes = new Vector<>();
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map.length; j++){
                if(map[i][j] != 'W') {
                    Node n = new Node(new Position(i, j), map[i][j]);
                    if (position.getX() == i && position.getY() == j) {
                        rootNode = n;
                        rootNode.setStart();
                    }
                    listOfNodes.add(n);
                }
            }
        }
        m.outputMap();
        System.out.println("\n\n");
        goThroughGraph();

        goal = new Node(treasure,map[treasure.getX()][treasure.getY()]);

        Vector<Node> start = new Vector<>();
        Vector<Node> checked = new Vector<>();

        start.add(rootNode);
        checked.add(rootNode);

        setNodes(start, checked);
        m.outputMap();
        System.out.println("\n\n");
        goThroughGraph();
    }

    public Node getRootNode() {
        return rootNode;
    }

    public Node getGoalNode() { return goal; }

    private void setNodes(Vector<Node> currentLevel, Vector<Node> checked) {
        Vector<Node> nextLevel = new Vector<>();
        for (int i = 0; i < currentLevel.size(); i++) {
            Node n = currentLevel.elementAt(i);
            Position position = n.getPosition();

            if (position.getX() - 1 >= 0) {
                Node n1 = getNodeByPosition(new Position(position.getX()-1,position.getY()));
                if (!checked.contains(n1) && !n1.isWaterTile()) {
                    n.addChildNode(n1);
                    n1.setParent(n);
                    nextLevel.add(n1);
                    checked.add(n1);
                }
            }
            if (position.getX() + 1 < map.length) {
                Node n2 = getNodeByPosition(new Position(position.getX()+1,position.getY()));
                if (!checked.contains(n2) && !n2.isWaterTile()) {
                    n.addChildNode(n2);
                    n2.setParent(n);
                    nextLevel.add(n2);
                    checked.add(n2);
                }
            }
            if ( position.getY() - 1 >= 0) {
                Node n3 = getNodeByPosition(new Position(position.getX(),position.getY()-1));
                if (!checked.contains(n3) && !n3.isWaterTile()) {
                    n.addChildNode(n3);
                    n3.setParent(n);
                    nextLevel.add(n3);
                    checked.add(n3);
                }
            }
            if (position.getY() + 1 < map.length) {
                Node n4 = getNodeByPosition(new Position(position.getX(),position.getY()+1));
                if (!checked.contains(n4) && !n4.isWaterTile()) {
                    n.addChildNode(n4);
                    n4.setParent(n);
                    nextLevel.add(n4);
                    checked.add(n4);
                }
            }
        }
        if(nextLevel.size() != 0) setNodes(nextLevel,checked);
    }

    public Node getNodeByPosition(Position p){
        Node n = new Node();
        boolean check = true;
        for(int i = 0; i < listOfNodes.size() && check; i++){
            Position p2 = listOfNodes.elementAt(i).getPosition();
            if(p2.getX() == p.getX() && p2.getY() == p.getY()){
                n = listOfNodes.elementAt(i);
                check = false;
            }
        }
        return n;
    }

    public void showPositions(){
        for(int i = 0; i < listOfNodes.size(); i++){
            System.out.println("x: "+listOfNodes.elementAt(i).getPosition().getX()+" | y: "+listOfNodes.elementAt(i).getPosition().getY());
        }
    }

    public void goThroughGraph(){
        for(int i = 0; i < listOfNodes.size(); i++){
            Position p = listOfNodes.elementAt(i).getPosition();
            if(listOfNodes.elementAt(i).getStart()) System.out.print("Start: ");
            System.out.println(p.getX()+","+p.getY()+" -> " + listOfNodes.elementAt(i).showChildPos());
        }

        System.out.println("\n\n\n\n");
    }
}