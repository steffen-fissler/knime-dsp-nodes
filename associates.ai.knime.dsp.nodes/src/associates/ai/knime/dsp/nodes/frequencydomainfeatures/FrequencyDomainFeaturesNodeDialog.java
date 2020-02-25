package associates.ai.knime.dsp.nodes.frequencydomainfeatures;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponent;
import org.knime.core.node.defaultnodesettings.DialogComponentBoolean;
import org.knime.core.node.defaultnodesettings.DialogComponentNumber;
import org.knime.core.node.defaultnodesettings.DialogComponentNumberEdit;
import org.knime.core.node.defaultnodesettings.DialogComponentOptionalString;

/**
 * <code>NodeDialog</code> for the "SpectralFeatures" Node.
 * 
 * @author AI.Associates <knime@ai.associates>
 */
public class FrequencyDomainFeaturesNodeDialog extends DefaultNodeSettingsPane {

  private final FrequencyDomainFeaturesNodeConfig config = new FrequencyDomainFeaturesNodeConfig();

  final static String LABEL_PADDING_VALUE_FOR_PRINCIPAL_FREQUENCIES_AND_MAGNITUDES = "Padding value for principal frequencies and magnitudes";

  protected FrequencyDomainFeaturesNodeDialog() {
    super();

    List<DialogComponent> d_Components = new ArrayList<DialogComponent>();

    DialogComponent c_Mean =
        new DialogComponentBoolean(config.getMeanSetting(), FrequencyDomainFeaturesNodeConfig.CFGKEY_MEAN);

    DialogComponent c_StandDev = new DialogComponentBoolean(config.getStandardDeviationSetting(),
        FrequencyDomainFeaturesNodeConfig.CFGKEY_STAND_DEV);

    DialogComponent c_Median = new DialogComponentBoolean(config.getMedianSetting(),
        FrequencyDomainFeaturesNodeConfig.CFGKEY_MEDIAN);

    DialogComponent c_25thPerc = new DialogComponentBoolean(config.getPercentile25thSetting(),
        FrequencyDomainFeaturesNodeConfig.CFGKEY_25TH_PERC);

    DialogComponent c_75thPerc = new DialogComponentBoolean(config.getPercentile75thSetting(),
        FrequencyDomainFeaturesNodeConfig.CFGKEY_75TH_PERC);

    DialogComponent c_SpecEnergy = new DialogComponentBoolean(config.getSpectralEnergySetting(),
        FrequencyDomainFeaturesNodeConfig.CFGKEY_SPECTRALENERGY);

    DialogComponent c_Rms =
        new DialogComponentBoolean(config.getRmsSetting(), FrequencyDomainFeaturesNodeConfig.CFGKEY_RMS);

    DialogComponent c_Skewness = new DialogComponentBoolean(config.getSkewnessSetting(),
        FrequencyDomainFeaturesNodeConfig.CFGKEY_SKEWNESS);

    DialogComponent c_Kurtosis = new DialogComponentBoolean(config.getKurtosisSetting(),
        FrequencyDomainFeaturesNodeConfig.CFGKEY_KURTOSIS);

    DialogComponentNumber c_princMags = new DialogComponentNumber(
        config.getPrincipalMagnitudesSetting(), FrequencyDomainFeaturesNodeConfig.CFGKEY_PRINC_MAGS, 1);

    DialogComponentNumber c_princFreqs = new DialogComponentNumber(
        config.getPrincipalFrequenciesSetting(), FrequencyDomainFeaturesNodeConfig.CFGKEY_PRINC_FREQS, 1);

    DialogComponentNumberEdit c_sampFreq = new DialogComponentNumberEdit(
        config.getSampleRateSetting(), FrequencyDomainFeaturesNodeConfig.CFGKEY_SAMPLE_RATE, 12);

    DialogComponentOptionalString c_ColPrefix = new DialogComponentOptionalString(
        config.getColPrefixSetting(), FrequencyDomainFeaturesNodeConfig.CFGKEY_COL_PREFIX);

    DialogComponent c_placeholderValue = new DialogComponentNumberEdit(
        config.getPaddingValuePrincipalFrequencyMagnitude(), LABEL_PADDING_VALUE_FOR_PRINCIPAL_FREQUENCIES_AND_MAGNITUDES);
    
    d_Components.addAll(Arrays.asList(
        c_Mean, 
        c_StandDev, 
        c_Median, 
        c_25thPerc, 
        c_75thPerc,
        c_SpecEnergy, 
        c_Rms, 
        c_Skewness, 
        c_Kurtosis, 
        c_princMags, 
        c_princFreqs, 
        c_sampFreq, 
        c_ColPrefix,
        c_placeholderValue));

    setHorizontalPlacement(false);
    for (DialogComponent comp : d_Components) {
      comp.getComponentPanel().setLayout(new FlowLayout(0));
      addDialogComponent(comp);
    }
  }
}
