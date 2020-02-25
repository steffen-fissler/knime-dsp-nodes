package associates.ai.knime.dsp.testing.welchaveraging;

import associates.ai.knime.dsp.nodes.testing.CSVReader;
import associates.ai.knime.dsp.nodes.welchaveraging.WelchAveragingMath;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;

public class WelchAveragingTest {
    private String testFftFile = "resources/welchaveraging/testFFTs.csv";

    private String test30Rows = "resources/welchaveraging/testWelchOver30Rows.csv";

    private String test32Rows = "resources/welchaveraging/testWelchOver32Rows.csv";

    private double delta = 1e-4;

    @Test
    public void testAverage() {
        double[] dummy1 = {0, 1, 0, 2, 0};
        double[] dummy2 = {0, 1, 0, 2, 0};

        double[] average = WelchAveragingMath.average(Arrays.asList(dummy1, dummy2));

        double[] expectedTopMags = {0, 1, 0, 2, 0};
        assertArrayEquals(expectedTopMags, average, 0);
    }

    public static List<double[]> toPrimitives(List<Double[]> doubleObjs) throws IOException {
        return doubleObjs
                .stream()
                .map(boxed -> Stream.of(boxed).mapToDouble(Double::doubleValue).toArray())
                .collect(Collectors.toList());
    }

    @Test
    public void testAverageOver30Rows() throws IOException {
        List<double[]> originalFFTs = toPrimitives(CSVReader.readCSV(new File(testFftFile)));
        List<double[]> expected = toPrimitives(CSVReader.readCSV(new File(test30Rows)));

        double[] result = WelchAveragingMath.average(originalFFTs.subList(0, 30));
        Assert.assertArrayEquals("Average over 30 rows.", expected.get(0), result, delta);
    }

    @Test
    public void testAverageOver32Rows() throws IOException {
        List<double[]> originalFFTs = toPrimitives(CSVReader.readCSV(new File(testFftFile)));
        List<double[]> expected = toPrimitives(CSVReader.readCSV(new File(test32Rows)));

        double[] result = WelchAveragingMath.average(originalFFTs.subList(0, 32));
        Assert.assertArrayEquals("Average over 32 rows.", expected.get(0), result, delta);
    }
}
