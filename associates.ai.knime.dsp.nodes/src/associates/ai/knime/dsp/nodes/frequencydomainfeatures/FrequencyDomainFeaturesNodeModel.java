package associates.ai.knime.dsp.nodes.frequencydomainfeatures;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.knime.core.data.DataColumnSpec;
import org.knime.core.data.DataColumnSpecCreator;
import org.knime.core.data.DataRow;
import org.knime.core.data.DataTableSpec;
import org.knime.core.data.RowKey;
import org.knime.core.data.def.DefaultRow;
import org.knime.core.data.def.DoubleCell;
import org.knime.core.node.BufferedDataContainer;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.defaultnodesettings.SettingsModel;

import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeLogger;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;

import static associates.ai.knime.dsp.nodes.frequencydomainfeatures.FDFMath.resolution;

/**
 * This is the model implementation of SpectralFeatures.
 *
 * @author AI.Associates <knime@ai.associates>
 */
public class FrequencyDomainFeaturesNodeModel extends NodeModel {
  private static final String FREQUENCY_COL = "Principal Frequency ";

  private static final String MAGNITUDE_COL = "Principal Magnitude ";

  private static final String PROGRESS_MESSAGE = "Adding row ";

  private static final NodeLogger logger = NodeLogger.getLogger(FrequencyDomainFeaturesNodeModel.class);
  private final FrequencyDomainFeaturesNodeConfig config = new FrequencyDomainFeaturesNodeConfig();

  protected FrequencyDomainFeaturesNodeModel() {
    super(1, 1);
  }

  @Override
  protected BufferedDataTable[] execute(final BufferedDataTable[] inData,
                                        final ExecutionContext exec) throws Exception {

    logger.debug("Started #execute");

    BufferedDataContainer container = exec.createDataContainer(createOutputTableSpec());

    List<String> booleanFeatureNames = getBooleanFeatureNames();

    final AtomicLong progressCounter = new AtomicLong();

    for (DataRow inRow : inData[0]) {
      double[] doubRow = doubleRowToDoubleArray(inRow);

      final List<Double> outRow = booleanFeatureNames.stream().mapToDouble(
          (key) -> FDFMath.stringToMethod
              .get(key)
              .apply(doubRow))
          .boxed()
          .collect(Collectors.toList());

      List<BinFreqPair> principalFreqsSortedByMagnitude = FDFMath.getPrincipalBinPairs(doubRow);
      final int principalMagnitudes = config.getPrincipalMagnitudes();

      final List<Double> topMagnitudes = FDFMath.getTopMagnitudes(principalFreqsSortedByMagnitude, principalMagnitudes);

      outRow.addAll(padMissingValues(topMagnitudes, principalMagnitudes));

      final int principalFrequenciesCount = config.getPrincipalFrequencies();

      double sampleRate = config.getSampleRate();
      int sampleCount = doubRow.length;
      double fRes = resolution(sampleRate, sampleCount);

      final List<Double> topFrequencies = FDFMath.getTopFrequencies(principalFreqsSortedByMagnitude, principalFrequenciesCount, fRes);
      outRow.addAll(padMissingValues(topFrequencies, principalFrequenciesCount));

      container.addRowToTable(new DefaultRow(inRow.getKey(),
          outRow.stream()
              .mapToDouble(x -> x)
              .toArray()));

      final long progress = progressCounter.incrementAndGet();
      exec.checkCanceled();
      exec.setProgress(progress * 1d / inData[0].size(), PROGRESS_MESSAGE + progress);
    }

    container.close();
    BufferedDataTable out = container.getTable();
    return new BufferedDataTable[]{out};
  }

  private List<Double> padMissingValues(final List<Double> bins, int expectedCount) {
    final List<Double> padded = new ArrayList<>(bins);
    final double placeholder = config.getPaddingValuePrincipalFrequencyMagnitude().getDoubleValue();

    while (padded.size() < expectedCount) {
      padded.add(placeholder);
    }

    return padded;
  }

  private DataTableSpec createOutputTableSpec() {
    int totalOutCols = getBooleanFeatureNames().size() + config.getPrincipalMagnitudes()
        + config.getPrincipalFrequencies();

    DataColumnSpec[] allColSpecs = new DataColumnSpec[totalOutCols];

    int ind = 0;

    for (String featureName : getBooleanFeatureNames()) {
      allColSpecs[ind] = doubleCellfromName(config.getColPrefix() + featureName);
      ++ind;
    }

    for (int indPM = 0; indPM < config.getPrincipalMagnitudes(); ++indPM, ++ind) {
      allColSpecs[ind] = doubleCellfromName(config.getColPrefix() + MAGNITUDE_COL + (indPM + 1));
    }

    for (int indPF = 0; indPF < config.getPrincipalFrequencies(); ++indPF, ++ind) {
      allColSpecs[ind] = doubleCellfromName(config.getColPrefix() + FREQUENCY_COL + (indPF + 1));
    }

    return new DataTableSpec(allColSpecs);
  }

  private DataColumnSpec doubleCellfromName(String fName) {
    return new DataColumnSpecCreator(fName, DoubleCell.TYPE).createSpec();
  }

  private List<String> getBooleanFeatureNames() {
    List<String> features = new ArrayList<>();

    if (config.isMean())
      features.add("Mean");
    if (config.isStandardDeviation())
      features.add("Standard Deviation");
    if (config.isMedian())
      features.add("Median");
    if (config.isPercentile25th())
      features.add("25th Percentile");
    if (config.isPercentile75th())
      features.add("75th Percentile");
    if (config.isSpectralEnergy())
      features.add("Spectral Energy");
    if (config.isRms())
      features.add("RMS");
    if (config.isSkewness())
      features.add("Skewness");
    if (config.isKurtosis())
      features.add("Kurtosis");

    return features;
  }

  private double[] doubleRowToDoubleArray(DataRow inRow) {
    double[] doubRow = new double[inRow.getNumCells()];

    for (int colInd = 0; colInd < inRow.getNumCells(); colInd++) {
      doubRow[colInd] = ((DoubleCell) inRow.getCell(colInd)).getDoubleValue();
    }

    return doubRow;
  }

  @Override
  protected void reset() {
  }

  @Override
  protected DataTableSpec[] configure(final DataTableSpec[] inSpecs)
      throws InvalidSettingsException {
    return new DataTableSpec[]{createOutputTableSpec()};
  }

  @Override
  protected void saveSettingsTo(final NodeSettingsWO settings) {
    for (SettingsModel featureSettings : config.featureSettings()) {
      featureSettings.saveSettingsTo(settings);
    }
  }

  @Override
  protected void loadValidatedSettingsFrom(final NodeSettingsRO settings)
      throws InvalidSettingsException {

    for (SettingsModel featureSettings : config.featureSettings()) {
      featureSettings.loadSettingsFrom(settings);
    }

  }

  @Override
  protected void validateSettings(final NodeSettingsRO settings) throws InvalidSettingsException {
    for (SettingsModel featureSettings : config.featureSettings()) {
      featureSettings.validateSettings(settings);
    }
  }

  @Override
  protected void loadInternals(final File internDir, final ExecutionMonitor exec)
      throws IOException, CanceledExecutionException {
  }

  @Override
  protected void saveInternals(final File internDir, final ExecutionMonitor exec)
      throws IOException, CanceledExecutionException {

  }
}
