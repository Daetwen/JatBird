package sample;

import java.io.*;

import static sample.Main.saves2;

public class SaveInfo implements Serializable {
    /** Счётчик для сохранения*/
    public double sScore;
    /** Счётчик монет для сохранения*/
    public int sCoinScore;
    /** Поле позиции персонажа по Х для сохранения */
    public double sPositionX;
    /** Поле позиции персонажа по Y для сохранения */
    public double sPositionY;
    /** Поле сложности по Х для сохранения */
    public double sComplexityX;
    /** Поле сложности по Y для сохранения */
    public double sComplexityY;
    /** Поле состояния лазера для сохраения */
    public int sLaserStation;

    /**
     * Конструктор - создание нового объекта с определенными значениями.
     * @param score - счётчик из основной игры.
     * @param coinscore - счётчик монет из основной игры.
     * @param positionX - позиция игрока по Х из основной игры.
     * @param positionY - позиция игрока по Y из основной игры.
     * @param complexityX - сложности по X из основной игры.
     * @param complexityY - сложности по Y  основной игры.
     * @param laserstation - состояние лазера из основной игры.
     */
    SaveInfo(double score, int coinscore, double positionX, double positionY, double complexityX, double complexityY, int laserstation){
        this.sScore = score;
        this.sCoinScore = coinscore;
        this.sPositionX = positionX;
        this.sPositionY = positionY;
        this.sComplexityX = complexityX;
        this.sComplexityY = complexityY;
        this.sLaserStation = laserstation;
    }

    SaveInfo(){
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
     * @param score - счётчик из основной игры.
     * @param coinscore - счётчик монет из основной игры.
     * @param positionX - позиция игрока по Х из основной игры.
     * @param positionY - позиция игрока по Y из основной игры.
     * @param complexityX - сложности по X из основной игры.
     * @param complexityY - сложности по Y  основной игры.
     * @param laserstation - состояние лазера из основной игры.
     */
    //В данный момент не используется
    public void SetInfo(double score, int coinscore, double positionX, double positionY, double complexityX, double complexityY, int laserstation){
        this.sScore = score;
        this.sCoinScore = coinscore;
        this.sPositionX = positionX;
        this.sPositionY = positionY;
        this.sComplexityX = complexityX;
        this.sComplexityY = complexityY;
        this.sLaserStation = laserstation;
    }

    /**
     * Функция записи объектов в файл.
     * @param name - название файла для работы.
     */
    //В данный момент не используется
    public void Write(String name){
        try{
            FileOutputStream fos = new FileOutputStream(name);
            ObjectOutputStream oos = new ObjectOutputStream((fos));
            for(SaveInfo save : saves2){
                oos.writeObject(save);
            }
            oos.flush();
            oos.close();
        }
        catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * Функция чтения объектов из файла.
     * @param name - название файла для работы.
     */
    //В данный момент не используется
    public void Read(String name){
        try{
            FileInputStream fis = new FileInputStream(name);
            ObjectInputStream oin = new ObjectInputStream((fis));
            while(true){
                try{
                    saves2.add((SaveInfo)oin.readObject());
                }
                catch (EOFException ex){
                    ex.getLocalizedMessage();
                    break;
                }
                catch (IOException ex2){
                    ex2.getLocalizedMessage();
                    break;
                }
                catch (ClassNotFoundException ex3){
                    ex3.getLocalizedMessage();
                    break;
                }
            }
            fis.close();
        }
        catch(EOFException e){
            System.out.println(e.getLocalizedMessage());
        }
        catch(FileNotFoundException ex){
            System.out.println(ex.getLocalizedMessage());
        }
        catch(IOException exx){
            System.out.println(exx.getLocalizedMessage());
        }
    }

    //В данный момент не используется
    public void Show(){
        System.out.println(this.sScore);
        System.out.println(this.sCoinScore);
        System.out.println(this.sPositionX);
        System.out.println(this.sPositionY);
        System.out.println(this.sComplexityX);
        System.out.println(this.sComplexityY);
        System.out.println(this.sLaserStation);
    }
}
