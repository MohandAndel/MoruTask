package morutask.GUI.Actions;

import morutask.GUI.EditTaskPanel;
import morutask.GUI.utils.viewUtils;
import morutask.models.Task;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 6/3/13
 * Time: 7:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class ActionEditTask extends AbstractAction {

     private static EditTaskPanel editTaskPanel = EditTaskPanel.getinstance();
     private static JDialog dialog = new JDialog();
    private static Task oldTask;
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    public static void EditTaskDialog(Task task)
    {

        editTaskPanel.getCancelButton().setEnabled(false);

        dialog.setSize(850,350);
        dialog.add(editTaskPanel);
        editTaskPanel.setTask(task);
        dialog.setVisible(true);
    }

    public static EditTaskPanel EditTask()
    {
        editTaskPanel.getCancelButton().setEnabled(true);
        Task selectedTask = viewUtils.getInstance().getSelectedTask();

        if( oldTask == null || !selectedTask.equals(oldTask))
        {
        editTaskPanel.setTask(selectedTask);
         oldTask = selectedTask;
        }

        return editTaskPanel;
    }
}
