package som.data.text;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class DictionaryOfTerms {
  private Map<String, Integer> termToId = new HashMap<String, Integer>();
  private Map<Integer, String> idToTerm = new HashMap<Integer, String>();
  
  public DictionaryOfTerms() {
  }
  
  public Integer get(String term) {
    Integer i = termToId.get(term);
    if (i == null) {
      i = new Integer(termToId.size() + 1);
      termToId.put(term, i);
      idToTerm.put(i, term);
    }
    
    return i;
  }
  
  public String get(int id) {
    return idToTerm.get(id);
  }
  
  public BitSet get(String[] terms) {
    BitSet bs = new BitSet();
    
    for (String term : terms) {
      Integer i = get(term);
      bs.set(i.intValue());
    }
    
    return bs;
  }
  
  public int size() {
    return termToId.size();
  }
}
