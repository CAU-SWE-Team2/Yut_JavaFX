package com.yut.ui.swing;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;


public class Piece extends JComponent {
    private int playerID;
    private Color color;
    private int x;
    private int y;
    public static final int radius = 10;
    private int count = 1; //by default is 1


    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setCoords(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getPlayerID(){
        return playerID;
    }

    public int getRadius(){
        return radius;
    }

    //for preview

    //something happens when piece is clicked, to be defined
    public Piece(int x, int  y, Color color, int count) {
        this.color = color;
        setSize(radius*2, radius*2); // 말의 크기
        setOpaque(false);
        this.x = x;
        this.y = y;
        this.count = count;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(1.5f));
        g2.fillOval(0, 0, getWidth(), getHeight());
        g2.setColor(Color.DARK_GRAY);
        g2.drawOval(0, 0, getWidth(), getHeight());

        // Draw the count number
        g2.setColor(Color.BLACK); // Text color
        g2.setFont(new Font("SansSerif", Font.BOLD, 14)); // Font and size

        String text = String.valueOf(count); // Convert count to string
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();

        // Center the text inside the circle
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2 - 2;

        g2.drawString(text, x, y);
    }
}
