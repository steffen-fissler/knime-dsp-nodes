package associates.ai.knime.dsp.nodes.frequencydomainfeatures;

public class BinFreqPair {
  final public int bin;
  final public double mag;

  public BinFreqPair(int freqBin, double magnitude) {
    this.mag = magnitude;
    this.bin = freqBin;
  }
}
