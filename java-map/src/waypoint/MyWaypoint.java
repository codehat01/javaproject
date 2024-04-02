package waypoint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JButton;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

public class MyWaypoint extends DefaultWaypoint {
    private final String name;
    private JButton button;
    public MyWaypoint(String name, EventWaypoint event, GeoPosition coord, Icon icon) {
        super(coord);
        this.name = name;
        initButton(event, icon);
    }
    public String getName() {
        return name;
    }
    public JButton getButton() {
        return button;
    }
    private void initButton(EventWaypoint event, Icon icon) {
        button = new JButton(icon);
        button.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight()); 
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                event.selected(MyWaypoint.this);
            }
        });
    }
}
