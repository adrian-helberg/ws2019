package app;

import genetic.GeneticAlgorithm;
import genetic.Preset;

public class App {
    public static void main (String[] args) {

        GeneticAlgorithm geneticAlgorithm = Preset.getDefaultGA();

        geneticAlgorithm.runWithDebugMode();
        geneticAlgorithm.showGraphInWindow();
        geneticAlgorithm.printProperties();
        geneticAlgorithm.printResults();

    }
}