package morutask.gui;

import com.leclercb.commons.gui.logger.GuiLogger;

import javax.annotation.Resources;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 9/9/13
 * Time: 6:45 PM
 * To change this template use File | Settings | File Templates.
 */

public final class Constants {

    private Constants() {

    }

    private static String VERSION = null;

    public static String getVersion() {
        return VERSION;
    }

    static {
        try {
            Properties properties = new Properties();
            //properties.load(Resources.class.getResourceAsStream("general.properties"));
            VERSION = (String) Main.getSettings().getStringProperty("general.version");//properties.get("version");
        } catch (Exception e) {
            GuiLogger.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static final String TITLE = "MoruTask";
    public static final boolean BETA = false;
    public static final String DEFAULT_SUFFIX = "_v3";


    public static final String VERSION_FILE = "http://morutask.sourceforge.net/version.txt";

    public static final String DOWNLOAD_URL = "http://sourceforge.net/projects/morutask/files/latest/download";
    //public static final String DONATE_URL = "http://sourceforge.net/p/taskunifier/donate/";
    public static final String REVIEW_URL = "https://sourceforge.net/projects/morutask/reviews";
    //public static final String FORUM_URL = "http://sourceforge.net/p/taskunifier/discussion/";
    public static final String BUGS_URL = "https://sourceforge.net/p/morutask/bugs/?source=navbar";
    public static final String FEATURE_REQUESTS_URL = "https://sourceforge.net/p/morutask/feature-requests/?source=navbar";
    //public static final String SUPPORT_REQUESTS_URL = "http://sourceforge.net/p/taskunifier/support-requests/";

    public static final int TIMEOUT_HTTP_CALL = 30000;







    public static void initialize() {

    }

}
