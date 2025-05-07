import java.util.HashMap;

public class SquareBoard extends Board {
    SquareBoard(){
        super();
        nodes = new HashMap<>();

        //put nodes to a node list
        for(int i = 1; i < 5; i++){
            nodes.put(i*100, new Node(i*100));
            int b = i + 1;
            if(b == 5){
                b = 1;
            }
            for(int j = 1; j < 5; j++){
                nodes.put(i*100+b*10+j, new Node(i*100+b*10+j));
            }
        }

        nodes.put(251, new Node(251));
        nodes.put(252, new Node(252));
        nodes.put(351, new Node(351));
        nodes.put(352, new Node(352));

        nodes.put(500, new Node(500));
        nodes.put(511, new Node(511));
        nodes.put(512, new Node(512));
        nodes.put(541, new Node(541));
        nodes.put(542, new Node(542));

        //link each other
        waitingNode.nextNodes = new Node[]{nodes.get(121), nodes.get(121), nodes.get(121), nodes.get(121), nodes.get(121)};

        nodes.get(100).nextNodes = new Node[]{endNode, endNode, endNode, endNode, endNode};
        nodes.get(200).nextNodes = new Node[]{nodes.get(231), nodes.get(251), null, null, null};
        nodes.get(300).nextNodes = new Node[]{null, nodes.get(341), nodes.get(351), null, null};
        nodes.get(400).nextNodes = new Node[]{null, nodes.get(411), nodes.get(411), nodes.get(411), nodes.get(411)};
        nodes.get(500).nextNodes = new Node[]{null, nodes.get(541), nodes.get(511), null, nodes.get(511)};

        for(int a = 1; a < 5; a++){
            int b = a+1;
            if(b == 5){
                b = 1;
            }
            for(int c = 1; c < 5; c++){
                Node[] nextNodes = new Node[5];
                for(int i = 0; i < 5; i++){
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

        nodes.get(251).nextNodes = new Node[]{nodes.get(252), nodes.get(252), nodes.get(252), nodes.get(252), nodes.get(252)};
        nodes.get(252).nextNodes = new Node[]{nodes.get(500), nodes.get(500), nodes.get(500), nodes.get(500), nodes.get(500)};
        nodes.get(351).nextNodes = new Node[]{nodes.get(352), nodes.get(352), nodes.get(352), nodes.get(352), nodes.get(352)};
        nodes.get(352).nextNodes = new Node[]{nodes.get(500), nodes.get(500), nodes.get(500), nodes.get(500), nodes.get(500)};
        nodes.get(541).nextNodes = new Node[]{nodes.get(542), nodes.get(542), nodes.get(542), nodes.get(542), nodes.get(542)};
        nodes.get(542).nextNodes = new Node[]{nodes.get(400), nodes.get(400), nodes.get(400), nodes.get(400), nodes.get(400)};
        nodes.get(511).nextNodes = new Node[]{nodes.get(512), nodes.get(512), nodes.get(512), nodes.get(512), nodes.get(512)};
        nodes.get(512).nextNodes = new Node[]{nodes.get(100), nodes.get(100), nodes.get(100), nodes.get(100), nodes.get(100)};
        endNode.nextNodes = new Node[]{endNode, endNode, endNode, endNode, endNode};
        waitingNode.beforeNode = waitingNode;


        //back-do link
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
    }
}
