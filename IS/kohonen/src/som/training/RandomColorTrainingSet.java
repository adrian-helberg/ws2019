package som.training;

public class RandomColorTrainingSet extends InMemoryTrainingSet {
  
  public RandomColorTrainingSet(int rows, int cols) {
    super(rows, cols);
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        this.weights[r][c] = Math.random();
      }
    }
  }
}
