package csvmapper;

import java.util.ArrayList;
import java.util.List;
public class Csvmapper {

    public static List<String> getOriginLocations(String filePath) {
        return getLocations(filePath, "originLat", "originLong");
    }

    public static List<String> getDestLocations(String filePath) {
        return getLocations(filePath, "destLat", "destLong");
    }
    private static List<String> getLocations(String filePath, String latitudeColumnName, String longitudeColumnName) {
        CSV csv = new CSV(filePath);

        List<Double> latitudes = csv.extractLocations(latitudeColumnName);
        List<Double> longitudes = csv.extractLocations(longitudeColumnName);

        return mergeLocations(latitudes, longitudes);
    }
    private static List<String> mergeLocations(List<Double> latitudes, List<Double> longitudes) {
        List<String> locations = new ArrayList<>();
        for (int i = 0; i < latitudes.size() && i < longitudes.size(); i++) {
            double latitude = latitudes.get(i);
            double longitude = longitudes.get(i);
            String location = latitude + "," + longitude;
            locations.add(location);
        }
        return locations;
    }
}
