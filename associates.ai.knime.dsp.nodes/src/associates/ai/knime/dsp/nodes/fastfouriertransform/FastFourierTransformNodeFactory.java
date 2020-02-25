package associates.ai.knime.dsp.nodes.fastfouriertransform;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "FastFourierTransform" Node.
 * 
 *
 * @author AI.Associates <knime@ai.associates>
 */
public class FastFourierTransformNodeFactory extends NodeFactory<FastFourierTransformNodeModel> {

  @Override
  public FastFourierTransformNodeModel createNodeModel() {
    return new FastFourierTransformNodeModel();
  }

  @Override
  public int getNrNodeViews() {
    return 0;
  }

  @Override
  public NodeView<FastFourierTransformNodeModel> createNodeView(final int viewIndex,
      final FastFourierTransformNodeModel nodeModel) {
    throw new IllegalStateException();
  }

  @Override
  public boolean hasDialog() {
    return true;
  }

  @Override
  public NodeDialogPane createNodeDialogPane() {
    return new FastFourierTransformNodeDialog();
  }

}

