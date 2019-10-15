package de.haw.isp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

/**
 * Intelligente Systeme Praktikum
 * Aufgabe 2 - Lernen
 * Handschrifterkennung mittels neuronalem Netz und Deep Learning (mind. 1 Hidden Layer)
 */
public class App {

    private static StringBuilder sb = new StringBuilder();
    private static File logFile;

    public static void main(String[] args) {

        logFile = new File(
                Objects.requireNonNull(
                        App.class.getClassLoader().getResource("log.txt")
                ).getFile()
        );

        print("--------------------------------------------------");
        print("Intelligente Systeme Praktikum");
        print("Aufgabe 2 - Lernen");
        print("Handschrifterkennung mittels neuronalem Netzwerk");
        print("--------------------------------------------------");

        new Training(new NeuralNetwork("Netz#1", 0, 0, 0, 0));
    }

    static void print(String str) {
        sb.append(str);
        printAndReset();
    }

    static void print(String str, Class cls) {
        sb.append("[");
        sb.append(cls.getSimpleName());
        sb.append("] ");
        sb.append(str);
        printAndReset();
    }

    private static void printAndReset() {
        System.out.println(sb.toString());

        // Log
        try (FileWriter fileWriter = new FileWriter(logFile, true);) {
            fileWriter.write(sb.toString() + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }

        sb.setLength(0);
        sb = new StringBuilder();
    }
}
