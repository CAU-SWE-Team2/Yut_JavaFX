package com.yut.ui.javaFX;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ClickableNodeFX extends Pane {
    private int x, y, radius;
    private int nodeID;

    private PieceFX onNodePiece;
    private boolean hasPreview;

    private Circle visualCircle;

    public ClickableNodeFX(int x, int y, int radius, int nodeID) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.nodeID = nodeID;

        setPrefSize(radius * 2, radius * 2);
        setLayoutX(x - radius);
        setLayoutY(y - radius);

        visualCircle = new Circle(radius, Color.TRANSPARENT);
        visualCircle.setStroke(Color.GRAY);
        visualCircle.setCenterX(radius);
        visualCircle.setCenterY(radius);
        getChildren().add(visualCircle);

        // Debug visuals
        /*
         * visualCircle.setFill(Color.rgb(255, 0, 0, 0.3)); // Semi-transparent red
         * visualCircle.setStroke(Color.BLUE); // Optional: outline for clarity
         * visualCircle.setStrokeWidth(1.5);
         * 
         * 
         */

        // Interaction
        // setOnMouseClicked(e -> {
        //     System.out.println("Clicked node ID: " + nodeID + " at (" + getNodeX() + ", " + getNodeY() + ")");
        // });
    }

    public int getNodeX() {
        return x;
    }

    public int getNodeY() {
        return y;
    }

    public boolean containsInRange(int mx, int my) {
        int dx = x - mx;
        int dy = y - my;
        return dx * dx + dy * dy <= radius * radius;
    }

    public PieceFX getOnNodePiece() {
        return onNodePiece;
    }

    public void setOnNodePiece(PieceFX piece) {
        this.onNodePiece = piece;
    }

    public void setPreviewPiece(boolean hasPreview) {
        this.hasPreview = hasPreview;
        // visualCircle.setStroke(hasPreview ? Color.RED : Color.GRAY);
    }

    public int getState() {
        if (hasPreview)
            return 1;
        if (onNodePiece == null)
            return 2;
        else
            return 3;
    }

    public int getNodeID() {
        return nodeID;
    }
}