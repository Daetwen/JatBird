package sample;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MenuItem extends StackPane {

    /**
     * Конструктор - создание нового объекта с определенными значениями.
     * @param name - текст, который будет отображён на объекте.
     * @param sizeX - размер объекта по вертикали.
     * @param sizeY - размер объекта по вертикали.
     * @throws IllegalArgumentException, если размер объекта меньше нуля.
     */
    public MenuItem(String name, int sizeY, int sizeX){
        try{
            if(sizeX < 0 || sizeY < 0)
                throw new IllegalArgumentException("Одно из чисел размера кнопки меню меньше 0.");
            Rectangle rect = new Rectangle(sizeY,sizeX, Color.BLACK);
            Text text = new Text(name);
            text.setFill(Color.WHITE);
            text.setFont(Font.loadFont("file:src/sample/MR_HOUSEBROKENROUGHG.ttf",20.0D));

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
        catch(Exception ex){
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }
}
