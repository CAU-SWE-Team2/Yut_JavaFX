package com.yut.ui.swing;

public class ClickableNode {
    int x, y, radius;
    int nodeID;

    ClickableNode(int x, int y, int radius, int nodeID) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.nodeID = nodeID;
    }

    boolean contains(int mx, int my) {
        int dx = x - mx;
        int dy = y - my;
        return dx * dx + dy * dy <= radius * radius;
    }
}
