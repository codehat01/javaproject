package csvmapper;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.List;
import java.util.ArrayList;

abstract class Callstatus {
    protected final int callDuration;
    protected final String calledNumber;
    protected final String endTime;
    protected final String startTime;
    protected final String destinationCellID;
    protected final double originLatitude;
    protected final double originLongitude;
    protected final double destLatitude;
    protected final double destLongitude;
    

    public Callstatus(int callDuration, String calledNumber, String startTime, String endTime,
                      String destinationCellID, double originLatitude, double originLongitude,
                      double destLatitude, double destLongitude){
        this.callDuration = callDuration;
        this.calledNumber = calledNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.destinationCellID = destinationCellID;
        this.originLatitude = originLatitude;
        this.originLongitude = originLongitude;
        this.destLatitude = destLatitude;
        this.destLongitude = destLongitude;
    }

    public abstract String getStatus();
}


class CompletedCall extends Callstatus {

    public CompletedCall(int callDuration, String calledNumber, String startTime, String endTime,
                         String destinationCellID, double originLatitude, double originLongitude,
                         double destLatitude, double destLongitude) {
        super(callDuration, calledNumber, startTime, endTime, destinationCellID,
              originLatitude, originLongitude, destLatitude, destLongitude);
    }

    @Override
    public String getStatus() {
        return "Completed Call";
    }
}

class EmergencyCall extends Callstatus {

    public EmergencyCall(int callDuration, String calledNumber, String startTime, String endTime,
                         String destinationCellID, double originLatitude, double originLongitude,
                         double destLatitude, double destLongitude) {
        super(callDuration, calledNumber, startTime, endTime, destinationCellID,
              originLatitude, originLongitude, destLatitude, destLongitude);
    }

    @Override
    public String getStatus() {
        return "Emergency Call";
    }
}

class BusyCall extends Callstatus {

    public BusyCall(int callDuration, String calledNumber, String startTime, String endTime,
                    String destinationCellID, double originLatitude, double originLongitude,
                    double destLatitude, double destLongitude) {
        super(callDuration, calledNumber, startTime, endTime, destinationCellID,
              originLatitude, originLongitude, destLatitude, destLongitude);
    }

    @Override
    public String getStatus() {
        return "Busy Call";
    }
}

class OutofRange extends Callstatus {

    public OutofRange(int callDuration, String calledNumber, String startTime, String endTime,
                      String destinationCellID, double originLatitude, double originLongitude,
                      double destLatitude, double destLongitude) {
        super(callDuration, calledNumber, startTime, endTime, destinationCellID,
              originLatitude, originLongitude, destLatitude, destLongitude);
    }

    @Override
    public String getStatus() {
        return "Out of Range";
    }
}

class DroppedCall extends Callstatus {

    public DroppedCall(int callDuration, String calledNumber, String startTime, String endTime,
                       String destinationCellID, double originLatitude, double originLongitude,
                       double destLatitude, double destLongitude) {
        super(callDuration, calledNumber, startTime, endTime, destinationCellID,
              originLatitude, originLongitude, destLatitude, destLongitude);
    }

    @Override
    public String getStatus() {
        return "Dropped Call";
    }
}

public class Callclassification {
    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose CSV File");

        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files (*.csv)", "csv");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
    
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            CSV csv = new CSV(filePath);


            List<String> calledNumbers = csv.extractData("calledNumber");
            List<String> startTimes = csv.extractData("startTime");
            List<String> callDurations = csv.extractData("callDuration");
            List<String> endTimes = csv.extractData("endTime");
            List<String> destinationCellIDs = csv.extractData("destCellID");
            List<String> originLatitudes = csv.extractData("originLat");
            List<String> originLongitudes = csv.extractData("originLong");
            List<String> destLatitudes = csv.extractData("destLat");
            List<String> destLongitudes = csv.extractData("destLong");

            List<Callstatus> classifiedCalls = new ArrayList<>();
            for (int i = 0; i < callDurations.size(); i++) {
                int callDuration = Integer.parseInt(callDurations.get(i));
                String calledNumber = calledNumbers.get(i);
                String startTime = startTimes.get(i);
                String endTime = endTimes.get(i);
                String destinationCellID = destinationCellIDs.get(i);
                double originLatitude = Double.parseDouble(originLatitudes.get(i));
                double originLongitude = Double.parseDouble(originLongitudes.get(i));
                double destLatitude = Double.parseDouble(destLatitudes.get(i));
                double destLongitude = Double.parseDouble(destLongitudes.get(i));

                Callstatus call;

                if (callDuration > 0 && !endTime.isEmpty()) {
                    call = new CompletedCall(callDuration, calledNumber, startTime, endTime,
                            destinationCellID, originLatitude, originLongitude,
                            destLatitude, destLongitude);
                } else if (("108".equals(calledNumber) || "100".equals(calledNumber) || "911".equals(calledNumber)) && endTime.isEmpty()) {
                    call = new EmergencyCall(callDuration, calledNumber, startTime, endTime,
                            destinationCellID, originLatitude, originLongitude,
                            destLatitude, destLongitude);
                } else if (callDuration == 0 && endTime.isEmpty()) {
                    call = new BusyCall(callDuration, calledNumber, startTime, endTime,
                            destinationCellID, originLatitude, originLongitude,
                            destLatitude, destLongitude);
                } else if (destinationCellID.isEmpty()) {
                    call = new OutofRange(callDuration, calledNumber, startTime, endTime,
                            destinationCellID, originLatitude, originLongitude,
                            destLatitude, destLongitude);
                } else if (callDuration != 0 && endTime.isEmpty()) {
                    call = new DroppedCall(callDuration, calledNumber, startTime, endTime,
                            destinationCellID, originLatitude, originLongitude,
                            destLatitude, destLongitude);
                } else {
                    System.out.println("Call status can't be classified");
                    continue;
                }

                classifiedCalls.add(call);
            }
            for (Callstatus call : classifiedCalls) {
                System.out.println("Call Status: " + call.getStatus());
            }
        } else {
            System.out.println("No file selected.");
        }
    }
}

