package som.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import som.FeatureMatrix;
import som.FeatureVector;
import som.training.IFeatureVectorInterpreter;

public class MatrixCanvas extends Canvas {
  private static final long serialVersionUID = 1L;
  private FeatureMatrix matrix;
  private IFeatureVectorInterpreter interpreter;
  
  public MatrixCanvas() {
    this.setBackground(Color.yellow);
  }

  public void setMatrix(FeatureMatrix matrix) {
    this.matrix = matrix;
  }
  
  public void setFeatureVectorInterpreter(IFeatureVectorInterpreter interpreter) {
    this.interpreter = interpreter;
  }
  
  public void drawCircle(int row, int col, double radius) {
    Dimension d = getSize();
    int cellWidth = d.width / matrix.getCols();
    int cellHeight = d.height / matrix.getRows();
    int r = (int)radius;
    
    int x = (col - r) * cellWidth;
    int y = (row - r) * cellHeight;
    int w = r * 2 * cellWidth;
    int h = r * 2 * cellHeight;
    
    getGraphics().drawOval(x, y, w, h);
  }
  
  public void drawSquare(int row, int col, int width, int height, Color c) {
    Dimension d = getSize();
    int cellWidth = d.width / matrix.getCols();
    int cellHeight = d.height / matrix.getRows();
    Graphics g = getGraphics();
    g.setColor(c);
    g.fillRect(col * cellWidth - cellWidth / 2, row * cellHeight - cellHeight / 2, width * cellWidth, height * cellHeight);
  }
  
  public void paint() {
    paint(getGraphics());
  }
  
  public FeatureVector getFeatureVectorAtXY(int x, int y) {
    Dimension d = getSize();
    int cellWidth = d.width / matrix.getCols();
    int cellHeight = d.height / matrix.getRows();
    int row = y / cellHeight;
    int col = x / cellWidth;
    
    return matrix.getFeatureVector(row, col);
  }
  
  @Override
  public void paint(Graphics g) {
    
    if (matrix == null) {
      return;
    }
    
    Dimension d = getSize();
    int cellWidth = d.width / matrix.getCols();
    int cellHeight = d.height / matrix.getRows();
    
    if (cellWidth * matrix.getCols() < d.width || cellHeight * matrix.getRows() < d.height) {
      System.err.println("Matrix canvas isn't big enough to render properly");
    }
    
    for (int i = 0; i < matrix.getRows(); i++) {
      for (int j = 0; j < matrix.getCols(); j++) {
        g.setColor(interpreter.toColor(matrix.getFeatureVector(i, j)));
        g.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
      }
    }
  }
}
