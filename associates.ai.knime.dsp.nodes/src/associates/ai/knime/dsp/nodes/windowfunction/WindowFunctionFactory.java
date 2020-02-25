package associates.ai.knime.dsp.nodes.windowfunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WindowFunctionFactory {
  
  public interface GeneralWindowFunction {
    double[] apply(int windowSize, double[] coeffs);
  }
  
  public interface WindowFunction {
    double[] apply(int windowSize);
  }
  
  private static double[] hannCoeffs = {0.5, -0.5};
  private static double[] hammingCoeffs = {0.54, -0.46};
  private static double[] flatTopCoeffs = {0.2156, -0.4160, 0.2781, -0.0836, 0.0069};
  private static double[] blackmanCoeffs = {0.42, -0.5, 0.08};
  
  public GeneralWindowFunction generalWindowFunction = (windowSize, coeffs) -> range(windowSize).stream()
                                                                          .mapToDouble( a -> a/((double) windowSize - 1))
                                                                          .map( x -> windowValue(coeffs,
                                                                                             x))
                                                                          .toArray();

  
  private List<Double> range(int size) {
    List<Double> ret = new ArrayList<Double>();
    
    for(int ind=0; ind<size; ++ind) ret.add(new Double(ind) );
    
    return ret;
  }
  
  private static double windowValue(double[] coeffs, double x) {
    double sum = 0;
    for (int i = 0; i < coeffs.length; ++i) {
      sum += coeffs[i] * Math.cos(2 * i * Math.PI * x);
    }
    return sum;
  }
  
  public Map<String, WindowFunction> stringToMethod = new HashMap<String, WindowFunction>();
  
  public WindowFunctionFactory() {
    List<String> methodKeys = Arrays.asList(
                                "hann",
                                "hamming",
                                "flattop",
                                "blackman");
    
    List<WindowFunction> methods = Arrays.asList(
                                (windowSize) -> generalWindowFunction.apply(windowSize, hannCoeffs),
                                (windowSize) -> generalWindowFunction.apply(windowSize, hammingCoeffs),
                                (windowSize) -> generalWindowFunction.apply(windowSize, flatTopCoeffs),
                                (windowSize) -> generalWindowFunction.apply(windowSize, blackmanCoeffs));
    
    for(int i=0; i<methodKeys.size(); ++i) {
      stringToMethod.put(methodKeys.get(i), methods.get(i));
    }
  }
}
