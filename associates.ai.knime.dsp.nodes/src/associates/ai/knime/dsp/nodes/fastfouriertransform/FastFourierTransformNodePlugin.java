package associates.ai.knime.dsp.nodes.fastfouriertransform;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * @author AI.Associates <knime@ai.associates>
 */
public class FastFourierTransformNodePlugin extends Plugin {
  private static FastFourierTransformNodePlugin plugin;

  public FastFourierTransformNodePlugin() {
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

  public static FastFourierTransformNodePlugin getDefault() {
    return plugin;
  }

}

