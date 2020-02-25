package associates.ai.knime.dsp.nodes.wavreader;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class WaveReaderNodePlugin extends Plugin {
  private static WaveReaderNodePlugin plugin;

  public WaveReaderNodePlugin() {
    super();
    plugin = this;
  }

  @Override
  public void start(final BundleContext context) throws Exception {
    super.start(context);

  }

  @Override
  public void stop(final BundleContext context) throws Exception {
    super.stop(context);
    plugin = null;
  }

  public static WaveReaderNodePlugin getDefault() {
    return plugin;
  }

}

