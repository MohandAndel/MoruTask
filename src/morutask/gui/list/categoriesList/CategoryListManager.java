package morutask.gui.list.categoriesList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 8/29/13
 * Time: 12:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class CategoryListManager {

    private ArrayList<Category> categories;
    private static CategoryListManager instance;

    private CategoryListManager() {
        categories = new ArrayList<>();
    }

    public static CategoryListManager getInstance() {
        if (instance == null) {
            instance = new CategoryListManager();
        }

        return instance;
    }

    public void AddCategory(Category category) {
        this.categories.add(category);
    }

    public List<Category> getCategories() {
        return this.categories;
    }

}
