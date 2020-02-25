package associates.ai.knime.dsp.nodes.wavreader;

import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentFileChooser;

class WaveReaderNodeDialog extends DefaultNodeSettingsPane {

  WaveReaderNodeDialog() {
    super();

    final WaveReaderNodeConfig config = new WaveReaderNodeConfig();

    addDialogComponent(new DialogComponentFileChooser(config.getFilePathSetting(), "historyID", ".wav"));
  }
}

