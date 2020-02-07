package som.training;

import java.awt.Color;
import java.util.Arrays;

import som.FeatureVector;

public class SimpleInterpreter implements IFeatureVectorInterpreter {
  @Override
  public Color toColor(FeatureVector fv) {
    double[] weights = fv.getWeights();
    
    if (weights.length == 1) {
      return new Color((int)(weights[0] * 255), (int)(weights[0] * 255), (int)(weights[0] * 255));
    }
    if (weights.length == 2) {
      return new Color((int)(weights[0] * 255), (int)(weights[1] * 255), 0);
    }
    if (weights.length == 3) {
      return new Color((int)(weights[0] * 255), (int)(weights[1] * 255), (int)(weights[2] * 255));
    }
    
    double sum = 0.0d;
    for (int i = 0; i < weights.length; i++) {
      sum += weights[i];
    }
    if (sum > 1.0d) {
      sum = 1.0d / sum;
    }
    return Color.getHSBColor((float)sum, 0.9f, 0.9f);
  }
  
  @Override
  public String getDescription(FeatureVector fv) {
    return Arrays.toString(fv.getWeights());
  }
}
