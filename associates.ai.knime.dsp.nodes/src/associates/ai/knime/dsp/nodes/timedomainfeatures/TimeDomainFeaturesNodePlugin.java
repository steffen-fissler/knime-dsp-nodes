package associates.ai.knime.dsp.nodes.timedomainfeatures;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * 
 * @author AI.Associates <knime@ai.associates>
 */
public class TimeDomainFeaturesNodePlugin extends Plugin {
  private static TimeDomainFeaturesNodePlugin plugin;

  public TimeDomainFeaturesNodePlugin() {
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

  public static TimeDomainFeaturesNodePlugin getDefault() {
    return plugin;
  }

}

