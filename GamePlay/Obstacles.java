package sample;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Obstacles extends Pane {
    /** картинка для отображения */
    String laser1image = getClass().getResource("Лазер1.png").toExternalForm();
    /** картинка для отображения */
    String laser3image = getClass().getResource("Лазер3.png").toExternalForm();
    /** картинка для отображения */
    String laser4image = getClass().getResource("Лазер без полосы.png").toExternalForm();
    /** картинка для отображения */
    String laser5image = getClass().getResource("Лазер с тонкой полоской.png").toExternalForm();
    /** Звук перед выстрелом лазера */
    Media sound1 = new Media(getClass().getResource("До появления.mp3").toString());
    /** Звук после выстрела лазера */
    Media sound2 = new Media(getClass().getResource("После появления.mp3").toString());
    /** объект класса для воспроизведения звуков */
    MediaPlayer mp = new MediaPlayer(sound1);
    /** объект класса для воспроизведения звуков */
    MediaPlayer mp2 = new MediaPlayer(sound2);
    /** Объект класса прямоугольник */
    Rectangle rect;
    /** Переменная ширины объекта (по горизонтали) */
    public int width;
    /** Переменная высоты объекта (по вертикали) */
    public int height;
    /** Переменная объекта-лазера */
    public static int station;
    public static boolean isSaved = false;

    /**
     * Конструктор - создание нового объекта с определенными значениями.
     * @param width - размер объекта по горизонтали.
     * @param height - размер объекта по вертикали.
     * @throws IllegalArgumentException, если размер объекта меньше нуля.
     */
    public Obstacles(int width,int height) {
        try{
            if(width < 0 || height < 0)
                throw new IllegalArgumentException("Одно из чисел размера лазера меньше 0.");
            this.height = height;
            this.width = width;
            rect = new Rectangle(width,height);
            getChildren().add(rect);
        }
        catch(Exception ex){
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Функция получения размера по горизонтали.
     *  @return возвращает размер объекта по горизонтали width.
     */
    public int GetWidth() {
        return this.width;
    }

    public void StopMP3(){
        this.mp.stop();
        this.mp2.stop();
    }

    /**
     * Функция изменения отображения, размера и свойст объекта в зависимости от состаяния.
     * @param width - объект класса-контейнера для всех элементов.
     * @param station - параметр для функции прыжка по X (по горизонтали).
     */
    public void SetStation(int width, int station){
        try{
            this.station = station;
            if(this.width == width && this.width != 30){
                if(this.station == 1) {
                    rect.setHeight(25);
                    if(isSaved == true){
                        rect.setTranslateY(rect.getTranslateY() + 12.5);
                    }
                    mp.play();
                    rect.setFill(new ImagePattern(new Image(laser5image),0,0,1,0.99,true));

                }
                if(this.station == 2) {
                    rect.setHeight(50);
                    rect.setTranslateY(rect.getTranslateY() - 12.5);
                    rect.setFill(new ImagePattern(new Image(laser1image),0,0,1,1.21,true));
                    mp2.play();
                }
                if(this.station == 3){
                    rect.setHeight(25);
                    rect.setTranslateY(rect.getTranslateY() + 12.5);
                    this.SetLaser();
                    isSaved = false;
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex.getLocalizedMessage());
            mp.stop();
            mp2.stop();
            System.exit(1);
        }
    }

    /**
     * Функция отображения картинки электоролазера на объекте-лазере.
     */
    public void SetElectroLaser(){
        try{
            rect.setFill(new ImagePattern(new Image(laser3image),0,0,1.1,1,true));
        }
        catch(Exception ex){
            System.out.println(ex.getLocalizedMessage());
            System.exit(1);
        }
    }

    /**
     * Функция отображения картинки нулевого состояния лазера на объекте-лазере.
     */
    public void SetLaser(){
        try{
            rect.setFill(new ImagePattern(new Image(laser4image),0,0,1,1.01,true));
        }
        catch(Exception ex){
            System.out.println(ex.getLocalizedMessage());
            System.exit(1);
        }
    }
}
