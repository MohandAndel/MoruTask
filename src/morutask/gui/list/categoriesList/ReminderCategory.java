package morutask.gui.list.categoriesList;

import morutask.gui.threads.reminder.ReminderList;
import morutask.gui.utils.ViewUtils;
import morutask.models.Task;
import morutask.models.TaskFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 8/30/13
 * Time: 1:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReminderCategory implements PropertyChangeListener {

   // private ReminderList reminderList;
    private Category reminder;
    private ReminderFilter filter;

    public ReminderCategory() {

        TaskFactory.getInstance().addPropertyChangeListener(Task.PROP_REMINDER, this);

        //reminderList = ReminderList.getInstance();
        filter = new ReminderFilter();
        reminder = new CategoryImpl("Reminder List", filter);

        CategoryListManager.getInstance().AddCategory(reminder);
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        ViewUtils.getInstance().getCategoryList().fireContentsChanged();
    }

    public class ReminderFilter implements FilterItem {

        @Override
        public String toDisplayinList(String str) {
           // String concat = str.concat("(" + reminderList.getNotifiedTasks().size() + ")");
            return str;
        }

        @Override
        public boolean include(Task t) {

            if (t.hasReminder())
                return true;

            return false;
        }
    }

}
