package som.training;

import som.FeatureMatrix;

public interface IFeatureMatrixTrainer {
  public void train(FeatureMatrix matrix, ITrainingSetProvider trainingSet, ITrainingObserver observer);
}
