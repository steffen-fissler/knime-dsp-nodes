package associates.ai.knime.dsp.nodes.welchaveraging;

import static associates.ai.knime.dsp.nodes.welchaveraging.SlidingCollector.sliding;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.StreamSupport;

import org.knime.core.data.DataColumnSpec;
import org.knime.core.data.DataColumnSpecCreator;
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

/**
 * This is the model implementation of WelchAveraging.
 *
 * @author AI.Associates <knime@ai.associates>
 */
class WelchAveragingNodeModel extends NodeModel {
    private static final NodeLogger logger = NodeLogger.getLogger(WelchAveragingNodeModel.class);

    private static final String PROGRESS_MSG = "Adding row";

    private final WelchAveragingNodeConfig config = new WelchAveragingNodeConfig();


    WelchAveragingNodeModel() {
        super(1, 1);
    }

    @Override
    protected BufferedDataTable[] execute(final BufferedDataTable[] inData, final ExecutionContext exec)
        throws Exception {
        final int rowsNumber = config.getNoRows();
        final int shift = 1;


        logger.debug("Started #execute...");

        BufferedDataContainer container = exec.createDataContainer(createOutputTableSpec(inData[0].getDataTableSpec()));
        RowAdvance rowAdvance = new RowAdvance(inData[0].size());

        List<List<double[]>> groupsOfRows = StreamSupport
            .stream(inData[0].spliterator(), false)
            .map(dataRow -> StreamSupport
                                .stream(dataRow.spliterator(), false)
                                .mapToDouble(cell -> ((DoubleCell)cell).getDoubleValue())
                                .toArray())
            .collect(sliding(rowsNumber, shift));

        for (List<double[]> rows: groupsOfRows) {
            double[] averagedRow = WelchAveragingMath.average(rows);
            exec.checkCanceled();
            container.addRowToTable(new DefaultRow(rowAdvance.getRowKeyAndIncrement(), averagedRow));
            exec.setProgress(rowAdvance.getProgress() * rowsNumber,
                String.format("%s %d", PROGRESS_MSG, rowAdvance.getCurrentIndex()));
        }

        container.close();
        BufferedDataTable out = container.getTable();

        logger.debug("#execution complete.");
        return new BufferedDataTable[]{out};
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

        config.getNoRowsSetting().saveSettingsTo(settings);
    }

    @Override
    protected void loadValidatedSettingsFrom(final NodeSettingsRO settings)
        throws InvalidSettingsException {

        config.getNoRowsSetting().loadSettingsFrom(settings);
    }

    @Override
    protected void validateSettings(final NodeSettingsRO settings) throws InvalidSettingsException {

        config.getNoRowsSetting().validateSettings(settings);
    }

    @Override
    protected void loadInternals(final File internDir, final ExecutionMonitor exec)
        throws IOException, CanceledExecutionException {

    }

    @Override
    protected void saveInternals(final File internDir, final ExecutionMonitor exec)
        throws IOException, CanceledExecutionException {
    }

    private DataTableSpec createOutputTableSpec(DataTableSpec inputSpec) {
        int numColumns = inputSpec.getNumColumns();

        DataColumnSpec[] dataColumnSpecs = new DataColumnSpec[numColumns];

        for (int columnIdx = 0; columnIdx < dataColumnSpecs.length; ++columnIdx) {
            dataColumnSpecs[columnIdx] =
                new DataColumnSpecCreator(String.format("Bin %d", columnIdx), DoubleCell.TYPE).createSpec();
        }

        return new DataTableSpec(dataColumnSpecs);
    }

    private class RowAdvance {
        private long index;
        private long totalRowCount;

        private RowAdvance(final long totalRowCount) {
            this.index = 0;
            this.totalRowCount = totalRowCount;
        }

        private RowKey getRowKeyAndIncrement() {
            return RowKey.createRowKey(index++);
        }

        private long getCurrentIndex() {
            return index;
        }

        private double getProgress() {
            return index / (double) totalRowCount;
        }
    }

}
