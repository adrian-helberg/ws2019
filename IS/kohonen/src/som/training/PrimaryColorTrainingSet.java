package som.training;

public class PrimaryColorTrainingSet extends InMemoryTrainingSet {
  private static final double[][] PALETTE = {{1.0d, 0.0d, 0.0d},  /* red */
                                             {0.0d, 1.0d, 0.0d},  /* green */
                                             {0.0d, 0.0d, 1.0d},  /* blue */
                                             {1.0d, 1.0d, 0.0d},  /* yellow */
                                             {1.0d, 0.0d, 1.0d},  /* magenta */
                                             {0.0d, 1.0d, 1.0d},  /* cyan */
                                             {0.0d, 0.0d, 0.0d},  /* black */
                                             {1.0d, 1.0d, 1.0d},  /* white */
                                             {0.5d, 0.5d, 0.5d}}; /* grey */
  
  public PrimaryColorTrainingSet() {
    super(PALETTE);
  }
}
