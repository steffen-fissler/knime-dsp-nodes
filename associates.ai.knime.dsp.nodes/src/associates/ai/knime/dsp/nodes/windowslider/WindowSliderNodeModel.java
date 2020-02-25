package associates.ai.knime.dsp.nodes.windowslider;

import java.io.File;
import java.io.IOException;
import java.util.stream.StreamSupport;

import org.knime.core.data.DataCell;
import org.knime.core.data.DataColumnSpec;
import org.knime.core.data.DataColumnSpecCreator;
import org.knime.core.data.DataRow;
import org.knime.core.data.DataTableSpec;
import org.knime.core.data.DataType;
import org.knime.core.data.RowKey;
import org.knime.core.data.def.DefaultRow;
import org.knime.core.data.def.DoubleCell;
import org.knime.core.node.BufferedDataContainer;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.defaultnodesettings.SettingsModel;

public class WindowSliderNodeModel extends NodeModel {

    private static final String COLUMN_PREFIX = "Column";
    private static final String ADDING_ROW = "Adding row";

    private final WindowSliderNodeConfig config = new WindowSliderNodeConfig();

    protected WindowSliderNodeModel() {
        super(1, 1);
    }

    private static DataCell[] parseData(final BufferedDataTable input, final String dataColumnName) {
      final int columnIndex = getWindowColumnIndex(input.getSpec(), dataColumnName);

      return StreamSupport.stream(input.spliterator(), false)
            .map(row -> row.getCell(columnIndex))
            .toArray(DataCell[]::new);
    }

  private static int getWindowColumnIndex(final DataTableSpec inputSpec, final String dataColumnName) {
    return inputSpec.findColumnIndex(dataColumnName);
  }

  private DataTableSpec createDataTableSpec(final DataType outputType) {
        Integer windowLength = config.getWindowLength();

        DataColumnSpec[] allColSpecs = new DataColumnSpec[windowLength];
        for (int i = 0; i < windowLength; i++) {
            allColSpecs[i] = new DataColumnSpecCreator(
                String.format("%s %s", COLUMN_PREFIX, i), outputType)
                .createSpec();
        }

        return new DataTableSpec(allColSpecs);
    }

    @Override
    protected BufferedDataTable[] execute(final BufferedDataTable[] inData,
                                          final ExecutionContext exec) throws Exception {

        DataCell[] parsedData = parseData(inData[0], config.getDataColumnName());

      final DataTableSpec inputSpec = inData[0].getDataTableSpec();
      int numChannels = inputSpec.getNumColumns();
        if (numChannels == 0) {
            return new BufferedDataTable[0];
        }

      //DataCell paddingValue = new MissingCell("Padding");
      DataCell paddingValue = new DoubleCell(0);

        WindowSlider slider = new WindowSlider(
            config.getWindowLength(),
            config.getStep(),
            config.isEnabledPadding(),
            paddingValue);

        DataCell[][] windows = slider.process(parsedData);

        final DataType outputType = inputSpec.getColumnSpec(config.getDataColumnName()).getType();
        DataTableSpec outputSpec = createDataTableSpec(outputType);
        BufferedDataContainer container = exec.createDataContainer(outputSpec);

        long windowIndex = 0;
        for (DataCell[] window : windows) {
            RowKey key = RowKey.createRowKey(windowIndex);

          DataRow row = new DefaultRow(key, window);
            container.addRowToTable(row);

            // check if the execution monitor was canceled
            exec.checkCanceled();
            exec.setProgress(windowIndex / (double) windows.length, String.format("%s %s", ADDING_ROW, windowIndex));
            windowIndex++;
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

        String[] inSpecsColumnNames = inSpecs[0].getColumnNames();
        int windowColumnIndex;

        if (inSpecsColumnNames.length < 1) {
            throw new InvalidSettingsException("No data columns in input data");
        }

        if (config.isDataColumnNotConfigured()) {
            windowColumnIndex = 0;
            String defaultColumnName = inSpecsColumnNames[windowColumnIndex];
            config.setDataColumnName(defaultColumnName);
        } else {
            windowColumnIndex = getWindowColumnIndex(inSpecs[0], config.getDataColumnName());
            if (windowColumnIndex < 0) {
                String configuredDataColumnName = config.getDataColumnName();
                String inSpecDataColumnNames = String.join(",", inSpecsColumnNames);
                throw new InvalidSettingsException("Currently configured data column: " + configuredDataColumnName + " is not available from input node. Please choose one of the following ones: " + inSpecDataColumnNames);
            }
        }

        final DataType outputType = inSpecs[0].getColumnSpec(windowColumnIndex).getType();
        DataTableSpec outputSpec = createDataTableSpec(outputType);
        return new DataTableSpec[]{outputSpec};
    }

    @Override
    protected void saveSettingsTo(final NodeSettingsWO settings) {

        for (SettingsModel sM : config.getSettings()) {
            sM.saveSettingsTo(settings);
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

}

