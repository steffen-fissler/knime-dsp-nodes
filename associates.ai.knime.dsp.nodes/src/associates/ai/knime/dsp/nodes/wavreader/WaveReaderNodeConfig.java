package associates.ai.knime.dsp.nodes.wavreader;

import java.nio.file.Path;

import org.knime.core.node.defaultnodesettings.SettingsModelString;

public class WaveReaderNodeConfig {

    private static final String CFG_KEY_FILEPATH = "FilePath";

    private final SettingsModelString modelFilePath;

    public WaveReaderNodeConfig() {
        this.modelFilePath = new SettingsModelString(CFG_KEY_FILEPATH, "");
    }

    public WaveReaderNodeConfig(final String path) {
        this.modelFilePath = new SettingsModelString(CFG_KEY_FILEPATH, path);
    }

    public String getFilePath() {
        return modelFilePath.getStringValue();
    }

    public SettingsModelString getFilePathSetting() {
        return modelFilePath;
    }

}
