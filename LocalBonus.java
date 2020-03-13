package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class LocalBonus extends Pane {
    Rectangle rect;
    public int width;
    public int height;
    String image = getClass().getResource("Буст.png").toExternalForm();
    LocalBonus(int width,int height){
        this.height = height;
        this.width = width;
        rect = new Rectangle(width,height);
        rect.setFill(new ImagePattern(new Image(image),0,0,11,7,true));
        getChildren().add(rect);
    }
}
