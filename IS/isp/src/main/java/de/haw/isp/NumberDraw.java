package de.haw.isp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.JPanel;

/**
 * Intelligente Systeme Praktikum, Aufgabe 2
 * Thema: Lernen im künstlichem neuronalem Netz (KNN)
 * <p>
 * Ausgabe der vom neuronalen Netz berechneten Zahl
 *
 * @author Adrian Helberg
 * @author Rodrigo Ehlers
 */
public class NumberDraw extends JPanel {
    private final static int scale = 10;
    private final static int pxCount = 28;
    private final static int width = pxCount * scale;
    private final static int height = pxCount * scale;
    private BufferedImage img;

    NumberDraw() {
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    void paintNumber(int number) {
        Graphics g = img.getGraphics();

        int fontSize = 256;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        g.setColor(Color.black);

        String stringNumber = String.valueOf(number);
        FontMetrics fontMetrics = g.getFontMetrics(g.getFont());

        int x = (width - fontMetrics.stringWidth(stringNumber)) / 2;
        int y = ((height - fontMetrics.getHeight()) / 2) + fontMetrics.getAscent();

        g.drawString(stringNumber, x, y);

        repaint();
    }

    void clear() {
        Graphics g = img.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, img.getWidth(null), img.getHeight(null));
        g.dispose();
        repaint();
    }
}
