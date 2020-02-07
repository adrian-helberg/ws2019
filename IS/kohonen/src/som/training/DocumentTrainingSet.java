package som.training;

import java.awt.Color;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import som.FeatureVector;
import som.data.text.DictionaryOfTerms;
import som.data.text.TextDocument;
import som.ui.Utils;

public class DocumentTrainingSet implements ITrainingSetProvider {
  private DictionaryOfTerms dictionary = new DictionaryOfTerms();
  private List<TextDocument> documents = new ArrayList<TextDocument>();
  Iterator<TextDocument> iterator;
  
  public DocumentTrainingSet() {
  }
  
  public void addDocument(String text) {
    TextDocument doc = new TextDocument();
    doc.parse(dictionary, text);
    addDocument(doc);
  }
  
  public void addDocuments(File folder) throws IOException {
    System.out.println("Loading documents from " + folder.getAbsolutePath());
    String[] files = folder.list(new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return name.endsWith(".txt");
      }
    });
    
    System.out.println("Adding " + files.length + " documents...");
    for (String s : files) {
      File file = new File(folder, s);
      addDocument(Utils.readTextFile(file));
    }
  }
  
  public void addDocument(TextDocument document) {
    documents.add(document);
  }
  
  @Override
  public int getNumFeatures() {
    return dictionary.size();
  }
  
  @Override
  public double[] next() {
    if (null == iterator) {
      reset();
    }
    
    if (!iterator.hasNext()) {
      return null;
    }
    
    TextDocument doc = iterator.next();
    return doc.getWeights(dictionary);
  }

  @Override
  public void reset() {
    iterator = documents.iterator();
  }

  @Override
  public IFeatureVectorInterpreter getInterpreter() {
    return new IFeatureVectorInterpreter() {
      public Color _toColor(FeatureVector fv) {
        double[] weights = fv.getWeights();
        int target = weights.length / 2;
        int count = 0;
        
        for (int i = 0; i < weights.length; i++) {
          if (weights[i] >= 0.5d) {
            if (++count >= target) {
              return Color.BLACK;
            }
          }
        }
        
        return Color.WHITE;
      }
      
      @Override
      public Color toColor(FeatureVector fv) {
        double[] weights = fv.getWeights();
        float hue = 0.0f;
        float brightness = 0.0f;
        float huesPerFeature = 360.0f / weights.length;
        for (int i = 0; i < weights.length; i++) {
          if (weights[i] > 0.5d) {
            double val = i * huesPerFeature;
            hue += val;
            brightness += val * weights[i];
          }
        }
        
        return Color.getHSBColor(hue, 0.8f, brightness);
      }
      
      @Override
      public String getDescription(FeatureVector fv) {
        double[] weights = fv.getWeights();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < weights.length; i++) {
          if (weights[i] >= 0.5d) {
            String term = dictionary.get(i + 1);
            if (sb.length() > 1) {
              sb.append(",");
            }
            sb.append(term);
          }
        }
        sb.append("]");
        return sb.toString();
      }
    };
  }
}
