package morutask.gui.trayIcons;

import morutask.gui.utils.ImageUtils;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 9/1/13
 * Time: 1:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainIconTray extends AbstractTrayIcon {

    private MenuPanel menuPanel;
    private JPopupMenu jpopup;

    public MainIconTray() {
        setTagName("Main");
        setMenu(null);
        menuPanel = new MenuPanel();
        setImage(ImageUtils.getImage("check.png"));


        jpopup = new JPopupMenu();
        jpopup.setSize(200, 200);
        jpopup.add(menuPanel);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON3) {
                    jpopup.setLocation(e.getX(), e.getY());
                    jpopup.setInvoker(jpopup);
                    jpopup.setVisible(true);
                }
            }
        });
    }

    @Override
    public <M> void onShow(M data) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
