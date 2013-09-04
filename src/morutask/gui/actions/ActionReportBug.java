package morutask.gui.actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URI;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 8/21/13
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActionReportBug extends AbstractAction {

    private String url;

    public ActionReportBug(String url) {
        this.url = url;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        ActionReportBug.ReportBug(url);
    }

    public static void ReportBug(String url) {

        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
