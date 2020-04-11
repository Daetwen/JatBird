package sample;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class LocalBonus extends Pane {
    /** Объект класса прямоугольник */
    Rectangle rect;
    /** Переменная ширины объекта (по горизонтали) */
    public int width;
    /** Переменная высоты объекта (по вертикали) */
    public int height;
    /** картинка для отображения */
    String image = getClass().getResource("Буст.png").toExternalForm();

    /**
     * Конструктор - создание нового объекта с определенными значениями.
     * @param width - размер объекта по горизонтали.
     * @param height - размер объекта по вертикали.
     * @throws IllegalArgumentException, если размер объекта меньше нуля.
     */
    LocalBonus(int width,int height){
        try{
            if(width < 0 || height < 0)
                throw new IllegalArgumentException("Одно из чисел размера бонуса меньше 0.");
            this.height = height;
            this.width = width;
            rect = new Rectangle(width,height);
            rect.setFill(new ImagePattern(new Image(image),0,0,1,1,true));
            getChildren().add(rect);
        }
        catch(Exception ex){
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }
}
