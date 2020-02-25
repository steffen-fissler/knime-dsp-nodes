package associates.ai.knime.dsp.testing.timedomainfeatures;

import org.junit.Before;
import org.junit.Test;

import associates.ai.knime.dsp.nodes.timedomainfeatures.MathToolbox;

import static org.junit.Assert.assertEquals;

public class TimeDomainFeaturesTest {

    private double[] dummy = {3, 5, 7, 11};
    private MathToolbox mathToolbox;

    @Before
    public void setUp() {
        mathToolbox = new MathToolbox();
    }

    @Test
    public void meanFeatureTest() {
        double result = mathToolbox.MeanFeature.apply(dummy);
        assertEquals(6.5, result, 0);
    }

    @Test
    public void standardDeviationTest() {
        double result = mathToolbox.StandardDeviation.apply(dummy);
        assertEquals(Math.sqrt(35.0 / 3.0), result, 0);
    }

    @Test
    public void medianFeatureTest() {
        double result = mathToolbox.MedianFeature.apply(dummy);
        assertEquals(6.0, result, 0);
    }

    @Test
    public void firstQuartileFeatureTest() {
        double result = mathToolbox.FirstQuartileFeature.apply(dummy);
        assertEquals(3.5, result, 0);
    }

    @Test
    public void thirdQuartileFeatureTest() {
        double result = mathToolbox.ThirdQuartileFeature.apply(dummy);
        assertEquals(10, result, 0);
    }

    @Test
    public void RmsFeatureTest() {
        double result = mathToolbox.RmsFeature.apply(dummy);
        assertEquals(Math.sqrt(51), result, 0);
    }

    @Test
    public void skewnessFeatureTest() {
        double result = mathToolbox.KurtosisFeature.apply(dummy);
        assertEquals(0.34, result, 0.5);
    }

    @Test
    public void kurtosisFeatureTest() {
        double result = mathToolbox.KurtosisFeature.apply(dummy);
        assertEquals(0.34, result, 0.5);
    }
}

