package sample;

import javafx.scene.layout.VBox;

public class SubMenu extends VBox {
    public SubMenu(MenuItem...items){
        setSpacing(5);
        setTranslateX(10);
        setTranslateY(300);
        for(MenuItem item: items){
            getChildren().addAll(item);
        }
    }
}
