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
}
