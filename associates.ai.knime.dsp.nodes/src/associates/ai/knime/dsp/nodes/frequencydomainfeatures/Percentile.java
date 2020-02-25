package associates.ai.knime.dsp.nodes.frequencydomainfeatures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//compute percentiles with linear interpolation
public class Percentile {
    final static double QUARTILE_FIRST = 25.0;
    final static double QUARTILE_SECOND = 50.0;
    final static double QUARTILE_THIRD = 75.0;

    public static double evaluate(double[] values, double perc) {
        final List<Double> valueList = new ArrayList<>();

        for (double el : values) {
            valueList.add(el);
        }

        Collections.sort(valueList);
        int N = valueList.size();
        if (N == 1) {
            return valueList.get(0);
        }

        Double pos = perc * (N - 1) / 100.0;
        if (pos < 1) {
            return valueList.get(0);
        }

        if (pos > N) {
            return valueList.get(N - 1);
        }

        Double d = pos - pos.intValue();
        Double lower = valueList.get((int) Math.floor(pos));
        Double upper = valueList.get((int) Math.ceil(pos));

        return lower + d * (upper - lower);
    }
}