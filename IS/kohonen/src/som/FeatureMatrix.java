package som;

public class FeatureMatrix {
  private int rows;
  private int cols;
  private int numFeatures;
  private FeatureVector[][] matrix;
  
  public FeatureMatrix(int rows, int cols, int numFeatures) {
    this.rows = rows;
    this.cols = cols;
    this.numFeatures = numFeatures;
    
    initialiseMatrix();
  }
  
  public int getRows() {
    return rows;
  }
  
  public int getCols() {
    return cols;
  }
  
  public FeatureVector getFeatureVector(int row, int col) {
    if (row < 0 || row >= matrix.length) {
      return null;
    }
    if (col < 0 || col >= matrix[row].length) {
      return null;
    }
    
    return matrix[row][col];
  }
  
  public void initialiseMatrix() {
    matrix = new FeatureVector[rows][cols];
    
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        matrix[r][c] = new FeatureVector(numFeatures);
      }
    }
  }
}
