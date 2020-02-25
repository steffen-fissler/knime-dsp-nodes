package associates.ai.knime.dsp.nodes.timedomainfeatures;

import org.knime.core.node.defaultnodesettings.SettingsModel;
import org.knime.core.node.defaultnodesettings.SettingsModelBoolean;
import org.knime.core.node.defaultnodesettings.SettingsModelOptionalString;

import java.util.Arrays;
import java.util.HashSet;

public class TimeDomainFeaturesNodeConfig {

  public static final String CFGKEY_MEAN = "Mean";
  public static final String CFGKEY_STAND_DEV = "Standard Deviation";
  public static final String CFGKEY_MEDIAN = "Median";
  public static final String CFGKEY_25TH_PERC = "25th Percentile";
  public static final String CFGKEY_75TH_PERC = "75th Percentile";
  public static final String CFGKEY_RMS = "RMS";
  public static final String CFGKEY_SPECTRALENERGY = "SpectralEnergy";
  public static final String CFGKEY_SKEWNESS = "Skewness";
  public static final String CFGKEY_KURTOSIS = "Kurtosis";
  public static final String CFGKEY_PRINC_FREQS = "Principal Frequencies";
  public static final String CFGKEY_PRINC_MAGS = "Principal Magnitudes";
  public static final String CFGKEY_SAMPLE_RATE = "Sample Rate";
  public static final String CFGKEY_COL_PREFIX = "Column Prefix";
  
  private SettingsModelBoolean m_Mean;
  private SettingsModelBoolean m_StandDev;
  private SettingsModelBoolean m_Median;
  private SettingsModelBoolean m_25thPerc;
  private SettingsModelBoolean m_75thPerc;
  private SettingsModelBoolean m_RMS;
  private SettingsModelBoolean m_Skewness;
  private SettingsModelBoolean m_Kurtosis;
  private SettingsModelOptionalString m_ColPrefix;
  
  private HashSet<SettingsModel> settings;

  public TimeDomainFeaturesNodeConfig() {
    m_Mean = new SettingsModelBoolean(CFGKEY_MEAN, true);

    m_StandDev = new SettingsModelBoolean(CFGKEY_STAND_DEV, true);

    m_Median = new SettingsModelBoolean(CFGKEY_MEDIAN, true);

    m_25thPerc = new SettingsModelBoolean(CFGKEY_25TH_PERC, true);

    m_75thPerc = new SettingsModelBoolean(CFGKEY_75TH_PERC, true);

    m_RMS = new SettingsModelBoolean(CFGKEY_RMS, true);

    m_Skewness = new SettingsModelBoolean(CFGKEY_SKEWNESS, true);
    
    m_Kurtosis = new SettingsModelBoolean(CFGKEY_KURTOSIS, true);
    
    m_ColPrefix = new SettingsModelOptionalString(CFGKEY_COL_PREFIX, "TDF ", false);
    
    settings = new HashSet<>(Arrays.asList(
        m_Mean,
        m_StandDev,
        m_Median,
        m_25thPerc,
        m_75thPerc,
        m_RMS,
        m_Skewness,
        m_Kurtosis,
        m_ColPrefix));
    
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

  public boolean isKurtosis() {
    return m_Kurtosis.getBooleanValue();
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

  public SettingsModelBoolean getKurtosisSetting() {
    return m_Kurtosis;
  }
  
  public SettingsModelOptionalString getColPrefixSetting() {
    return m_ColPrefix;
  }
}
