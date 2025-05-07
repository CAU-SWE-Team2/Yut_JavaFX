package com.yut.model;

import java.util.HashMap;

public class PentagonBoard extends Board {
    PentagonBoard(){
        super();
        nodes = new HashMap<>();

        for(int i = 1; i < 6; i++){
            nodes.put(i*100, new Node(i*100));
            int b = i + 1;
            if(b == 6){
                b = 1;
            }
            for(int j = 1; j < 5; j++){
                nodes.put(i*100+b*10+j, new Node(i*100+b*10+j));
            }
        }

        nodes.put(261, new Node(261));
        nodes.put(262, new Node(262));
        nodes.put(361, new Node(361));
        nodes.put(362, new Node(362));
        nodes.put(461, new Node(461));
        nodes.put(462, new Node(462));

        nodes.put(600, new Node(600));
        nodes.put(611, new Node(611));
        nodes.put(612, new Node(612));
        nodes.put(651, new Node(651));
        nodes.put(652, new Node(652));

        waitingNode.nextNodes = new Node[]{nodes.get(121), nodes.get(121), nodes.get(121), nodes.get(121), nodes.get(121), nodes.get(121)};

        nodes.get(100).nextNodes = new Node[]{endNode, endNode, endNode, endNode, endNode, endNode};
        nodes.get(200).nextNodes = new Node[]{nodes.get(231), nodes.get(261), null, null, null, null};
        nodes.get(300).nextNodes = new Node[]{null, nodes.get(341), nodes.get(361), null, null, null};
        nodes.get(400).nextNodes = new Node[]{null, null, nodes.get(451), nodes.get(461), null, null};
        nodes.get(500).nextNodes = new Node[]{null, nodes.get(511), nodes.get(511), nodes.get(511), nodes.get(511), nodes.get(511)};
        nodes.get(600).nextNodes = new Node[]{null, nodes.get(651), nodes.get(651), nodes.get(651), null, nodes.get(611)};

        for(int a = 1; a < 6; a++){
            int b = a+1;
            if(b == 6){
                b = 1;
            }
            for(int c = 1; c < 5; c++){
                Node[] nextNodes = new Node[6];
                for(int i = 0; i < 6; i++){
                    if(c == 4){
                        nextNodes[i] = nodes.get(b*100);
                    }
                    else {
                        nextNodes[i] = nodes.get(a * 100 + b * 10 + c + 1);
                    }
                }
                nodes.get(a*100+b*10+c).nextNodes = nextNodes;
            }
        }

        nodes.get(261).nextNodes = new Node[]{nodes.get(262), nodes.get(262), nodes.get(262), nodes.get(262), nodes.get(262), nodes.get(262)};
        nodes.get(262).nextNodes = new Node[]{nodes.get(600), nodes.get(600), nodes.get(600), nodes.get(600), nodes.get(600), nodes.get(600)};
        nodes.get(361).nextNodes = new Node[]{nodes.get(362), nodes.get(362), nodes.get(362), nodes.get(362), nodes.get(362), nodes.get(362)};
        nodes.get(362).nextNodes = new Node[]{nodes.get(600), nodes.get(600), nodes.get(600), nodes.get(600), nodes.get(600), nodes.get(600)};
        nodes.get(461).nextNodes = new Node[]{nodes.get(462), nodes.get(462), nodes.get(462), nodes.get(462), nodes.get(462), nodes.get(462)};
        nodes.get(462).nextNodes = new Node[]{nodes.get(600), nodes.get(600), nodes.get(600), nodes.get(600), nodes.get(600), nodes.get(600)};
        nodes.get(651).nextNodes = new Node[]{nodes.get(652), nodes.get(652), nodes.get(652), nodes.get(652), nodes.get(652), nodes.get(652)};
        nodes.get(652).nextNodes = new Node[]{nodes.get(500), nodes.get(500), nodes.get(500), nodes.get(500), nodes.get(500), nodes.get(500)};
        nodes.get(611).nextNodes = new Node[]{nodes.get(612), nodes.get(612), nodes.get(612), nodes.get(612), nodes.get(612), nodes.get(612)};
        nodes.get(612).nextNodes = new Node[]{nodes.get(100), nodes.get(100), nodes.get(100), nodes.get(100), nodes.get(100), nodes.get(100)};
        endNode.nextNodes = new Node[]{endNode, endNode, endNode, endNode, endNode, endNode};
        waitingNode.beforeNode = waitingNode;
    }
}
