package associates.ai.knime.dsp.nodes.frequencydomainfeatures;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "SpectralFeatures" Node.
 * 
 *
 * @author AI.Associates <knime@ai.associates>
 */
public class FrequencyDomainFeaturesNodeFactory extends NodeFactory<FrequencyDomainFeaturesNodeModel> {

  @Override
  public FrequencyDomainFeaturesNodeModel createNodeModel() {
    return new FrequencyDomainFeaturesNodeModel();
  }

  @Override
  public int getNrNodeViews() {
    return 0;
  }

  @Override
  public NodeView<FrequencyDomainFeaturesNodeModel> createNodeView(final int viewIndex,
      final FrequencyDomainFeaturesNodeModel nodeModel) {
    throw new IllegalStateException();
  }

  @Override
  public boolean hasDialog() {
    return true;
  }

  @Override
  public NodeDialogPane createNodeDialogPane() {
    return new FrequencyDomainFeaturesNodeDialog();
  }

}
