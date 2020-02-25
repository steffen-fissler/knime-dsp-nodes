package associates.ai.knime.dsp.nodes.wavreader;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import associates.ai.knime.dsp.nodes.wavreader.utils.WavFile;
import associates.ai.knime.dsp.nodes.wavreader.utils.WavFileException;
import associates.ai.knime.dsp.nodes.wavreader.utils.WavResult;
import org.knime.core.data.DataTableSpec;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeCreationContext;
import org.knime.core.node.NodeLogger;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.util.CheckUtils;
import org.knime.core.util.FileUtil;

import associates.ai.knime.dsp.nodes.wavreader.utils.WaveFileToolbox;

public class WaveReaderNodeModel extends NodeModel {

  public WaveReaderNodeModel() {
    super(0, 1);
    m_config = new WaveReaderNodeConfig();
  }

  public WaveReaderNodeModel(final NodeCreationContext context) {
    super(0, 1);
    m_config = new WaveReaderNodeConfig(context.getUrl().toString());
  }

  private final WaveReaderNodeConfig m_config;
  private static final NodeLogger logger = NodeLogger.getLogger(WaveReaderNodeModel.class);

  @Override
  protected BufferedDataTable[] execute(final BufferedDataTable[] inData, final ExecutionContext exec) throws Exception {
    logger.debug("#execute started");

    CheckUtils.checkSourceFile(m_config.getFilePath());
    URL url = FileUtil.toURL(m_config.getFilePath());
    File f = FileUtil.getFileFromURL(url);


    logger.debug("Using file: " + f.getAbsolutePath());

    WavResult wavResult = WaveFileToolbox.readAll(f);

    final int numChannels = wavResult.getNumberOfChannels();
    final double[][] data = wavResult.getData();

    final BufferedDataTable outSignal = WaveFileToolbox.createDataTable(exec, numChannels, data);

    pushFlowVariableInt("sampleRate", ((int) wavResult.getSampleRate()));
    return new BufferedDataTable[]{outSignal};
  }

  @Override
  protected void reset() {
  }

  @Override
  protected DataTableSpec[] configure(final DataTableSpec[] inSpecs) throws InvalidSettingsException {
    URL url;
    try {
      CheckUtils.checkSourceFile(m_config.getFilePath());
      url = FileUtil.toURL(m_config.getFilePath());
    } catch (IOException e) {
      logger.warn(e.getMessage());
      return new DataTableSpec[]{null};
    }

    try (WavFile wavFile = WavFile.openWavFile(FileUtil.getFileFromURL(url))) {
      logger.debug("Configuring wav reader with file [" + url + "]");
      pushFlowVariableInt("sampleRate", ((int) wavFile.getSampleRate()));
      return new DataTableSpec[]{WaveFileToolbox.dataOutSpecCreator(wavFile.getNumChannels())};
    } catch (IOException | WavFileException e) {
      logger.warn("Couldn't open wav file" + url);
      return new DataTableSpec[]{null};
    }
  }

  @Override
  protected void saveSettingsTo(final NodeSettingsWO settings) {
    m_config.getFilePathSetting().saveSettingsTo(settings);
  }

  @Override
  protected void loadValidatedSettingsFrom(final NodeSettingsRO settings) throws InvalidSettingsException {
    m_config.getFilePathSetting().loadSettingsFrom(settings);
  }

  @Override
  protected void validateSettings(final NodeSettingsRO settings) throws InvalidSettingsException {
    m_config.getFilePathSetting().validateSettings(settings);
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

