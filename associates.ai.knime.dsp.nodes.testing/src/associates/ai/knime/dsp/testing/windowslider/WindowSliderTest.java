package associates.ai.knime.dsp.testing.windowslider;

import org.junit.Test;

import associates.ai.knime.dsp.nodes.windowslider.WindowSlider;
import org.knime.core.data.DataCell;
import org.knime.core.data.def.DoubleCell;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class WindowSliderTest {


  @Test
  public void testProcess_shouldPadWithNumberIfLastWindowIsShort() {
    WindowSlider slider = new WindowSlider(5, 2, true, new DoubleCell(1.0));
    DoubleCell[] data = doubleCellArray(1., 2., 3., 4., 5., 6., 7., 8., 9., 10., 11., 12., 13., 14., 15., 16.);

    DoubleCell[][] expected = new DoubleCell[][]{
        doubleCellArray(1., 2., 3., 4., 5.),
        doubleCellArray(3., 4., 5., 6., 7.),
        doubleCellArray(5., 6., 7., 8., 9.),
        doubleCellArray(7., 8., 9., 10., 11.),
        doubleCellArray(9., 10., 11., 12., 13.),
        doubleCellArray(11., 12., 13., 14., 15.),
        doubleCellArray(13., 14., 15., 16., 1.0)
    };
    DataCell[][] result = slider.process(data);
    assertArrayEquals(expected, result);
  }

  private static DoubleCell[] doubleCellArray(double... doubles) {
    return Arrays.stream(doubles).mapToObj(DoubleCell::new).toArray(DoubleCell[]::new);
  }

  @Test
  public void testProcess_shouldSkipWindowsIfNoPadding() {
    WindowSlider slider = new WindowSlider(5, 2, false, new DoubleCell(0.0));
    DoubleCell[] data =
        doubleCellArray(1., 2., 3., 4., 5., 6., 7., 8., 9., 10., 11., 12., 13., 14., 15., 16.);

    DoubleCell[][] expected = new DoubleCell[][]{
        doubleCellArray(1., 2., 3., 4., 5.),
        doubleCellArray(3., 4., 5., 6., 7.),
        doubleCellArray(5., 6., 7., 8., 9.),
        doubleCellArray(7., 8., 9., 10., 11.),
        doubleCellArray(9., 10., 11., 12., 13.),
        doubleCellArray(11., 12., 13., 14., 15.),
    };
    DataCell[][] result = slider.process(data);
    assertArrayEquals(expected, result);
  }
}
