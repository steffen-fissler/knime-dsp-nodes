package associates.ai.knime.dsp.nodes.frequencydomainfeatures;

import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math3.stat.descriptive.moment.Skewness;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FDFMath {

  public interface FeatureExtractor {
    double apply(double[] doubRow);
  }

  public static FeatureExtractor meanFE = (doubRow) -> Arrays.stream(doubRow)
      .reduce(0, (c, a) -> c + a)
      / doubRow.length;

  static public FeatureExtractor standDevFE = (doubRow) -> (new StandardDeviation()).evaluate(doubRow);

  static public FeatureExtractor medianFE = (doubRow) -> (Percentile.evaluate(doubRow, Percentile.QUARTILE_SECOND));

  static public FeatureExtractor firstQuartileFE = (doubRow) -> (Percentile.evaluate(doubRow, Percentile.QUARTILE_FIRST));

  static public FeatureExtractor thirdQuartileFE = (doubRow) -> (Percentile.evaluate(doubRow, Percentile.QUARTILE_THIRD));

  static public FeatureExtractor rmsFE = (doubRow) -> Math.sqrt(
      Arrays.stream(doubRow)
          .reduce(0, (c, a) -> c + a * a)
          / doubRow.length
  );
  static public FeatureExtractor specEnergyFE = (doubRow) -> Arrays.stream(doubRow).reduce(0, (c, a) -> c + a * a) * 1.0;

  static public FeatureExtractor kurtosisFE = (doubRow) -> (new Kurtosis()).evaluate(doubRow, 0, doubRow.length);

  static public FeatureExtractor skewnessFE = (doubRow) -> (new Skewness()).evaluate(doubRow, 0, doubRow.length);

  static public Map<String, FeatureExtractor> stringToMethod = new HashMap<>();

  static {
    List<String> methodKeys = Arrays.asList(
        "Mean",
        "Standard Deviation",
        "Median",
        "25th Percentile",
        "75th Percentile",
        "RMS",
        "Spectral Energy",
        "Kurtosis",
        "Skewness");

    List<FeatureExtractor> methods = Arrays.asList(
        meanFE,
        standDevFE,
        medianFE,
        firstQuartileFE,
        thirdQuartileFE,
        rmsFE,
        specEnergyFE,
        kurtosisFE,
        skewnessFE);

    for (int i = 0; i < methodKeys.size(); ++i) {
      stringToMethod.put(methodKeys.get(i), methods.get(i));
    }
  }

  /**
   * Finds local maxima in the given spectrum.
   *
   * @param spectrum values are magnitudes
   * @return list of local maxima bins sorted by magnitude; might return an empty list if there's no local maxima in the spectrum (e.g. no peaks)
   */
  public static List<BinFreqPair> getPrincipalBinPairs(double[] spectrum) {
    int inputLength = spectrum.length;
    int[] signs = new int[inputLength - 1];

    List<BinFreqPair> princBins = new ArrayList<>();

    for (int i = 0; i < inputLength - 1; ++i) {
      double diff = spectrum[i + 1] - spectrum[i];

      if (diff >= 0)
        signs[i] = 1;
      else
        signs[i] = -1;
    }

    for (int i = 0; i < inputLength - 2; ++i) {
      double diff = signs[i + 1] - signs[i];

      if (diff < 0) {
        BinFreqPair tmp = new BinFreqPair(i + 1, spectrum[i + 1]);
        princBins.add(tmp);
      }
    }

    Collections.sort(princBins,
        (pair1, pair2) -> ((Double) pair2.mag).compareTo(pair1.mag));

    return princBins;
  }

  /**
   * Get top magnitudes from supplied principal bins. Bin list <b>MUST</b> be sorted.
   *
   * @param binList list of freq/magnitude bins. MUST be sorted.
   * @param count   desired count of top magnitudes
   * @return List of top magnitude values. Might be shorter than desired size if binList doesn't contain enough elements.
   */
  public static List<Double> getTopMagnitudes(List<BinFreqPair> binList, int count) {
    return binList.subList(0, Math.min(binList.size(), count))
        .stream()
        .mapToDouble(pair -> pair.mag)
        .boxed()
        .collect(Collectors.toList());
  }


  /**
   * Get top frequencies from supplied principal bins. Bin list <b>MUST</b> be sorted.
   *
   * @param binList list of freq/magnitude bins. MUST be sorted.
   * @param count   desired count of top magnitudes
   * @param fRes    frequency resolution
   * @return List of top frequency values. Might be shorter than desired size if binList doesn't contain enough elements.
   */
  public static List<Double> getTopFrequencies(List<BinFreqPair> binList, int count, double fRes) {
    return binList.subList(0, Math.min(binList.size(), count))
        .stream()
        .mapToDouble(pair -> pair.bin * fRes)
        .boxed()
        .collect(Collectors.toList());
  }

  public static double resolution(double sampleRate, int sampleCount) {
    return sampleRate / (double) ((sampleCount - 1) * 2);
  }

}

