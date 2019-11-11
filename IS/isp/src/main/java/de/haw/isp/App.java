package de.haw.isp;

import javax.swing.*;
import java.awt.*;

/**
 * Intelligente Systeme Praktikum, Aufgabe 2
 * Thema: Lernen im künstlichem neuronalem Netz (KNN)
 *
 * Aufgabe 2 - Lernen
 * Handschrifterkennung mittels neuronalem Netz und Deep Learning (mind. 1 Hidden Layer)
 * @author Adrian Helberg
 * @author Rodrigo Ehlers
 */
public class App {
    // Logging
    private static StringBuilder sb = new StringBuilder();
    // App entry point
    public static void main(String[] args) {
        print("------------------------------------------------", App.class);
        print("Intelligente Systeme Praktikum", App.class);
        print("Aufgabe 2 - Lernen", App.class);
        print("Handschrifterkennung mittels neuronalem Netzwerk", App.class);
        print("------------------------------------------------", App.class);
        // Setup neural network environment
        print("Erstelle Trainings- und Testumgebung", App.class);

        Environment environment = new Environment();

        // Setup GUI
        UserDraw userDraw = new UserDraw();
        userDraw.clear();
        NumberDraw numberDraw = new NumberDraw();
        numberDraw.clear();
        JFrame frame = new JFrame("Intelligente Systeme Praktikum - Aufgabe 2");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setMaximumSize(new Dimension(Integer.MAX_VALUE, btnSubmit.getMinimumSize().height));
        btnSubmit.addActionListener(e -> {
            numberDraw.clear();
            numberDraw.paintNumber(
                    // Trigger the trained neural network with user input data
                    environment.userTest(userDraw.getImageString())
            );
        });
        JButton btnClear = new JButton("Clear");
        btnClear.setMaximumSize(new Dimension(Integer.MAX_VALUE, btnClear.getMinimumSize().height));
        btnClear.addActionListener(e -> {
            userDraw.clear();
            numberDraw.clear();
        });
        buttonPanel.add(btnSubmit);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(btnClear);
        buttonPanel.add(Box.createVerticalGlue());
        frame.add(userDraw, BorderLayout.WEST);
        frame.add(numberDraw, BorderLayout.EAST);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Print function for output and logging purpose
     * @param str String to be printed/logged
     * @param cls Class name for identifying which object requests to print/log
     */
    static void print(String str, Class cls) {
        sb.append("[");
        sb.append(cls.getSimpleName());
        sb.append("] ");
        sb.append(str);
        printAndReset();
    }

    /**
     * Print
     */
    private static void printAndReset() {
        System.out.println(sb.toString());
        sb.setLength(0);
        sb = new StringBuilder();
    }
}
