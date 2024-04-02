package csvmapper;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class CSV {
    private final String filePath;

    public CSV(String filePath) {
        this.filePath = filePath;
    }
    public List<Double> extractLocations(String columnHeader) {
        List<Double> locations = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String[] headers = br.readLine().split(",");
            int index = -1;
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equalsIgnoreCase(columnHeader)) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                throw new IllegalArgumentException("Column header not found: " + columnHeader);
            }

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > index) {
                    double location = Double.parseDouble(parts[index]);
                    locations.add(location);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return locations;
    }   
    public List<String> extractData(String columnHeader) {
        List<String> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String[] headers = br.readLine().split(",");
            int index = -1;
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equalsIgnoreCase(columnHeader)) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                throw new IllegalArgumentException("Column header not found: " + columnHeader);
            }
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > index) {
                    data.add(parts[index]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}


