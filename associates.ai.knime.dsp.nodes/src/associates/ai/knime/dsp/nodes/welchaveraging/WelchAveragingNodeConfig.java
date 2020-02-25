package associates.ai.knime.dsp.nodes.welchaveraging;

import org.knime.core.node.defaultnodesettings.SettingsModelIntegerBounded;

public class WelchAveragingNodeConfig {
  static final String CFG_KEY_NO_ROWS = "Number of Rows";
  
  private final SettingsModelIntegerBounded m_NoRows;
  
  public WelchAveragingNodeConfig() {
    this.m_NoRows = new SettingsModelIntegerBounded(CFG_KEY_NO_ROWS, 2, 1, Integer.MAX_VALUE);
  }
  
  public Integer getNoRows() {
    return m_NoRows.getIntValue();
  }
  
  public SettingsModelIntegerBounded getNoRowsSetting() {
    return m_NoRows;
  }
}
