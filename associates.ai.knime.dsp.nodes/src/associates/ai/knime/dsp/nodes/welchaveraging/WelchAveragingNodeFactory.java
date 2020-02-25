package associates.ai.knime.dsp.nodes.welchaveraging;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "WelchAveraging" Node.
 * 
 * @author AI.Associates <knime@ai.associates>
 */
public class WelchAveragingNodeFactory extends NodeFactory<WelchAveragingNodeModel> {

  @Override
  public WelchAveragingNodeModel createNodeModel() {
    return new WelchAveragingNodeModel();
  }

  @Override
  public int getNrNodeViews() {
    return 0;
  }

  @Override
  public NodeView<WelchAveragingNodeModel> createNodeView(final int viewIndex,
      final WelchAveragingNodeModel nodeModel) {
    throw new IllegalStateException();
  }

  @Override
  public boolean hasDialog() {
    return true;
  }

  @Override
  public NodeDialogPane createNodeDialogPane() {
    return new WelchAveragingNodeDialog();
  }

}

