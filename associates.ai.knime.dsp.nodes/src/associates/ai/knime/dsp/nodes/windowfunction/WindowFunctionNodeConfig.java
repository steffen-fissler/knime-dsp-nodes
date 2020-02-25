package associates.ai.knime.dsp.nodes.windowfunction;

import org.knime.core.node.defaultnodesettings.SettingsModelString;

public class WindowFunctionNodeConfig {
  static final String CFGKEY_WINDOW_FUNCTION = "Window Function";
  private final SettingsModelString m_WindowFunction;
  
  public WindowFunctionNodeConfig() {
    m_WindowFunction = new SettingsModelString(CFGKEY_WINDOW_FUNCTION, "Hann");
  }
  
  public String getWindowFunction() {
    return m_WindowFunction.getStringValue();
  }
  
  public SettingsModelString getWindowFunctionSetting() {
    return m_WindowFunction;
  }
}
