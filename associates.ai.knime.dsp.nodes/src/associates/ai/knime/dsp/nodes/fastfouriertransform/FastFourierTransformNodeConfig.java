package associates.ai.knime.dsp.nodes.fastfouriertransform;

import org.knime.core.node.defaultnodesettings.SettingsModel;
import org.knime.core.node.defaultnodesettings.SettingsModelDoubleBounded;
import org.knime.core.node.defaultnodesettings.SettingsModelString;

import java.util.HashSet;

public class FastFourierTransformNodeConfig {
  private static final String CFGKEY_NORMALIZATION = "Normalization";
  private static final String CFGKEY_SAMPLE_RATE = "Sample Rate";


  private final SettingsModelString m_Normalization;
  private final SettingsModelDoubleBounded m_SampleRate;
  
  private HashSet<SettingsModel> settings;
  
  public FastFourierTransformNodeConfig() {
    settings = new HashSet<>();
    
    m_Normalization = new SettingsModelString(CFGKEY_NORMALIZATION, FFTMath.NORMALIZATION_STANDARD);
    settings.add(m_Normalization);
    
    m_SampleRate = new SettingsModelDoubleBounded(CFGKEY_SAMPLE_RATE, 1.0, 0.0,
        Double.MAX_VALUE);
    settings.add(m_SampleRate);
  }
  
  public HashSet<SettingsModel> getSettings() {
    return settings;
  }
  
  public String getNormalization() {
    return m_Normalization.getStringValue();
  }
  
  public Double getSampleRate() {
    return m_SampleRate.getDoubleValue();
  }
  
  public SettingsModelString getNormalizationSetting() {
    return m_Normalization;
  }
  
  public SettingsModelDoubleBounded getSampleRateSetting() {
    return m_SampleRate;
  }
}
