package com.yut.ui.swing;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
import com.yut.ui.swing.ClickableNode;

public class BoardCanvas extends JToggleButton {
    private int boardType;
    private List<ClickableNode> clickableNodes = new ArrayList<>();
    private GameScreen gameScreen;
    private int startX;
    private int startY;

    public int getStartX(){
        return startX;
    }

    public int getStartY(){
        return startY;
    }

    public void setGameScreen (GameScreen gs){
        this.gameScreen = gs;
    }

    // default는 interactive하지 않음 -> GameScreen에서 interactive = true 하면 노드 클릭가능
    private boolean interactive = false;

    public BoardCanvas(int boardType) {
        this.boardType = boardType;
        setPreferredSize(new Dimension(250, 250));
        setFocusPainted(false);
        setContentAreaFilled(false);

<<<<<<< HEAD
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!interactive)
                    return; // disable node interaction in preview mode
                for (ClickableNode node : clickableNodes) {
                    if (node.contains(e.getX(), e.getY())) {
                        System.out.println("Clicked on node at (" + node.x + ", " + node.y + ")");
                        // TODO: Add game logic here (e.g., move piece)
                        break;
=======
        //size hard coded
        int w = 400;
        int h = 400;

        int padding = 20;
        int boardSize = Math.min(w, h) - 2 * padding;

        if (boardType == 4){
            int step = boardSize / 4;
            int cStep = boardSize / 5; // cardinal steps
            int dStep = boardSize / 6; // diagonal steps

            startX = padding;
            startY = padding;

            for (int i = 0; i < 5; i++){
                if (i == 0) {
                    clickableNodes.add(new ClickableNode(startX + 5 * cStep, startY + 5*cStep, 16, 100));
                }
                else {
                    clickableNodes.add(new ClickableNode(startX + 5 * cStep, startY + (5 - i) * cStep, 9, 120 + i));
                }
            }
            for (int i = 0; i < 5; i++){
                if (i == 0) {
                    clickableNodes.add(new ClickableNode(startX + 5 * cStep, startY, 16, 200));
                }
                else {
                    clickableNodes.add(new ClickableNode(startX + (5 - i)*cStep, startY, 9, 230 + i));
                }
            }
            for (int i = 0; i < 5; i++){
                if (i == 0) {
                    clickableNodes.add(new ClickableNode(startX, startY, 16, 300));
                }
                else {
                    clickableNodes.add(new ClickableNode(startX, startY + i*cStep, 9, 340 + i));
                }
            }
            for (int i = 0; i < 5; i++){
                if (i == 0) {
                    clickableNodes.add(new ClickableNode(startX, startY + 5*cStep, 16, 400));
                }
                else {
                    clickableNodes.add(new ClickableNode(startX + i*cStep, startY + 5*cStep, 9, 410 + i));
                }
            }

            // Draw diagonal nodes

            for (int row = 1; row <= 5; row++) {
                for (int col = 1; col <= 5; col++) {
                    boolean diagTLtoC = (row == col) && (row < 3);
                    boolean diagTRtoC = (row + col == 6) && (row < 3);
                    boolean diagCtoBR = (row == col) && (row > 3);
                    boolean diagCtoBL = (row + col == 6) && (row > 3);

                    if (diagTLtoC) {
                        clickableNodes.add(new ClickableNode(startX + col*dStep, startY + row*dStep, 9, 350 + row));
                    }
                    else if (diagTRtoC){
                        clickableNodes.add(new ClickableNode(startX + col*dStep, startY + row*dStep, 9, 250 + row));
                    }
                    else if (diagCtoBR){
                        clickableNodes.add(new ClickableNode(startX + col*dStep, startY + row*dStep, 9, 510 + row - 3));
                    }
                    else if (diagCtoBL){
                        clickableNodes.add(new ClickableNode(startX + col*dStep, startY + row*dStep, 9, 540 + row - 3));
>>>>>>> feature-controller
                    }
                }
            }

            // Draw central circle bigger
            clickableNodes.add(new ClickableNode(startX + 3 * dStep, startY + 3 * dStep, 16, 500));
        }

        else if (boardType == 5) {
            int cx = w / 2;
            int cy = h / 2 + padding / 2;
            int r = Math.min(w, h) / 2 - padding; // radius from center to vertex

            // Calculate 5 pentagon points
            int[] xPoints = new int[5];
            int[] yPoints = new int[5];
            for (int i = 0; i < 5; i++) {
                double angle = Math.toRadians(-90 - 72 - i * 72); // start at top (-90°)
                xPoints[i] = cx + (int) (r * Math.cos(angle));
                yPoints[i] = cy + (int) (r * Math.sin(angle));
            }

            //define start point in the scheme of BoardCanvas
            startX = xPoints[0];
            startY = yPoints[0];

            // Draw circles at corners
            for (int i = 0; i < 5; i++) {
                clickableNodes.add(new ClickableNode(xPoints[i], yPoints[i], 16, 100 * (i + 1)));
            }


            // values are all set to be positive
            int intervalTopX = (xPoints[4] - xPoints[0]) / 5;
            int intervalTopY = (yPoints[4] - yPoints[0]) / 5;
            int intervalMidX = (xPoints[3] - xPoints[2]) / 5;
            int intervalMidY = (yPoints[3] - yPoints[2]) / 5;
            int intervalBotX = (xPoints[2] - xPoints[1]) / 5;

            for (int i = 1; i < 5; i++) {
                clickableNodes.add(new ClickableNode(xPoints[0] + intervalMidX * i, yPoints[0] - intervalMidY * i, 9, 120 + i));
                clickableNodes.add(new ClickableNode(xPoints[1] + intervalBotX * i, yPoints[1], 9, 230 + i));
                clickableNodes.add(new ClickableNode(xPoints[2] + intervalMidX * i, yPoints[2] + intervalMidY * i, 9, 340 + i));
                clickableNodes.add(new ClickableNode(xPoints[3] - intervalTopX * i, yPoints[3] + intervalTopY * i, 9, 450 + i));
                clickableNodes.add(new ClickableNode(xPoints[4] - intervalTopX * i, yPoints[4] - intervalTopY * i, 9, 510 + i));
            }

            int intervalCTopY = (yPoints[4] - cx) / 3;
            int intervalCMidX = (xPoints[3] - cx) / 3;
            int intervalCMidY = (yPoints[3] - cy) / 3;
            int intervalCBotX = (cx - xPoints[1]) / 3;
            int intervalCBotY = (cy - yPoints[1]) / 3;

            for (int i = 1; i < 3; i++) {
                clickableNodes.add(new ClickableNode(cx - intervalCMidX * i, cy + intervalCMidY * i, 9, 610 + i));
                clickableNodes.add(new ClickableNode(xPoints[1] + intervalCBotX * i, yPoints[1] + intervalCBotY * i, 9, 260 + i));
                clickableNodes.add(new ClickableNode(xPoints[2] - intervalCBotX * i, yPoints[2] + intervalCBotY * i, 9, 360 + i));
                clickableNodes.add(new ClickableNode(xPoints[3] - intervalCMidX * i, yPoints[3] - intervalCMidY * i, 9, 460 + i));
                clickableNodes.add(new ClickableNode(cx, cx + intervalCTopY * i, 9, 650 + i));
            }

            // Draw center node
            clickableNodes.add(new ClickableNode(cx, cy, 16, 600));
        }
        else if (boardType == 6) {
            int cx = w / 2;
            int cy = h / 2;
            int r = Math.min(w, h) / 2 - padding; // radius from center to vertex

            // Calculate 5 pentagon points
            int[] xPoints = new int[6];
            int[] yPoints = new int[6];
            for (int i = 0; i < 6; i++) {
                double angle = Math.toRadians(-90 - 90 - i * 60); // start at top (-90°)
                xPoints[i] = cx + (int) (r * Math.cos(angle));
                yPoints[i] = cy + (int) (r * Math.sin(angle));
            }

            startX = xPoints[0];
            startY = yPoints[0];


            // Draw circles at corners
            for (int i = 0; i < 6; i++) {
                clickableNodes.add(new ClickableNode(xPoints[i], yPoints[i], 15, 100 * (i + 1)));
            }


            // values are all set to be positive
            int interval1X = (xPoints[3] - xPoints[2]) / 5;
            int interval1Y = (yPoints[3] - yPoints[2]) / 5;
            int interval2X = (xPoints[2] - xPoints[1]) / 5;

            for (int i = 0; i < 5; i++) {
                clickableNodes.add(new ClickableNode(xPoints[0] + interval1X * i, yPoints[0] - interval1Y * i, 9, 120 + i));
                clickableNodes.add(new ClickableNode(xPoints[1] + interval2X * i, yPoints[1], 9, 230 + i));
                clickableNodes.add(new ClickableNode(xPoints[2] + interval1X * i, yPoints[2] + interval1Y * i, 9, 340 + i));
                clickableNodes.add(new ClickableNode(xPoints[3] - interval1X * i, yPoints[3] + interval1Y * i, 9, 450 + i));
                clickableNodes.add(new ClickableNode(xPoints[4] - interval2X * i, yPoints[4], 9, 560 + i));
                clickableNodes.add(new ClickableNode(xPoints[5] - interval1X * i, yPoints[5] - interval1Y * i, 9, 610 + i));
            }

            int intervalC1X = (cx - xPoints[1]) / 3;
            int intervalC1Y = (cy - yPoints[1]) / 3;
            int intervalC2X = (cx - xPoints[0]) / 3;

            for (int i = 1; i < 3; i++) {
                clickableNodes.add(new ClickableNode(cx - intervalC2X * i, cy, 9, 710 + i));
                clickableNodes.add(new ClickableNode(xPoints[1] + intervalC1X * i, yPoints[1] + intervalC1Y * i, 9, 270 + i));
                clickableNodes.add(new ClickableNode(xPoints[2] - intervalC1X * i, yPoints[2] + intervalC1Y * i, 9, 370 + i));
                clickableNodes.add(new ClickableNode(xPoints[3] - intervalC2X * i, yPoints[3], 9, 470 + i));
                clickableNodes.add(new ClickableNode(xPoints[4] - intervalC1X * i, yPoints[4] - intervalC1Y * i, 9, 570 + i));
                clickableNodes.add(new ClickableNode(cx - intervalC1X * i, cy + intervalC1Y * i, 9, 760 + i));
            }

            // Draw center node
            clickableNodes.add(new ClickableNode(cx, cy, 15, 700));
        }

    }


    public void setInteractive(boolean interactive) {
        this.interactive = interactive;
    }

    public boolean getInteractive(){
        return interactive;
    }

    public List<ClickableNode> getClickableNodes() {
        return clickableNodes;
    }

    public int getBoardType() {
        return boardType;
    }

    public ClickableNode getNodeById(int id) {
        for (ClickableNode node : clickableNodes) {
            if (node.nodeID == id) return node;
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        int padding = 20;
        int boardSize = Math.min(w, h) - 2 * padding;
        if (boardType == 4) {

            int step = boardSize / 4;
            int cStep = boardSize / 5; // cardinal steps
            int dStep = boardSize / 6; // diagonal steps

            //this might not work
            startX = padding;
            startY = padding;


            // Draw outer square
            g2.setColor(Color.BLACK);
            g2.drawRect(startX, startY, boardSize, boardSize);

            // Draw diagonals
            g2.drawLine(startX, startY, startX + 4 * step, startY + 4 * step);
            g2.drawLine(startX, startY + 4 * step, startX + 4 * step, startY);

<<<<<<< HEAD
            // Draw edge nodes
            for (int row = 0; row <= 5; row++) {
                for (int col = 0; col <= 5; col++) {
                    boolean shouldDraw = row == 0 || row == 5 || col == 0 || col == 5;
                    boolean corner = (row == 0 || row == 5) && (col == 0 || col == 5);

                    if (shouldDraw) {
                        int cx = startX + col * cStep;
                        int cy = startY + row * cStep;
                        if (corner) {
                            drawCircle(g2, cx, cy, 16);
                            drawCircle(g2, cx, cy, 12);
                            clickableNodes.add(new ClickableNode(cx, cy, 16));
                        } else {
                            drawCircle(g2, cx, cy, 9);
                            clickableNodes.add(new ClickableNode(cx, cy, 9));
                        }
                    }
=======
            for (int i = 0; i < 5; i++){
                if (i == 0) {
                    drawCircle(g2, startX + 5 * cStep, startY + 5*cStep, 16);
                    drawCircle(g2, startX + 5 * cStep, startY + 5*cStep, 12);
                }
                else {
                    drawCircle(g2, startX + 5 * cStep, startY + (5 - i) * cStep, 9);
                }
            }
            for (int i = 0; i < 5; i++){
                if (i == 0) {
                    drawCircle(g2, startX + 5 * cStep, startY, 16);
                    drawCircle(g2, startX + 5 * cStep, startY, 12);
                }
                else {
                    drawCircle(g2, startX + (5 - i)*cStep, startY, 9);
                }
            }
            for (int i = 0; i < 5; i++){
                if (i == 0) {
                    drawCircle(g2, startX, startY, 16);
                    drawCircle(g2, startX, startY, 12);
                }
                else {
                    drawCircle(g2, startX, startY + i*cStep, 9);
                }
            }
            for (int i = 0; i < 5; i++){
                if (i == 0) {
                    drawCircle(g2, startX, startY + 5*cStep, 16);
                    drawCircle(g2, startX, startY + 5*cStep, 12);
                }
                else {
                    drawCircle(g2, startX + i*cStep, startY + 5*cStep, 9);
>>>>>>> feature-controller
                }
            }

            // Draw diagonal nodes

            for (int row = 1; row <= 5; row++) {
                for (int col = 1; col <= 5; col++) {
                    boolean diagTLtoC = (row == col) && (row < 3);
                    boolean diagTRtoC = (row + col == 6) && (row < 3);
                    boolean diagCtoBR = (row == col) && (row > 3);
                    boolean diagCtoBL = (row + col == 6) && (row > 3);

                    if (diagTLtoC) {
                        drawCircle(g2, startX + col*dStep, startY + row*dStep, 9);

                    }
                    else if (diagTRtoC){
                        drawCircle(g2, startX + col*dStep, startY + row*dStep, 9);
                    }
                    else if (diagCtoBR){
                        drawCircle(g2, startX + col*dStep, startY + row*dStep, 9);
                    }
                    else if (diagCtoBL){
                        drawCircle(g2, startX + col*dStep, startY + row*dStep, 9);
                    }
                }
            }

            // Draw central circle bigger
            drawCircle(g2, startX + 3 * dStep, startY + 3 * dStep, 16);
            drawCircle(g2, startX + 3 * dStep, startY + 3 * dStep, 12);

            // Draw "출발" in bottom-right node
            int textX = startX + 5 * cStep;
            int textY = startY + 5 * cStep;
            drawCenteredString(g2, "출발", textX, textY);
        }

        else if (boardType == 5) {
            int cx = w / 2;
            int cy = h / 2 + padding / 2;
            int r = Math.min(w, h) / 2 - padding; // radius from center to vertex

            // Calculate 5 pentagon points
            int[] xPoints = new int[5];
            int[] yPoints = new int[5];
            for (int i = 0; i < 5; i++) {
                double angle = Math.toRadians(-90 - 72 - i * 72); // start at top (-90°)
                xPoints[i] = cx + (int) (r * Math.cos(angle));
                yPoints[i] = cy + (int) (r * Math.sin(angle));
            }

            //define start point in the scheme of BoardCanvas
            startX = xPoints[0];
            startY = yPoints[0];

            // Draw outer pentagon
            g2.setColor(Color.BLACK);
            g2.drawPolygon(xPoints, yPoints, 5);

            // Draw lines from each corner to center
            for (int i = 0; i < 5; i++) {
                g2.drawLine(cx, cy, xPoints[i], yPoints[i]);
            }

            // Draw circles at corners
            for (int i = 0; i < 5; i++) {
                drawCircle(g2, xPoints[i], yPoints[i], 16);
                drawCircle(g2, xPoints[i], yPoints[i], 12);
            }


            // values are all set to be positive
            int intervalTopX = (xPoints[4] - xPoints[0]) / 5;
            int intervalTopY = (yPoints[4] - yPoints[0]) / 5;
            int intervalMidX = (xPoints[3] - xPoints[2]) / 5;
            int intervalMidY = (yPoints[3] - yPoints[2]) / 5;
            int intervalBotX = (xPoints[2] - xPoints[1]) / 5;

            for (int i = 1; i < 5; i++) {
                drawCircle(g2, xPoints[0] + intervalMidX * i, yPoints[0] - intervalMidY * i, 9);
                drawCircle(g2, xPoints[1] + intervalBotX * i, yPoints[1], 9);
                drawCircle(g2, xPoints[2] + intervalMidX * i, yPoints[2] + intervalMidY * i, 9);
                drawCircle(g2, xPoints[3] - intervalTopX * i, yPoints[3] + intervalTopY * i, 9);
                drawCircle(g2, xPoints[4] - intervalTopX * i, yPoints[4] - intervalTopY * i, 9);
            }

            int intervalCTopY = (yPoints[4] - cx) / 3;
            int intervalCMidX = (xPoints[3] - cx) / 3;
            int intervalCMidY = (yPoints[3] - cy) / 3;
            int intervalCBotX = (cx - xPoints[1]) / 3;
            int intervalCBotY = (cy - yPoints[1]) / 3;

            for (int i = 1; i < 3; i++) {
                drawCircle(g2, cx - intervalCMidX * i, cy + intervalCMidY * i, 9);
                drawCircle(g2, xPoints[1] + intervalCBotX * i, yPoints[1] + intervalCBotY * i, 9);
                drawCircle(g2, xPoints[2] - intervalCBotX * i, yPoints[2] + intervalCBotY * i, 9);
                drawCircle(g2, xPoints[3] - intervalCMidX * i, yPoints[3] - intervalCMidY * i, 9);
                drawCircle(g2, xPoints[4], yPoints[4] - intervalCTopY * i, 9);
            }

            // Draw center node
            drawCircle(g2, cx, cy, 16);
            drawCircle(g2, cx, cy, 12);

            // Optionally: Draw "출발" at bottom-right point
            int startIdx = 0; // You can change this to any corner (0~4)
            drawCenteredString(g2, "출발", xPoints[startIdx], yPoints[startIdx]);
        }

        else if (boardType == 6) {
            int cx = w / 2;
            int cy = h / 2;
            int r = Math.min(w, h) / 2 - padding; // radius from center to vertex

            // Calculate 5 pentagon points
            int[] xPoints = new int[6];
            int[] yPoints = new int[6];
            for (int i = 0; i < 6; i++) {
                double angle = Math.toRadians(-90 - 90 - i * 60); // start at top (-90°)
                xPoints[i] = cx + (int) (r * Math.cos(angle));
                yPoints[i] = cy + (int) (r * Math.sin(angle));
            }

            startX = xPoints[0];
            startY = yPoints[0];

            // Draw outer pentagon
            g2.setColor(Color.BLACK);
            g2.drawPolygon(xPoints, yPoints, 6);

            // Draw lines from each corner to center
            for (int i = 0; i < 6; i++) {
                g2.drawLine(cx, cy, xPoints[i], yPoints[i]);
            }


            // Draw circles at corners
            for (int i = 0; i < 6; i++) {
                drawCircle(g2, xPoints[i], yPoints[i], 15);
                drawCircle(g2, xPoints[i], yPoints[i], 12);
            }


            // values are all set to be positive
            int interval1X = (xPoints[3] - xPoints[2]) / 5;
            int interval1Y = (yPoints[3] - yPoints[2]) / 5;
            int interval2X = (xPoints[2] - xPoints[1]) / 5;

            for (int i = 0; i < 5; i++) {
                drawCircle(g2, xPoints[0] + interval1X * i, yPoints[0] - interval1Y * i, 9);
                drawCircle(g2, xPoints[1] + interval2X * i, yPoints[1], 9);
                drawCircle(g2, xPoints[2] + interval1X * i, yPoints[2] + interval1Y * i, 9);
                drawCircle(g2, xPoints[3] - interval1X * i, yPoints[3] + interval1Y * i, 9);
                drawCircle(g2, xPoints[4] - interval2X * i, yPoints[4], 9);
                drawCircle(g2, xPoints[5] - interval1X * i, yPoints[5] - interval1Y * i, 9);
            }

            int intervalC1X = (cx - xPoints[1]) / 3;
            int intervalC1Y = (cy - yPoints[1]) / 3;
            int intervalC2X = (cx - xPoints[0]) / 3;

            for (int i = 1; i < 3; i++) {
                drawCircle(g2, cx - intervalC2X * i, cy, 9);
                drawCircle(g2, xPoints[1] + intervalC1X * i, yPoints[1] + intervalC1Y * i, 9);
                drawCircle(g2, xPoints[2] - intervalC1X * i, yPoints[2] + intervalC1Y * i, 9);
                drawCircle(g2, xPoints[3] - intervalC2X * i, yPoints[3], 9);
                drawCircle(g2, xPoints[4] - intervalC1X * i, yPoints[4] - intervalC1Y * i, 9);
                drawCircle(g2, cx - intervalC1X * i, cy + intervalC1Y * i, 9);
<<<<<<< HEAD
                clickableNodes.add(new ClickableNode(cx - intervalC1X * i, cy + intervalC1Y * i, 9));
                drawCircle(g2, cx + intervalC1X * i, cy + intervalC1Y * i, 9);
                clickableNodes.add(new ClickableNode(cx + intervalC1X * i, cy + intervalC1Y * i, 9));
                drawCircle(g2, cx + intervalC2X * i, cy, 9);
                clickableNodes.add(new ClickableNode(cx + intervalC2X * i, cy, 9));
                drawCircle(g2, cx + intervalC1X * i, cy - intervalC1Y * i, 9);
                clickableNodes.add(new ClickableNode(cx + intervalC1X * i, cy - intervalC1Y * i, 9));
                drawCircle(g2, cx - intervalC1X * i, cy - intervalC1Y * i, 9);
                clickableNodes.add(new ClickableNode(cx - intervalC1X * i, cy - intervalC1Y * i, 9));
=======
>>>>>>> feature-controller
            }

            // Draw center node
            drawCircle(g2, cx, cy, 15);
            drawCircle(g2, cx, cy, 12);

            // Optionally: Draw "출발" at bottom-right point
            int startIdx = 0; // You can change this to any corner (0~4)
            drawCenteredString(g2, "출발", xPoints[startIdx], yPoints[startIdx]);
        }

    }

    private void drawCircle(Graphics2D g2, int x, int y, int r) {
        g2.setColor(Color.WHITE); // Fill color
        g2.fillOval(x - r, y - r, 2 * r, 2 * r);

        g2.setColor(Color.BLACK); // Outline
        g2.drawOval(x - r, y - r, 2 * r, 2 * r);
    }

    private void drawCenteredString(Graphics2D g2, String text, int x, int y) {
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int textX = x - textWidth / 2;
        int textY = y + textHeight / 2 - 2;
        g2.drawString(text, textX, textY);
    }

    public void setSelectedStyle(boolean selected) {
        if (selected) {
            setBorder(BorderFactory.createLineBorder(new Color(60, 120, 200), 3, true));
            setBackground(new Color(240, 245, 255));
            setOpaque(true);
        } else {
            setBorder(BorderFactory.createEmptyBorder());
            setBackground(null);
            setOpaque(false);
        }
    }

}
