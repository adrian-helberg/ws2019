package som;
import java.util.Arrays;


public class FeatureVector {
  private double[] weights;
  
  public FeatureVector(int numWeights) {
    double[] weights = new double[numWeights];
    
    for (int i = 0; i < weights.length; i++) {
      weights[i] = Math.random();
    }
    
    this.weights = weights;
  }
  
  public FeatureVector(double[] weights) {
    this.weights = weights;
  }
  
  public double[] getWeights() {
    return weights;
  }
  
  public void adjustWeights(double[] weights, double learningRate, double influence) {
    for (int i = 0; i < this.weights.length; i++) {
      double oldWeight = this.weights[i];
      this.weights[i] = oldWeight + learningRate * influence * (weights[i] - oldWeight);
    }
  }
  
  public String toString() {
    return Arrays.toString(weights);
  }
}
