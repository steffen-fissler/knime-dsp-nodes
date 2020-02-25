package associates.ai.knime.dsp.nodes.windowslider;

import org.knime.core.data.DataColumnSpec;
import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentBoolean;
import org.knime.core.node.defaultnodesettings.DialogComponentColumnNameSelection;
import org.knime.core.node.defaultnodesettings.DialogComponentNumber;
import org.knime.core.node.util.ColumnFilter;

public class WindowSliderNodeDialog extends DefaultNodeSettingsPane {

  private static final String LABEL_WINDOW_LENGTH = "Window Length";
  private static final String LABEL_STEP = "Window Step";
  private static final String LABEL_ENABLED_PADDING = "Perform right padding with 0 values to include last row";


  private static final String LABEL_DATA_COLUMN = "Data column";
  private static final String MSG_NO_COLUMNS_AVAILABLE = "No columns available - this shouldn't happen!";


  private static final ColumnFilter DOUBLE_COLUMN_FILTER = new ColumnFilter() {
    @Override
    public boolean includeColumn(final DataColumnSpec colSpec) {
      return true;
    }

    @Override
    public String allFilteredMsg() {
      return MSG_NO_COLUMNS_AVAILABLE;
    }
  };

  private final WindowSliderNodeConfig config = new WindowSliderNodeConfig();

  protected WindowSliderNodeDialog() {
    super();

    addDialogComponent(new DialogComponentNumber(config.getWindowLengthSetting(),
        LABEL_WINDOW_LENGTH, /* step */ 1, /* componentwidth */ 5));
    addDialogComponent(new DialogComponentNumber(config.getStepSetting(),
        LABEL_STEP, /* step */ 1, /* componentwidth */ 5));

    addDialogComponent(new DialogComponentColumnNameSelection(
        config.getDataColumnSetting(), LABEL_DATA_COLUMN, 0, DOUBLE_COLUMN_FILTER));

    addDialogComponent(new DialogComponentBoolean(config.getEnabledPaddingSetting(),
        LABEL_ENABLED_PADDING));
  }
}

