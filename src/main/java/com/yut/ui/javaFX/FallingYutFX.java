package com.yut.ui.javaFX;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.util.Duration;

public class FallingYutFX {
    private Node node;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private double rotationStartDeg;
    private double rotationDeg;
    private double durationSec;

    FallingYutFX(Node node, int startX, int startY, int endX, int endY, double rotationStartDeg,
                 double rotationDeg, double durationSec) {
        this.node = node;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.rotationStartDeg = rotationStartDeg;
        this.rotationDeg = rotationDeg;
        this.durationSec = durationSec;

        node.setTranslateX(startX);
        node.setTranslateY(startY);

        playAnimation();
    }

    private void playAnimation() {
        TranslateTransition translate = new TranslateTransition(Duration.seconds(durationSec), node);
        translate.setByX(endX);
        translate.setByY(endY);

        RotateTransition rotate = new RotateTransition(Duration.seconds(durationSec), node);
        rotate.setFromAngle(rotationStartDeg);
        rotate.setToAngle(rotationStartDeg + rotationDeg);

        translate.setInterpolator(Interpolator.LINEAR);
        rotate.setInterpolator(Interpolator.LINEAR);

        ParallelTransition animation = new ParallelTransition(node, translate, rotate);

        animation.setOnFinished(e -> {
            node.setTranslateX(startX);
            node.setTranslateY(startY);
            node.setRotate(rotationStartDeg);
            playAnimation();
            System.out.println("Resetting Yut...");
            System.out.println("Before reset: X = " + node.getTranslateX() + ", Y = " + node.getTranslateY());

        });

        animation.play();
    }

    public Node getNode() {
        return node;
    }
}
