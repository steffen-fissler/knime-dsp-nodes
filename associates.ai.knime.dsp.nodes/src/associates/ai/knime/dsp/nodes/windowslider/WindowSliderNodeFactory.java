package associates.ai.knime.dsp.nodes.windowslider;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

public class WindowSliderNodeFactory extends NodeFactory<WindowSliderNodeModel> {

  @Override
  public WindowSliderNodeModel createNodeModel() {
    return new WindowSliderNodeModel();
  }

  @Override
  public int getNrNodeViews() {
    return 0;
  }

  @Override
  public NodeView<WindowSliderNodeModel> createNodeView(final int viewIndex,
      final WindowSliderNodeModel nodeModel) {
    throw new IllegalStateException();
  }

  @Override
  public boolean hasDialog() {
    return true;
  }

  @Override
  public NodeDialogPane createNodeDialogPane() {
    return new WindowSliderNodeDialog();
  }

}

