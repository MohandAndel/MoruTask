package morutask.gui.trayIcons;

import morutask.gui.timer.StringValueTimer;
import morutask.gui.timer.TimerOperations;
import morutask.gui.timer.TimerThread;
import morutask.gui.utils.ImageUtils;
import morutask.models.Task;
import morutask.models.Timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 8/31/13
 * Time: 3:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class TimerTrayIcon extends AbstractTrayIcon implements TimerOperations {

    private TimerThread timerThread;
    private Timer timer;
    private Task task;
    private boolean removeTrayicon = false;

    public TimerTrayIcon() {
        setTagName("Timer");
        setImage(ImageUtils.getImage("clock.png"));

        addMenuItem(new MenuItem("Remaining time: 11"));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Pressed");
            }
        });

        MenuItem pauseItem = new MenuItem("Pause");
        pauseItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pause();

            }
        });
        //menu.add(pauseItem);

        MenuItem startItem = new MenuItem("Start");
        startItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });
        //menu.add(startItem);

        addMenuItem(pauseItem);
        addMenuItem(startItem);

    }

    @Override
    public <M> void onShow(M data) {

        System.out.println(data.getClass().toString());
        this.task = (Task) data;

        this.timer = task.getTimer();
        timer.start();

        timerThread = new TimerThread(this, task);
        timerThread.start();
    }

    @Override
    public void setTask(Task t) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Task getTask() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void start() {

        timer.start();
        task.setTimer(timer);
        timerThread = new TimerThread(this, task);
        timerThread.start();
        setImage(ImageUtils.getImage("clock.png"));
    }

    @Override
    public void pause() {

        if (timer.isStarted()) {
            System.out.println("started : True");
            //timer.setValue(timer.getTimerValue());
            timer.stop();
            task.setTimer(timer);
            setImage(ImageUtils.getImage("pause.png"));
            JOptionPane.showMessageDialog(null, "Remaining time:" + StringValueTimer.INSTANCE.getString(timer));
            setPauseFlag(true);
            timerThread.interrupt();
        }
    }

    @Override
    public void stop() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void TimerFinish() {

        System.out.println("Timer is Done ,Pause Flag :" + isPause());
        timer.stop();
        task.setTimer(timer);
        //pause();
        if (!isPause()) {
            System.out.println("Current Icon is Removed");
            TrayIconManager.getInstance().disableTray();

        }

    }

    @Override
    public boolean isPause() {
        return this.removeTrayicon;
    }

    @Override
    public void setPauseFlag(boolean f) {
        this.removeTrayicon = f;
    }
}
