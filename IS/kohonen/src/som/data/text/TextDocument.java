package som.data.text;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import utils.MutableInteger;
import utils.MutableInteger;

public class TextDocument {
  private static final Pattern REGEX_TERMDELIM = Pattern.compile("[\\s+(){}\\[\\]<>\\?\\.\\,\\;\\:\\!\\\"]");
  private static final Set<String> STOP_WORDS = new HashSet<String>();
  
  static {
    STOP_WORDS.add("a");
    STOP_WORDS.add("i");
    STOP_WORDS.add("as");
    STOP_WORDS.add("an");
    STOP_WORDS.add("in");
    STOP_WORDS.add("it");
    STOP_WORDS.add("is");
    STOP_WORDS.add("its");
    STOP_WORDS.add("it's");
    STOP_WORDS.add("the");
    STOP_WORDS.add("and");
    STOP_WORDS.add("or");
    STOP_WORDS.add("not");
    STOP_WORDS.add("to");
    STOP_WORDS.add("of");
    STOP_WORDS.add("at");
    STOP_WORDS.add("but");
    STOP_WORDS.add("for");
    STOP_WORDS.add("was");
    STOP_WORDS.add("with");
    STOP_WORDS.add("how");
    STOP_WORDS.add("who");
    STOP_WORDS.add("what");
    STOP_WORDS.add("when");
    STOP_WORDS.add("where");
    STOP_WORDS.add("which");
    STOP_WORDS.add("you");
    STOP_WORDS.add("your");
    STOP_WORDS.add("he");
    STOP_WORDS.add("she");
    STOP_WORDS.add("his");
    STOP_WORDS.add("him");
    STOP_WORDS.add("her");
    STOP_WORDS.add("hers");
    STOP_WORDS.add("wasn't");
    STOP_WORDS.add("there");
    STOP_WORDS.add("their");
    STOP_WORDS.add("they're");
    STOP_WORDS.add("they");
    STOP_WORDS.add("this");
    STOP_WORDS.add("that");
    STOP_WORDS.add("has");
    STOP_WORDS.add("had");
    STOP_WORDS.add("have");
    STOP_WORDS.add("having");
    STOP_WORDS.add("been");
    STOP_WORDS.add("from");
    STOP_WORDS.add("on");
    STOP_WORDS.add("off");
  }
  
  Map<Integer, MutableInteger> termCounts;
  
  public TextDocument() {
  }
  
  public void parse(DictionaryOfTerms dictionary, String text) {
    String[] terms = REGEX_TERMDELIM.split(text);
    termCounts = new HashMap<Integer, MutableInteger>();
    
    for (String term : terms) {
      term = term.toLowerCase();
      if (!STOP_WORDS.contains(term)) {
        Integer termId = dictionary.get(term);
        MutableInteger count = termCounts.get(termId);
        if (count == null) {
          count = new MutableInteger(1);
          termCounts.put(termId, count);
        } else {
          count.increment();
        }
      }
    }
  }
  public Map<Integer, MutableInteger> getTermCounts() {
    return termCounts;
  }
  
  public double[] getWeights(DictionaryOfTerms dictionary) {
    double[] weights = new double[dictionary.size()];
    
    for (Entry<Integer, MutableInteger> entry : termCounts.entrySet()) {
      weights[entry.getKey().intValue() - 1] = 1.0d - (1.0d / (entry.getValue().intValue() + 1.0d));
    }
    
    return weights;
  }
}
