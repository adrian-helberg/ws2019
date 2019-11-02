package de.haw.isp;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Intelligente Systeme Praktikum, Aufgabe 2
 * Thema: Lernen im künstlichem neuronalem Netz (KNN)
 *
 * MNIST-Daten-Repreäsentation
 *
 * @author Adrian Helberg
 * @author Rodrigo Ehlers
 */
class MNISTData {
    // Data container
    private ArrayList<String> data = new ArrayList<>();

    MNISTData(String fileName) {
        // Get the file
        App.print("Lese Daten ein (" + fileName + ".csv)", getClass());
        File file = new File(
                Objects.requireNonNull(
                        // Get file from resource folder
                        getClass().getClassLoader().getResource(fileName)
                ).getFile()
        );

        try (
                // Auto-closables
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            // Prozess file line-wise
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Store data
                this.data.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the string at index position
     * @param index Index
     * @return String at index position
     */
    String getByIndex(int index) {
        return data.get(index);
    }

    /**
     * Get the size of the list
     * @return Size of the list
     */
    int getSize() {
        return this.data.size();
    }
}
