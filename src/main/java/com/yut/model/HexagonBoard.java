package com.yut.model;

import java.util.HashMap;

public class HexagonBoard extends Board {
    HexagonBoard(){
        super();
        nodes = new HashMap<>();
        
        for(int i = 1; i < 7; i++){
            nodes.put(i*100, new Node(i*100));
            int b = i + 1;
            if(b == 7){
                b = 1;
            }
            for(int j = 1; j < 5; j++){
                nodes.put(i*100+b*10+j, new Node(i*100+b*10+j));
            }
        }

        nodes.put(271, new Node(271));
        nodes.put(272, new Node(272));
        nodes.put(371, new Node(371));
        nodes.put(372, new Node(372));
        nodes.put(471, new Node(471));
        nodes.put(472, new Node(472));
        nodes.put(571, new Node(571));
        nodes.put(572, new Node(572));

        nodes.put(700, new Node(700));
        nodes.put(711, new Node(711));
        nodes.put(712, new Node(712));
        nodes.put(761, new Node(761));
        nodes.put(762, new Node(762));

        waitingNode.nextNodes = new Node[]{nodes.get(121), nodes.get(121), nodes.get(121), nodes.get(121), nodes.get(121), nodes.get(121), nodes.get(121)};

        nodes.get(100).nextNodes = new Node[]{endNode, endNode, endNode, endNode, endNode, endNode, endNode};
        nodes.get(200).nextNodes = new Node[]{nodes.get(231), nodes.get(271), null, null, null, null, null};
        nodes.get(300).nextNodes = new Node[]{null, nodes.get(341), nodes.get(371), null, null, null, null};
        nodes.get(400).nextNodes = new Node[]{null, null, nodes.get(451), nodes.get(471), null, null, null};
        nodes.get(500).nextNodes = new Node[]{null, null, null, nodes.get(561), nodes.get(571), null, null};
        nodes.get(600).nextNodes = new Node[]{null, nodes.get(611), nodes.get(611), nodes.get(611), nodes.get(611), nodes.get(611), nodes.get(611)};
        nodes.get(700).nextNodes = new Node[]{null, nodes.get(761), nodes.get(761), nodes.get(761), nodes.get(761), null, nodes.get(711)};

        for(int a = 1; a < 7; a++){
            int b = a+1;
            if(b == 7){
                b = 1;
            }
            for(int c = 1; c < 5; c++){
                Node[] nextNodes = new Node[7];
                for(int i = 0; i < 7; i++){
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

        nodes.get(271).nextNodes = new Node[]{nodes.get(272), nodes.get(272), nodes.get(272), nodes.get(272), nodes.get(272), nodes.get(272), nodes.get(272)};
        nodes.get(272).nextNodes = new Node[]{nodes.get(700), nodes.get(700), nodes.get(700), nodes.get(700), nodes.get(700), nodes.get(700), nodes.get(700)};
        nodes.get(371).nextNodes = new Node[]{nodes.get(372), nodes.get(372), nodes.get(372), nodes.get(372), nodes.get(372), nodes.get(372), nodes.get(372)};
        nodes.get(372).nextNodes = new Node[]{nodes.get(700), nodes.get(700), nodes.get(700), nodes.get(700), nodes.get(700), nodes.get(700), nodes.get(700)};
        nodes.get(471).nextNodes = new Node[]{nodes.get(472), nodes.get(472), nodes.get(472), nodes.get(472), nodes.get(472), nodes.get(472), nodes.get(472)};
        nodes.get(472).nextNodes = new Node[]{nodes.get(700), nodes.get(700), nodes.get(700), nodes.get(700), nodes.get(700), nodes.get(700), nodes.get(700)};
        nodes.get(571).nextNodes = new Node[]{nodes.get(572), nodes.get(572), nodes.get(572), nodes.get(572), nodes.get(572), nodes.get(572), nodes.get(572)};
        nodes.get(572).nextNodes = new Node[]{nodes.get(700), nodes.get(700), nodes.get(700), nodes.get(700), nodes.get(700), nodes.get(700), nodes.get(700)};
        nodes.get(761).nextNodes = new Node[]{nodes.get(762), nodes.get(762), nodes.get(762), nodes.get(762), nodes.get(762), nodes.get(762), nodes.get(762)};
        nodes.get(762).nextNodes = new Node[]{nodes.get(600), nodes.get(600), nodes.get(600), nodes.get(600), nodes.get(600), nodes.get(600), nodes.get(600)};
        nodes.get(711).nextNodes = new Node[]{nodes.get(712), nodes.get(712), nodes.get(712), nodes.get(712), nodes.get(712), nodes.get(712), nodes.get(712)};
        nodes.get(712).nextNodes = new Node[]{nodes.get(100), nodes.get(100), nodes.get(100), nodes.get(100), nodes.get(100), nodes.get(100), nodes.get(100)};
        endNode.nextNodes = new Node[]{endNode, endNode, endNode, endNode, endNode, endNode, endNode};
        waitingNode.beforeNode = waitingNode;

        for(int i: nodes.keySet()){
            int id = nodes.get(i).id;
            if(id != 500) {
                if ((id - 1) % 10 == 0) {
                    int a = id / 100;
                    nodes.get(i).beforeNode = nodes.get(a * 100);
                }
                else if(id % 100 == 0){
                    int b = id / 100;
                    nodes.get(i).beforeNode = nodes.get((b-1)*100+b*10+4);
                }
                else {
                    nodes.get(i).beforeNode = nodes.get(id - 1);
                }
            }
            else{
                nodes.get(i).beforeNode = nodes.get(352);
            }
        }
        nodes.get(121).beforeNode = waitingNode;
    }
}
