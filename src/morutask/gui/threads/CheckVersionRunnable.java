package morutask.gui.threads;

import com.leclercb.commons.api.utils.EqualsUtils;
import com.leclercb.commons.api.utils.HttpResponse;
import com.leclercb.commons.api.utils.HttpUtils;
import com.leclercb.commons.gui.logger.GuiLogger;
import com.leclercb.commons.gui.utils.DesktopUtils;
import morutask.gui.Constants;
import morutask.gui.Main;
import morutask.gui.Translations;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

import javax.swing.*;
import java.net.URI;
import java.util.logging.Level;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 9/9/13
 * Time: 6:41 PM
 * To change this template use File | Settings | File Templates.
 */

public class CheckVersionRunnable implements Runnable {

    private boolean silent;

    public CheckVersionRunnable(boolean silent) {
        this.silent = silent;
    }

    @Override
    public void run() {
        try {
            HttpResponse response = HttpUtils.getHttpGetResponse(new URI(
                    Constants.VERSION_FILE));

            if (!response.isSuccessfull())
                throw new Exception();

            String version = response.getContent().trim();

            if (version.length() > 10)
                throw new Exception();

            if (Constants.getVersion().compareTo(version) < 0) {
                GuiLogger.getLogger().info("New version available : " + version);

                String showed = Main.getSettings().getStringProperty(
                        "new_version.showed");

                if (!this.silent || !EqualsUtils.equals(version, showed)) {
                    Main.getSettings().setStringProperty(
                            "new_version.showed",
                            version);

                    String[] options = new String[] {
                            Translations.getString("general.download"),
                            Translations.getString("general.cancel") };

                    int result = JOptionPane.showOptionDialog(
                            null,
                            Translations.getString(
                                    "action.check_version.new_version_available",
                                    version),
                            Translations.getString("general.information"),
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            options,
                            options[0]);

                    if (result == 0) {
                        DesktopUtils.browse(Constants.DOWNLOAD_URL);
                    }
                }
            } else {
                GuiLogger.getLogger().info("No new version available");

                if (!this.silent) {
                    JOptionPane.showMessageDialog(
                            null,
                            Translations.getString(
                                    "action.check_version.no_new_version_available",
                                    Constants.getVersion()),
                            Translations.getString("general.information"),
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            if (this.silent) {
                GuiLogger.getLogger().warning(
                        "An error occured while checking for updates");
            } else {
                ErrorInfo info = new ErrorInfo(
                        Translations.getString("general.error"),
                        Translations.getString("error.check_version_error"),
                        null,
                        "GUI",
                        e,
                        Level.INFO,
                        null);

                JXErrorPane.showDialog(null, info);
            }
        }
    }

}

