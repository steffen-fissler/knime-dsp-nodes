package associates.ai.knime.dsp.nodes.windowfunction;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "WindowFunction" Node.
 * 
 *
 * @author AI.Associates <knime@ai.associates>
 */
public class WindowFunctionNodeFactory extends NodeFactory<WindowFunctionNodeModel> {

  @Override
  public WindowFunctionNodeModel createNodeModel() {
    return new WindowFunctionNodeModel();
  }

  @Override
  public int getNrNodeViews() {
    return 0;
  }

  @Override
  public NodeView<WindowFunctionNodeModel> createNodeView(final int viewIndex,
      final WindowFunctionNodeModel nodeModel) {
    throw new IllegalStateException();
  }

  @Override
  public boolean hasDialog() {
    return true;
  }

  @Override
  public NodeDialogPane createNodeDialogPane() {
    return new WindowFunctionNodeDialog();
  }

}

