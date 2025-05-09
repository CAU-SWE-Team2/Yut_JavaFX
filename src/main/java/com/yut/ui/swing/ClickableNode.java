package com.yut.ui.swing;

public class ClickableNode {
    int x, y, radius;
    int nodeID;
    //what piece is currently on the node
    Piece OnNodePiece;
    //what preview is currently on the node
    Piece PreviewPiece;
    boolean selected;

    ClickableNode(int x, int y, int radius, int nodeID) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.nodeID = nodeID;
    }

    public int getNodeX(){
        return x;
    }

    public int getNodeY(){
        return y;
    }

    boolean contains(int mx, int my) {
        int dx = x - mx;
        int dy = y - my;
        return dx * dx + dy * dy <= radius * radius;
    }

    public Piece getOnNodePiece() {
        return OnNodePiece;
    }

    public void setOnNodePiece(Piece OnNodePiece) {
        this.OnNodePiece = OnNodePiece;
    }

    public void setPreviewPiece(Piece PreviewPiece) {
        this.PreviewPiece = PreviewPiece;
    }

    //빈 노드인지 = 1, 선택된 말이 있는 노드인지 = 2, 말이 있는 노드인지 = 3;
    public int getState(){
        if (OnNodePiece == null)
            return 1;
        else{
            if (selected)
                return 2;
            else
                return 3;
        }
    }

    //어떤 말이 위에 있는지
    //어떤 힌트가 위에 있는지
    //위에꺼 두개 각각 변수 만들어서 CONSTRUCTOR말고 SETTER로 정의
}
