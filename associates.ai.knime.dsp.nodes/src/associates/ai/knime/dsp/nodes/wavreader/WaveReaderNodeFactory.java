package associates.ai.knime.dsp.nodes.wavreader;

import org.knime.core.node.ContextAwareNodeFactory;
import org.knime.core.node.NodeCreationContext;
import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeView;

public class WaveReaderNodeFactory extends ContextAwareNodeFactory<WaveReaderNodeModel> {

    @Override
    public WaveReaderNodeModel createNodeModel() {
        return new WaveReaderNodeModel();
    }

    @Override
    public int getNrNodeViews() {
        return 0;
    }

    @Override
    public NodeView<WaveReaderNodeModel> createNodeView(final int viewIndex, final WaveReaderNodeModel nodeModel) {
        throw new IllegalStateException();
    }

    @Override
    public boolean hasDialog() {
        return true;
    }

    @Override
    public NodeDialogPane createNodeDialogPane() {
        return new WaveReaderNodeDialog();
    }

    @Override
    public WaveReaderNodeModel createNodeModel(final NodeCreationContext context) {
        return new WaveReaderNodeModel(context);
    }

}
