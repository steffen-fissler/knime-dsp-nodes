package associates.ai.knime.dsp.nodes.fastfouriertransform;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FFTMath {

    static final String NORMALIZATION_STANDARD = "standard";
    static final String NORMALIZATION_UNITARY = "unitary";

    static final List<String> SUPPORTED_NORMALIZATIONS = Collections.unmodifiableList(Arrays.asList(NORMALIZATION_STANDARD, NORMALIZATION_UNITARY));

    public interface Transformer {
        double[] transform(double[] row, String normalization, int frequencyBinCount);
    }

    public Transformer fastFourierTransformer = (row, normalization, frequencyBinCount) -> {
        FastFourierTransformer fastFourierTransformer1 =
            new FastFourierTransformer(getNormalization(normalization));
        Complex[] complexFFTResult = fastFourierTransformer1.transform(row, TransformType.FORWARD);

        return Arrays.stream(Arrays.copyOfRange(complexFFTResult, 0, frequencyBinCount))
            .mapToDouble(Complex::abs)
            .toArray();
    };

    private DftNormalization getNormalization(String cfgNormalization) {
        final DftNormalization normalization;

        switch (cfgNormalization) {
            case NORMALIZATION_STANDARD:
                normalization = DftNormalization.STANDARD;
                break;
            case NORMALIZATION_UNITARY:
                normalization = DftNormalization.UNITARY;
                break;
            default:
                normalization = DftNormalization.STANDARD;
                break;
        }

        return normalization;
    }
}
