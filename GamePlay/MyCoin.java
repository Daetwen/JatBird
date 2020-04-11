package sample;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class MyCoin extends Pane {
    /** Объект класса круг */
    Circle circle;
    /** Переменная ширины объекта (и по горизонтали, и по вертикали) */
    public int width;
    /** картинка для отображения */
    String Coinimage = getClass().getResource("Монетка.png").toExternalForm();

    /**
     * Конструктор - создание нового объекта с определенными значениями.
     * @param width - размер объекта.
     * @throws IllegalArgumentException, если размер объекта меньше нуля.
     */
    public MyCoin(int width) {
        try{
            if(width < 0)
                throw new IllegalArgumentException("Число размера монеты меньше 0.");
            this.width = width;
            circle = new Circle(width);
            circle.setFill(new ImagePattern(new Image(Coinimage)));
            getChildren().add(circle);
        }
        catch(Exception ex){
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }
}
