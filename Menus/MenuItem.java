package sample;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MenuItem extends StackPane {
    public MenuItem(String name){
        Rectangle rect = new Rectangle(250,40, Color.BLACK);
        Text text = new Text(name);
        text.setFill(Color.WHITE);
        text.setFont(Font.font("TimesNewRoman", FontWeight.BOLD, 9));

        this.setAlignment(Pos.CENTER);
        getChildren().addAll(rect,text);
        FillTransition trans = new FillTransition(Duration.seconds(0.5),rect);
        setOnMouseEntered(event->{
            trans.setFromValue(Color.BLACK);
            trans.setToValue(Color.DARKGOLDENROD);
            trans.setCycleCount(Animation.INDEFINITE);
            trans.setAutoReverse(true);
            trans.play();
        });
        setOnMouseExited(event->{
            trans.stop();
            rect.setFill(Color.BLACK);
        });
    }
}
