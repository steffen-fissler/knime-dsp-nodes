package associates.ai.knime.dsp.nodes.welchaveraging;

import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentNumber;

/**
 * <code>NodeDialog</code> for the "WelchAveraging" Node.
 *
 * @author AI.Associates <knime@ai.associates>
 */
public class WelchAveragingNodeDialog extends DefaultNodeSettingsPane {

  private static final String LABEL_NUMBER_OF_ROWS = "Number of rows";
  private static final int COMP_WIDTH = 5;
  private static final int STEP_SIZE = 1;

  protected WelchAveragingNodeDialog() {
    super();

    WelchAveragingNodeConfig config = new WelchAveragingNodeConfig();

    addDialogComponent(new DialogComponentNumber(
        config.getNoRowsSetting(),
        LABEL_NUMBER_OF_ROWS, STEP_SIZE, COMP_WIDTH));

  }
}
