package som.training;


public interface ITrainingSetProvider {
  public int getNumFeatures();
  public double[] next();
  public void reset();
  public IFeatureVectorInterpreter getInterpreter();
}
