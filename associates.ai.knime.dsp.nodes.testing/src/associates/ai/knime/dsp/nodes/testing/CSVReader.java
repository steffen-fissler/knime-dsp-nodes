package associates.ai.knime.dsp.nodes.testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import associates.ai.knime.dsp.nodes.DSPBundleActivator;

public class CSVReader implements AutoCloseable {

    private final BufferedReader m_bufferedReader;
    
    public static List<Double[]> readCSV(File file) throws IOException {
        List<Double[]> result = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(
            new BufferedReader(new InputStreamReader(DSPBundleActivator.resolve("/" + file.getPath()).openStream())))) {
            
            String[] row;
            while ((row = csvReader.readNext()) != null) {
                Double[] doubRow = new Double[row.length];
    
                for (int ind = 0; ind < row.length; ++ind) {
                    doubRow[ind] = Double.parseDouble(row[ind]);
                }
                result.add(doubRow);
            }

        }

        return result;
    }       
    
    public static List<Double> readCSVBooleanFeature(File file) throws IOException {
        List<Double> result = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(
            new BufferedReader(new InputStreamReader(DSPBundleActivator.resolve("/" + file.getPath()).openStream())))) {

            String[] row;
            while ((row = csvReader.readNext()) != null) {
                result.addAll(Arrays.asList(row)
                    .stream()
                    .mapToDouble(Double::parseDouble)
                    .boxed()
                    .collect(Collectors.toList()));
            }
        }
        return result;
    }

    
    public CSVReader(final FileReader fileReader) {
        this(new BufferedReader(fileReader));
    }
    
    public CSVReader(final BufferedReader reader) {
        m_bufferedReader = reader;
    }
    
    public String[] readNext() throws IOException {
        String line = m_bufferedReader.readLine();
        return line != null ? line.split(",") : null;
    }
    
    @Override
    public void close() throws IOException {
        m_bufferedReader.close();
    }
}
