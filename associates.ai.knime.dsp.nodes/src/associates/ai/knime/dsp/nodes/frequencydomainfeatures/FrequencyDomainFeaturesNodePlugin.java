package associates.ai.knime.dsp.nodes.frequencydomainfeatures;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 *
 * @author AI.Associates <knime@ai.associates>
 */
public class FrequencyDomainFeaturesNodePlugin extends Plugin {
  private static FrequencyDomainFeaturesNodePlugin plugin;

  public FrequencyDomainFeaturesNodePlugin() {
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

  public static FrequencyDomainFeaturesNodePlugin getDefault() {
    return plugin;
  }

}
