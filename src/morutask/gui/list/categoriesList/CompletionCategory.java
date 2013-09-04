package morutask.gui.list.categoriesList;

import morutask.gui.utils.ViewUtils;
import morutask.models.Task;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 8/29/13
 * Time: 12:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class CompletionCategory {

    private Category completed;
    private Category unCompleted;
    private CompletionFilter filter;

    public CompletionCategory() {
        filter = new CompletionFilter();

        completed = new CategoryImpl("Completed", filter);
        unCompleted = new CategoryImpl("UnCompleted", filter);

        CategoryListManager.getInstance().AddCategory(completed);
        CategoryListManager.getInstance().AddCategory(unCompleted);
    }

    public class CompletionFilter implements FilterItem {

        @Override
        public String toDisplayinList(String str) {
            return str;
        }

        @Override
        public boolean include(Task t) {

            if (ViewUtils.getInstance().getSelectedCategory().equals(completed) && t.isCompleted())
                return true;

            if (ViewUtils.getInstance().getSelectedCategory().equals(unCompleted) && t.isCompleted() == false)
                return true;

            return false;
        }
    }
}
