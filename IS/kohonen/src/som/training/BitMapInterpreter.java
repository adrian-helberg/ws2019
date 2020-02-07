package som.training;

import java.awt.Color;
import java.util.Arrays;

import som.FeatureVector;

public class BitMapInterpreter implements IFeatureVectorInterpreter {
  @Override
  public Color toColor(FeatureVector fv) {
    double[] weights = fv.getWeights();
    int count = 0;
    
    for (int i = 0; i < weights.length; i++) {
      if (weights[i] >= 0.5d) {
        count++;
      }
    }
    
    if (count >= weights.length / 2) {
      return Color.BLACK;
    } else {
      return Color.WHITE;
    }
  }
  
  @Override
  public String getDescription(FeatureVector fv) {
    return Arrays.toString(fv.getWeights());
  }
}
