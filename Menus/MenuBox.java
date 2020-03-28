package sample;

import javafx.scene.layout.Pane;

public class MenuBox extends Pane {
    static SubMenu subMenu;
    public MenuBox(SubMenu subMenu){
        MenuBox.subMenu = subMenu;
        setVisible(true);
        getChildren().add(subMenu);
    }
    public void setSubMenu(SubMenu subMenu){
        getChildren().remove(MenuBox.subMenu);
        MenuBox.subMenu = subMenu;
        getChildren().add(MenuBox.subMenu);
    }
}
