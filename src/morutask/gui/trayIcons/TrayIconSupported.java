package morutask.gui.trayIcons;

import java.awt.*;
import java.awt.event.MouseListener;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 8/31/13
 * Time: 3:18 AM
 * To change this template use File | Settings | File Templates.
 */
public interface TrayIconSupported {

    public void setImage(Image image);

    public Image getImage();

    public void setTagName(String tag);

    public String getTagName();

    public void addMouseListener(MouseListener mouseListener);

    //public MouseListener getMouseListener();
    public void setMenu(PopupMenu menu);

    public PopupMenu getMenu();

    public void addMenuItem(MenuItem item);

    public MenuItem getMenuItem();

    public TrayIcon getTrayIcon();

    public <M> void onShow(M data);
}
