package associates.ai.knime.dsp.nodes.timedomainfeatures;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "SignalFeatures" Node.
 * 
 * @author AI.Associates <knime@ai.associates>
 */
public class TimeDomainFeaturesNodeFactory extends NodeFactory<TimeDomainFeaturesNodeModel> {

  @Override
  public TimeDomainFeaturesNodeModel createNodeModel() {
    return new TimeDomainFeaturesNodeModel();
  }

  @Override
  public int getNrNodeViews() {
    return 0;
  }

  @Override
  public NodeView<TimeDomainFeaturesNodeModel> createNodeView(final int viewIndex,
      final TimeDomainFeaturesNodeModel nodeModel) {
    throw new IllegalStateException();
  }

  @Override
  public boolean hasDialog() {
    return true;
  }

  @Override
  public NodeDialogPane createNodeDialogPane() {
    return new TimeDomainFeaturesNodeDialog();
  }

}

