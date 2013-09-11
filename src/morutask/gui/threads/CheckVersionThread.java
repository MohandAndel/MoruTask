package morutask.gui.threads;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 9/9/13
 * Time: 6:40 PM
 * To change this template use File | Settings | File Templates.
 */

public class CheckVersionThread extends Thread {

    public CheckVersionThread(boolean silent) {
        super(new CheckVersionRunnable(silent), "MT_CheckVersionThread");
    }
}
