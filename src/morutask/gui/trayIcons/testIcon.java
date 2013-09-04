package morutask.gui.trayIcons;

import com.leclercb.commons.api.coder.exc.FactoryCoderException;
import morutask.gui.utils.ImageUtils;
import morutask.models.TaskFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 8/31/13
 * Time: 7:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class testIcon {


    public static void main(String[] args) throws AWTException, FactoryCoderException {


        MainIconTray mainIconTray = new MainIconTray();
        System.out.println(TaskFactory.getInstance().size());
        TrayIconManager.getInstance().fireShowTrayIcon("Main", null);

    }

    public void JtrayIcon() throws AWTException {

        SystemTray tray = SystemTray.getSystemTray();


        TrayIcon icon2 = new TrayIcon(ImageUtils.getImage("pause.png"));

        icon2.setImageAutoSize(true);

        tray.addPropertyChangeListener("trayIcons", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                System.out.println("trayIcon added or removed !!");
            }
        });

        final JPopupMenu jpopup = new JPopupMenu();

        JMenuItem javaCupMI = new JMenuItem("Example", new ImageIcon("images" + File.separator + "check.png"));
        jpopup.add(javaCupMI);

        jpopup.addSeparator();

        JMenuItem exitMI = new JMenuItem("Exit");
        jpopup.add(exitMI);

        icon2.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    jpopup.setLocation(e.getX(), e.getY());
                    jpopup.setInvoker(jpopup);
                    jpopup.setVisible(true);
                }
            }
        });


        tray.add(icon2);

    }
}
