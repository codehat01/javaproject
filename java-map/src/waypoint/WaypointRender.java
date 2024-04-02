package waypoint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.WaypointPainter;

public class WaypointRender extends WaypointPainter<MyWaypoint> {
    @Override
    protected void doPaint(Graphics2D g, JXMapViewer map, int width, int height) {     
        List<Point2D> waypointsPositions = new ArrayList<>();
        for (MyWaypoint wp : getWaypoints()) {
            Point2D p = map.getTileFactory().geoToPixel(wp.getPosition(), map.getZoom());
            Rectangle rec = map.getViewportBounds();
            int x = (int) (p.getX() - rec.getX());
            int y = (int) (p.getY() - rec.getY());
            JButton cmd = wp.getButton();
            cmd.setLocation(x - cmd.getWidth() / 2, y - cmd.getHeight()); 
            waypointsPositions.add(new Point2D.Double(x, y));
        }
        g.setColor(Color.GREEN);
        g.setStroke(new BasicStroke(2));     
        for (int i = 0; i < waypointsPositions.size() - 1; i++) {
            Point2D startPoint = waypointsPositions.get(i);
            Point2D endPoint = waypointsPositions.get(i + 1);
            g.drawLine((int) startPoint.getX(), (int) startPoint.getY(), (int) endPoint.getX(), (int) endPoint.getY());
        }
    }
}
