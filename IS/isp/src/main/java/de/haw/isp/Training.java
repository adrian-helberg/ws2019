package de.haw.isp;

public class Training {
    Training(NeuralNetwork neuralNetwork) {
        App.print("Trainiere neuronales Netz '" + neuralNetwork.getIdentifier() + "'", Training.class);
    }
}
