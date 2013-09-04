package morutask.gui.timer;

import morutask.models.Task;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 5/8/13
 * Time: 8:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class TimerRunnable implements Runnable {

    private TimerOperations timer;
    private Task task;
    private boolean showtimer = true;

    public TimerRunnable(TimerOperations timer, Task t) {
        this.timer = timer;
        this.task = t;
    }

    @Override
    public void run() {
        //if(!Thread.currentThread().isInterrupted())
        {
            //task.getTimer().start();
            System.out.println("TimerRunnable is running");
            long timerValue = task.getTimer().getTimerValue();
            System.out.println("TimerThread timerValue:" + timerValue);
            System.out.println("TimerThread Value:" + task.getTimer().getValue());
            try {
                Thread.sleep(task.getTimer().getTimerValue() * 1000);
                System.out.println("waked up after sleeping");
                timer.setPauseFlag(false);
                timer.TimerFinish();

            } catch (InterruptedException e) {
                System.out.println("TimerRunnable is interrupted");
                timer.setPauseFlag(true);
                timer.TimerFinish();
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

//        while(!Thread.currentThread().isInterrupted())
//        {
//            if(ReminderList.getInstance().getNotifiedTasks().isEmpty())
//                continue;
//
//          task = ReminderList.getInstance().getNotifiedTasks().get(0);
//        }


    }
}
