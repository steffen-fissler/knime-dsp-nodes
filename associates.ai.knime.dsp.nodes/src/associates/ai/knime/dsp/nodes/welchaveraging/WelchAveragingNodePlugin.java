package associates.ai.knime.dsp.nodes.welchaveraging;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 *
 * @author AI.Associates <knime@ai.associates>
 */
public class WelchAveragingNodePlugin extends Plugin {
  private static WelchAveragingNodePlugin plugin;

  public WelchAveragingNodePlugin() {
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

  public static WelchAveragingNodePlugin getDefault() {
    return plugin;
  }

}
