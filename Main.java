package sample;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FillTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.KeyEvent;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    public enum GameState{
        READY,RUNNING,GAMEOVER;
    }

    public static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();

    BorderPane root = new BorderPane();
    HBox hbox = new HBox(2);
    Button button1 = addButton("Speed");
    Button button2 = addButton("Time");

    public static ArrayList<Obstacles> obstacles = new ArrayList<>();
    public static ArrayList<MyCoin> coins = new ArrayList<>();
    public static ArrayList<LocalBonus> bonuses = new ArrayList<>();
    Person person = new Person();
    public static int score = 0;
    public static int coin_score = 0;
    public Label scoreLabel = new Label("Score: "+ score);
    public Label scoreLabel2 = new Label("Coins: "+ coin_score);

    public Parent createContent() {
        gameRoot.setPrefSize(600,600);
        Image image1 = new Image(getClass().getResourceAsStream("Начало.jpg"));
        ImageView imgg = new ImageView(image1);
        imgg.setFitHeight(600);
        imgg.setFitWidth(763);
        gameRoot.getChildren().add(imgg);
        for(int i = 0; i < 40; i++) {
            Image image2 = new Image(getClass().getResourceAsStream("Коридор.jpg"));
            ImageView imggg = new ImageView(image2);
            imggg.setFitHeight(600);
            imggg.setFitWidth(1500);
            imggg.setTranslateX((i)*1500 + 763);
            gameRoot.getChildren().add(imggg);

            int enter = 100;
            int width = 600;
            int height = 75;
            Obstacles obstacle = new Obstacles(width,height);
            //obstacle.SetLaser();
            obstacle.setTranslateX(i*1500 + 600);
            obstacle.setTranslateY(Math.random()*100+20);
            obstacles.add(obstacle);

            Obstacles obstalce2 = new Obstacles(width,height);
            //obstalce2.SetLaser();
            obstalce2.setTranslateX(i*1500 + 600);
            obstalce2.setTranslateY(Math.random()*100 + height + enter);
            obstacles.add(obstalce2);

            Obstacles obstalce3 = new Obstacles(width,height);
            //obstalce3.SetLaser();
            obstalce3.setTranslateX(i*1500 + 600);
            obstalce3.setTranslateY(Math.random()*150 + 2*height+2*enter);
            obstacles.add(obstalce3);

            Obstacles obstalce4 = new Obstacles(35,175);
            obstalce4.SetElectroLaser();
            obstalce4.setTranslateX(i*1500 + 1600);
            obstalce4.setTranslateY(400);
            obstacles.add(obstalce4);

            gameRoot.getChildren().addAll(obstacle,obstalce2,obstalce3,obstalce4);
        }
        for(int i = 0;i < 20;i++){
            LocalBonus bonus = new LocalBonus(50,50);
            bonus.setTranslateX(i*5000+2500);
            bonus.setTranslateY(300);
            bonuses.add(bonus);

            gameRoot.getChildren().addAll(bonus);
        }
        for(int i = 0;i < 450;i++) {
            MyCoin coin = new MyCoin(13);
            coin.setTranslateX(i*85+600);
            coin.setTranslateY(200);
            coins.add(coin);

            gameRoot.getChildren().addAll(coin);
        }

        gameRoot.getChildren().add(person);
        appRoot.getChildren().addAll(gameRoot);
        return appRoot;
    }
    public void update() {
        if(person.velocity.getY()<5){
            person.velocity = person.velocity.add(0,1);
        }
        person.moveX((int)person.velocity.getX());
        person.moveY((int)person.velocity.getY());
        scoreLabel.setText("Score: " + score);
        System.out.println("Score: " + score);
        person.translateXProperty().addListener((ovs,old,newValue)->{
            int offset = newValue.intValue();
            if(offset>200)gameRoot.setLayoutX(-(offset-200));
        });
    }

    public void restart(){
        obstacles.clear();
        score = 0;
        person = new Person();
    }
    private Button addButton(String name){
        Button button = new Button(name);
        button.setPrefSize(100,20);
        return button;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane menu_pane = new Pane();
        Scene menu = new Scene(menu_pane);
        Image image = new Image(getClass().getResourceAsStream("фон.jpg"));
        ImageView imgg = new ImageView(image);
        imgg.setFitHeight(600);
        imgg.setFitWidth(600);
        menu_pane.getChildren().add(imgg);
        Rectangle black = new Rectangle(200,200, Color.BLACK);
        black.setTranslateX(200);
        menu_pane.getChildren().add(black);

        MenuItem newGame = new MenuItem("НОВАЯ ИГРА");
        MenuItem options = new MenuItem("НАСТРОЙКИ");
        MenuItem records= new MenuItem("РЕКОРДЫ");
        MenuItem exit = new MenuItem("ВЫХОД");
        SubMenu mainMenu = new SubMenu(newGame,options,records,exit);

        MenuItem sound = new MenuItem("ВКЛ/ВЫКЛ МУЗЫКУ");
        MenuItem volume = new MenuItem("ГРОМКОСТЬ");
        MenuItem casee = new MenuItem("УПРАВЛЕНИЕ");
        MenuItem exit2 = new MenuItem("НАЗАД");
        SubMenu optionsMenu = new SubMenu(sound,volume,casee,exit2);

        MenuItem one = new MenuItem("ОДИН ИГРОК");
        MenuItem two = new MenuItem("ДВА ИГРОКА");
        MenuItem exit3 = new MenuItem("НАЗАД");
        SubMenu newGameMenu = new SubMenu(one,two,exit3);

        MenuBox menuBox = new MenuBox(mainMenu);
        //MenuBox optionsBox = new MenuBox(optionsMenu);
       // MenuBox newGameBox = new MenuBox(newGameMenu);

        newGame.setOnMouseClicked(event->menuBox.setSubMenu(newGameMenu));
        options.setOnMouseClicked(event->menuBox.setSubMenu(optionsMenu));
        exit.setOnMouseClicked(event-> System.exit(0));
        exit2.setOnMouseClicked(event->menuBox.setSubMenu(mainMenu));
        exit3.setOnMouseClicked(event->menuBox.setSubMenu(mainMenu));
        menu_pane.getChildren().add(menuBox);

        primaryStage.setTitle("Menu");
        primaryStage.setScene(menu);
        primaryStage.show();

        hbox.getChildren().add(button1);
        hbox.getChildren().add(button2);
        root.setTop(hbox);

        button1.setOnAction(event->{
            person.jump(8,30);
            if(coin_score >= 35){
                person.jump(8,30);
                coin_score = coin_score - 35;
            }
        });


        Scene scene = new Scene(createContent());
        one.setOnMouseClicked(event->{
            gameRoot.getChildren().add(root);
            scene.setOnKeyPressed(key->{
                KeyCode keyCode = key.getCode();
                if (keyCode.equals(KeyCode.CONTROL)) {
                    if(coin_score >= 35){
                        person.jump(8,30);
                        coin_score = coin_score - 35;
                    }
                    return;
                }
                if (keyCode.equals(KeyCode.W)) {
                    person.jump(2.5,20);
                    return;
                }
            });
            //scene.setOnMousePressed(event2->{person.jump(1);});
            primaryStage.setTitle("JatBird");
            primaryStage.setScene(scene);
            primaryStage.show();


            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    update();
/*                    if(person.Death() == true){
                        primaryStage.setTitle("Menu");
                        primaryStage.setScene(menu);
                        primaryStage.show();
                        person.count = false;
                        restart();
                    }*/
                }
            };
            timer.start();
        });
    }


    private static class MenuItem extends StackPane {
        public MenuItem(String name){
            Rectangle rect = new Rectangle(200,40,Color.BLACK);
            Text text = new Text(name);
            text.setFill(Color.WHITE);
            text.setFont(Font.font("TimesNewRoman", FontWeight.BOLD, 8));

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
    private static class SubMenu extends VBox{
        public SubMenu(MenuItem...items){
            setSpacing(5);
            setTranslateX(200);
            setTranslateY(10);
            for(MenuItem item: items){
                getChildren().addAll(item);
            }
        }
    }
    private static class MenuBox extends Pane{
        static SubMenu subMenu;
        public MenuBox(SubMenu subMenu){
            MenuBox.subMenu = subMenu;
            setVisible(true);
            getChildren().add(subMenu);
        }
        public void setSubMenu(SubMenu subMenu){
            getChildren().remove(MenuBox.subMenu);
            MenuBox.subMenu = subMenu;
            getChildren().add(MenuBox.subMenu);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
