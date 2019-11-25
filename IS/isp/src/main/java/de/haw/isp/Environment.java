package de.haw.isp;

import java.util.Arrays;
import java.math.BigDecimal;
import java.math.MathContext;
import Jama.Matrix;

/**
 * Intelligente Systeme Praktikum, Aufgabe 2
 * Thema: Lernen im künstlichem neuronalem Netz (KNN)
 *
 * Training- und Testumgebung
 *
 * @author Adrian Helberg
 * @author Rodrigo Ehlers
 */
class Environment {
    // Number of input, hidden and output nodes.
    private int inputNodes = 784; // 28 * 28 Pixel
    private int hiddenNodes = 150; // 520
    private int outputNodes = 10;
    // Degree of change while correcting weights
    private double learningRate = 0.2;
    // Actual neural network
    private NeuralNetwork neuralNetwork = new NeuralNetwork(inputNodes, hiddenNodes, outputNodes, learningRate);

    Environment() {
        // Training and test data
        MNISTData trainingData = new MNISTData("./mnist_train.csv");
        MNISTData testingData = new MNISTData("./mnist_test.csv");
        // Train neural network
        train(trainingData);
        // Test neural network
        BigDecimal accuracy = new BigDecimal(test(testingData) * 100).round(new MathContext(4));
        // Finished
        App.print("Training und Testing abgeschlossen", Environment.class);
        App.print("Genauigkeit des neuronalen Netzes: " + accuracy + "%", Environment.class);
    }

    /**
     * Normalize training data and start training the neural network
     *
     * @param mnistTrainingData Training data
     */
    private void train(MNISTData mnistTrainingData) {
        //train neural network
        App.print("Starte Training des neuronalen Netzes", getClass());
        for (int i = 0; i < mnistTrainingData.getSize(); i++) {
            // Convert string data to double data and scale it to the given value range
            double[] doubleInputs = scaleInput(convertInput(mnistTrainingData.getByIndex(i)));
            // Create 1-dimensional matrix out of the scaled input values exept the first number (magic number)
            Matrix inputs = new Matrix(Arrays.copyOfRange(doubleInputs, 1, doubleInputs.length), doubleInputs.length - 1);
            // Train the actual neural network
            neuralNetwork.train(inputs, createTargetMatrix(doubleInputs[0]));
            System.out.println("Train data " + (i + 1) + "/" + mnistTrainingData.getSize());
        }
    }

    /**
     * Test the neural network with a test data set
     *
     * @param mnistTestingData Data set to test against
     * @return Accuracy of the tested neural network
     */
    private double test(MNISTData mnistTestingData) {
        App.print("Teste neuronales Netz", getClass());
        // Count the correct results
        int correctValueCounter = 0;
        for (int i = 0; i < mnistTestingData.getSize(); i++) {
            // Convert string data to double data and scale it to the given value range
            double[] doubleInputs = scaleInput(convertInput(mnistTestingData.getByIndex(i)));
            // Create 1-dimensional matrix out of the scaled input values exept the first number (magic number)
            Matrix inputs = new Matrix(Arrays.copyOfRange(doubleInputs, 1, doubleInputs.length), doubleInputs.length - 1);
            // Get a guess from trained neural network to determine its correctness
            Matrix guessMatrix = neuralNetwork.generateGuess(inputs);
            if (guess(guessMatrix) == (int) doubleInputs[0]) {
                // Guess is correct!
                correctValueCounter += 1;
            }
        }
        // Calculate accuracy
        return (double) correctValueCounter / (mnistTestingData.getSize() - 1);
    }

    /**
     * Test the neural network against user input
     *
     * @param inputString User input
     * @return Max value of the guess matrix
     */
    int userTest(String inputString) {
        // Convert user string data to double data and scale it to the given value range
        double[] doubleInputs = scaleInput(convertInput(inputString));
        // Create 1-dimensional matrix out of the scaled input values
        Matrix inputs = new Matrix(Arrays.copyOfRange(doubleInputs, 0, doubleInputs.length), doubleInputs.length);
        // Get a guess of the trained neural network
        Matrix guessMatrix = neuralNetwork.generateGuess(inputs);
        // Return max value of the guess matrix
        return guess(guessMatrix);
    }

    /**
     * Scale data fram value range of [0, 255] to relative amount
     *
     * @param mnistDataDouble Double array to be scaled
     * @return Scaled data
     */
    private double[] scaleInput(double[] mnistDataDouble) {
        // Value at index 0 should not be changed (magic number)
        for (int i = 1; i < mnistDataDouble.length; i++) {
            // Scaled output = (value - min)/(max - min) + offset
            mnistDataDouble[i] = mnistDataDouble[i] / 255 + .01;
        }
        // Scaled data
        return mnistDataDouble;
    }

    /**
     * Convert a given string to a double
     *
     * @param mnistDataString String to be converted (comma-seperated values)
     * @return converted double
     */
    private double[] convertInput(String mnistDataString) {
        // Split input to single strings
        String[] splitMnistData = mnistDataString.split(",");
        // Create resulting array of doubles
        double[] mnistDataDouble = new double[splitMnistData.length];
        // Fill resulting array
        for (int i = 0; i < splitMnistData.length; i++) {
            // Parse double
            mnistDataDouble[i] = Double.parseDouble(splitMnistData[i]);
        }
        // Return converted doubles
        return mnistDataDouble;
    }

  /**
   * Return the target matrix (expected output matrix)
   * @param target Magic number
   * @return Target matrix
   */
    private Matrix createTargetMatrix(double target) {
        //create an array of length ouputNodes initialized to .01
        double[] targetArray = new double[this.outputNodes];
        Arrays.fill(targetArray, .01);
        //index of target value = to .99
        targetArray[(int) target] = .99;
        // Return target matrix
        return new Matrix(targetArray, targetArray.length);
    }

    /**
     * Find the max value in the guess matrix.
     * @param guessMatrix Guess matrix
     * @return Max value from guess matrix
     */
    private int guess(Matrix guessMatrix) {
        // Create 1-dimensional array out of 2-dimensional array
        double[] guessArray = guessMatrix.getRowPackedCopy();
        // Local max
        double currentMax = guessArray[0];
        // Local guess
        int guess = 0;
        // Iterate guess matrix
        for (int i = 1; i < guessArray.length; i++) {
            if (guessArray[i] >= currentMax) {
                guess = i;
                currentMax = guessArray[i];
            }
        }
        // Return guess
        return guess;
    }
}
