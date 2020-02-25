package associates.ai.knime.dsp.nodes.windowslider;

import java.util.ArrayList;
import java.util.List;

import org.knime.core.data.DataCell;

public class WindowSlider {
  private final int windowSize;
  private final int windowStep;
  private final boolean enablePadding;
  private final DataCell paddingValue;

  public WindowSlider(final int size, final int step, final boolean enablePadding, final DataCell paddingValue) {
    this.windowSize = size;
    this.windowStep = step;
    this.enablePadding = enablePadding;
    this.paddingValue = paddingValue;
  }

  public DataCell[][] process(final DataCell[] data) {
    int windowIndex = 0;
    List<DataCell[]> result = new ArrayList<>();
    boolean hadToPad = false;

    while (windowIndex < data.length) {
      if (windowIndex + windowSize > data.length && !enablePadding) {
        break;
      }
      DataCell[] currentWindow = new DataCell[windowSize];
      for (int i = 0; i < windowSize; i++) {
        if (i + windowIndex >= data.length) {
          currentWindow[i] = paddingValue;
          hadToPad = true;
        } else {
          currentWindow[i] = data[i + windowIndex];
        }
      }

      result.add(currentWindow);
      windowIndex += windowStep;
    }

    if (enablePadding && hadToPad) {
        result.remove(result.size() - 1);
    }

    return result.toArray(new DataCell[0][0]);
  }
}
