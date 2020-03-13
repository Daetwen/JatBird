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

    public Person() {
        rect = new Rectangle(51,36,Color.GREEN);
        velocity = new Point2D(0,0);
        setTranslateY(300);
        setTranslateX(100);
        getChildren().add(rect);
    }
    public void moveY(int value) {
        boolean moveDown = value>0 ? true:false;
        for (int i = 0;i < Math.abs(value);i++) {
            for(Obstacles obs: Main.obstacles) {
                if(this.getBoundsInParent().intersects(obs.getBoundsInParent())) {
                    if(moveDown) {
                       setTranslateY(getTranslateY()+1);
                       return;
                    }
                    else {
                        setTranslateY(getTranslateY()-1);
                        return;
                    }
                }
            }
        }

        if(getTranslateY()<0){
            setTranslateY(0);
        }
        if(getTranslateY()>580){
            setTranslateY(580);
        }
        this.setTranslateY(getTranslateY() + (moveDown?1:-1));
    }
    public void moveX(int value){
        for(int i = 0;i < value;i++){
           for(Obstacles obs : Main.obstacles){
               if(this.getBoundsInParent().intersects(obs.getBoundsInParent())){
                   if(getTranslateX()+700 == obs.getTranslateX()){

                       setTranslateX(getTranslateX()-1);
                       return;
                   }
               }
               IsCoinPicked();
               IsBonusPicked();
/*               if(getTranslateX() == obs.getTranslateX()+i*700) {
                   obs.SetColor();
               }*/
           }
           setTranslateX(getTranslateX()+1);
            if(getTranslateX()%20 == 0) {
                Main.score++;
            }
        }
    }
    public void jump(double x,int y) {
        velocity = new Point2D(x,-y);
    }
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
    public void IsCoinPicked(){
        Main.coins.forEach((coin)->{
            if(this.getBoundsInParent().intersects(coin.getBoundsInParent())){
                removeCoin = coin;
                Main.coin_score++;
                System.out.println(Main.coin_score);
            }
        });
        Main.coins.remove(removeCoin);
        Main.gameRoot.getChildren().remove(removeCoin);
    }
    public void IsBonusPicked(){
        Main.bonuses.forEach((bonus)->{
            if(this.getBoundsInParent().intersects(bonus.getBoundsInParent())){
                removeBonus = bonus;
                this.jump(15,30);
            }
        });
        Main.bonuses.remove(removeBonus);
        Main.gameRoot.getChildren().remove(removeBonus);
    }
}
