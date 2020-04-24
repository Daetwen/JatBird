package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
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
    static Person person = new Person();
    /** Коллекция игровых объектов препятствий-лазеров */
    public static ArrayList<Obstacles> obstacles = new ArrayList<>();
    /** Коллекция игровых объектов монет */
    public static ArrayList<MyCoin> coins = new ArrayList<>();
    /** Коллекция игровых объектов бонусов */
    public static ArrayList<LocalBonus> bonuses = new ArrayList<>();
    /** Коллекция игровых объектов бонусов-вторых шансов */
    public static ArrayList<SecondChansBonus> secondChanses = new ArrayList<>();
    /** Счётчик достигнутого результата */
    public static double score = 0;
    /** Счётчик монет в наличии*/
    public static int coin_score = 0;
    /** Переменная силы прыжка по Х для сложности игры */
    public static double ComplexityX;
    /** Переменная силы прыжка по Y для сложности игры */
    public static double ComplexityY;
    /** Таймер для обновления постоянного вызова обновления положения персонажа */
    AnimationTimer timer;
    /** Коллекция объектов сохранений */
    public static ArrayList<SaveInfo> saves2 = new ArrayList<>();
    /** Массив стрингов с набором информации для таблицы рекордов */
    String[] SaveText = this.SaveMessage();
    /** Объекты класса  MenuItem, являющиеся кнопками-сообщениями в таблице рекордов*/
    MenuItem save1 = new MenuItem(SaveText[0],580,40, Pos.CENTER_LEFT);
    MenuItem save2 = new MenuItem(SaveText[1],580,40, Pos.CENTER_LEFT);
    MenuItem save3 = new MenuItem(SaveText[2],580,40, Pos.CENTER_LEFT);
    MenuItem save4 = new MenuItem(SaveText[3],580,40, Pos.CENTER_LEFT);
    MenuItem save5 = new MenuItem(SaveText[4],580,40, Pos.CENTER_LEFT);
    MenuItem save6 = new MenuItem(SaveText[5],580,40, Pos.CENTER_LEFT);
    MenuItem save7 = new MenuItem(SaveText[6],580,40, Pos.CENTER_LEFT);
    MenuItem save8 = new MenuItem(SaveText[7],580,40, Pos.CENTER_LEFT);
    MenuItem save9 = new MenuItem(SaveText[8],580,40, Pos.CENTER_LEFT);
    MenuItem save10 = new MenuItem(SaveText[9],580,40, Pos.CENTER_LEFT);
    /** Объект класса  MenuItem, являющиеся кнопккой возврата в главное меню*/
    MenuItem saveback = new MenuItem("НАЗАД",580,40);

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
            for(int i = 0;i < 3;i++){                                //Цикл создания и спавна бонуса
                SecondChansBonus second = new SecondChansBonus(15);
                second.setTranslateX(i*1000+1400);
                second.setTranslateY(Math.random()*420);
                secondChanses.add(second);

                gameRoot.getChildren().addAll(second);                //Добавление бонуса для отображения
            }
            int positionY = 50;
            int positionX = 600;
            for(int i = 1;i <= 25;i++) {                            //Цикл создания и спавна монеток
                //double position = Math.random()*400;
                for (int y = 1; y <= 10; y++) {
                    MyCoin coin = new MyCoin(13);
                    coin.setTranslateX(positionX + y*100);
                    coin.setTranslateY(positionY);
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
                    Save("Save.txt");
                    return;
                }
                if(keyCode.equals(KeyCode.R)){
                    Reload("Save.txt");
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
                    if(person.count == true && isSaved == true){
                        Reload("Save.txt");
                        isSaved = false;
                    }
                    if(person.count == true && isSaved == false){
                        IsDead(primaryStage, menu,person.count);
                        timer.stop();
                        IsRecord();
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
                SaveText = this.SaveMessage();
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
     * @see "class MenuBox"
     * @see "class MenuItem"
     * @see "class SubMenu"
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
            MenuItem records = new MenuItem("РЕКОРДЫ",250,40);
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
            records.setOnMouseClicked(event->{
                SaveText = SaveMessage();
                RecordsMap();
                SubMenu SaveMenu = new SubMenu(10,107, save1, save2, save3, save4, save5, save6, save7, save8, save9, save10,saveback);
                menuBox.setSubMenu(SaveMenu);
                //SortMenu(menu_pane);
            });
            saveback.setOnMouseClicked((event->menuBox.setSubMenu(mainMenu)));
            exit.setOnMouseClicked(event-> System.exit(0));
            exit2.setOnMouseClicked(event->menuBox.setSubMenu(mainMenu));
            exit3.setOnMouseClicked(event->menuBox.setSubMenu(mainMenu));
            menu_pane.getChildren().add(menuBox);

            reloadGame.setOnMouseClicked(event->{
                Reload("Save.txt");
                Play(primaryStage,menu,ComplexityX ,ComplexityY);
            });
            save1.setOnMouseClicked(event->{
                StartFromRecords(0);
                Play(primaryStage,menu,ComplexityX ,ComplexityY);
            });
            save10.setOnMouseClicked(event->{
                StartFromRecords(9);
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
            text1.setFont(Font.loadFont("file:src/sample/LJK_DOWNCOME.otf",23.0D));
            text2.setFont(Font.loadFont("file:src/sample/LJK_DOWNCOME.otf",23.0D));
            appRoot.getChildren().addAll(rect,text1,text2);
        }
        catch(Exception ex){
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Функция сохранения в файл основных геимплеиных полей.
     * @param NameOfFile - название файла для работы с сохранениями
     */
    public static void Save(String NameOfFile) {
        try(FileWriter writer = new FileWriter(NameOfFile, false))
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
            writer.write( "\r\n");
            writer.flush();
            writer.close();
        }
        catch(IOException ex){
            System.out.println(ex.getLocalizedMessage());
        }
    }

    /**
     * Функция чтения из файла и записи в соответсвующие поля данных.
     * @param NameOfFile - название файла для работы с сохранениями
     */
    public static void Reload(String NameOfFile){
        try (BufferedReader reader = new BufferedReader(new FileReader(NameOfFile))) {
            person.count = false;
            String sScore, CoinScore, sPositionX, sPositionY, sComplexityX, sComplexityY;
            sScore = reader.readLine();
            CoinScore = reader.readLine();
            sPositionX = reader.readLine();
            sPositionY = reader.readLine();
            sComplexityX = reader.readLine();
            sComplexityY = reader.readLine();
            String sLaserStation = reader.readLine();
            String newStr = reader.readLine();
            score = Double.valueOf(sScore);
            coin_score = Integer.valueOf(CoinScore);
            person.setTranslateX(Double.valueOf(sPositionX));
            person.setTranslateY(Double.valueOf(sPositionY));
            ComplexityX = Double.valueOf(sComplexityX);
            ComplexityY = Double.valueOf(sComplexityY);
            station = Integer.valueOf(sLaserStation);
            reader.close();
        }
        catch (IOException ex1){
            ex1.getLocalizedMessage();
            System.exit(1);
        }
        catch (Exception ex){
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Функция создания рекордного сохранения, сортировки с остальными рекордами и записи таблицы рекордов в файл.
     */
    public void IsRecord(){
        SaveInfo save = new SaveInfo(score,coin_score,person.getTranslateX(),person.getTranslateY(),ComplexityX,ComplexityY,station);
        Read("Save2.txt",saves2);
        saves2.add(save);
        SaveInfo localsave = new SaveInfo();
        for(int i = 1; i < saves2.size(); ++i){
            for(int y = 0; y < saves2.size() - 1; y++){
                if(saves2.get(y).sScore < saves2.get(y+1).sScore){
                    Switch(localsave,saves2.get(y));
                    Switch(saves2.get(y),saves2.get(y+1));
                    Switch(saves2.get(y+1),localsave);
                }
            }
        }
        if(saves2.size() == 11) saves2.remove(10);
        Write("Save2.txt",saves2);
        SaveMessage();
    }

    /**
     * Функция чтения параметров из одного объекта класса SaveInfo в другой.
     * @param first - объект, в который происходит считывание
     * @param second -  объект, из которого происходит считывание
     */
    public void Switch(SaveInfo first,SaveInfo second){
        first.sScore = second.sScore;
        first.sCoinScore = second.sCoinScore;
        first.sPositionX = second.sPositionX;
        first.sPositionY = second.sPositionY;
        first.sComplexityX = second.sComplexityX;
        first.sComplexityY = second.sComplexityY;
        first.sLaserStation = second.sLaserStation;
    }

    /**
     * Функция записи в файл содержимого коллекции.
     * @param name - название файла, в который происходит запись
     * @param saves -  коллекция, из которой происходит считывание
     */
    public void Write(String name,ArrayList<SaveInfo> saves){
        try(FileWriter writer = new FileWriter(name, false))
        {
            int i = 0;
            while(saves.size() != i) {
                String sScore = Double.toString(saves.get(i).sScore);
                String sCoinScore = Integer.toString(saves.get(i).sCoinScore);
                String sPositionX = Double.toString(saves.get(i).sPositionX);
                String sPositionY = Double.toString(saves.get(i).sPositionY);
                String sComplexityX = Double.toString(saves.get(i).sComplexityX);
                String sComplexityY = Double.toString(saves.get(i).sComplexityY);
                String sLaserStation = Integer.toString(saves.get(i).sLaserStation);
                writer.write(sScore + "\r\n");
                writer.write(sCoinScore + "\r\n");
                writer.write(sPositionX + "\r\n");
                writer.write(sPositionY + "\r\n");
                writer.write(sComplexityX + "\r\n");
                writer.write(sComplexityY + "\r\n");
                writer.write(sLaserStation + "\r\n");
                writer.write("=============================================" + "\r\n");
                writer.flush();
                i++;
            }
            writer.close();
        }
        catch(IOException ex){
            System.out.println(ex.getLocalizedMessage());
        }
    }

    /**
     * Функция чтения из файла в коллекцию.
     * @param name - название файла, из которого происходит считывание
     * @param saves -  коллекция, в которую происходит считывание
     */
    public void Read(String name,ArrayList<SaveInfo> saves){
        try (BufferedReader reader = new BufferedReader(new FileReader(name))) {
            saves.clear();
            String sScore, sCoinScore, sPositionX, sPositionY, sComplexityX, sComplexityY, sLaserStation, probels;
            int i = 1;
            do{
                sScore = reader.readLine();
                if((sCoinScore = reader.readLine()) == null)break;
                sPositionX = reader.readLine();
                sPositionY = reader.readLine();
                sComplexityX = reader.readLine();
                sComplexityY = reader.readLine();
                sLaserStation = reader.readLine();
                probels = reader.readLine();
                SaveInfo localsave = new SaveInfo();
                localsave.sScore = Double.valueOf(sScore);
                localsave.sCoinScore = Integer.valueOf(sCoinScore);
                localsave.sPositionX = Double.valueOf(sPositionX);
                localsave.sPositionY = Double.valueOf(sPositionY);
                localsave.sComplexityX = Double.valueOf(sComplexityX);
                localsave.sComplexityY = Double.valueOf(sComplexityY);
                localsave.sLaserStation = Integer.valueOf(sLaserStation);
                saves.add(localsave);
                i++;
            }while(true);
        }
        catch (Exception ex){
            System.out.println("Error in method Read().");
            System.out.println(ex.getLocalizedMessage());
            System.exit(1);
        }
    }

    /**
     * Функция записи массива стрингов информации сохранений из файла
     * @return String[] - массив строк с информацие для таблицы рекордов
     */
    public String[] SaveMessage(){
        String[] SaveText = new String[10];
        saves2.clear();
        Read("Save2.txt",saves2);
        for(int i = 0;i < saves2.size();i++){
            SaveText[i] = "                 " + (i+1) + " )   ";
            if(saves2.get(i).sComplexityX == 1) SaveText[i] += "Лёгкий уровень. ";
            else if(saves2.get(i).sComplexityX == 1.2) SaveText[i] += "Средний уровень. ";
            else if(saves2.get(i).sComplexityX == 1.5) SaveText[i] += "Тяжёлый уровень. ";
            else if(saves2.get(i).sComplexityX == 2) SaveText[i] += "Уровень для психов. ";
            SaveText[i] += "Счёт: " + saves2.get(i).sScore + " | Монеты: " + saves2.get(i).sCoinScore;
        }
        return SaveText;
    }

    /**
     * Функция получения из файла и записи в соответствующие поля сохранённой информации
     * @param index - номер сохранения
     */
    public void StartFromRecords(int index){
        try{
            saves2.clear();
            Read("Save2.txt",saves2);
            this.score = saves2.get(index).sScore;
            this.coin_score = saves2.get(index).sCoinScore;
            person.setTranslateX(saves2.get(index).sPositionX);
            person.setTranslateY(saves2.get(index).sPositionY);
            this.ComplexityX = saves2.get(index).sComplexityX;
            this.ComplexityY = saves2.get(index).sComplexityY;
            station = saves2.get(index).sLaserStation;
        }
        catch (Exception ex){
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Функция записи в соответствующие ячейки значений из массива информации
     */
    public void RecordsMap(){
        save1 = new MenuItem(SaveText[0],580,40, Pos.CENTER_LEFT);
        save2 = new MenuItem(SaveText[1],580,40, Pos.CENTER_LEFT);
        save3 = new MenuItem(SaveText[2],580,40, Pos.CENTER_LEFT);
        save4 = new MenuItem(SaveText[3],580,40, Pos.CENTER_LEFT);
        save5 = new MenuItem(SaveText[4],580,40, Pos.CENTER_LEFT);
        save6 = new MenuItem(SaveText[5],580,40, Pos.CENTER_LEFT);
        save7 = new MenuItem(SaveText[6],580,40, Pos.CENTER_LEFT);
        save8 = new MenuItem(SaveText[7],580,40, Pos.CENTER_LEFT);
        save9 = new MenuItem(SaveText[8],580,40, Pos.CENTER_LEFT);
        save10 = new MenuItem(SaveText[9],580,40, Pos.CENTER_LEFT);
    }
}
