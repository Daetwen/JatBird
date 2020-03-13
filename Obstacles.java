package sample;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


public class Obstacles extends Pane {
    String laser1image = getClass().getResource("Лазер1.png").toExternalForm();
    String laser3image = getClass().getResource("Лазер3.png").toExternalForm();
    public Point2D velocity;
    Rectangle rect;
    public int width;
    public int height;
    public Obstacles(int width,int height)
    {
        this.height = height;
        this.width = width;
        velocity = new Point2D(0,0);
        rect = new Rectangle(width,height);

        getChildren().add(rect);
    }
    public void SetColor(){
        this.rect.setFill(Color.RED);
    }
    public void SetElectroLaser(){
        rect.setFill(new ImagePattern(new Image(laser3image),0,0,1.1,1,true));
    }
    public void SetLaser(){
        rect.setFill(new ImagePattern(new Image(laser1image),0,0,1,1.21,true));
    }
    public void Move(int y) {
        velocity = new Point2D(0,y);
    }

}
