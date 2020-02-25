package associates.ai.knime.dsp.nodes.windowfunction;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 *
 * @author AI.Associates <knime@ai.associates>
 */
public class WindowFunctionNodePlugin extends Plugin {
  private static WindowFunctionNodePlugin plugin;

  public WindowFunctionNodePlugin() {
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

  public static WindowFunctionNodePlugin getDefault() {
    return plugin;
  }
}

