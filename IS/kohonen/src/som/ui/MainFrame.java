package som.ui;

import javax.swing.JFrame;
import som.FeatureMatrix;
import som.training.IFeatureMatrixTrainer;
import som.training.ITrainingSetProvider;

public class MainFrame extends JFrame {
  private static final long serialVersionUID = 1L;

  public MainFrame(FeatureMatrix matrix, ITrainingSetProvider trainingSet, IFeatureMatrixTrainer trainer) {
    this.add(new MyPanel(matrix, trainingSet, trainer));
    
    this.setSize(880, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }
}
