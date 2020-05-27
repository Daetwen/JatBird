package Saves;

import java.io.*;
import java.util.ArrayList;

import static sample.Main.score;
import static sample.Main.coin_score;
import static sample.Main.person;
import static sample.Main.saves2;
import static sample.Obstacles.station;
import static sample.Main.ComplexityX;
import static sample.Main.ComplexityY;

public class SaveInfo implements Serializable {
    /**
     * Счётчик для сохранения
     */
    public double sScore;
    /**
     * Счётчик монет для сохранения
     */
    public int sCoinScore;
    /**
     * Поле позиции персонажа по Х для сохранения
     */
    public double sPositionX;
    /**
     * Поле позиции персонажа по Y для сохранения
     */
    public double sPositionY;
    /**
     * Поле сложности по Х для сохранения
     */
    public double sComplexityX;
    /**
     * Поле сложности по Y для сохранения
     */
    public double sComplexityY;
    /**
     * Поле состояния лазера для сохраения
     */
    public int sLaserStation;

    /**
     * Конструктор - создание нового объекта с определенными значениями.
     *
     * @param score        - счётчик из основной игры.
     * @param coinscore    - счётчик монет из основной игры.
     * @param positionX    - позиция игрока по Х из основной игры.
     * @param positionY    - позиция игрока по Y из основной игры.
     * @param complexityX  - сложности по X из основной игры.
     * @param complexityY  - сложности по Y  основной игры.
     * @param laserstation - состояние лазера из основной игры.
     */
    public SaveInfo(double score, int coinscore, double positionX, double positionY, double complexityX, double complexityY, int laserstation) {
        this.sScore = score;
        this.sCoinScore = coinscore;
        this.sPositionX = positionX;
        this.sPositionY = positionY;
        this.sComplexityX = complexityX;
        this.sComplexityY = complexityY;
        this.sLaserStation = laserstation;
    }

    /**
     * Конструктор - создание нового объекта с определенными значениями.
     */
    public SaveInfo() {
        this.sScore = 0;
        this.sCoinScore = 0;
        this.sPositionX = 0;
        this.sPositionY = 0;
        this.sComplexityX = 0;
        this.sComplexityY = 0;
        this.sLaserStation = 0;
    }

    /**
     * Функция записи полей класса (большой сеттер).
     *
     * @param score        - счётчик из основной игры.
     * @param coinscore    - счётчик монет из основной игры.
     * @param positionX    - позиция игрока по Х из основной игры.
     * @param positionY    - позиция игрока по Y из основной игры.
     * @param complexityX  - сложности по X из основной игры.
     * @param complexityY  - сложности по Y  основной игры.
     * @param laserstation - состояние лазера из основной игры.
     */

    //В данный момент не используется
    public void SetInfo(double score, int coinscore, double positionX, double positionY, double complexityX, double complexityY, int laserstation) {
        this.sScore = score;
        this.sCoinScore = coinscore;
        this.sPositionX = positionX;
        this.sPositionY = positionY;
        this.sComplexityX = complexityX;
        this.sComplexityY = complexityY;
        this.sLaserStation = laserstation;
    }

    //В данный момент не используется
    public void Show() {
        System.out.println(this.sScore);
        System.out.println(this.sCoinScore);
        System.out.println(this.sPositionX);
        System.out.println(this.sPositionY);
        System.out.println(this.sComplexityX);
        System.out.println(this.sComplexityY);
        System.out.println(this.sLaserStation);
        System.out.println("=================");
    }

    /**
     * Функция сохранения в файл основных геимплеиных полей.
     *
     * @param NameOfFile - название файла для работы с сохранениями
     */
    public static void Save(String NameOfFile) {
        try (FileWriter writer = new FileWriter(NameOfFile, false)) {
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
            writer.write(sLaserStation + "\r\n");
            writer.write("\r\n");
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    /**
     * Функция чтения из файла и записи в соответсвующие поля данных.
     *
     * @param NameOfFile - название файла для работы с сохранениями
     */
    public static void Reload(String NameOfFile) {
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
        } catch (IOException ex1) {
            ex1.getLocalizedMessage();
            System.exit(1);
        } catch (Exception ex) {
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Функция создания рекордного сохранения, сортировки с остальными рекордами и записи таблицы рекордов в файл.
     */
    public void IsRecord() {
        SaveInfo save = new SaveInfo(score, coin_score, person.getTranslateX(), person.getTranslateY(), ComplexityX, ComplexityY, station);
        saves2 = this.Read("Save2.txt", saves2);
        saves2.add(save);
        SaveInfo localsave = new SaveInfo();
        for (int i = 1; i < saves2.size(); ++i) {
            for (int y = 0; y < saves2.size() - 1; y++) {
                if (saves2.get(y).sScore < saves2.get(y + 1).sScore) {
                    Switch(localsave, saves2.get(y));
                    Switch(saves2.get(y), saves2.get(y + 1));
                    Switch(saves2.get(y + 1), localsave);
                }
            }
        }
        if (saves2.size() == 11) saves2.remove(10);
        this.Write("Save2.txt", saves2);
        this.SaveMessage();
    }

    /**
     * Функция чтения параметров из одного объекта класса SaveInfo в другой.
     *
     * @param second -  объект, из которого происходит считывание
     */
    public void Switch(SaveInfo first, SaveInfo second) {
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
     *
     * @param name  - название файла, в который происходит запись
     * @param saves -  коллекция, из которой происходит считывание
     */
    public void Write(String name, ArrayList<SaveInfo> saves) {
        try (FileWriter writer = new FileWriter(name, false)) {
            int i = 0;
            while (saves2.size() != i) {
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
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    /**
     * Функция записи массива стрингов информации сохранений из файла
     *
     * @return String[] - массив строк с информацие для таблицы рекордов
     */
    public String[] SaveMessage() {
        String[] SaveText = new String[10];
        saves2 = Read("Save2.txt", saves2);
        for (int i = 0; i < saves2.size(); i++) {
            SaveText[i] = "                 " + (i + 1) + " )   ";
            if (saves2.get(i).sComplexityX == 1) SaveText[i] += "Лёгкий уровень. ";
            else if (saves2.get(i).sComplexityX == 1.2) SaveText[i] += "Средний уровень. ";
            else if (saves2.get(i).sComplexityX == 1.5) SaveText[i] += "Тяжёлый уровень. ";
            else if (saves2.get(i).sComplexityX == 2) SaveText[i] += "Уровень для психов. ";
            SaveText[i] += "Счёт: " + saves2.get(i).sScore + " | Монеты: " + saves2.get(i).sCoinScore;
        }
        return SaveText;
    }

    /**
     * Функция чтения из файла в коллекцию.
     *
     * @param name  - название файла, из которого происходит считывание
     * @param saves -  коллекция, в которую происходит считывание
     */
    public ArrayList Read(String name, ArrayList<SaveInfo> saves) {
        try (BufferedReader reader = new BufferedReader(new FileReader(name))) {
            saves.clear();
            String sScore, sCoinScore, sPositionX, sPositionY, sComplexityX, sComplexityY, sLaserStation, probels;
            do {
                sScore = reader.readLine();
                if ((sCoinScore = reader.readLine()) == null) break;
                sPositionX = reader.readLine();
                sPositionY = reader.readLine();
                sComplexityX = reader.readLine();
                sComplexityY = reader.readLine();
                sLaserStation = reader.readLine();
                probels = reader.readLine();
                SaveInfo saveinfo = new SaveInfo();
                saveinfo.sScore = Double.valueOf(sScore);
                saveinfo.sCoinScore = Integer.valueOf(sCoinScore);
                saveinfo.sPositionX = Double.valueOf(sPositionX);
                saveinfo.sPositionY = Double.valueOf(sPositionY);
                saveinfo.sComplexityX = Double.valueOf(sComplexityX);
                saveinfo.sComplexityY = Double.valueOf(sComplexityY);
                saveinfo.sLaserStation = Integer.valueOf(sLaserStation);
                saves.add(saveinfo);
            } while (true);
        } catch (Exception ex) {
            System.out.println("Error in method Read().");
            System.out.println(ex.getLocalizedMessage());
            System.exit(1);
        }
        return saves;
    }

    /**
     * Функция получения из файла и записи в соответствующие поля сохранённой информации
     *
     * @param index - номер сохранения
     */
    public void StartFromRecords(int index) {
        try {
            saves2.clear();
            Read("Save2.txt", saves2);
            score = saves2.get(index).sScore;
            coin_score = saves2.get(index).sCoinScore;
            person.setTranslateX(saves2.get(index).sPositionX);
            person.setTranslateY(saves2.get(index).sPositionY);
            ComplexityX = saves2.get(index).sComplexityX;
            ComplexityY = saves2.get(index).sComplexityY;
            station = saves2.get(index).sLaserStation;
        } catch (Exception ex) {
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }
}
