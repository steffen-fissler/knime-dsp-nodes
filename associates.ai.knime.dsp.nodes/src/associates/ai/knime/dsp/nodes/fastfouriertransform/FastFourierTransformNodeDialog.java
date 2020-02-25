package associates.ai.knime.dsp.nodes.fastfouriertransform;

import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentNumberEdit;
import org.knime.core.node.defaultnodesettings.DialogComponentStringSelection;

/**
 * <code>NodeDialog</code> for the "FastFourierTransform" Node.
 *
 * @author AI.Associates <knime@ai.associates>
 */
public class FastFourierTransformNodeDialog extends DefaultNodeSettingsPane {

  static final String LABEL_NORMALIZATION = "Normalization";
  static final String LABEL_SAMPLE_RATE = "Sample Rate";


  protected FastFourierTransformNodeDialog() {
    super();

    FastFourierTransformNodeConfig config = new FastFourierTransformNodeConfig();

    addDialogComponent(new DialogComponentStringSelection(
        config.getNormalizationSetting(), LABEL_NORMALIZATION, FFTMath.SUPPORTED_NORMALIZATIONS));

    DialogComponentNumberEdit c_sampFreq =
        new DialogComponentNumberEdit(config.getSampleRateSetting(), LABEL_SAMPLE_RATE, 12);

    addDialogComponent(c_sampFreq);
  }
}
