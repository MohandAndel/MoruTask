package morutask.gui.list.categoriesList;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 8/29/13
 * Time: 12:26 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Category {

    public void setFilter(FilterItem filter);

    public FilterItem getFilter();

    public void setDisplayName(String name);

    public String getDisplayName();

    public void increase(int x);

    public void setCount(int count);

    public int getCount();

}
