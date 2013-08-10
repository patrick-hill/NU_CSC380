package service;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Patrick
 * Date: 8/9/13
 */
public class Menu {

    public Menu() {}

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public List<MenuItem> menuItems = new ArrayList<>();

    public Menu(String... items) {
        for (String item : items) {
            menuItems.add(new MenuItem(item));
        }
    }
}
