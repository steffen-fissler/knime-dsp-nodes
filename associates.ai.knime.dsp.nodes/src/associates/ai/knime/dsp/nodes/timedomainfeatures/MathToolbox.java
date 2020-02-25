package associates.ai.knime.dsp.nodes.timedomainfeatures;

import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math3.stat.descriptive.moment.Skewness;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MathToolbox {

    public interface Feature {
        double apply(double[] row);
    }

    public Feature MeanFeature = (row) -> Arrays.stream(row).reduce(0, (c, a) -> c + a) / row.length;

    public Feature StandardDeviation = (row) -> {
        double mean = MeanFeature.apply(row);

        return Math.sqrt(
            Arrays.stream(row)
                .reduce(0, (c, a) -> c + (a - mean) * (a - mean))
                / ((double) (row.length - 1))
        );
    };

    public Feature MedianFeature = (row) -> (new Percentile()).evaluate(row, 50.0);

    public Feature FirstQuartileFeature = (row) -> (new Percentile()).evaluate(row, 25.0);

    public Feature ThirdQuartileFeature = (row) -> (new Percentile()).evaluate(row, 75.0);

    public Feature RmsFeature = (row) -> Math.sqrt(Arrays.stream(row)
        .boxed()
        .mapToDouble(value -> (1 / (double) row.length) * value * value).sum());

    public Feature SkewnessFeature = (row) -> (new Skewness()).evaluate(row, 0, row.length);

    public Feature KurtosisFeature = (row) -> (new Kurtosis()).evaluate(row, 0, row.length);

    public Map<String, Feature> getFeatures() {

        HashMap<String, Feature> featureHashMap = new HashMap<>();
        featureHashMap.put(TimeDomainFeaturesNodeConfig.CFGKEY_MEAN, MeanFeature);
        featureHashMap.put(TimeDomainFeaturesNodeConfig.CFGKEY_STAND_DEV, StandardDeviation);
        featureHashMap.put(TimeDomainFeaturesNodeConfig.CFGKEY_MEDIAN, MedianFeature);
        featureHashMap.put(TimeDomainFeaturesNodeConfig.CFGKEY_25TH_PERC, FirstQuartileFeature);
        featureHashMap.put(TimeDomainFeaturesNodeConfig.CFGKEY_75TH_PERC, ThirdQuartileFeature);
        featureHashMap.put(TimeDomainFeaturesNodeConfig.CFGKEY_RMS, RmsFeature);
        featureHashMap.put(TimeDomainFeaturesNodeConfig.CFGKEY_SKEWNESS, SkewnessFeature);
        featureHashMap.put(TimeDomainFeaturesNodeConfig.CFGKEY_KURTOSIS, KurtosisFeature);

        return featureHashMap;
    }
}
