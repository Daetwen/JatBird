package sample;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import static sample.Obstacles.isSaved;

import Saves.SaveInfo;

public class Person extends Pane {
    /**
     * Объект 2D точка для позиции прыжка
     */
    public Point2D velocity;
    /**
     * Объект класса прямоугольник
     */
    Rectangle rect;
    /**
     * Объект класса монета. Создан для выбора удаления объекта монеты
     */
    MyCoin removeCoin = null;
    /**
     * Объект класса бонус. Создан для выбора удаления объекта бонуса
     */
    LocalBonus removeBonus = null;
    /**
     * Объект класса бонус-второй шанс. Создан для выбора удаления объекта бонуса-второго шанса
     */
    SecondChansBonus removeSecondChanse = null;
    /**
     * Параметр определения смерти персонажа
     */
    public static boolean count = false;
    /**
     * Параметр проверки активна ли зона лазера
     */
    public static boolean active = false;
    /**
     * Параметр дальности продвижения объекта персонажа по горизонтали
     */
    public static double levelmodY = 2;
    /**
     * Параметр дальности продвижения объекта персонажа по вертикали
     */
    public static double levelmodX = 1.5;
    /**
     * Параметр для повышения дальности прыжка и соответсвенно уровня сложности
     */
    public static double upspeed = 0;
    /**
     * Параметр размера лазера по горизонтали для проверки столкновения с нужным лазером
     */
    public int width = 600;
    /**
     * Параметр размера лазера по вертикали для проверки столкновения с нужным лазером
     */
    public int height = 25;
    /**
     * картинка для отображения персонажа
     */
    String image = getClass().getResource("Персонаж.png").toExternalForm();
    /**
     * картинка для отображения персонажа в защитной капсуле
     */
    String image2 = getClass().getResource("Капсула.png").toExternalForm();
    /**
     * Звук перед выстрелом лазера
     */
    Media sound1 = new Media(getClass().getResource("До появления.mp3").toString());
    /**
     * Звук после выстрела лазера
     */
    Media sound2 = new Media(getClass().getResource("После появления.mp3").toString());
    /**
     * объект класса для воспроизведения звуков
     */
    public MediaPlayer mp = new MediaPlayer(sound1);
    /**
     * объект класса для воспроизведения звуков
     */
    public MediaPlayer mp2 = new MediaPlayer(sound2);
    public int distance = 1000;
    public boolean bonused = false;
    double localcounter = 0;
    SaveInfo saveinfo = new SaveInfo();

    /**
     * Конструктор - создание персонажа.
     */
    public Person(int StartY) {
        try {
            rect = new Rectangle(50, 50);
            velocity = new Point2D(0, 0);
            setTranslateY(StartY);
            setTranslateX(100);
            rect.setFill(new ImagePattern(new Image(image), 2, 2, 1, 1, true));
            getChildren().add(rect);
        } catch (Exception ex) {
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Функция проверки движения и пересечения с игровыми объектами по вертикали.
     *
     * @param value - параметр прыжка по вертикали.
     */
    public void moveY(int value) {
        try {
            boolean moveDown = value > 0 ? true : false;
            for (int i = 0; i < Math.abs(value); i++) {
                for (Obstacles obs : Main.obstacles) {//Цикл, не позовляющий вывалится за карту из объектов
                    if (this.getBoundsInParent().intersects(obs.getBoundsInParent()) && active == true) {
                        //obs.StopMP3();
                        StopMP3();
                        //count = true;
                    }
//                if(this.getBoundsInParent().intersects(obs.getBoundsInParent())) {//Если пересечение с объектами
//                    if(moveDown) {                            //Возврат вверх, если западание под объект
//                       setTranslateY(getTranslateY()+0.5);
//                       return;
//                    }
//                    else {                                    //Возврат вниз, если западание выше объекта
//                        setTranslateY(getTranslateY()-0.5);
//                        return;
//                    }
//                }
                }
            }
            //Не позволяет вывалится за обычную карту сверху и снизу
            if (getTranslateY() < 15) {
                setTranslateY(15);
            }
            if (getTranslateY() > 510) {
                setTranslateY(510);
            }
            this.setTranslateY(getTranslateY() + (moveDown ? levelmodY : -levelmodY));
        } catch (Exception ex) {
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Функция проверки движения и пересечения с игровыми объектами по горизонтали.
     *
     * @param value - параметр прыжка по горизонтали.
     */
    public void moveX(int value) {
        try {
            for (int i = 0; i < value; i++) {
                for (Obstacles obs : Main.obstacles) {
                    if (this.getBoundsInParent().intersects(obs.getBoundsInParent())) {
                        if (obs.GetWidth() == 30) count = true;
                        if (this.getTranslateX() >= obs.getTranslateX() + 112) {
                            //obs.StopMP3();
                            StopMP3();
                            active = true;
                            count = true;
                        }
                        if (obs.getTranslateX() % 600 == 0) active = false;
                    }
                    IsCoinPicked();
                    IsBonusPicked();
                    IsSecondChansePicked();
                    if (getTranslateX() == obs.getTranslateX()) {
                        if (obs.GetWidth() != 30) {
                            obs.SetStation(width, 1);
                            mp.play();
                        }
                    } else if ((getTranslateX() % (obs.getTranslateX() + 108)) < 1) {
                        if (obs.GetWidth() != 30) {
                            mp2.play();
                            obs.SetStation(width, 2);
                        }
                    }
                    if (this.getTranslateX() % (obs.getTranslateX() + 600) == 0) {
                        if (obs.GetWidth() != 30) obs.SetStation(width, 3);
                        active = false;
                    }
                }
                setTranslateX(getTranslateX() + levelmodX);
                if (getTranslateX() % 20 == 0) Main.score++;
//            if((getTranslateX() - levelmodX)%20 <= 0.1) {
//                Main.score++;
//                upspeed = upspeed + 0.0001;
//                levelmodX += upspeed;
//                levelmodY += upspeed;
//            }
            }
        } catch (Exception ex) {
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Функция перемещения персонажа вверх по вертикали и вперёд по горизонтали.
     *
     * @param x - параметр прыжка по горизонтали.
     * @param y - параметр прыжка по вертикали.
     */
    public void jump(double x, double y) {
        velocity = new Point2D(x, -y);
    }

    public void Savejump(double x, double y) {
        velocity = new Point2D(x, -y);
    }

    /**
     * Функция проверки сбора объекта-монетки.
     */
    public void IsCoinPicked() {
        try {
            Main.coins.forEach((coin) -> {                                               //Проходим по коллекции монет,
                if (this.getBoundsInParent().intersects(coin.getBoundsInParent())) {     //Если взяли, то накручиваем счётчик
                    removeCoin = coin;
                    Main.coin_score++;
                    System.out.println(Main.coin_score);
                }
            });                                                                       //И удаляем из коллекции
            Main.coins.remove(removeCoin);
            Main.gameRoot.getChildren().remove(removeCoin);
        } catch (Exception ex) {
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Функция проверки сбора бонуса и увеличение скорости при сборе.
     */
    public boolean IsBonusPicked() {
        try {
            Main.bonuses.forEach((bonus) -> {                                          //Аналогично монетке
                if (this.getBoundsInParent().intersects(bonus.getBoundsInParent())) {  //Только вместо накручивания счётчика
                    removeBonus = bonus;                                             //Большой прыжок вперёд
                    Main.bonus_score++;
                    localcounter = this.getTranslateX();
                    this.bonused = true;
                    //rect.setFill(new ImagePattern(new Image(image2),2,2,1,1,true));
                    this.Savejump(9, 20);
                }
            });
            Main.bonuses.remove(removeBonus);
            Main.gameRoot.getChildren().remove(removeBonus);
        } catch (Exception ex) {
            ex.getLocalizedMessage();
            System.exit(1);
        }
        return bonused;
    }

    /**
     * Функция проверки сбора бонуса на сохранение и сохранение при сборе.
     */
    public void IsSecondChansePicked() {
        try {
            Main.secondChanses.forEach((secondChans) -> {                                          //Аналогично монетке
                if (this.getBoundsInParent().intersects(secondChans.getBoundsInParent())) {  //Только вместо накручивания счётчика
                    removeSecondChanse = secondChans;                                             //Большой прыжок вперёд
                    Main.bonus_score++;
                    saveinfo.Save("Save.txt");
                    isSaved = true;
                }
            });
            Main.secondChanses.remove(removeSecondChanse);
            Main.gameRoot.getChildren().remove(removeSecondChanse);
        } catch (Exception ex) {
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Функция остановки воспроизведения звуков лазера.
     */
    public void StopMP3() {
        this.mp.stop();
        this.mp2.stop();
    }
}


//    double count = this.getTranslateX();
//    double count1 = count;
//                    while(count < count1 + distance * Main.ComplexityX){
//        this.state = true;
//        rect.setFill(new ImagePattern(new Image(image2),2,2,1,1,true));
//        this.Savejump(12,1);
//        count = this.getTranslateX();
//        }
//        if(count > count1 + distance * Main.ComplexityX){
//        this.state = false;
//        rect.setFill(new ImagePattern(new Image(image),2,2,1,1,true));
//        }