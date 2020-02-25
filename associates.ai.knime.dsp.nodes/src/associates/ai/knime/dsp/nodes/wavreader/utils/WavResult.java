package associates.ai.knime.dsp.nodes.wavreader.utils;

public class WavResult {
  private final double[][] data;
  private final int numberOfChannels;
  private final long sampleRate;

  WavResult(final double[][] data, final int numberOfChannels, final long sampleRate) {
    this.data = data;
    this.numberOfChannels = numberOfChannels;
    this.sampleRate = sampleRate;
  }

  public double[][] getData() {
    return this.data;
  }

  public int getNumberOfChannels() {
    return this.numberOfChannels;
  }

  public long getSampleRate() {
    return this.sampleRate;
  }
}
