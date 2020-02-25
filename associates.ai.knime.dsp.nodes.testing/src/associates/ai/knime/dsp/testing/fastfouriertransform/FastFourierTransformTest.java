package associates.ai.knime.dsp.testing.fastfouriertransform;

import associates.ai.knime.dsp.nodes.fastfouriertransform.FFTMath;
import associates.ai.knime.dsp.nodes.testing.CSVReader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FastFourierTransformTest {

    private FFTMath mathTB;

    String signalsFile = "resources/fastfouriertransform/test_signals.csv";

    String standardFile = "resources/fastfouriertransform/test_transformed_signals_standard.csv";

    String unitaryFile = "resources/fastfouriertransform/test_transformed_signals_unitary.csv";

    double delta = 1e-4;

    @Before
    public void setupTests() {
        mathTB = new FFTMath();
    }

    public List<Double[]> readInFile(String fName) {
        //Read in File
        File file = new File(fName);

        try {
            return CSVReader.readCSV(file);
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        return null;
    }

    public double[] unbox(Double[] arr) {
        return Stream.of(arr).mapToDouble(Double::doubleValue).toArray();
    }

    @Test
    public void testStandardFFT() {
        List<Double[]> signals = readInFile(signalsFile);
        List<double[]> expected = readInFile(standardFile).stream().map(arr -> unbox(arr)).collect(Collectors.toList());

        List<double[]> result = signals.stream()
            .map(signal -> mathTB.fastFourierTransformer.transform(unbox(signal), "standard", signal.length / 2 + 1))
            .collect(Collectors.toList());

        for (int i = 0; i < signals.size(); ++i) {
            Assert.assertArrayEquals("Standard transform #" + i, expected.get(i), result.get(i), delta);
        }
    }

    @Test
    public void testUnitaryFFT() {
        List<Double[]> signals = readInFile(signalsFile);
        List<double[]> expected = readInFile(unitaryFile).stream().map(arr -> unbox(arr)).collect(Collectors.toList());

        List<double[]> result = signals.stream()
            .map(signal -> mathTB.fastFourierTransformer.transform(unbox(signal), "unitary", signal.length / 2 + 1))
            .collect(Collectors.toList());

        for (int i = 0; i < signals.size(); ++i) {
            Assert.assertArrayEquals("Unitary transform #" + i, expected.get(i), result.get(i), delta);
        }
    }
}
