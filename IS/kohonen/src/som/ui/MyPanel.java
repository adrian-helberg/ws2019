package som.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import som.FeatureMatrix;
import som.FeatureVector;
import som.training.IFeatureMatrixTrainer;
import som.training.ITrainingObserver;
import som.training.ITrainingSetProvider;

public class MyPanel extends JPanel {
  private static final long serialVersionUID = 1L;

  public MyPanel(final FeatureMatrix matrix, final ITrainingSetProvider trainingSet, final IFeatureMatrixTrainer trainer) {
    setBorder(BorderFactory.createLineBorder(Color.black, 1));

    final MatrixCanvas canvas = new MatrixCanvas();
    canvas.setSize(400, 400);
    canvas.setMatrix(matrix);
    canvas.setFeatureVectorInterpreter(trainingSet.getInterpreter());
    this.add(canvas);

    final JTextArea textArea = new JTextArea("hello");
    textArea.setPreferredSize(new Dimension(400, 400));
    textArea.setBackground(Color.white);
    textArea.setFont(Font.getFont("Serif"));
//    textArea.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
    textArea.setForeground(Color.black);
    textArea.setEditable(false);
    textArea.setLineWrap(true);

    this.add(textArea);

    JButton train = new JButton("Train");
    this.add(train);

    canvas.addMouseMotionListener(new MouseMotionListener() {
      public void mouseDragged(MouseEvent event) {
      }

      public void mouseMoved(MouseEvent event) {
        FeatureVector fv = canvas.getFeatureVectorAtXY(event.getX(), event.getY());
        if (fv != null) {
          textArea.setText(trainingSet.getInterpreter().getDescription(fv));
        }
      }
    });

    train.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            matrix.initialiseMatrix();
            trainer.train(matrix, trainingSet, new ITrainingObserver() {
              public void iterationComplete(int iteration, int maxIterations) {
                canvas.paint();
                textArea.setText("Iteration " + iteration + " of " + maxIterations);
                textArea.paint(textArea.getGraphics());
                Utils.sleep(10);
              }

              public void foundBestMatchingUnit(int row, int col, double neighbourHoodRadius) {
                canvas.drawCircle(row, col, neighbourHoodRadius);
                canvas.drawSquare(row, col, 1, 1, Color.red);
              }
            });
            canvas.paint();
          }
        });
      }
    });
  }
}
