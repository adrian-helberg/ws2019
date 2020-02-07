package som.training;

import java.awt.Color;

import som.FeatureVector;

public interface IFeatureVectorInterpreter {

  public abstract Color toColor(FeatureVector fv);

  public abstract String getDescription(FeatureVector fv);

}
