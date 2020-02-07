package som.training;


public interface ITrainingObserver {
  public void foundBestMatchingUnit(int row, int col, double neighborHoodRadius);
  public void iterationComplete(int current, int max);
}
