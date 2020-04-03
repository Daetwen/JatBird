package sample;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.desktop.ScreenSleepEvent;
import java.awt.desktop.ScreenSleepListener;
import java.util.Timer;
import java.util.TimerTask;

public class Person extends Pane {
    public Point2D velocity;
    Rectangle rect;
    MyCoin removeCoin = null;
    LocalBonus removeBonus = null;
    public static boolean count = false;
    public static double levelmodY = 2;
    public static double levelmodX = 1.5;
    public static double upspeed = 0;

    String image = getClass().getResource("Персонаж.png").toExternalForm();
    //Конструктор создания персонажа
    public Person() {
        rect = new Rectangle(50,50);
        velocity = new Point2D(0,0);
        setTranslateY(300);
        setTranslateX(100);
        rect.setFill(new ImagePattern(new Image(image),0,0,1,1,true));
        getChildren().add(rect);
    }

    //Функция проверки движения по вертикали
    public void moveY(int value) {
        boolean moveDown = value>0 ? true:false;
        for (int i = 0;i < Math.abs(value);i++) {
            for(Obstacles obs: Main.obstacles) {//Цикл, не позовляющий вывалится за карту из объектов
                if(this.getBoundsInParent().intersects(obs.getBoundsInParent())) {//Если пересечение с объектами
                    if(moveDown) {                            //Возврат вверх, если западание под объект
                       setTranslateY(getTranslateY()+0.5);
                       return;
                    }
                    else {                                    //Возврат вниз, если западание выше объекта
                        setTranslateY(getTranslateY()-0.5);
                        return;
                    }
                }
            }
        }
        //Не позволяет вывалится за обычную карту сверху и снизу
        if(getTranslateY()<0){
            setTranslateY(0);
        }
        if(getTranslateY()>580){
            setTranslateY(580);
        }
        this.setTranslateY(getTranslateY() + (moveDown?levelmodY:-levelmodY));
    }

    //Функция проверки движения по горизонтали
    public void moveX(int value){
        for(int i = 0;i < value;i++){
           for(Obstacles obs : Main.obstacles){
               if(this.getBoundsInParent().intersects(obs.getBoundsInParent())){
                   if(this.getTranslateX()+1500 == obs.getTranslateX()){
                       setTranslateX(getTranslateX()-levelmodX);
                       return;
                   }
               }
               IsCoinPicked();
               IsBonusPicked();
               /*if(getTranslateX() == obs.getTranslateX()+i*700) {
                   obs.SetColor();
               }*/
           }
           setTranslateX(getTranslateX()+levelmodX);
            if((getTranslateX() - levelmodX)%20 <= 0.1) {
                Main.score++;
                upspeed = upspeed + 0.0001;
                levelmodX += upspeed;
                levelmodY += upspeed;
            }
        }
    }

    //Функция перемещения персонажа вверх по вертикали и вперёд по горизонтали
    public void jump(double x,double y) {
        velocity = new Point2D(x,-y);
    }

    //Функция проверки птички на смерть (Написана на данный момент не совсем корректно, будет переписана)
    public boolean Death(){
        int i = 0;
        for(Obstacles obs : Main.obstacles){
            if(this.getBoundsInParent().intersects(obs.getBoundsInParent())){
                if(obs.getTranslateX() < i*600){
                    return true;
                }
            }
            i++;
        }
/*        Main.obstacles.forEach((obstacle)-> {
            if (this.getBoundsInParent().intersects(obstacle.getBoundsInParent())) {
                count = true;
            }
        });*/
        return count;
    }

    //Функция проверки сбора монетки
    public void IsCoinPicked(){
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

    //Функция проверки сбора бонуса(Это бонус на скорость)(будет дорабатываться)
    public void IsBonusPicked(){
        Main.bonuses.forEach((bonus)->{                                          //Аналогично монетке
            if(this.getBoundsInParent().intersects(bonus.getBoundsInParent())){  //Только вместо накручивания счётчика
                removeBonus = bonus;                                             //Большой прыжок вперёд
                this.jump(12,30);
            }
        });
        Main.bonuses.remove(removeBonus);
        Main.gameRoot.getChildren().remove(removeBonus);
    }
}
