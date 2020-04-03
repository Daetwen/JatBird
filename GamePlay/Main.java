package sample;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FillTransition;
import javafx.animation.PauseTransition;
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

    public static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();
    Person person = new Person();

    BorderPane root = new BorderPane();
    HBox hbox = new HBox(2);
    Button button1 = addButton("Speed");
    Button button2 = addButton("Time");

    public static ArrayList<Obstacles> obstacles = new ArrayList<>();
    public static ArrayList<MyCoin> coins = new ArrayList<>();
    public static ArrayList<LocalBonus> bonuses = new ArrayList<>();
    public static double score = 0;
    public static int coin_score = 0;
    public Label scoreLabel = new Label("Score: "+ score);
    public Label scoreLabel2 = new Label("Coins: "+ coin_score);

    public void PrintObstacle(Obstacles obstacle, int width, int height, int i, int x1, int x2, double y1, int y2){
        obstacle.SetLaser();
        obstacle.setTranslateX(i*x1 + x2);
        obstacle.setTranslateY(Math.random()*y1+y2);
        obstacles.add(obstacle);
    }

    public ImageView PrintImage(String name,int Height,int Width){
        Image image = new Image(getClass().getResourceAsStream(name));
        ImageView img = new ImageView(image);
        img.setFitHeight(Height);
        img.setFitWidth(Width);
        return img;
    }

    //Функция создания, добавления в коллекции и добавления на основной Pane игровых объектов
    public Parent createContent() {

        gameRoot.setPrefSize(900,900);
        ImageView imgg = PrintImage("Начало.jpg",600,763);  //Добавление картинки начала уровня
        gameRoot.getChildren().add(imgg);

        for(int i = 0; i < 50; i++) {               //Добавление картинки корридора каждые 1500 пикселей после начальной
            ImageView imggg = PrintImage("Коридор.jpg",600,1500);
            imggg.setTranslateX((i)*1500 + 763);
            gameRoot.getChildren().add(imggg);

            int enter = 100;                      //Добавление корриборов из панелек с лазерами, где корридоры создаются
            int width = 600;                      //блоками по 3 лазера
            int height = 50;                      //Создание непосредственно 3 объектов лазеров
            Obstacles obstacle = new Obstacles(width,height);
            PrintObstacle(obstacle, width,height,i,1500,600,100,20);
            Obstacles obstacle2 = new Obstacles(width,height);
            PrintObstacle(obstacle2, width,height,i,1500,600,Math.random()*100,height + enter);
            Obstacles obstacle3 = new Obstacles(width,height);
            PrintObstacle(obstacle3, width,height,i,1500,600,Math.random()*150,2*height+2*enter);

            Obstacles obstalce4 = new Obstacles(35,350);     //Создание вертикального электролазера
            obstalce4.SetElectroLaser();
            obstalce4.setTranslateX(i*1500 + 1600);
            obstalce4.setTranslateY(250);
            obstacles.add(obstalce4);

            gameRoot.getChildren().addAll(obstacle,obstacle2,obstacle3,obstalce4); //Добавление для отображения
        }
        for(int i = 0;i < 20;i++){                                //Цикл создания и спавна бонуса
            LocalBonus bonus = new LocalBonus(50,50);
            bonus.setTranslateX(i*5000+2500);
            bonus.setTranslateY(Math.random()*420);
            bonuses.add(bonus);

            gameRoot.getChildren().addAll(bonus);                //Добавление бонуса для отображения
        }
        for(int i = 0;i < 400;i++) {                            //Цикл создания и спавна монеток
            MyCoin coin = new MyCoin(13);
            coin.setTranslateX(i*85+600);
            coin.setTranslateY(200);
            coins.add(coin);

            gameRoot.getChildren().addAll(coin);               //Добавление монеток для отображения
        }

        gameRoot.getChildren().add(person);                    //добавление персонажа
        appRoot.getChildren().addAll(gameRoot);                //Добавление всего на главный Pane
        return appRoot;
    }

    //Функция обновления положения птички
    public void update() {
        if(person.velocity.getY()<15) {
            person.velocity = person.velocity.add(0, 1);
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

    //Функция непосредственно геимплея
    public void Play(Stage primaryStage,Scene menu,double x,double y){
        Person.levelmodX = x;
        Person.levelmodY = y;
        Scene scene = new Scene(createContent());        //Создание сцены с игровыми объектами
        gameRoot.getChildren().add(root);                //Добавление на сцену кнопок
        scene.setOnKeyPressed(key->{
            KeyCode keyCode = key.getCode();
                                                         //На Ctrl использовать буст на скорость с тратой монет
            if (keyCode.equals(KeyCode.CONTROL)) {
                if(coin_score >= 35){
                    person.jump(8,30);
                    coin_score = coin_score - 35;
                }
                return;
            }
                                                         //На W подниматься объектом вверх
            if (keyCode.equals(KeyCode.W)) {
                person.jump(2.5,18);
                return;
            }
            if(keyCode.equals(KeyCode.ESCAPE)){
            }
        });
                                                          //Обновление и показ сцены
        primaryStage.setTitle("JatBird");
        primaryStage.setScene(scene);
        primaryStage.show();

                                                          //Вызов обновления непосредственно карты и игровых объектов
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
//                    if(person.Death() == true){
//                        primaryStage.setTitle("Menu");
//                        primaryStage.setScene(menu);
//                        primaryStage.show();
//                        person.count = false;
//                        restart();
//                    }
            }
        };
        timer.start();
    }

    //Функция обновления полей и коллекция перед стартом новой игры после смерти
    public void restart(){
        obstacles.clear();
        coins.clear();
        bonuses.clear();
        score = 0;
        coin_score = 0;
        person = new Person();
    }

    //Функция создания кнопки с задаваемым именем определённых размеров
    private Button addButton(String name){
        Button button = new Button(name);
        button.setPrefSize(100,20);
        return button;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane menu_pane = new Pane();
        Scene menu = new Scene(menu_pane);
        ImageView imgg = PrintImage("Фон главного меню2.jpg",600,900);
        menu_pane.getChildren().add(imgg);
//        Rectangle black = new Rectangle(200,200, Color.BLACK);
//        black.setTranslateX(200);
//        menu_pane.getChildren().add(black);

        //Создание блоков меню
        //Главное меню
        MenuItem newGame = new MenuItem("НОВАЯ ИГРА");
        MenuItem options = new MenuItem("НАСТРОЙКИ");
        MenuItem records= new MenuItem("РЕКОРДЫ");
        MenuItem exit = new MenuItem("ВЫХОД");
        SubMenu mainMenu = new SubMenu(newGame,options,records,exit);

        //Меню настроек
        MenuItem sound = new MenuItem("ВКЛ/ВЫКЛ МУЗЫКУ");
        MenuItem volume = new MenuItem("ГРОМКОСТЬ");
        MenuItem casee = new MenuItem("УПРАВЛЕНИЕ");
        MenuItem exit2 = new MenuItem("НАЗАД");
        SubMenu optionsMenu = new SubMenu(sound,volume,casee,exit2);

        //Меню выбора сложности игры
        MenuItem one = new MenuItem("ЛЕГКИЙ УРОВЕНЬ");
        MenuItem two = new MenuItem("СРЕДНИЙ УРОВЕНЬ");
        MenuItem three = new MenuItem("ТЯЖЁЛЫЙ УРОВЕНЬ");
        MenuItem fore = new MenuItem("УРОВЕНЬ ДЛЯ ПСИХОВ");
        MenuItem exit3 = new MenuItem("НАЗАД");
        SubMenu newGameMenu = new SubMenu(one,two,three,fore,exit3);

        MenuBox menuBox = new MenuBox(mainMenu);

        //Переходы из меню в разные разделы и обратно по нажатии нужного пункта
        newGame.setOnMouseClicked(event->menuBox.setSubMenu(newGameMenu));
        options.setOnMouseClicked(event->menuBox.setSubMenu(optionsMenu));
        exit.setOnMouseClicked(event-> System.exit(0));
        exit2.setOnMouseClicked(event->menuBox.setSubMenu(mainMenu));
        exit3.setOnMouseClicked(event->menuBox.setSubMenu(mainMenu));
        menu_pane.getChildren().add(menuBox);

        //Запуск игры с установкой параметров сложности
        one.setOnMouseClicked(event->Play(primaryStage,menu,1,1.6));
        two.setOnMouseClicked(event->Play(primaryStage,menu,1.2,1.8));
        three.setOnMouseClicked(event->Play(primaryStage,menu,1.5,2.1));
        fore.setOnMouseClicked(event->Play(primaryStage,menu,2,2.7));

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
    }

    public static void main(String[] args) {
        launch(args);
    }
}
