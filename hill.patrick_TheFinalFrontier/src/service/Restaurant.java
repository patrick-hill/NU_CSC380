package service;

/**
 * User: Patrick
 * Date: 8/9/13
 */
public class Restaurant {
    private String name;
    private Menu menu;

    public Restaurant() {}

    public Restaurant(String name, String... items) {
        this.name = name;
        this.menu = new Menu(items);
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Menu getMenu() {
        return menu;
    }
}
