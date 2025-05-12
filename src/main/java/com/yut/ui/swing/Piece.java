package com.yut.ui.swing;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Piece extends JComponent {
    private int playerID;
    private Color color;
    private int x;
    private int y;
    public static final int radius = 10;
    private int cnt = 1;

    private BufferedImage cachedImage; // 💾 캐싱된 이미지

    public Piece(int x, int y, Color color, int cnt) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.cnt = cnt;

        setSize(radius * 2, radius * 2); // 원 크기
        setOpaque(false);

        generateImage(); // 한 번만 그림
        setLocation(x, y); // 위치 설정
    }

    private void generateImage() {
        int size = radius * 2;
        cachedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = cachedImage.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(1.5f));
        g2.fillOval(0, 0, size, size);
        g2.setColor(Color.DARK_GRAY);
        g2.drawOval(0, 0, size, size);

        // 텍스트
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 14));
        String text = String.valueOf(cnt);
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int textX = (size - textWidth) / 2;
        int textY = (size + textHeight) / 2 - 2;
        g2.drawString(text, textX, textY);

        g2.dispose();
    }

    // 🎯 위치 변경 가능하게
    public void setCoords(int x, int y) {
        this.x = x;
        this.y = y;
        setLocation(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(cachedImage, 0, 0, this); // draw만 함
    }
}
