package Menu;

import javafx.scene.layout.Pane;

public class MenuBox extends Pane {

    /**
     * Объект-контейнер набора объектов-кнопок
     */
    static SubMenu subMenu;

    /**
     * Конструктор - создание нового объекта с определенными значениями.
     *
     * @param subMenu - объекты класса SubMenu, являющегося контейнером объектов-кнопок.
     */
    public MenuBox(SubMenu subMenu) {
        try {
            MenuBox.subMenu = subMenu;
            setVisible(true);
            getChildren().add(subMenu);
        } catch (Exception ex) {
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Функция установки нужного контейнера объектов-кнопок вместо старого.
     *
     * @param subMenu - объекты класса SubMenu, являющегося контейнером объектов-кнопок.
     */
    public void setSubMenu(SubMenu subMenu) {
        try {
            getChildren().remove(MenuBox.subMenu);
            MenuBox.subMenu = subMenu;
            getChildren().add(MenuBox.subMenu);
        } catch (Exception ex) {
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }
}
