package associates.ai.knime.dsp.nodes.welchaveraging;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;


public class WelchAveragingMath {

    public static double[] average(final List<double[]> rowList) {
        double[] rowAverage = null;
        final int numRows = rowList.size();
        final double avgFactor = 1.0 / ((double) numRows);

        Optional<double[]> firstRow = StreamSupport.stream(rowList.spliterator(), false)
            .findFirst();

        if (firstRow.isPresent()) {
            rowAverage = StreamSupport
                .stream(rowList.spliterator(), false)
                .reduce(null, (acc, row) -> {
                		if (acc == null) return row;
                		else return addArrays(acc, row);
                	});
        }
        
        return multArray(rowAverage, avgFactor);
    }

    private static double[] multArray(final double[] array, final double factor) {
        int length = array.length;
        double[] result = new double[length];

        for (int idx = 0; idx < length; ++idx) {
            result[idx] = array[idx] * factor;
        }

        return result;
    }

    private static double[] addArrays(double[] arr1, final double[] arr2) {
        int arrayLength = Math.min(arr1.length, arr2.length);

        double[] result = new double[arrayLength];

        for (int idx = 0; idx < arrayLength; ++idx) {
            result[idx] = arr1[idx] + arr2[idx];
        }

        return result;
    }
}
