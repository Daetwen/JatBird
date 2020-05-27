package Saves;

import java.io.*;
import java.util.ArrayList;

import static java.lang.System.exit;
import static sample.Main.person;

public class MapOfGame {
    /**Переменная для сохранения среднего значения высоты смерти персонажа по Y*/
    public double DeathPositionY;
    /**Переменная для сохранения среднего значения высоты полёта персонажа по Y*/
    public double AverageFlightAltitudeY;
    /**Переменная для сохранения среднего значения дальности персонажа по Х*/
    public double AverageFlightAltitudeX;
    /**Переменная для сохранения среднего значения собранных монет*/
    public double AverageNumberOfCoins;
    /**Переменная для сохранения среднего значения собранных бонусов*/
    public double AverageBoosts;
    /**Переменная для сохранения количества сыгранных игр*/
    public static int counter = 0;
    /**Переменная для подсчёта "тиков" при работе в функции GetSum()*/
    public static int CounterForY = 0;
    /**Переменная для сохранения суммы значений высоты смерти персонажа по Y*/
    public double SumDeathPositionY;
    /**Переменная для сохранения суммы значений высоты полёта персонажа по Y*/
    public double SumAverageFlightAltitudeY;
    /**Переменная для сохранения суммы значений дальности полёта персонажа по Х*/
    public double SumAverageFlightAltitudeX;
    /**Переменная для сохранения суммы значений собранных монет*/
    public double SumAverageNumberOfCoins;
    /**Переменная для сохранения суммы значений собранных бонусов*/
    public double SumAverageBoosts;
    /**Переменная для сохранения названия первого файла для сохранения средних значений*/
    String NameOfFile = "MapOfGame.txt";
    /**Переменная для сохранения названия второго файла для сохранения сумм значений*/
    String NameOfFile2 = "MapOfGameInfo.txt";

    /**Конструктор - создание нового объекта с занулнными всеми полями.*/
    public MapOfGame() {
        this.DeathPositionY = 0;
        this.AverageFlightAltitudeY = 0;
        this.AverageFlightAltitudeX = 0;
        this.AverageNumberOfCoins = 0;
        this.AverageBoosts = 0;
    }

    /**
     * Конструктор - создание нового объекта с определенными значениями, переданными через параметры.
     * @param DeathPositionY           - высота смерти из 1 игры.
     * @param AverageFlightAltitudeY   - средняя высота полёта из 1 игры.
     * @param AverageFlightAltitudeX   - дальность полёта из 1 игры.
     * @param AverageNumberOfCoins     - количество собранных монет из 1 игры.
     * @param AverageBoosts            - количество собранных бустов из 1 игры.
     */
    public MapOfGame(double DeathPositionY, double AverageFlightAltitudeY,
                     double AverageFlightAltitudeX, double AverageNumberOfCoins, double AverageBoosts) {
        this.DeathPositionY = DeathPositionY;
        this.AverageFlightAltitudeY = AverageFlightAltitudeY;
        this.AverageFlightAltitudeX = AverageFlightAltitudeX;
        this.AverageNumberOfCoins = AverageNumberOfCoins;
        this.AverageBoosts = AverageBoosts;
    }

    /**
     * Функция подсчёта из заполнения полей карты игры с учётом последней сыгранной игры.
     * @param positionY                - высота смерти из 1 игры.
     * @param AverageFlightAltitudeX   - дальность полёта из 1 игры.
     * @param AverageNumberOfCoins     - количество собранных монет из 1 игры.
     * @param AverageBoosts            - количество собранных бустов из 1 игры.
     */
    public void SetMapOfGame(double positionY, double AverageFlightAltitudeX,
                             double AverageNumberOfCoins, double AverageBoosts) {
        try {
            double temp = (this.SumAverageFlightAltitudeY / this.CounterForY);
            this.counter = ReloadMapOfGame(NameOfFile2, counter);

            this.SumDeathPositionY = this.DeathPositionY;
            this.SumAverageFlightAltitudeY = this.AverageFlightAltitudeY;
            this.SumAverageFlightAltitudeX = this.AverageFlightAltitudeX;
            this.SumAverageNumberOfCoins = this.AverageNumberOfCoins;
            this.SumAverageBoosts = this.AverageBoosts;

            this.SumDeathPositionY += positionY;
            this.SumAverageFlightAltitudeY = +temp;
            this.SumAverageFlightAltitudeX += AverageFlightAltitudeX;
            this.SumAverageNumberOfCoins += AverageNumberOfCoins;
            this.SumAverageBoosts += AverageBoosts;
            this.counter++;

            this.DeathPositionY = this.SumDeathPositionY;
            this.AverageFlightAltitudeY = this.SumAverageFlightAltitudeY;
            this.AverageFlightAltitudeX = this.SumAverageFlightAltitudeX;
            this.AverageNumberOfCoins = this.SumAverageNumberOfCoins;
            this.AverageBoosts = this.SumAverageBoosts;
            this.SaveMapeOfGame(NameOfFile2, this.counter);

            this.DeathPositionY = this.SumDeathPositionY / counter;
            this.AverageFlightAltitudeY = this.SumAverageFlightAltitudeY / counter;
            this.AverageFlightAltitudeX = this.SumAverageFlightAltitudeX / counter;
            this.AverageNumberOfCoins = this.SumAverageNumberOfCoins / counter;
            this.AverageBoosts = this.SumAverageBoosts / counter;

            this.SaveMapeOfGame(NameOfFile, this.counter);
            this.Refresh();
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            this.Refresh();
            this.SaveMapeOfGame(NameOfFile, this.counter);
            this.SaveMapeOfGame(NameOfFile2, this.counter);
        }
    }

    /**
     * Функция сохранения полей карты игры в файл.
     * @param NameOfFile - название файла.
     * @param count      - количество сыгранных игр.
     */
    public void SaveMapeOfGame(String NameOfFile, int count) {
        try (FileWriter writer = new FileWriter(NameOfFile, false)) {
            writer.write(this.DeathPositionY + "\r\n");
            writer.write(this.AverageFlightAltitudeY + "\r\n");
            writer.write(this.AverageFlightAltitudeX + "\r\n");
            writer.write(this.AverageNumberOfCoins + "\r\n");
            writer.write(this.AverageBoosts + "\r\n");
            writer.write(count + "\r\n");
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    /**
     * Функция считывания полей карты игры из файла.
     * @param NameOfFile - название файла.
     * @param count      - количество сыгранных игр.
     */
    public int ReloadMapOfGame(String NameOfFile, int count) {
        try (BufferedReader reader = new BufferedReader(new FileReader(NameOfFile))) {
            File file = new File(NameOfFile);
            if (file.length() != 0) {
                this.DeathPositionY = Double.valueOf(reader.readLine());
                this.AverageFlightAltitudeY = Double.valueOf(reader.readLine());
                this.AverageFlightAltitudeX = Double.valueOf(reader.readLine());
                this.AverageNumberOfCoins = Double.valueOf(reader.readLine());
                this.AverageBoosts = Double.valueOf(reader.readLine());
                count = Integer.valueOf(reader.readLine());
            } else exit(0);
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            this.Refresh();
        }
        return count;
    }

    /**
     * Функция созания коллекции текстовых строк на основании карты игры.
     * @param Map - коллекция.
     */
    public ArrayList SaveMessage(ArrayList<String> Map) {
        String[] buffer = new String[6];
        Map.clear();
        this.counter = ReloadMapOfGame(NameOfFile, this.counter);
        buffer[0] = "    Средняя высота смерти персонажа: " + Double.toString(this.DeathPositionY);
        buffer[1] = "    Средняя высота полёта персонажа: " + Double.toString(this.AverageFlightAltitudeY);
        buffer[2] = "    Средняя дальность полёта персонажа: " + Double.toString(this.AverageFlightAltitudeX);
        buffer[3] = "    Среднее количество собираемых монет: " + Double.toString(this.AverageNumberOfCoins);
        buffer[4] = "    Среднее количество собираемых бонусов: " + Double.toString(this.AverageBoosts);
        buffer[5] = "    Количество сыгранных игр: " + Integer.toString(this.counter);
        for (int i = 0; i < 6; i++) {
            Map.add(buffer[i]);
        }
        return Map;
    }

    /**
     * Функция подсчёта суммы суммы высот, на которых был персонаж и "тиков",
     * для дальнейшего вычесления средней высоты полта за игру
     */
    public void GetSum() {
        CounterForY++;
        this.SumAverageFlightAltitudeY += person.getTranslateY();
    }

    /**
     * Функция обнуления всех полей данного класса.
     */
    public void Refresh() {
        this.DeathPositionY = 0;
        this.AverageFlightAltitudeY = 0;
        this.AverageFlightAltitudeX = 0;
        this.AverageNumberOfCoins = 0;
        this.AverageBoosts = 0;
        this.counter = 0;
        this.CounterForY = 1;
        this.SumDeathPositionY = 0;
        this.SumAverageFlightAltitudeY = 0;
        this.SumAverageFlightAltitudeX = 0;
        this.SumAverageNumberOfCoins = 0;
        this.SumAverageBoosts = 0;

    }
}
