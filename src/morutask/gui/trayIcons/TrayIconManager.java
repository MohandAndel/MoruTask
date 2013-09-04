package morutask.gui.trayIcons;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 8/31/13
 * Time: 7:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class TrayIconManager {

    private List<TrayIconSupported> icons;
    private TrayIconSupported currentIcon;
    private SystemTray tray;
    private static TrayIconManager instance;

    private TrayIconManager() {
        if (!SystemTray.isSupported()) {
            try {
                throw new Exception("SystemTray is not supported");
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        icons = new ArrayList<>();
        tray = SystemTray.getSystemTray();
        currentIcon = null;

//        tray.addPropertyChangeListener("trayIcons", new PropertyChangeListener() {
//            @Override
//            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
//                System.out.println("Tray Icon Changed ...");
//            }
//        });
    }

    public static TrayIconManager getInstance() {
        if (instance == null)
            instance = new TrayIconManager();

        return instance;
    }

    public void addTrayIcon(TrayIconSupported icon) {
        this.icons.add(icon);
    }

    public List<TrayIconSupported> getIcons() {
        return this.icons;
    }

    public void fireShowTrayIcon(String tagIconName, Object data) throws AWTException {

        Iterator<TrayIconSupported> iterator = this.icons.iterator();

        while (iterator.hasNext()) {
            TrayIconSupported next = iterator.next();

            if (next.getTagName().equals(tagIconName)) {
                showTray(next, data);
                return;
            }
        }

    }

    public void showTray(TrayIconSupported icon, Object data) throws AWTException {

        disableTray();
        icon.onShow(data);
        currentIcon = icon;
        tray.add(icon.getTrayIcon());

    }

    public void disableTray() {
        if (currentIcon != null) {
            tray.remove(currentIcon.getTrayIcon());
            currentIcon = null;
        }
    }


}
