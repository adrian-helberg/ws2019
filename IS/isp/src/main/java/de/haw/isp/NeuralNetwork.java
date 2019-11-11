package de.haw.isp;

import java.util.Random;
import java.lang.Math;

import Jama.Matrix;

/**
 * Intelligente Systeme Praktikum, Aufgabe 2
 * Thema: Lernen im künstlichem neuronalem Netz (KNN)
 * <p>
 * Neuronales Netz
 *
 * @author Adrian Helberg
 * @author Rodrigo Ehlers
 */
class NeuralNetwork {
    private final int hiddenNodes;
    // How much the network can change during training.
    private double learningRate;
    // Weighted input and output
    private Matrix weightInputHidden, weightOutputHidden;
    // Random for gaussian sequence
    private Random random = new Random();

    /**
     * Constructor
     *
     * @param inputNodes   Number of input layer nodes
     * @param hiddenNodes  Number of hidden layer nodes
     * @param outputNodes  Number of output layer nodes
     * @param learningRate Rate of changes to weights
     */
    NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes, double learningRate) {
        // Number of nodes at each layer.
        this.hiddenNodes = hiddenNodes;
        this.learningRate = learningRate;
        // Create random weighted matrices
        weightInputHidden = randomWeightMatrix(hiddenNodes, inputNodes);
        weightOutputHidden = randomWeightMatrix(outputNodes, hiddenNodes);
    }

    /**
     * Train the neural network against a train data set
     *
     * @param inputs  Train data mastrix
     * @param targets Target matrix
     */
    void train(Matrix inputs, Matrix targets) {
        // Generate hidden outputs
        Matrix hiddenOutput = generateHiddenOutput(inputs);
        Matrix outputOutput = generateOutputOutput(hiddenOutput);

        // Calulate output error. (targets - actuall)
        Matrix outputError = targets.minus(outputOutput);
        // Apply weightHiddenOutput to output error.
        Matrix hiddenError = weightOutputHidden.transpose().times(outputError);

        backwardPropagation(weightOutputHidden, outputError, outputOutput, hiddenOutput);
        backwardPropagation(weightInputHidden, hiddenError, hiddenOutput, inputs);
    }

    /**
     * Transport errors through layers backwards to update layer weights
     * @param weightMatrix Weights to be updated
     * @param error Errors
     * @param from Starting layer
     * @param to Ending layer
     */
    private void backwardPropagation(Matrix weightMatrix, Matrix error, Matrix from, Matrix to) {
        // weights = weights + (Errors x Starting layer x ("1"Matrix - Starting layer) x Ending layer^-1 x Lerning rate
        weightMatrix.plusEquals(
            (error.arrayTimes(from).arrayTimes(
                    new Matrix(error.getRowDimension(), error.getColumnDimension(), 1).minus(from))
            ).times(to.transpose())
            .times(this.learningRate)
        );
    }

    /**
     * Create guess by transporting inputs through hidden and output layer
     *
     * @param inputs Inputs
     * @return Guess matrix
     */
    Matrix generateGuess(Matrix inputs) {
        // Generate output from hidden layer
        Matrix hiddenOutput = generateHiddenOutput(inputs);
        // Generate output from output layer
        return generateOutputOutput(hiddenOutput);
    }

    /**
     * Calculate outputs of hidden layer
     *
     * @param inputs Inputs to hidden layer
     * @return Outputs
     */
    private Matrix generateHiddenOutput(Matrix inputs) {
        // Calculate input from hidden layer
        Matrix hiddenInput = weightInputHidden.times(inputs);
        // Prozess activation function with inputs
        return activationFunction(hiddenInput);
    }

    /**
     * Calculate outputs of output layer
     *
     * @param hiddenOutput Output from hidden layer
     * @return Outputs
     */
    private Matrix generateOutputOutput(Matrix hiddenOutput) {
        //feed hidden layer outputs into output layer.
        Matrix outputInput = weightOutputHidden.times(hiddenOutput);
        // Prozess activation function with inputs
        return activationFunction(outputInput);
    }

    /**
     * Activation function (Sigmoid)
     * sig(t) = 1 / (1 + e^-t)
     *
     * @param input Inputs
     * @return Outputs
     */
    private Matrix activationFunction(Matrix input) {
        // Iterate inputs
        for (int i = 0; i < input.getRowDimension(); i++) {
            for (int j = 0; j < input.getColumnDimension(); j++) {
                // Override input value with result of sigmoid function
                input.set(i, j, 1 / (1 + Math.exp(-input.get(i, j))));
            }
        }
        // Return modified inputs
        return input;
    }

    /**
     * Random weight matrix
     *
     * @param row Number of rows
     * @param col Number of columns
     * @return Row x column matrix
     */
    private Matrix randomWeightMatrix(int row, int col) {
        // Create matrix with given dimensions
        Matrix retMatrix = new Matrix(row, col);
        for (int i = 0; i < retMatrix.getRowDimension(); i++) {
            for (int j = 0; j < retMatrix.getColumnDimension(); j++) {
                // Set pseudo-random weights
                retMatrix.set(i, j, random.nextGaussian() * Math.pow(hiddenNodes, -0.5));
            }
        }
        return retMatrix;
    }
}
