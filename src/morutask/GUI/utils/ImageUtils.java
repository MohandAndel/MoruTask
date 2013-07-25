package morutask.GUI.utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 7/25/13
 * Time: 10:17 PM
 * To change this template use File | Settings | File Templates.
 */

public class ImageUtils {


    public static ImageIcon getImage(String file, int width, int height) {

        ImageIcon instance = new ImageIcon("images" + File.separator + file);

        if (width >= 0 && height >= 0) {
            instance = new ImageIcon(instance.getImage().getScaledInstance(
                    width,
                    height,
                    Image.SCALE_SMOOTH));
        }
        return instance;
    }
}