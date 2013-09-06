package morutask.gui.trayIcons;

import morutask.gui.Main;
import morutask.gui.utils.ImageUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 9/6/13
 * Time: 12:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class MoruTaskIconTray extends AbstractTrayIcon {

    public MoruTaskIconTray() {
        setTagName("Main");
        setImage(ImageUtils.getImage("Morulogo.png"));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    Main.setGuiVisible(true);
                }
            }
        });

    }

    @Override
    public <M> void onShow(M data) {

        System.out.println(TrayIconManager.getInstance().getIcons().size());
    }
}
