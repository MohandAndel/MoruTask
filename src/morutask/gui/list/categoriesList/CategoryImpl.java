package morutask.gui.list.categoriesList;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 8/29/13
 * Time: 12:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class CategoryImpl implements Category {

    private String name;
    private FilterItem filterItem;
    private int counter;

    public CategoryImpl() {

    }

    public CategoryImpl(String name, FilterItem filterItem) {
        this.name = name;
        this.filterItem = filterItem;
        counter = 0;
    }

    @Override
    public void setFilter(FilterItem filter) {
        this.filterItem = filter;
    }

    @Override
    public FilterItem getFilter() {
        return this.filterItem;
    }

    @Override
    public void setDisplayName(String name) {
        this.name = name;
    }

    @Override
    public String getDisplayName() {
        return this.name;
    }

    @Override
    public void increase(int x) {
        counter += x;
    }

    @Override
    public int getCount() {
        return counter;
    }

    @Override
    public void setCount(int count) {
        this.counter = count;
    }

    @Override
    public String toString() {
        String concat = getDisplayName().concat("(" + getCount() + ")");
        return concat;
        //return getDisplayName();
        //return filterItem.toDisplayinList(name);
    }
}
