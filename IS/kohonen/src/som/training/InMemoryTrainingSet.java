package som.training;


public class InMemoryTrainingSet implements ITrainingSetProvider {
  protected double[][] weights;
  private int nextRow = 0;
  private IFeatureVectorInterpreter interpreter = new SimpleInterpreter();
  
  public InMemoryTrainingSet(int rows, int cols) {
    this(new double[rows][cols]);
  }
  
  public InMemoryTrainingSet(double[][] weights) {
    this.weights = weights;
  }
  
  @Override
  public double[] next() {
    if (nextRow >= weights.length) {
      return null;
    }
    
    return weights[nextRow++];
  }

  @Override
  public void reset() {
    nextRow = 0;
  }

  @Override
  public IFeatureVectorInterpreter getInterpreter() {
    return interpreter;
  }

  @Override
  public int getNumFeatures() {
    return weights[0].length;
  }
}
