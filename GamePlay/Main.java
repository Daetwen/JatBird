package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static sample.Obstacles.isSaved;
import static sample.Obstacles.station;

/**
 * Класс основной части исгры JatBird, в которм происходит вызов всех игровый объектов и игровой процесс
 * @autor Владислав Макарей, Илья Новиков
 */
public class Main extends Application {

    /** Поле всех компонентов на сцене игры*/
    public static Pane appRoot = new Pane();
    /** Поле игровых компонентов */
    public static Pane gameRoot = new Pane();
    /** Объект класса игрового персонажа */
    Person person = new Person();
    /** Коллекция игровых объектов препятствий-лазеров */
    public static ArrayList<Obstacles> obstacles = new ArrayList<>();
    /** Коллекция игровых объектов монет */
    public static ArrayList<MyCoin> coins = new ArrayList<>();
    /** Коллекция игровых объектов бонусов */
    public static ArrayList<LocalBonus> bonuses = new ArrayList<>();
    /** Счётчик достигнутого результата */
    public static double score = 0;
    /** Счётчик монет в наличии*/
    public static int coin_score = 0;
    /** Переменная силы прыжка по Х для сложности игры */
    double ComplexityX;
    /** Переменная силы прыжка по Y для сложности игры */
    double ComplexityY;
    /** Таймер для обновления постоянного вызова обновления положения персонажа */
    AnimationTimer timer;

    /**
     * Функция добавления картинки лазера, положения лазера и добавления объекта лазера в соответствующую коллекцию.
     * @param obstacle - объект-лазер.
     * @param i - номер объекта в массиве.
     * @param x1 - область спавна объекта лазера.
     * @param x2 - отступ (для того, чтобы не заспунить в самом начале экрана).
     * @param y1 - первый парамет для спауна по вертикали.
     * @param y2 - второй парамет с учётом прохода и отступа от предыдущего объекта-лазера.
     */
    public void PrintObstacle(Obstacles obstacle, int i, int x1, int x2, double y1, int y2){
        try{
            obstacle.SetLaser();
            obstacle.setTranslateX(i*x1 + x2);
            obstacle.setTranslateY(y1+y2);
            obstacles.add(obstacle);
        }
        catch(Exception ex){
            ex.getLocalizedMessage();
        }
    }

    /**
     * Функция отображения картинки.
     * @param name - название отображаемой картинки.
     * @param Height - высота отображаемой картинки.
     * @param Width - ширина отображаемой картинки.
     *  @throws IllegalArgumentException, если размеры картинки не находятся в диапазоне больше 0.
     *  @return элемент для отображения объекта.
     */
    public ImageView PrintImage(String name,int Height,int Width){
        Image image = new Image(getClass().getResourceAsStream(name));
        ImageView img = new ImageView(image);
        try{
            if(Height < 0) {
                throw new IllegalArgumentException("Число меньше 0, а должно быть больше.");
            }
            else img.setFitHeight(Height);
            if(Width < 0) {
                throw new IllegalArgumentException("Число меньше 0, а должно быть больше.");
            }
            else img.setFitWidth(Width);
            return img;
        }catch(Exception ex){
            ex.getLocalizedMessage();
            System.exit(1);
        }
        return img;
    }

    /**
     * Функция создания и отображения всех игоровых объектов на игровом поле appRoot.
     *  @return возвращает главное игровое поле appRoot.
     */
    public Parent createContent() {
        try{
            gameRoot.setPrefSize(900,600);
            ImageView imgg = PrintImage("Начало.jpg",600,763);  //Добавление картинки начала уровня
            gameRoot.getChildren().add(imgg);

            for(int i = 0; i < 35; i++) {               //Добавление картинки корридора каждые 1500 пикселей после начальной
                ImageView imggg = PrintImage("Коридор.jpg",600,1500);
                imggg.setTranslateX((i)*1500 + 763);
                gameRoot.getChildren().add(imggg);

                int enter = 100;                      //Добавление корриборов из панелек с лазерами, где корридоры создаются
                int width = 600;                      //блоками по 3 лазера
                int height = 25;                      //Создание непосредственно 3 объектов лазеров
                Obstacles obstacle = new Obstacles(width,height);
                PrintObstacle(obstacle, i,1500,600,100,20);

                Obstacles obstacle2 = new Obstacles(width,height);
                PrintObstacle(obstacle2, i,1500,600,Math.random()*100,height*2 + enter);

                Obstacles obstacle3 = new Obstacles(width,height);
                PrintObstacle(obstacle3, i,1500,600,Math.random()*150,4*height+2*enter);

                Obstacles obstalce4 = new Obstacles(30,350);     //Создание вертикального электролазера
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
            int positionY = 50;
            int positionX = 600;
            for(int i = 1;i <= 25;i++) {                            //Цикл создания и спавна монеток
                //double position = Math.random()*400;
                for (int y = 1; y <= 10; y++) {
                    MyCoin coin = new MyCoin(13);
                    coin.setTranslateX(positionX + y*100);
                    coin.setTranslateY(positionY);
                    System.out.println(positionY);
                    coins.add(coin);
                    gameRoot.getChildren().addAll(coin);               //Добавление монеток для отображения
                }
                positionX+=1000;
                positionY +=25;
                if(positionY >= 485){
                    positionY = 50;
                }
            }

            for(int z = 0;z < 8;z++) {
                int Yposition = 300;
                for (int i = 0; i < 5; i++) {
                    for (int y = 0; y < 5; y++) {                            //Цикл создания и спавна монеток
                        MyCoin coin = new MyCoin(13);
                        coin.setTranslateX(y * 40 + (z+3)*1400);
                        coin.setTranslateY(200);
                        coins.add(coin);
                        coin.setTranslateY(Yposition);
                        gameRoot.getChildren().addAll(coin);
                    }
                    Yposition += 25;
                }
            }
            for(int z = 0;z < 20;z++) {
                int Yposition = 450;
                int size = 4;
                for (int i = 0; i < 5; i++) {
                    for (int y = 0; y < size; y++) {                            //Цикл создания и спавна монеток
                        MyCoin coin = new MyCoin(13);
                        coin.setTranslateX(y * 40 + (z + 1) * 1780);
                        coin.setTranslateY(200);
                        coins.add(coin);
                        coin.setTranslateY(Yposition);
                        gameRoot.getChildren().addAll(coin);
                    }
                    size--;
                    Yposition += 25;
                }
            }
            gameRoot.getChildren().add(person);                    //добавление персонажа
            appRoot.getChildren().addAll(gameRoot);                //Добавление всего на главный Pane
        }
        catch(Exception ex){
            ex.getLocalizedMessage();
            System.exit(1);
        }
        return appRoot;
    }

    /**
     * Функция обновления положения персонажа.
     */
    public void update() {
        try{
            if(person.velocity.getY()<15) {
                person.velocity = person.velocity.add(0, 1);
            }
            person.moveX((int)person.velocity.getX());
            person.moveY((int)person.velocity.getY());
            //System.out.println("Score: " + score);
            person.translateXProperty().addListener((ovs,old,newValue)->{
                int offset = newValue.intValue();
                if(offset>200)gameRoot.setLayoutX(-(offset-200));
            });
        }
        catch(Exception ex){
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Функция геймплея: проверка нажатия клавишь и соответсвующий вызов функций, обновление таймер.
     * @param primaryStage - объект класса-контейнера для всех элементов.
     * @param menu - сцена, на которую доступен переход.
     * @param x - параметр для функции прыжка по X (по горизонтали).
     * @param y - параметр для функции прыжка по Y (по вертикали).
     */
    public void Play(Stage primaryStage,Scene menu,double x,double y){
        try{
            boolean pause = false;
            Person.levelmodX = x;
            Person.levelmodY = y;
            Scene scene = new Scene(createContent());        //Создание сцены с игровыми объектами
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
                if(keyCode.equals(KeyCode.F5)){
                    Save();
                    return;
                }
                if(keyCode.equals(KeyCode.R)){
                    Reload();
                    isSaved = true;
                    return;
                }
                if(keyCode.equals(KeyCode.ESCAPE)){
                    IsDead(primaryStage, menu,true);
                    timer.stop();
                    restart();
                    return;
                }
            });
            //Обновление и показ сцены
            primaryStage.setTitle("JatBird");
            primaryStage.setScene(scene);
            primaryStage.show();

            //Вызов обновления непосредственно карты и игровых объектов
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    update();
                    if(person.count == true){
                        IsDead(primaryStage, menu,person.count);
                        timer.stop();
                        restart();
                    }
                }
            };
            timer.start();
        }
        catch(Exception ex){
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Функция создания "меню" выбора действий после смерти и выполнения этих действий.
     * @param primaryStage - объект класса-контейнера для всех элементов.
     * @param menu - сцена, на которую доступен переход.
     * @param state - отвечает за то, является персонаж мёртвым.
     */
    public void IsDead(Stage primaryStage,Scene menu ,boolean state){
        try{
            StopMenu(score, coin_score,900, 600);
            MenuItem mainmenu = new MenuItem("ВЕРНУТЬСЯ В ГЛАВНОЕ МЕНЮ",450,40);
            MenuItem game = new MenuItem("НАЧАТЬ ИГРУ ЗАНОВО",450,40);
            SubMenu localmenu = new SubMenu(225,290,mainmenu,game);
            if(state == true) appRoot.getChildren().addAll(localmenu);

            mainmenu.setOnMouseClicked(event->{
                primaryStage.setTitle("Menu");
                primaryStage.setScene(menu);
                primaryStage.show();
                timer.stop();
                restart();
            });
            game.setOnMouseClicked(event->{Play(primaryStage,menu,ComplexityX,ComplexityY);});
        }
        catch(Exception ex){
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Функция очистки полей и коллекция перед стартом новой игры после смерти.
     */
    public void restart(){
        try{
            obstacles.clear();
            coins.clear();
            bonuses.clear();
            score = 0;
            coin_score = 0;
            person = new Person();
            person.count = false;
            appRoot = new Pane();
            gameRoot = new Pane();
        }
        catch(Exception ex){
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Функция создания начального меню игры и ивентов начала игрового процесса с заданным уровнем сложности.
     * @param primaryStage - объект класса-контейнера для всех элементов.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        try{
            Pane menu_pane = new Pane();
            Scene menu = new Scene(menu_pane);
            ImageView imgg = PrintImage("Фон главного меню2.png",600,900);
            menu_pane.getChildren().add(imgg);

            //Создание блоков меню
            //Главное меню
            MenuItem newGame = new MenuItem("НОВАЯ ИГРА",250,40);
            MenuItem reloadGame = new MenuItem("ПОСЛЕДНЕЕ СОХРАНЕНИЕ",250,40);
            MenuItem options = new MenuItem("НАСТРОЙКИ",250,40);
            MenuItem records= new MenuItem("РЕКОРДЫ",250,40);
            MenuItem exit = new MenuItem("ВЫХОД",250,40);
            SubMenu mainMenu = new SubMenu(newGame, reloadGame, options, records, exit);

            //Меню настроек
            MenuItem sound = new MenuItem("ВКЛ/ВЫКЛ МУЗЫКУ",250,40);
            MenuItem volume = new MenuItem("ГРОМКОСТЬ",250,40);
            MenuItem casee = new MenuItem("УПРАВЛЕНИЕ",250,40);
            MenuItem exit2 = new MenuItem("НАЗАД",250,40);
            SubMenu optionsMenu = new SubMenu(sound, volume, casee, exit2);

            //Меню выбора сложности игры
            MenuItem one = new MenuItem("ЛЕГКИЙ УРОВЕНЬ",250,40);
            MenuItem two = new MenuItem("СРЕДНИЙ УРОВЕНЬ",250,40);
            MenuItem three = new MenuItem("ТЯЖЁЛЫЙ УРОВЕНЬ",250,40);
            MenuItem fore = new MenuItem("УРОВЕНЬ ДЛЯ ПСИХОВ",250,40);
            MenuItem exit3 = new MenuItem("НАЗАД",250,40);
            SubMenu newGameMenu = new SubMenu(one, two, three, fore, exit3);

            MenuBox menuBox = new MenuBox(mainMenu);

            //Переходы из меню в разные разделы и обратно по нажатии нужного пункта
            newGame.setOnMouseClicked(event->menuBox.setSubMenu(newGameMenu));
            options.setOnMouseClicked(event->menuBox.setSubMenu(optionsMenu));
            exit.setOnMouseClicked(event-> System.exit(0));
            exit2.setOnMouseClicked(event->menuBox.setSubMenu(mainMenu));
            exit3.setOnMouseClicked(event->menuBox.setSubMenu(mainMenu));
            menu_pane.getChildren().add(menuBox);

            reloadGame.setOnMouseClicked(event->{
                Reload();
                Play(primaryStage,menu,ComplexityX ,ComplexityY);
            });

            //Запуск игры с установкой параметров сложности
            one.setOnMouseClicked(event->{
                menuBox.setSubMenu(mainMenu);
                ComplexityX = 1;
                ComplexityY = 1.6;
                Play(primaryStage,menu,1,1.6);
            });
            two.setOnMouseClicked(event->{
                menuBox.setSubMenu(mainMenu);
                ComplexityX = 1.2;
                ComplexityY = 1.8;
                Play(primaryStage,menu,1.2,1.8);
            });
            three.setOnMouseClicked(event->{
                menuBox.setSubMenu(mainMenu);
                ComplexityX = 1.5;
                ComplexityY = 2.1;
                Play(primaryStage,menu,1.5,2.1);
            });
            fore.setOnMouseClicked(event->{
                menuBox.setSubMenu(mainMenu);
                ComplexityX = 2;
                ComplexityY = 2.7;
                Play(primaryStage,menu,2,2.7);});

            primaryStage.setTitle("Menu");
            primaryStage.setScene(menu);
            primaryStage.show();
        }
        catch(Exception ex){
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Функция геймплея: проверка нажатия клавишь и соответсвующий вызов функций, обновление таймер.
     * @param score - парамет-счётчик результата игры (текущего игрового счёта).
     * @param coin_score - парамет-счётчик текущего количества монет.
     * @param SizeScreenX - размер "меню" по горизонтали.
     * @param SizeScreenY - размер "меню" по вертикали.
     */
    public void StopMenu(double score, int coin_score,int SizeScreenX, int SizeScreenY){
        try{
            Rectangle rect = new Rectangle(500,200, Color.BLACK);
            rect.setTranslateX((SizeScreenX - 500)/2);
            rect.setTranslateY((SizeScreenY - 200)/2);
            Text text1 = new Text("Количество набранных очков: " + score);
            Text text2 = new Text("Количество собранных монет: " + coin_score);
            text1.setTranslateX((SizeScreenX - 500)/2 + 20);
            text1.setTranslateY(230);
            text2.setTranslateX((SizeScreenX - 500)/2 + 20);
            text2.setTranslateY(270);
            text1.setFill(Color.WHITESMOKE);
            text2.setFill(Color.WHITESMOKE);
            text1.setFont(Font.loadFont("file:src/sample/MR_HOUSEBROKENROUGHG.ttf",23.0D));
            text2.setFont(Font.loadFont("file:src/sample/MR_HOUSEBROKENROUGHG.ttf",23.0D));
            appRoot.getChildren().addAll(rect,text1,text2);
        }
        catch(Exception ex){
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Функция сохранения в файл основных геимплеиных полей.
     */
    public void Save() {
        try(FileWriter writer = new FileWriter("Save.txt", false))
        {
            String sScore = Double.toString(score);
            String sCoinScore = Integer.toString(coin_score);
            String sPositionX = Double.toString(person.getTranslateX());
            String sPositionY = Double.toString(person.getTranslateY());
            String sComplexityX = Double.toString(ComplexityX);
            String sComplexityY = Double.toString(ComplexityY);
            String sLaserStation = Integer.toString(station);
            writer.write(sScore + "\r\n");
            writer.write(sCoinScore + "\r\n");
            writer.write(sPositionX + "\r\n");
            writer.write(sPositionY + "\r\n");
            writer.write(sComplexityX + "\r\n");
            writer.write(sComplexityY + "\r\n");
            writer.write( sLaserStation + "\r\n");
            writer.flush();
            writer.close();
        }
        catch(IOException ex){
            System.out.println(ex.getLocalizedMessage());
        }
    }

    /**
     * Функция чтения из файла и записи в соответсвующие поля данных.
     */
    public void Reload(){
        try (BufferedReader reader = new BufferedReader(new FileReader("Save.txt"))) {

            String sScore, CoinScore, sPositionX, sPositionY, sComplexityX, sComplexityY;
            sScore = reader.readLine();
            CoinScore = reader.readLine();
            sPositionX = reader.readLine();
            sPositionY = reader.readLine();
            sComplexityX = reader.readLine();
            sComplexityY = reader.readLine();
            String sLaserStation = reader.readLine();
            score = Double.valueOf(sScore);
            coin_score = Integer.valueOf(CoinScore);
            person.setTranslateX(Double.valueOf(sPositionX));
            person.setTranslateY(Double.valueOf(sPositionY));
            ComplexityX = Double.valueOf(sComplexityX);
            ComplexityY = Double.valueOf(sComplexityY);
            station = Integer.valueOf(sLaserStation);
            reader.close();
        }
        catch (Exception ex){
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }
}
