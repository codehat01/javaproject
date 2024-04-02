package test;

import javax.swing.JOptionPane;
import csvmapper.Csvmapper;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import waypoint.EventWaypoint;
import waypoint.MyWaypoint;
import waypoint.WaypointRender;
import javax.swing.JFileChooser;

import javax.swing.event.MouseInputListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Main extends javax.swing.JFrame {
    private final Set<MyWaypoint> waypoints = new HashSet<>();
    private EventWaypoint event;


  public Main() {
        initComponents();
        init();
    }

    private void init() {
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jXMapViewer.setTileFactory(tileFactory);
        GeoPosition geo = new GeoPosition(11.1271, 78.6569);
        jXMapViewer.setAddressLocation(geo);
        jXMapViewer.setZoom(12);
        
        MouseInputListener mm = new PanMouseInputListener(jXMapViewer);
        jXMapViewer.addMouseListener(mm);
        jXMapViewer.addMouseMotionListener(mm);
        jXMapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(jXMapViewer));
        event = getEvent();
    }

  private void pinLocations(String filePath) {
    List<String> originLocations = Csvmapper.getOriginLocations(filePath);
    List<String> destLocations = Csvmapper.getDestLocations(filePath);

    if (originLocations == null || destLocations == null || originLocations.isEmpty() || destLocations.isEmpty()) {
        System.out.println("No locations retrieved.");
        return;
    }

    Icon originIcon = new ImageIcon(getClass().getResource("/icon/pin.png")); // Origin icon
    Icon destIcon = new ImageIcon(getClass().getResource("/icon/pin4.png")); // Destination icon

    for (int i = 0; i < originLocations.size() && i < destLocations.size(); i++) {
        String originLocation = originLocations.get(i);
        MyWaypoint origin = new MyWaypoint("Origin " + (i + 1), event, parseGeoPosition(originLocation), originIcon);
        addWaypoint(origin);

        String destLocation = destLocations.get(i);
        MyWaypoint destination = new MyWaypoint("Destination " + (i + 1), event, parseGeoPosition(destLocation), destIcon);
        addWaypoint(destination);
    }
}


    private GeoPosition parseGeoPosition(String location) {
        String[] parts = location.split(",");
        double latitude = Double.parseDouble(parts[0].trim());
        double longitude = Double.parseDouble(parts[1].trim());
        return new GeoPosition(latitude, longitude);
    }

    private void chooseCsvFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose CSV File");

        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files (*.csv)", "csv");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            pinLocations(filePath);
        } else {
            JOptionPane.showMessageDialog(this, "No file selected.");
        }
    }

private void addWaypoint(MyWaypoint waypoint) {
        for (MyWaypoint d : waypoints) {
            jXMapViewer.remove(d.getButton());
        }
        waypoints.add(waypoint);
        initWaypoint();
}

    private void initWaypoint() {
        WaypointPainter<MyWaypoint> wp = new WaypointRender();
        wp.setWaypoints(waypoints);
        jXMapViewer.setOverlayPainter(wp);
        for (MyWaypoint d : waypoints) {
            jXMapViewer.add(d.getButton());
        }
    }
    private void clearWaypoint() {
    for (MyWaypoint d : waypoints) {
        jXMapViewer.remove(d.getButton());
    }
    waypoints.clear();
    initWaypoint();
}
     private EventWaypoint getEvent() {
        return (MyWaypoint waypoint) -> {
            JOptionPane.showMessageDialog(Main.this, waypoint.getName());
        };
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jXMapViewer = new org.jxmapviewer.JXMapViewer();
        comboMapType = new javax.swing.JComboBox<>();
        cmdAdd = new javax.swing.JButton();
        cmdClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        comboMapType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Open Stree", "Virtual Earth", "Hybrid", "Satellite" }));
        comboMapType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMapTypeActionPerformed(evt);
            }
        });

        cmdAdd.setText("Add Waypoint");
        cmdAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAddActionPerformed(evt);
            }
        });

        cmdClear.setText("Clear Waypoint");
        cmdClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXMapViewerLayout = new javax.swing.GroupLayout(jXMapViewer);
        jXMapViewer.setLayout(jXMapViewerLayout);
        jXMapViewerLayout.setHorizontalGroup(
            jXMapViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXMapViewerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmdAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmdClear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 564, Short.MAX_VALUE)
                .addComponent(comboMapType, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jXMapViewerLayout.setVerticalGroup(
            jXMapViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXMapViewerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXMapViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboMapType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdAdd)
                    .addComponent(cmdClear))
                .addContainerGap(486, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jXMapViewer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jXMapViewer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void comboMapTypeActionPerformed(java.awt.event.ActionEvent evt) {                                             
//GEN-FIRST:event_comboMapTypeActionPerformed
        TileFactoryInfo info;
        int index = comboMapType.getSelectedIndex();
        info = switch (index) {
            case 0 -> new OSMTileFactoryInfo();
            case 1 -> new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
            case 2 -> new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
            default -> new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
        };
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jXMapViewer.setTileFactory(tileFactory);
    
    }//GEN-LAST:event_comboMapTypeActionPerformed

    private void cmdAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAddActionPerformed
        chooseCsvFile();
    }//GEN-LAST:event_cmdAddActionPerformed

    private void cmdClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdClearActionPerformed
        clearWaypoint();
    }//GEN-LAST:event_cmdClearActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdAdd;
    private javax.swing.JButton cmdClear;
    private javax.swing.JComboBox<String> comboMapType;
    private org.jxmapviewer.JXMapViewer jXMapViewer;
    // End of variables declaration//GEN-END:variables
}

