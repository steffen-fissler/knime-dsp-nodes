package associates.ai.knime.dsp.testing.frequencydomainfeatures;

import org.junit.Before;
import org.junit.Test;

import associates.ai.knime.dsp.nodes.frequencydomainfeatures.BinFreqPair;
import associates.ai.knime.dsp.nodes.frequencydomainfeatures.FDFMath;
import associates.ai.knime.dsp.nodes.testing.CSVReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class FrequencyDomainFeaturesTest {
    private String meanFile = "resources/frequencydomainfeatures/meanFeature.csv";
    private String stdDevFile = "resources/frequencydomainfeatures/standardDeviationFeature.csv";
    private String medianFile = "resources/frequencydomainfeatures/medianFeature.csv";
    private String firstQuartileFile = "resources/frequencydomainfeatures/firstQuartileFeature.csv";
    private String thirdQuartileFile = "resources/frequencydomainfeatures/thirdQuartileFeature.csv";
    private String specEnergyFile = "resources/frequencydomainfeatures/spectralEnergyFeature.csv";
    private String rmsFile = "resources/frequencydomainfeatures/RmsFeature.csv";
    private String skewnessFile = "resources/frequencydomainfeatures/SkewnessFeature.csv";
    private String kurtosisFile = "resources/frequencydomainfeatures/KurtosisFeature.csv";


    private double delta = 1e-5; //Acceptable Error

    private double[] dummy = {3, 5, 7, 11};
    private double[] dummy2 = {0, 1, 0, 2, 0};

    private List<Double[]> fftArrays = new ArrayList<>();
    private List<Double[]> expectedTopMags = new ArrayList<>();
    private List<Double[]> expectedTopFreqs = new ArrayList<>();

    private List<BinFreqPair> binFreqs;
    
    private static File fileFromPath(String filePath) {
        return new File(filePath);
    }
    
    @Before
    public void setupTests() {
        binFreqs = FDFMath.getPrincipalBinPairs(dummy2);
    }

    @Before
    public void readInFftArrays() {
        //Read in fftArrays
        File file = new File("resources/frequencydomainfeatures/FDF_fft_arrays.csv");

        try {
            fftArrays = CSVReader.readCSV(file);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    @Before
    public void readInTopMags() {
        //Read in topMags
        File file = new File("resources/frequencydomainfeatures/6topMags.csv");

        try {
            expectedTopMags = CSVReader.readCSV(file);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    @Before
    public void readInTopFreqs() {
        //Read in topMags
        File file = new File("resources/frequencydomainfeatures/6topFreqs.csv");

        try {
            expectedTopFreqs = CSVReader.readCSV(file);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public double[] unbox(Double[] arr) {
        return Stream.of(arr).mapToDouble(Double::doubleValue).toArray();
    }

    public double[] doubleListToArray(List<Double> list) {
        return list.stream().mapToDouble(Double::doubleValue).toArray();
    }
    //TESTS

    //Mean

    @Test
    public void testDummyMean() {
        double result = FDFMath.meanFE.apply(dummy);
        assertEquals(result, 6.5, 0);
    }

    @Test
    public void testMean() throws IOException {
        List<Double> expectedMeans = CSVReader.readCSVBooleanFeature(fileFromPath(meanFile));
        List<Double> computedMeans = new ArrayList<>();

        for (Double[] arr : fftArrays) {
            computedMeans.add(FDFMath.meanFE.apply(unbox(arr)));
        }

        for (int ind = 0; ind < fftArrays.size(); ++ind) {
            assertEquals("Mean #" + ind, expectedMeans.get(ind), computedMeans.get(ind), delta);
        }
    }

    //Standard Deviation
    @Test
    public void testDummyStandardDeviation() {
        double result = FDFMath.standDevFE.apply(dummy);
        assertEquals(result, Math.sqrt(35.0 / 3.0), 0);
    }

    @Test
    public void testStandardDeviation() throws IOException {
        List<Double> expected = CSVReader.readCSVBooleanFeature(fileFromPath(stdDevFile));
        List<Double> computed = new ArrayList<>();

        for (Double[] arr : fftArrays) {
            computed.add(FDFMath.standDevFE.apply(unbox(arr)));
        }

        for (int ind = 0; ind < fftArrays.size(); ++ind) {
            assertEquals("Standard Deviation #" + Integer.toString(ind)
                , expected.get(ind), computed.get(ind), delta);
        }
    }

    //MEDIAN
    @Test
    public void testDummyMedian() {
        double result = FDFMath.medianFE.apply(dummy);
        assertEquals(result, 6.0, 0);
    }

    @Test
    public void testMedian() throws IOException {
        List<Double> expected = CSVReader.readCSVBooleanFeature(fileFromPath(medianFile));
        List<Double> computed = new ArrayList<>();

        for (Double[] arr : fftArrays) {
            computed.add(FDFMath.medianFE.apply(unbox(arr)));
        }

        for (int ind = 0; ind < fftArrays.size(); ++ind) {
            assertEquals("Median #" + Integer.toString(ind),
                expected.get(ind), computed.get(ind), delta);
        }
    }

    //FIRST QUARTILE
    @Test
    public void testDummyFirstQuartile() {
        double result = FDFMath.firstQuartileFE.apply(dummy);
        assertEquals(result, 3.0, 0);
    }

    @Test
    public void testFirstQuartile() throws IOException {
        List<Double> expected = CSVReader.readCSVBooleanFeature(fileFromPath(firstQuartileFile));
        List<Double> computed = new ArrayList<>();

        for (Double[] arr : fftArrays) {
            computed.add(FDFMath.firstQuartileFE.apply(unbox(arr)));
        }

        for (int ind = 0; ind < fftArrays.size(); ++ind) {
            assertEquals("First Quartile #" + Integer.toString(ind),
                expected.get(ind), computed.get(ind), delta);
        }
    }

    //THIRD QUARTILE
    @Test
    public void testDummyThirdQuartile() {
        double result = FDFMath.thirdQuartileFE.apply(dummy);
        assertEquals(result, 8.0, 0);
    }

    @Test
    public void testThirdQuartile() throws IOException {
        List<Double> expected = CSVReader.readCSVBooleanFeature(fileFromPath(thirdQuartileFile));
        List<Double> computed = new ArrayList<>();

        for (Double[] arr : fftArrays) {
            computed.add(FDFMath.thirdQuartileFE.apply(unbox(arr)));
        }

        for (int ind = 0; ind < fftArrays.size(); ++ind) {
            assertEquals("Third Quartile #" + Integer.toString(ind),
                expected.get(ind), computed.get(ind), delta);
        }
    }

    //SPECTRAL ENERGY
    @Test
    public void testDummySpectralEnergy() {
        double result = FDFMath.specEnergyFE.apply(dummy);
        assertEquals(204, result, 0);
    }

    @Test
    public void testSpectralEnergy() throws IOException {
        List<Double> expected = CSVReader.readCSVBooleanFeature(fileFromPath(specEnergyFile));
        List<Double> computed = new ArrayList<>();

        for (Double[] arr : fftArrays) {
            computed.add(FDFMath.specEnergyFE.apply(unbox(arr)));
        }

        for (int ind = 0; ind < fftArrays.size(); ++ind) {
            assertEquals("Spectral Energy #" + Integer.toString(ind),
                expected.get(ind), computed.get(ind), delta);
        }
    }

    //RMS
    @Test
    public void testDummyRMS() {
        double result = FDFMath.rmsFE.apply(dummy);
        assertEquals(Math.sqrt(51), result, 0);
    }

    @Test
    public void testRMS() throws IOException {
        List<Double> expected = CSVReader.readCSVBooleanFeature(fileFromPath(rmsFile));
        List<Double> computed = new ArrayList<>();

        for (Double[] arr : fftArrays) {
            computed.add(FDFMath.rmsFE.apply(unbox(arr)));
        }

        for (int ind = 0; ind < fftArrays.size(); ++ind) {
            assertEquals("RMS #" + Integer.toString(ind),
                expected.get(ind), computed.get(ind), delta);
        }
    }

    //SKEWNESS

    @Test
    public void testSkewness() throws IOException {
        List<Double> expected = CSVReader.readCSVBooleanFeature(fileFromPath(skewnessFile));
        List<Double> computed = new ArrayList<>();

        for (Double[] arr : fftArrays) {
            computed.add(FDFMath.skewnessFE.apply(unbox(arr)));
        }

        for (int ind = 0; ind < fftArrays.size(); ++ind) {
            assertEquals("Skewness #" + Integer.toString(ind),
                expected.get(ind), computed.get(ind), delta);
        }
    }

    //KURTOSIS

    @Test
    public void testKurtosis() throws IOException {
        List<Double> expected = CSVReader.readCSVBooleanFeature(fileFromPath(kurtosisFile));
        List<Double> computed = new ArrayList<>();

        for (Double[] arr : fftArrays) {
            computed.add(FDFMath.kurtosisFE.apply(unbox(arr)));
        }

        for (int ind = 0; ind < fftArrays.size(); ++ind) {
            assertEquals("Kurtosis #" + Integer.toString(ind),
                expected.get(ind), computed.get(ind), delta);
        }
    }

    //TOP MAGNITUDES
    @Test
    public void testDummyGetTopMags() {
        double[] expectedTopMags = {2, 1};
        double[] resultTopMags = FDFMath.getTopMagnitudes(binFreqs, 2)
            .stream()
            .mapToDouble(Double::doubleValue)
            .toArray();
        assertArrayEquals(expectedTopMags, resultTopMags, 0);
    }


    @Test
    public void testGetTopMags() {
        List<List<Double>> actualTopMags = new ArrayList<>();
        int noTopMags = expectedTopMags.get(0).length;

        for (Double[] arr : fftArrays) {
            actualTopMags.add(FDFMath.getTopMagnitudes(
                FDFMath.getPrincipalBinPairs(unbox(arr)),
                noTopMags));
        }

        for (int ind = 0; ind < fftArrays.size(); ++ind) {
            assertArrayEquals(unbox(expectedTopMags.get(ind)),
                doubleListToArray(actualTopMags.get(ind)), delta);
        }
    }

    //TOP FREQUENCIES
    @Test
    public void testDummyGetTopFreqs() {
        double[] expectedTopFreqs = {3, 1};
        double[] resultTopFreqs = FDFMath.getTopFrequencies(binFreqs, 2, 1)
            .stream()
            .mapToDouble(Double::doubleValue)
            .toArray();
        assertArrayEquals(expectedTopFreqs, resultTopFreqs, delta);
    }

    @Test
    public void testGetTopFreqs() {
        List<List<Double>> actualTopFreqs = new ArrayList<>();
        int noTopFreqs = expectedTopFreqs.get(0).length;

        for (Double[] arr : fftArrays) {
            actualTopFreqs.add(FDFMath.getTopFrequencies(
                FDFMath.getPrincipalBinPairs(unbox(arr)),
                noTopFreqs,
                1.0 //RESOLUTION ist 1.0
            ));
        }

        for (int ind = 0; ind < fftArrays.size(); ++ind) {
            assertArrayEquals(unbox(expectedTopFreqs.get(ind)),
                doubleListToArray(actualTopFreqs.get(ind)), delta);
        }
    }
}
