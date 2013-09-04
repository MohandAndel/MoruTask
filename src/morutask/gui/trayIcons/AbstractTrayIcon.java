package morutask.gui.trayIcons;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 8/31/13
 * Time: 3:24 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractTrayIcon implements TrayIconSupported {

    private Image image;
    private TrayIcon trayicon;
    private String tagname;
    private PopupMenu menu;

    public AbstractTrayIcon() {
        trayicon = new TrayIcon(new BufferedImage(50, 50, Image.SCALE_SMOOTH));
        trayicon.setImageAutoSize(true);
        menu = new PopupMenu();
        trayicon.setPopupMenu(menu);

        TrayIconManager.getInstance().addTrayIcon(this);

    }

    @Override
    public void setImage(Image image) {
        this.image = image;
        trayicon.setImage(image);
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void setTagName(String tag) {
        this.tagname = tag;
    }

    @Override
    public String getTagName() {
        return this.tagname;
    }

    @Override
    public void addMouseListener(MouseListener mouseListener) {
        this.trayicon.addMouseListener(mouseListener);
    }

    @Override
    public void setMenu(PopupMenu menu) {
        this.menu = menu;
        trayicon.setPopupMenu(this.menu);
    }

    @Override
    public PopupMenu getMenu() {
        return this.menu;
    }

    @Override
    public TrayIcon getTrayIcon() {
        return this.trayicon;
    }

    @Override
    public void addMenuItem(MenuItem item) {
        this.menu.add(item);
    }

    @Override
    public MenuItem getMenuItem() {
        return null;
    }
}
