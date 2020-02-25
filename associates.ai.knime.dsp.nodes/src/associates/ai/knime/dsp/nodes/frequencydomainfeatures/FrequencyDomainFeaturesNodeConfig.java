package associates.ai.knime.dsp.nodes.frequencydomainfeatures;

import org.knime.core.node.defaultnodesettings.*;

import java.util.Arrays;
import java.util.HashSet;

public class FrequencyDomainFeaturesNodeConfig {

  static final String CFGKEY_MEAN = "Mean";
  static final String CFGKEY_STAND_DEV = "Standard Deviation";
  static final String CFGKEY_MEDIAN = "Median";
  static final String CFGKEY_25TH_PERC = "25th Percentile";
  static final String CFGKEY_75TH_PERC = "75th Percentile";
  static final String CFGKEY_RMS = "RMS";
  static final String CFGKEY_SPECTRALENERGY = "SpectralEnergy";
  static final String CFGKEY_SKEWNESS = "Skewness";
  static final String CFGKEY_KURTOSIS = "Kurtosis";
  static final String CFGKEY_PRINC_FREQS = "Principal Frequencies";
  static final String CFGKEY_PRINC_MAGS = "Principal Magnitude";
  static final String CFGKEY_SAMPLE_RATE = "Sample Rate";
  static final String CFGKEY_COL_PREFIX = "Column Prefix";
  static final String CFGKEY_PADDING_VALUE_PRINCIPAL_FREQUENCY_MAGNITUDE = "padding_value_principal_frequency_magnitude";

  private SettingsModelBoolean m_Mean;
  private SettingsModelBoolean m_StandDev;
  private SettingsModelBoolean m_Median;
  private SettingsModelBoolean m_25thPerc;
  private SettingsModelBoolean m_75thPerc;
  private SettingsModelBoolean m_SpectralEnergy;
  private SettingsModelBoolean m_RMS;
  private SettingsModelBoolean m_Skewness;
  private SettingsModelBoolean m_Kurtosis;
  private SettingsModelIntegerBounded m_PrincMags;
  private SettingsModelIntegerBounded m_PrincFreqs;
  private SettingsModelDoubleBounded m_SampleRate;
  private SettingsModelOptionalString m_ColPrefix;
  private SettingsModelDouble m_PaddingValuePrincipalFrequencyMagnitude;

  private HashSet<SettingsModel> settings;

  public FrequencyDomainFeaturesNodeConfig() {
    m_Mean = new SettingsModelBoolean(CFGKEY_MEAN, true);

    m_StandDev = new SettingsModelBoolean(CFGKEY_STAND_DEV, true);

    m_Median = new SettingsModelBoolean(CFGKEY_MEDIAN, true);

    m_25thPerc = new SettingsModelBoolean(CFGKEY_25TH_PERC, true);

    m_75thPerc = new SettingsModelBoolean(CFGKEY_75TH_PERC, true);

    m_SpectralEnergy = new SettingsModelBoolean(CFGKEY_SPECTRALENERGY, true);

    m_RMS = new SettingsModelBoolean(CFGKEY_RMS, true);

    m_Skewness = new SettingsModelBoolean(CFGKEY_SKEWNESS, true);

    m_Kurtosis = new SettingsModelBoolean(CFGKEY_KURTOSIS, true);

    m_PrincMags = new SettingsModelIntegerBounded(CFGKEY_PRINC_MAGS, 3, 0, Integer.MAX_VALUE);

    m_PrincFreqs = new SettingsModelIntegerBounded(CFGKEY_PRINC_FREQS, 3, 0, Integer.MAX_VALUE);

    m_SampleRate = new SettingsModelDoubleBounded(CFGKEY_SAMPLE_RATE, 1.0, 0.0, Double.MAX_VALUE);

    m_ColPrefix = new SettingsModelOptionalString(CFGKEY_COL_PREFIX, "FDF ", false);

    m_PaddingValuePrincipalFrequencyMagnitude = new SettingsModelDouble(CFGKEY_PADDING_VALUE_PRINCIPAL_FREQUENCY_MAGNITUDE, 0.0);

    settings = new HashSet<SettingsModel>(Arrays.asList(
        m_Mean,
        m_StandDev,
        m_Median,
        m_25thPerc,
        m_75thPerc,
        m_SpectralEnergy,
        m_RMS,
        m_Skewness,
        m_Kurtosis,
        m_PrincMags,
        m_PrincFreqs,
        m_SampleRate,
        m_ColPrefix,
        m_PaddingValuePrincipalFrequencyMagnitude));
  }

  public HashSet<SettingsModel> featureSettings() {
    return settings;
  }

  public boolean isMean() {
    return m_Mean.getBooleanValue();
  }

  public boolean isStandardDeviation() {
    return m_StandDev.getBooleanValue();
  }

  public boolean isMedian() {
    return m_Median.getBooleanValue();
  }

  public boolean isPercentile25th() {
    return m_25thPerc.getBooleanValue();
  }

  public boolean isPercentile75th() {
    return m_75thPerc.getBooleanValue();
  }

  public boolean isRms() {
    return m_RMS.getBooleanValue();
  }

  public boolean isSkewness() {
    return m_Skewness.getBooleanValue();
  }

  public boolean isSpectralEnergy() {
    return m_SpectralEnergy.getBooleanValue();
  }

  public boolean isKurtosis() {
    return m_Kurtosis.getBooleanValue();
  }

  public int getPrincipalMagnitudes() {
    return m_PrincMags.getIntValue();
  }

  public int getPrincipalFrequencies() {
    return m_PrincFreqs.getIntValue();
  }

  public double getSampleRate() {
    return m_SampleRate.getDoubleValue();
  }

  public String getColPrefix() {
    if(m_ColPrefix.isActive()) return m_ColPrefix.getStringValue();
    else return "";
  }

  public SettingsModelBoolean getMeanSetting() {
    return m_Mean;
  }

  public SettingsModelBoolean getStandardDeviationSetting() {
    return m_StandDev;
  }

  public SettingsModelBoolean getMedianSetting() {
    return m_Median;
  }

  public SettingsModelBoolean getPercentile25thSetting() {
    return m_25thPerc;
  }

  public SettingsModelBoolean getPercentile75thSetting() {
    return m_75thPerc;
  }

  public SettingsModelBoolean getRmsSetting() {
    return m_RMS;
  }

  public SettingsModelBoolean getSkewnessSetting() {
    return m_Skewness;
  }

  public SettingsModelBoolean getSpectralEnergySetting() {
    return m_SpectralEnergy;
  }

  public SettingsModelBoolean getKurtosisSetting() {
    return m_Kurtosis;
  }

  public SettingsModelIntegerBounded getPrincipalMagnitudesSetting() {
    return m_PrincMags;
  }

  public SettingsModelIntegerBounded getPrincipalFrequenciesSetting() {
    return m_PrincFreqs;
  }

  public SettingsModelDoubleBounded getSampleRateSetting() {
    return m_SampleRate;
  }

  public SettingsModelOptionalString getColPrefixSetting() {
    return m_ColPrefix;
  }

  public SettingsModelDouble getPaddingValuePrincipalFrequencyMagnitude() {
    return m_PaddingValuePrincipalFrequencyMagnitude;
  }
}
