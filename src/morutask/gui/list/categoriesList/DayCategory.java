package morutask.gui.list.categoriesList;

import com.leclercb.commons.api.utils.DateUtils;
import morutask.gui.utils.ViewUtils;
import morutask.models.Task;
import morutask.models.TaskFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 8/29/13
 * Time: 1:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class DayCategory implements PropertyChangeListener {

    public final static byte ALL = 0;
    public final static byte TODAY = 1;
    public final static byte TOMORROW = 2;
    public final static byte THIS_WEEK = 3;
    public final static byte LATER = 4;

    private List<Category> days;
    private DayFilter filter;

    public DayCategory() {

        filter = new DayFilter();

        days = new ArrayList<>();
        days.add(new CategoryImpl("All", filter));
        days.add(new CategoryImpl("Today", filter));
        days.add(new CategoryImpl("Tomorrow", filter));
        days.add(new CategoryImpl("This Week", filter));
        days.add(new CategoryImpl("Later", filter));

        //***
        TaskFactory.getInstance().addPropertyChangeListener(Task.PROP_START_DATE, this);
        TaskFactory.getInstance().addPropertyChangeListener(Task.PROP_MODEL_STATUS, this);

        scan();
        //***

        CategoryListManager.getInstance().getCategories().addAll(days);
    }

    public void scan() {
        for (Task tt : TaskFactory.getInstance().getList()) {
            countdays(tt);
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getSource() instanceof Task) {
            if ((evt.getOldValue() instanceof Calendar) && (DateUtils.getDiffInDays((Calendar) evt.getOldValue(), (Calendar) evt.getNewValue(), false) == 0))
                return;

            Task tt = (Task) evt.getSource();
            countdays(tt);
            ViewUtils.getInstance().getCategoryList().fireContentsChanged();
        }

    }

    public boolean countdays(Task t) {

        int daysBetween;
        int i = 1;

        daysBetween = (int) DateUtils.getDiffInDays(Calendar.getInstance(), t.getStartDate(), false);

        if ((t.getStartDate() == null) || (daysBetween == -1)) {
            return false;
        }

        if (!t.getModelStatus().isEndUserStatus()) {
            i = -1;
        }

        days.get(ALL).increase(i);

        if (daysBetween == 0) {
            ;
            days.get(TODAY).increase(i);
            return true;
        }

        if (daysBetween == 1) {
            days.get(TOMORROW).increase(i);
            return true;
        }

        if (daysBetween > 1 && daysBetween <= 6) {
            days.get(THIS_WEEK).increase(i);
            return true;
        }

        if (daysBetween >= 6) {
            days.get(LATER).increase(i);
            return true;
        }

        return false;

    }


    public class DayFilter implements FilterItem {


        @Override
        public String toDisplayinList(String str) {

            //String ss = str.concat("(" + String.valueOf(TimeDifference.GetValue(str) + ")"));
            return str;
        }

        @Override
        public boolean include(Task t) {

            Category selected = ViewUtils.getInstance().getSelectedCategory();

            if (days.get(ALL).equals(selected)) {
                return true;
            }

            int daysBetween;

            daysBetween = (int) DateUtils.getDiffInDays(Calendar.getInstance(), t.getStartDate(), false);

            if ((t.getStartDate() == null) || (daysBetween == -1)) {
                return false;
            }

            if (days.get(TODAY).equals(selected) && daysBetween == 0) {
                return true;
            }

            if (days.get(TOMORROW).equals(selected) && daysBetween == 1) {
                return true;
            }

            if (days.get(THIS_WEEK).equals(selected) && daysBetween > 1 && daysBetween <= 6) {
                return true;
            }

            if (days.get(LATER).equals(selected) && daysBetween >= 6) {
                return true;
            }

            return false;

        }
    }

}
