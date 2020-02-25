package associates.ai.knime.dsp.nodes.timedomainfeatures;

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
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeLogger;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.defaultnodesettings.SettingsModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


/**
 * This is the model implementation of SignalFeatures.
 *
 * @author AI.Associates <knime@ai.associates>
 */
public class TimeDomainFeaturesNodeModel extends NodeModel {
    private static final NodeLogger logger = NodeLogger.getLogger(TimeDomainFeaturesNodeModel.class);

    private static final String ROW_PROGRESS = "Adding row";
    private final TimeDomainFeaturesNodeConfig config = new TimeDomainFeaturesNodeConfig();

    protected TimeDomainFeaturesNodeModel() {
        super(1, 1);
    }

    @Override
    protected BufferedDataTable[] execute(final BufferedDataTable[] inData,
                                          final ExecutionContext exec) throws Exception {
        logger.debug("Started #execution...");

        BufferedDataContainer container = exec.createDataContainer(createOutputTableSpec());
        AtomicLong progressCounter = new AtomicLong();
        List<String> bfNames = getBooleanFeatureNames();

        MathToolbox mathToolbox = new MathToolbox();
        final long totalInputSize = inData[0].size();
        for (DataRow inRow : inData[0]) {

            double[] doubRow = doubleRowToDoubleArray(inRow);
            final long progress = progressCounter.incrementAndGet();
            List<Double> outRow = bfNames.stream()
                .mapToDouble(key -> mathToolbox.getFeatures().get(key).apply(doubRow))
                .boxed()
                .collect(Collectors.toList());

            container.addRowToTable(new DefaultRow(inRow.getKey(),
                outRow.stream()
                    .mapToDouble(x -> x)
                    .toArray()));

            exec.checkCanceled();
            exec.setProgress(progress * 1.0d / totalInputSize, String.format("%s %s", ROW_PROGRESS, progress));
        }

        container.close();
        BufferedDataTable out = container.getTable();
        return new BufferedDataTable[]{out};
    }

    private DataTableSpec createOutputTableSpec() {
        int totalOutCols = getBooleanFeatureNames().size();

        DataColumnSpec[] allColSpecs = new DataColumnSpec[totalOutCols];

        int ind = 0;
        for (String featureName : getBooleanFeatureNames()) {
            allColSpecs[ind] = doubleCellfromName(config.getColPrefix() + featureName);
            ++ind;
        }

        return new DataTableSpec(allColSpecs);
    }

    private double[] doubleRowToDoubleArray(DataRow inRow) {
        double[] doubRow = new double[inRow.getNumCells()];

        for (int colInd = 0; colInd < inRow.getNumCells(); colInd++) {
            doubRow[colInd] = ((DoubleCell) inRow.getCell(colInd)).getDoubleValue();
        }

        return doubRow;
    }

    private DataColumnSpec doubleCellfromName(String fName) {
        return new DataColumnSpecCreator(fName, DoubleCell.TYPE).createSpec();
    }

    private List<String> getBooleanFeatureNames() {
        List<String> features = new ArrayList<>();

        if (config.isMean()) {
            features.add(TimeDomainFeaturesNodeConfig.CFGKEY_MEAN);
        }
        if (config.isStandardDeviation()) {
            features.add(TimeDomainFeaturesNodeConfig.CFGKEY_STAND_DEV);
        }
        if (config.isMedian()) {
            features.add(TimeDomainFeaturesNodeConfig.CFGKEY_MEDIAN);
        }
        if (config.isPercentile25th()) {
            features.add(TimeDomainFeaturesNodeConfig.CFGKEY_25TH_PERC);
        }
        if (config.isPercentile75th()) {
            features.add(TimeDomainFeaturesNodeConfig.CFGKEY_75TH_PERC);
        }
        if (config.isRms()) {
            features.add(TimeDomainFeaturesNodeConfig.CFGKEY_RMS);
        }
        if (config.isSkewness()) {
            features.add(TimeDomainFeaturesNodeConfig.CFGKEY_SKEWNESS);
        }
        if (config.isKurtosis()) {
            features.add(TimeDomainFeaturesNodeConfig.CFGKEY_KURTOSIS);
        }

        return features;
    }

    @Override
    protected void reset() {
    }

    @Override
    protected DataTableSpec[] configure(final DataTableSpec[] inSpecs) throws InvalidSettingsException {
        return new DataTableSpec[]{createOutputTableSpec()};
    }

    @Override
    protected void saveSettingsTo(final NodeSettingsWO settings) {

        for (SettingsModel featureSettings : config.featureSettings()) {
            featureSettings.saveSettingsTo(settings);
        }
    }

    @Override
    protected void loadValidatedSettingsFrom(final NodeSettingsRO settings) throws InvalidSettingsException {

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

