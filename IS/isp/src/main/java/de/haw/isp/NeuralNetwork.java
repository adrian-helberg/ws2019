package de.haw.isp;

/**
 * Küstliches neuronales Netz (KNN)
 *
 * Input Layer (i Knoten):
 *      Annahme der hereinkommenden Daten
 *      Gewichtung der Daten
 *      Weitergabe an das erste Hidden Layer
 * Hidden Layer (h Knoten):
 *      Annahme der gewichteten Daten
 *      Informationsverarbeitung
 * Output Layer (o Knoten):
 *      Annahme der Ergabnisse des Hidden Layers
 *
 * @author Adrian Helberg
 * @author Rodrigo Antonio Ehlers Terraza
 */
class NeuralNetwork {
    private String identifier;

    private int inputNodes, hiddenNodes, outputNodes;

    NeuralNetwork(String name, int i, int h, int o, double learningRate) {
        identifier = name;
        inputNodes = i;
        hiddenNodes = h;
        outputNodes = o;

        App.print("Initialisiere '" + name + "'", NeuralNetwork.class);
        App.print(i + " 'Input Layer' Knoten");
        App.print(h + " 'Hidden Layer' Knoten");
        App.print(o + " 'Output Layer' Knoten");
        App.print(learningRate + " Lern Rate");
    }

    String getIdentifier() {
        return identifier;
    }
}
