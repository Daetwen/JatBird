package sample;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Person extends Pane {
    /** Объект 2D точка для позиции прыжка */
    public Point2D velocity;
    /** Объект класса прямоугольник */
    Rectangle rect;
    /** Объект класса монета. Создан для выбора удаления объекта монеты */
    MyCoin removeCoin = null;
    /** Объект класса бонус. Создан для выбора удаления объекта бонуса */
    LocalBonus removeBonus = null;
    /** Параметр определения смерти персонажа */
    public static boolean count = false;
    /** Параметр проверки активна ли зона лазера */
    public static boolean active = false;
    /** Параметр дальности продвижения объекта персонажа по горизонтали */
    public static double levelmodY = 2;
    /** Параметр дальности продвижения объекта персонажа по вертикали */
    public static double levelmodX = 1.5;
    /** Параметр для повышения дальности прыжка и соответсвенно уровня сложности */
    public static double upspeed = 0;
    /** Параметр размера лазера по горизонтали для проверки столкновения с нужным лазером */
    public int width = 600;
    /** Параметр размера лазера по вертикали для проверки столкновения с нужным лазером */
    public int height = 25;
    /** картинка для отображения персонажа */
    String image = getClass().getResource("Персонаж.png").toExternalForm();

    /**
     * Конструктор - создание персонажа.
     */
    public Person() {
        try{
            rect = new Rectangle(50,50);
            velocity = new Point2D(0,0);
            setTranslateY(300);
            setTranslateX(100);
            rect.setFill(new ImagePattern(new Image(image),2,2,1,1,true));
            getChildren().add(rect);
        }
        catch(Exception ex){
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Функция проверки движения и пересечения с игровыми объектами по вертикали.
     * @param value - параметр прыжка по вертикали.
     */
    public void moveY(int value) {
        try{
            boolean moveDown = value>0 ? true:false;
            for (int i = 0;i < Math.abs(value);i++) {
                for(Obstacles obs: Main.obstacles) {//Цикл, не позовляющий вывалится за карту из объектов
                    if(this.getBoundsInParent().intersects(obs.getBoundsInParent()) && active == true){
                        count = true;
                        obs.StopMP3();
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
            if(getTranslateY()<15){
                setTranslateY(15);
            }
            if(getTranslateY()>510){
                setTranslateY(510);
            }
            this.setTranslateY(getTranslateY() + (moveDown?levelmodY:-levelmodY));
        }
        catch(Exception ex){
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Функция проверки движения и пересечения с игровыми объектами по горизонтали.
     * @param value - параметр прыжка по горизонтали.
     */
    public void moveX(int value){
        try{
            for(int i = 0;i < value;i++){
                for(Obstacles obs : Main.obstacles){
                    if(this.getBoundsInParent().intersects(obs.getBoundsInParent())){
                        if(obs.GetWidth() == 30) count = true;
                        if(this.getTranslateX() >= obs.getTranslateX()+112) {
                            count = true;
                            active = true;
                            obs.StopMP3();
                        }
                        if(obs.getTranslateX()%600 == 0) active = false;
//                   if(this.getTranslateX()+1500 == obs.getTranslateX()){
//                       setTranslateX(getTranslateX()-levelmodX);
//                       return;
//                   }
                    }
//                    for (int y = 0; y < 1;y++){
//                        if(obs.isSaved == true) obs.SetStation(width, obs.station);
//                    }
                    IsCoinPicked();
                    IsBonusPicked();
                    if(getTranslateX() == obs.getTranslateX()) {
                        if(obs.GetWidth() != 30) obs.SetStation(width, 1);
                    }
                    else if((getTranslateX() % (obs.getTranslateX()+ 108)) < 1) {
                        if(obs.GetWidth() != 30) obs.SetStation(width, 2);
                    }
                    if(this.getTranslateX() % (obs.getTranslateX()+ 600) == 0){
                        if(obs.GetWidth() != 30) obs.SetStation(width, 3);
                    }
                }
                setTranslateX(getTranslateX()+levelmodX);
                if(getTranslateX() % 20 == 0) Main.score++;
//            if((getTranslateX() - levelmodX)%20 <= 0.1) {
//                Main.score++;
//                upspeed = upspeed + 0.0001;
//                levelmodX += upspeed;
//                levelmodY += upspeed;
//            }
            }
        }
        catch(Exception ex){
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Функция перемещения персонажа вверх по вертикали и вперёд по горизонтали.
     * @param x - параметр прыжка по горизонтали.
     * @param y - параметр прыжка по вертикали.
     */
    public void jump(double x,double y){
        velocity = new Point2D(x,-y);
    }

    /**
     * Функция проверки сбора объекта-монетки.
     */
    public void IsCoinPicked(){
        try{
            Main.coins.forEach((coin)->{                                               //Проходим по коллекции монет,
                if(this.getBoundsInParent().intersects(coin.getBoundsInParent())){     //Если взяли, то накручиваем счётчик
                    removeCoin = coin;
                    Main.coin_score++;
                    System.out.println(Main.coin_score);
                }
            });                                                                       //И удаляем из коллекции
            Main.coins.remove(removeCoin);
            Main.gameRoot.getChildren().remove(removeCoin);
        }
        catch(Exception ex){
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Функция проверки сбора бонуса и увеличение скорости при сборе.
     */
    public void IsBonusPicked(){
        try{
            Main.bonuses.forEach((bonus)->{                                          //Аналогично монетке
                if(this.getBoundsInParent().intersects(bonus.getBoundsInParent())){  //Только вместо накручивания счётчика
                    removeBonus = bonus;                                             //Большой прыжок вперёд
                    this.jump(12,30);
                }
            });
            Main.bonuses.remove(removeBonus);
            Main.gameRoot.getChildren().remove(removeBonus);
        }
        catch(Exception ex){
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }
}
