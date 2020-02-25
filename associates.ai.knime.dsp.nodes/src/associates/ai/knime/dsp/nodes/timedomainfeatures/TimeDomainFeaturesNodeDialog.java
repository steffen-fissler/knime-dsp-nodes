package associates.ai.knime.dsp.nodes.timedomainfeatures;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponent;
import org.knime.core.node.defaultnodesettings.DialogComponentBoolean;
import org.knime.core.node.defaultnodesettings.DialogComponentOptionalString;

/**
 * <code>NodeDialog</code> for the "SignalFeatures" Node.
 * 
 * @author AI.Associates <knime@ai.associates>
 */
public class TimeDomainFeaturesNodeDialog extends DefaultNodeSettingsPane {
  private final TimeDomainFeaturesNodeConfig config = new TimeDomainFeaturesNodeConfig();

  public static final String LABEL_MEAN = "Mean";
  public static final String LABEL_STAND_DEV = "Standard Deviation";
  public static final String LABEL_MEDIAN = "Median";
  public static final String LABEL_25TH_PERC = "25th Percentile";
  public static final String LABEL_75TH_PERC = "75th Percentile";
  public static final String LABEL_RMS = "RMS";
  public static final String LABEL_SKEWNESS = "Skewness";
  public static final String LABEL_KURTOSIS = "Kurtosis";
  public static final String LABEL_COL_PREFIX = "Column Prefix";


  protected TimeDomainFeaturesNodeDialog() {
    super();

    List<DialogComponent> d_Components = new ArrayList<DialogComponent>();

    DialogComponent c_Mean =
        new DialogComponentBoolean(config.getMeanSetting(), LABEL_MEAN);

    DialogComponent c_StandDev = new DialogComponentBoolean(config.getStandardDeviationSetting(),
        LABEL_STAND_DEV);

    DialogComponent c_Median = new DialogComponentBoolean(config.getMedianSetting(),
        LABEL_MEDIAN);

    DialogComponent c_25thPerc = new DialogComponentBoolean(config.getPercentile25thSetting(),
        LABEL_25TH_PERC);

    DialogComponent c_75thPerc = new DialogComponentBoolean(config.getPercentile75thSetting(),
        LABEL_75TH_PERC);

    DialogComponent c_Rms =
        new DialogComponentBoolean(config.getRmsSetting(), LABEL_RMS);

    DialogComponent c_Skewness = new DialogComponentBoolean(config.getSkewnessSetting(),
        LABEL_SKEWNESS);

    DialogComponent c_Kurtosis = new DialogComponentBoolean(config.getKurtosisSetting(),
        LABEL_KURTOSIS);

    DialogComponentOptionalString c_ColPrefix = new DialogComponentOptionalString(
        config.getColPrefixSetting(), LABEL_COL_PREFIX);
    
    d_Components.addAll(Arrays.asList(
        c_Mean, 
        c_StandDev, 
        c_Median, 
        c_25thPerc, 
        c_75thPerc, 
        c_Rms,
        c_Skewness, 
        c_Kurtosis,
        c_ColPrefix));

    setHorizontalPlacement(false);
    for (DialogComponent comp : d_Components) {
      comp.getComponentPanel().setLayout(new FlowLayout(0));
      addDialogComponent(comp);
    }

  }
}

