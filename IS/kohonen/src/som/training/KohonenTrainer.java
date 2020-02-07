package som.training;

import som.FeatureMatrix;
import som.FeatureVector;
import som.ui.Utils;

public class KohonenTrainer implements IFeatureMatrixTrainer {
  private static final int NUM_TRAINING_ITERATIONS = 500;
  private static final double START_LEARNING_RATE = 0.07d;
  
  private int numTrainingIterations = NUM_TRAINING_ITERATIONS;
  
  static class NodeRowCol {
    public int row;
    public int col;
    
    public NodeRowCol(int row, int col) {
      this.row = row;
      this.col = col;
    }
  }
  
  public KohonenTrainer() {
  }
  
  public KohonenTrainer(int numTrainingIterations) {
    this.numTrainingIterations = numTrainingIterations;
  }
  
  private static double calculateDistance(double[] w1, double[] w2) {
    double distance = 0.0d;
    
    for (int i = 0; i < w1.length; i++) {
      double diff = w1[i] - w2[i];
      distance += diff * diff;
    }
    
    return Math.sqrt(distance);
  }
  
  private NodeRowCol findBestMatchingUnit(FeatureMatrix matrix, double[] featureWeights) {
    NodeRowCol bmu = new NodeRowCol(0, 0);
    double bmuDistance = calculateDistance(matrix.getFeatureVector(0, 0).getWeights(), featureWeights);
    
    int rows = matrix.getRows();
    int cols = matrix.getCols();
    
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        FeatureVector fv = matrix.getFeatureVector(r, c);
        double featureDistance = calculateDistance(fv.getWeights(), featureWeights);
        if (featureDistance < bmuDistance) {
          bmu.row = r;
          bmu.col = c;
          bmuDistance = featureDistance;
        }
      }
    }
    
    return bmu;
  }
  
  public void train(FeatureMatrix matrix, ITrainingSetProvider trainingSet, ITrainingObserver observer) {
    int rows = matrix.getRows();
    int cols = matrix.getCols();
    double mapRadius = Math.max(rows, cols) / 2;
    double timeConstant = numTrainingIterations / Math.log(mapRadius);
    double learningRate = START_LEARNING_RATE;
    int iteration = 0;
    
    while(iteration < numTrainingIterations) {
      double neighbourHoodRadius = mapRadius * Math.exp(-(double)iteration / timeConstant);
      double neighbourHoodRadiusSqrd = neighbourHoodRadius * neighbourHoodRadius;
      
      trainingSet.reset();
      
      double[] trainingWeights;
      while ((trainingWeights = trainingSet.next()) != null) {
        NodeRowCol bestMatchingUnit = findBestMatchingUnit(matrix, trainingWeights);
        
        int rStart = Math.max(0, (int)(bestMatchingUnit.row - neighbourHoodRadius - 1.0d));
        int cStart = Math.max(0, (int)(bestMatchingUnit.col - neighbourHoodRadius - 1.0d));
        int rEnd = Math.min(rows, (int)(bestMatchingUnit.row + neighbourHoodRadius + 1.0d));
        int cEnd = Math.min(cols, (int)(bestMatchingUnit.col + neighbourHoodRadius + 1.0d));
        
        if (observer != null) {
          observer.foundBestMatchingUnit(bestMatchingUnit.row, bestMatchingUnit.col, neighbourHoodRadius);
        }
        
        for (int r = rStart; r < rEnd; r++) {
          for (int c = cStart; c < cEnd; c++) {
            double rDelta = bestMatchingUnit.row - r;
            double cDelta = bestMatchingUnit.col - c;
            double distanceToNodeSqrd = rDelta * rDelta + cDelta * cDelta;
            
            if (distanceToNodeSqrd <= neighbourHoodRadiusSqrd) {
              double influence = Math.exp(-(distanceToNodeSqrd) / (2 * neighbourHoodRadiusSqrd));
              matrix.getFeatureVector(r, c).adjustWeights(trainingWeights, learningRate, influence);
            }
          }
        }
      }
      
      ++iteration;
      learningRate = START_LEARNING_RATE * Math.exp(-(double)iteration / numTrainingIterations);
      
      if (observer != null) {
        observer.iterationComplete(iteration, numTrainingIterations);
      }
    }
  }
}
