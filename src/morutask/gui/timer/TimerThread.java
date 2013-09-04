package morutask.gui.timer;

import morutask.models.Task;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 5/8/13
 * Time: 8:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class TimerThread extends Thread {


    public TimerThread(TimerOperations timer, Task t) {
        //timerRunnable = new TimerRunnable(timer,t);
        super(new TimerRunnable(timer, t), "TimerThread");
    }
}
