package associates.ai.knime.dsp.nodes.wavreader.utils;

import static org.knime.core.node.util.CheckUtils.checkArgument;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import associates.ai.knime.dsp.nodes.wavreader.utils.WavFile;
import org.knime.core.data.DataCell;
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

import associates.ai.knime.dsp.nodes.wavreader.utils.WavFileException;
import associates.ai.knime.dsp.nodes.wavreader.utils.WavResult;

public class WaveFileToolbox {
  private static final String PROGRESS_PREFIX = "Adding row";
  private static final String CHANNEL_COL_PREFIX = "Channel";
  public static final int FRAMES_TO_READ = 100;

  public static WavResult readAll(final File file)
      throws IOException, WavFileException, CanceledExecutionException, URISyntaxException {
    checkArgument(file != null, "'file' must not be null!");

    return process(file);
  }

  public static BufferedDataTable createDataTable(final ExecutionContext exec, final int numChannels,
                                            final double[][] data) throws CanceledExecutionException {
    checkArgument(exec != null, "'exec' must not be null!");
    checkArgument(numChannels != 0, "'numChannels' must not be null!");
    checkArgument(data != null, "'data' must not be null!");

    final BufferedDataContainer dataContainer = exec.createDataContainer(dataOutSpecCreator(numChannels));

    for (int i = 0; i < data.length; i++) {
      final double[] channels = data[i];

      final RowKey key = RowKey.createRowKey(i);
      final DataCell[] cells = new DataCell[channels.length];
      for (int j = 0; j < channels.length; j++) {
        cells[j] = new DoubleCell(channels[j]);
      }
      final DataRow row = new DefaultRow(key, cells);
      dataContainer.addRowToTable(row);

      exec.checkCanceled();
      exec.setProgress((double) i / (double) data.length, String.format("%s %d", PROGRESS_PREFIX, i));
    }

    dataContainer.close();
    return dataContainer.getTable();
  }

  public static DataTableSpec dataOutSpecCreator(final int numChannels) {
    final DataColumnSpec[] allColSpecs = new DataColumnSpec[numChannels];
    for (int i = 0; i < numChannels; i++) {
      allColSpecs[i] = new DataColumnSpecCreator(String.format("%s %d", CHANNEL_COL_PREFIX, i), DoubleCell.TYPE).createSpec();
    }

    return new DataTableSpec(allColSpecs);
  }

  private static WavResult process(final File file) throws IOException, WavFileException {
    final WavFile wavFile = WavFile.openWavFile(file);

    final int numChannels = wavFile.getNumChannels();
    final long sampleRate = wavFile.getSampleRate();

    final List<double[]> result = new ArrayList<>();

    final int bufferSizeBytes = FRAMES_TO_READ * numChannels;
    final double[] buffer = new double[bufferSizeBytes];
    int framesRead;
    do {
      framesRead = wavFile.readFrames(buffer, FRAMES_TO_READ);

      int index = 0, numberOfElements = framesRead * numChannels;
      while (index < numberOfElements) {
        double[] channelsData = new double[numChannels];
        for (int i = 0; i < numChannels; i++) {
          channelsData[i] = buffer[index];
          index++;
        }
        result.add(channelsData);
      }
    }
    while (framesRead != 0);

    wavFile.close();

    return new WavResult(result.toArray(new double[0][0]), numChannels, sampleRate);
  }
}
