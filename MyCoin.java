package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class MyCoin extends Pane {
    Circle circle;
    public int width;
    //String Coinimage = getClass().getResource("Монета.jpg").toExternalForm();
    public MyCoin(int width)
    {
        this.width = width;
        circle = new Circle(width,Color.YELLOW);
        //circle.setFill(new ImagePattern(new Image(image),0,0,1,1,true));
        getChildren().add(circle);
    }
}
