package som.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.BitSet;

public class Utils {
  public static final double GOLDEN_RATIO = (1.0d + Math.sqrt(5)) / 2.0d;
  
  //http://matpalm.com/resemblance/jaccard_coeff/
  //  |A n B| / |A u B|
  public static float jaccardCoefficient(BitSet bs1, BitSet bs2) {
    BitSet union = new BitSet();
    union.or(bs1);
    union.or(bs2);
    
    BitSet intersection = new BitSet();
    intersection.or(bs1);
    intersection.and(bs2);
    
    return intersection.cardinality() / (float)union.cardinality();
  }
  
  //  http://matpalm.com/resemblance/jaccard_distance/
  //  |A ^ B| / (|A ^ B| + |A u B|)
  public static float jaccardDistance(BitSet bs1, BitSet bs2) {
    BitSet union = new BitSet();
    union.or(bs1);
    union.or(bs2);
    
    BitSet xor = new BitSet();
    xor.or(bs1);
    xor.xor(bs2);
    
    return xor.cardinality() / (float)(xor.cardinality() + union.cardinality());
  }
  
  public static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  public static String readTextFile(File file) throws IOException {
    BufferedReader br = null;
    
    try {
      StringBuffer sb = new StringBuffer();
      CharBuffer cb = CharBuffer.allocate(8192);
      br = new BufferedReader(new FileReader(file));
      int len;
      while ((len = br.read(cb)) > 0) {
        sb.append(cb.array(), 0, len);
      }
      
      return sb.toString();
    }
    finally {
      if (br != null) {
        try { br.close(); } catch (IOException e){}
      }
    }
  }
}
