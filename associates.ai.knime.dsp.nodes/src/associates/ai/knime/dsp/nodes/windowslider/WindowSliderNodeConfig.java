package associates.ai.knime.dsp.nodes.windowslider;

import java.util.Arrays;
import java.util.HashSet;

import org.knime.core.node.defaultnodesettings.SettingsModel;
import org.knime.core.node.defaultnodesettings.SettingsModelBoolean;
import org.knime.core.node.defaultnodesettings.SettingsModelDouble;
import org.knime.core.node.defaultnodesettings.SettingsModelIntegerBounded;
import org.knime.core.node.defaultnodesettings.SettingsModelString;

public class WindowSliderNodeConfig {
  static final String CFGKEY_WINDOW_LENGTH = "Window Length";
  static final String CFGKEY_STEP = "Window Step";
  static final String CFGKEY_ENABLED_PADDING = "Perform Padding";
  static final String CFGKEY_DATA_COLUMN_NAME = "data_column_name";

  private final SettingsModelIntegerBounded m_WindowLength;
  private final SettingsModelIntegerBounded m_Step;
  private final SettingsModelBoolean m_EnabledPadding;

  private final SettingsModelString m_Data_Column;

  private HashSet<SettingsModel> settings = new HashSet<>();

  public WindowSliderNodeConfig() {
    m_WindowLength = new SettingsModelIntegerBounded(CFGKEY_WINDOW_LENGTH, 128, Integer.MIN_VALUE,
        Integer.MAX_VALUE);

    m_Step = new SettingsModelIntegerBounded(CFGKEY_STEP, 64, Integer.MIN_VALUE, Integer.MAX_VALUE);

    m_EnabledPadding = new SettingsModelBoolean(CFGKEY_ENABLED_PADDING, true);


    m_Data_Column = new SettingsModelString(CFGKEY_DATA_COLUMN_NAME, null);

    settings.addAll(Arrays.asList(m_WindowLength, m_Step, m_EnabledPadding, m_Data_Column));
  }

  public HashSet<SettingsModel> getSettings() {
    return settings;
  }

  public Integer getWindowLength() {
    return m_WindowLength.getIntValue();
  }

  public Integer getStep() {
    return m_Step.getIntValue();
  }

  public Boolean isEnabledPadding() {
    return m_EnabledPadding.getBooleanValue();
  }

  public String getDataColumnName() { return getDataColumnSetting().getStringValue(); }

  public SettingsModelIntegerBounded getWindowLengthSetting() {
    return m_WindowLength;
  }

  public SettingsModelIntegerBounded getStepSetting() {
    return m_Step;
  }

  public SettingsModelBoolean getEnabledPaddingSetting() {
    return m_EnabledPadding;
  }

  public SettingsModelString getDataColumnSetting() { return m_Data_Column; }

  public String setDataColumnName(final String dataColumnName) {
      m_Data_Column.setStringValue(dataColumnName);

      return getDataColumnName();
  }

  public boolean isDataColumnNotConfigured() {
      return getDataColumnName() == null;
  }
}
