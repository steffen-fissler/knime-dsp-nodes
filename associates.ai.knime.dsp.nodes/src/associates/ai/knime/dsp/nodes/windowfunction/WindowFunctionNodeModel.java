package associates.ai.knime.dsp.nodes.windowfunction;

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

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;


/**
 * This is the model implementation of WindowFunction.
 *
 * @author AI.Associates <knime@ai.associates>
 */
public class WindowFunctionNodeModel extends NodeModel {
    private static final String COLUMN_PREFIX = "Timestep";
    private static final String PROGRESS_MESSAGE = "Adding row";

    private static final NodeLogger logger = NodeLogger.getLogger(WindowFunctionNodeModel.class);

    private final WindowFunctionNodeConfig config = new WindowFunctionNodeConfig();

    WindowFunctionNodeModel() {
        super(1, 1);
    }

    @Override
    protected BufferedDataTable[] execute(final BufferedDataTable[] inData,
                                          final ExecutionContext exec) throws Exception {

        logger.debug("Started #execute");

        BufferedDataContainer container =
            exec.createDataContainer(createOutputTableSpec(inData[0].getDataTableSpec()));

        int windowSize = inData[0].getSpec().getNumColumns();

        WindowFunctionFactory windowFactory = new WindowFunctionFactory();

        double[] window = windowFactory.stringToMethod.get(config.getWindowFunction().toLowerCase().replace(" ", ""))
            .apply(windowSize);

        final AtomicLong progressCounter = new AtomicLong();
        final long totalInputSize = inData[0].size();

        double[] inputRow, outputRow;

        for (DataRow inRow : inData[0]) {

            inputRow = StreamSupport.stream(inRow.spliterator(), false)
                .map(cell -> (DoubleCell) cell)
                .mapToDouble(DoubleCell::getDoubleValue)
                .toArray();

            outputRow = multiplyDoubleArrays(window, inputRow);

            container.addRowToTable(new DefaultRow(
                inRow.getKey(),
                outputRow));
            final long progress = progressCounter.incrementAndGet();
            exec.checkCanceled();
            exec.setProgress(progress * 1.0d / totalInputSize, String.format("%s %s", PROGRESS_MESSAGE, progress));
        }

        container.close();
        BufferedDataTable out = container.getTable();
        return new BufferedDataTable[]{out};
    }

    private double[] multiplyDoubleArrays(double[] arr1, double[] arr2) {
        return IntStream.range(0, Math.min(arr1.length, arr2.length))
            .mapToDouble(i -> arr1[i] * arr2[i])
            .toArray();
    }

    private DataTableSpec createOutputTableSpec(DataTableSpec inSpec) {
        int noCols = inSpec.getNumColumns();

        DataColumnSpec[] allColSpecs = new DataColumnSpec[noCols];

        for (int colInd = 0; colInd < allColSpecs.length; ++colInd) {
            allColSpecs[colInd] =
                new DataColumnSpecCreator(String.format("%s %s", COLUMN_PREFIX, colInd), DoubleCell.TYPE).createSpec();
        }

        return new DataTableSpec(allColSpecs);
    }

    @Override
    protected void reset() {
    }

    @Override
    protected DataTableSpec[] configure(final DataTableSpec[] inSpecs)
        throws InvalidSettingsException {
        return new DataTableSpec[]{createOutputTableSpec(inSpecs[0])};
    }

    @Override
    protected void saveSettingsTo(final NodeSettingsWO settings) {
        config.getWindowFunctionSetting().saveSettingsTo(settings);
    }

    @Override
    protected void loadValidatedSettingsFrom(final NodeSettingsRO settings)
        throws InvalidSettingsException {
        config.getWindowFunctionSetting().loadSettingsFrom(settings);
    }

    @Override
    protected void validateSettings(final NodeSettingsRO settings) throws InvalidSettingsException {
        config.getWindowFunctionSetting().validateSettings(settings);
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
