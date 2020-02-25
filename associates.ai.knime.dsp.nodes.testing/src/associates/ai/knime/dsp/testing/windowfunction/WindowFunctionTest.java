package associates.ai.knime.dsp.testing.windowfunction;

import org.junit.Assert;
import org.junit.Test;

import associates.ai.knime.dsp.nodes.testing.CSVReader;
import associates.ai.knime.dsp.nodes.windowfunction.WindowFunctionFactory;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WindowFunctionTest {

  public double delta = 1e-5;

  public double[] unbox(Double[] arr) {
    return Stream.of(arr).mapToDouble(Double::doubleValue).toArray();
  }

  public double[] doubleListToArray(List<Double> list) {
    return list.stream().mapToDouble(Double::doubleValue).toArray();
  }

  public List<Double> stringArrayToDoubleList(String[] row) {
    return  Arrays.asList(row)
        .stream()
        .mapToDouble(Double::parseDouble)
        .boxed()
        .collect(Collectors.toList());
  }

  public List<List<Double>> readInExpectedWindowValues(String windowType) {
    List<List<Double>> windowValueList = new ArrayList<>();
    
    //Read in parameters of windowValues
    CSVReader csvReader;
    File file = new File("resources/windowfunction/test_" + windowType + "_window.csv");
    
    try {
      csvReader = new CSVReader(new FileReader(file.getPath()));
      
      String[] row = null;
      while((row = csvReader.readNext()) != null) {
        windowValueList.add(stringArrayToDoubleList(row));
      }

      csvReader.close();
    } catch (Exception e) {
      System.err.println(e.toString());
    }
    
    return windowValueList;
  }
 
  public List<List<Double>> computeActualWindowValues(String windowType, List<Integer> windowSizes) {
    List<List<Double>> actualWindowValues = new ArrayList<>();
    WindowFunctionFactory factory = new WindowFunctionFactory();
    
    for(int ind=0; ind<windowSizes.size(); ++ind) {
      double[] doubRow = factory.stringToMethod.get(
                                            windowType)
                                        .apply(
                                            windowSizes.get(ind).intValue());
      List<Double> tmp = new ArrayList<>();
      for(double doub: doubRow) tmp.add(doub);
      
      actualWindowValues.add(tmp);
    }

    return actualWindowValues;
  }

  @Test
  public void testHannWindow() {
    String windowType = "hann";
    List<List<Double>> expected = readInExpectedWindowValues(windowType);
    List<Integer> windowSizes = expected.stream()
        .mapToInt(List::size)
        .boxed()
        .collect(Collectors.toList());

    List<List<Double>> actual = computeActualWindowValues(windowType, windowSizes);

    for(int ind=0; ind<expected.size(); ++ind) {
      Assert.assertArrayEquals(windowType + " window of size " + windowSizes.get(ind),
          doubleListToArray(expected.get(ind)),
          doubleListToArray(actual.get(ind)),
          delta);
    }
  }

  @Test
  public void testHammingWindow() {
    String windowType = "hamming";
    List<List<Double>> expected = readInExpectedWindowValues(windowType);
    List<Integer> windowSizes = expected.stream()
        .mapToInt(List::size)
        .boxed()
        .collect(Collectors.toList());

    List<List<Double>> actual = computeActualWindowValues(windowType, windowSizes);

    for(int ind=0; ind<expected.size(); ++ind) {
      Assert.assertArrayEquals(windowType + " window of size " + windowSizes.get(ind),
          doubleListToArray(expected.get(ind)),
          doubleListToArray(actual.get(ind)),
          delta);
    }
  }

  @Test
  public void testFlatTopWindow() {
    String windowType = "flattop";
    List<List<Double>> expected = readInExpectedWindowValues(windowType);
    List<Integer> windowSizes = expected.stream()
        .mapToInt(List::size)
        .boxed()
        .collect(Collectors.toList());

    List<List<Double>> actual = computeActualWindowValues(windowType, windowSizes);

    for(int ind=0; ind<expected.size(); ++ind) {
      Assert.assertArrayEquals(windowType + " window of size " + windowSizes.get(ind),
          doubleListToArray(expected.get(ind)),
          doubleListToArray(actual.get(ind)),
          delta);
    }
  }

  @Test
  public void testBlackmanWindow() {
    String windowType = "blackman";
    List<List<Double>> expected = readInExpectedWindowValues(windowType);
    List<Integer> windowSizes = expected.stream()
        .mapToInt(List::size)
        .boxed()
        .collect(Collectors.toList());

    List<List<Double>> actual = computeActualWindowValues(windowType, windowSizes);

    for(int ind=0; ind<expected.size(); ++ind) {
      Assert.assertArrayEquals(windowType + " window of size " + windowSizes.get(ind),
          doubleListToArray(expected.get(ind)),
          doubleListToArray(actual.get(ind)),
          delta);
    }
  }

}
