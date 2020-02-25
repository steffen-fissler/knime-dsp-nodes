package associates.ai.knime.dsp.nodes.windowfunction;

import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentStringSelection;

/**
 * <code>NodeDialog</code> for the "WindowFunction" Node.
 * 
 * @author AI.Associates <knime@ai.associates>
 */
public class WindowFunctionNodeDialog extends DefaultNodeSettingsPane {

  public final String DIALOG_LABEL_WINDOW_FUNCTION = "Window Function";

  private final WindowFunctionNodeConfig config = new WindowFunctionNodeConfig();
  
  protected WindowFunctionNodeDialog() {
    super();

    
    
    addDialogComponent(new DialogComponentStringSelection(
        config.getWindowFunctionSetting(),
        DIALOG_LABEL_WINDOW_FUNCTION,
        "Hann", 
        "Hamming", 
        "Flat Top", 
        "Blackman"));
  }
}
