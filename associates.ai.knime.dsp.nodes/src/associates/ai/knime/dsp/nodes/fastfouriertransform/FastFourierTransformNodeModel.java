package associates.ai.knime.dsp.nodes.fastfouriertransform;

import org.knime.core.data.DataColumnSpec;
import org.knime.core.data.DataColumnSpecCreator;
import org.knime.core.data.DataRow;
import org.knime.core.data.DataTableSpec;
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
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.StreamSupport;

/**
 * This is the model implementation of FastFourierTransform.
 *
 * @author AI.Associates <knime@ai.associates>
 */
class FastFourierTransformNodeModel extends NodeModel {
    private static final String PROGRESS_STR = "Adding row";
    private static final String COLUMN_PREFIX = "Frequency";

    private static final NodeLogger logger = NodeLogger.getLogger(FastFourierTransformNodeModel.class);

    private FastFourierTransformNodeConfig config = new FastFourierTransformNodeConfig();

    FastFourierTransformNodeModel() {
        super(1, 1);
    }

    @Override
    protected BufferedDataTable[] execute(final BufferedDataTable[] inData,
                                          final ExecutionContext exec) throws Exception {
        logger.debug("Executing FastFourierTransform");
        AtomicLong progressCounter = new AtomicLong();

        final int frequencyBinCount = getFrequencyBinCount(inData[0].getDataTableSpec());
        final DataTableSpec outputTableSpec = createOutputTableSpec(frequencyBinCount, config.getSampleRate());
        BufferedDataContainer container = exec.createDataContainer(
            outputTableSpec);
        long totalInputSize = inData[0].size();

        for (DataRow inRow : inData[0]) {
            double[] inputRow = StreamSupport.stream(inRow.spliterator(), false)
                .map(cell -> (DoubleCell) cell)
                .mapToDouble(DoubleCell::getDoubleValue)
                .toArray();

            double[] outputRow = new FFTMath()
                .fastFourierTransformer
                .transform(inputRow, config.getNormalization(), frequencyBinCount);

            long progress = progressCounter.incrementAndGet();
            container.addRowToTable(new DefaultRow(inRow.getKey(), outputRow));

            exec.checkCanceled();
            exec.setProgress(progress * 1.0d / totalInputSize, String.format("%s %d", PROGRESS_STR, progress));
        }

        container.close();
        BufferedDataTable out = container.getTable();
        return new BufferedDataTable[]{out};
    }

    @Override
    protected void reset() {
    }

    @Override
    protected DataTableSpec[] configure(final DataTableSpec[] inSpecs)
        throws InvalidSettingsException {
        final int frequencyBinCount = getFrequencyBinCount(inSpecs[0]);
        final DataTableSpec outputTableSpec = createOutputTableSpec(frequencyBinCount, config.getSampleRate());
        return new DataTableSpec[]{outputTableSpec};
    }

    @Override
    protected void saveSettingsTo(final NodeSettingsWO settings) {
        for (SettingsModel settingsModel : config.getSettings()) {
            settingsModel.saveSettingsTo(settings);
        }
    }

    @Override
    protected void loadValidatedSettingsFrom(final NodeSettingsRO settings)
        throws InvalidSettingsException {
        for (SettingsModel sM : config.getSettings()) {
            sM.loadSettingsFrom(settings);
        }
    }

    @Override
    protected void validateSettings(final NodeSettingsRO settings) throws InvalidSettingsException {
        for (SettingsModel sM : config.getSettings()) {
            sM.validateSettings(settings);
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

    private int getFrequencyBinCount(DataTableSpec inputSpec) {
        int windowLength = inputSpec.getNumColumns();
        return windowLength / 2 + 1;
    }

    private DataTableSpec createOutputTableSpec(int frequencyBinCount, Double sampleRate) {
        // the data table spec of the single output table,
        // the table will have #numberFreqBins columns:
        DataColumnSpec[] allColSpecs = new DataColumnSpec[frequencyBinCount];

        double nDFT = (frequencyBinCount - 1) * 2;
        double frequencyResolution = sampleRate / nDFT;

        for (int colInd = 0; colInd < allColSpecs.length; ++colInd) {
            allColSpecs[colInd] = new DataColumnSpecCreator(
                String.format("%s %s", COLUMN_PREFIX, colInd * frequencyResolution), DoubleCell.TYPE)
                .createSpec();
        }

        return new DataTableSpec(allColSpecs);
    }

}
