package associates.ai.knime.dsp.nodes.windowslider;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class WindowSliderNodePlugin extends Plugin {
  private static WindowSliderNodePlugin plugin;

  public WindowSliderNodePlugin() {
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

  public static WindowSliderNodePlugin getDefault() {
    return plugin;
  }

}

