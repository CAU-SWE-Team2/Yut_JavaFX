package com.yut.ui.swing;

import javax.swing.*;

public class ClickableNode extends JComponent {
    int x, y, radius;
    int nodeID;
    //what piece is currently on the node
    Piece OnNodePiece;
    //what preview is currently on the node
    boolean hasPreview;

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

    boolean containsInRange(int mx, int my) {
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

    public void setPreviewPiece(boolean hasPreview) {
        this.hasPreview = hasPreview;
    }

    //힌트가 있는 노드인지 = 1, 빈 노드인지 = 2, 말이 있는 노드인지 = 3;
    public int getState(){
        if (hasPreview)
            return 1;
        if (OnNodePiece == null)
            return 2;
        else
            return 3;
    }

    //어떤 말이 위에 있는지
    //어떤 힌트가 위에 있는지
    //위에꺼 두개 각각 변수 만들어서 CONSTRUCTOR말고 SETTER로 정의
}
