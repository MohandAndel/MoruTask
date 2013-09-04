package morutask.gui.timer;

import morutask.models.Task;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 5/10/13
 * Time: 12:18 AM
 * To change this template use File | Settings | File Templates.
 */
public interface TimerOperations {

    public void setTask(Task t);

    public Task getTask();

    public void start();

    public void pause();

    public void stop();

    public void TimerFinish();

    public boolean isPause();

    public void setPauseFlag(boolean f);
}
