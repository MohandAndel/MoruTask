package morutask.gui;

import com.leclercb.commons.api.utils.ResourceBundleUtils;
import com.leclercb.commons.gui.logger.GuiLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Level;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 9/10/13
 * Time: 1:41 PM
 * To change this template use File | Settings | File Templates.
 */

public final class Translations {

    private Translations() {

    }

    private static final String BUNDLE_FOLDER = "resources"
            + File.separator
            + "translations";

    private static final String BUNDLE_NAME = "Translations";

    private static final String DEFAULT_BUNDLE = BUNDLE_FOLDER
            + File.separator
            + BUNDLE_NAME
            + ".properties";

    public static final Locale DEFAULT_LOCALE = new Locale("en", "US");

    private static Map<Locale, File> locales;
    private static ResourceBundle defaultMessages;
    private static ResourceBundle messages;

    static {
        try {
            locales = ResourceBundleUtils.getAvailableLocales(new File(
                    BUNDLE_FOLDER), BUNDLE_NAME);
        } catch (Exception e) {
            GuiLogger.getLogger().log(
                    Level.SEVERE,
                    "Cannot load available locales",
                    e);

            locales = new HashMap<Locale, File>();
        }

        try {
            defaultMessages = new PropertyResourceBundle(new InputStreamReader(
                    new FileInputStream(DEFAULT_BUNDLE),
                    "UTF-8"));
        } catch (Exception e) {
            GuiLogger.getLogger().log(
                    Level.SEVERE,
                    "Cannot load default locale",
                    e);

            defaultMessages = null;
        }
    }

    public static Locale[] getAvailableLocales() {
        return locales.keySet().toArray(new Locale[0]);
    }

    public static Locale getLocale() {
        return Locale.getDefault();
    }

    public static void setLocale(Locale locale) {
        try {
            File file = locales.get(locale);

            if (file == null)
                throw new Exception();

            messages = new PropertyResourceBundle(new InputStreamReader(
                    new FileInputStream(file),
                    "UTF-8"));
            Locale.setDefault(locale);
        } catch (Exception e) {
            GuiLogger.getLogger().log(Level.SEVERE, "Cannot load locale", e);
            messages = null;
            Locale.setDefault(DEFAULT_LOCALE);
        }

        //Tips.setLocale(locale);
    }

    public static String getString(String key) {
        if (messages != null && messages.containsKey(key))
            return messages.getString(key);

        if (defaultMessages != null && defaultMessages.containsKey(key))
            return defaultMessages.getString(key);

        return "#" + key + "#";
    }

    public static String getString(String key, Object... args) {
        String value = getString(key);

        try {
            return String.format(value, args);
        } catch (IllegalFormatException e) {
            return value;
        }
    }

}
