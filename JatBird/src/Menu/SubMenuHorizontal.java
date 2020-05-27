package Menu;

import javafx.scene.layout.HBox;

public class SubMenuHorizontal extends HBox {

    /**
     * Конструктор - создание нового объекта, являющегося контейнером для объектов-кнопок.
     *
     * @param items - объекты класса MenuItem, являющиеся кнопками.
     */
    public SubMenuHorizontal(MenuItem... items) {
        try {
            setSpacing(5);
            setTranslateX(10);
            setTranslateY(375);
            for (MenuItem item : items) {
                getChildren().addAll(item);
            }
        } catch (Exception ex) {
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }

    /**
     * Конструктор - создание нового объекта, являющегося контейнером для объектов-кнопок.
     *
     * @param X     - координата для отображения объекта по вертикали
     * @param Y     - координата для отображения объекта по горизонтали.
     * @param items - объекты класса MenuItem, являющиеся кнопками.
     * @throws IllegalArgumentException, если координаты для отображения меньше 0.
     */
    public SubMenuHorizontal(int X, int Y, MenuItem... items) {
        try {
            setSpacing(5);
            if (X < 0)
                throw new IllegalArgumentException("Число положения меню по горизонтали меньше 0.");
            else setTranslateX(X);
            if (Y < 0)
                throw new IllegalArgumentException("Число положения меню по вертикали меньше 0.");
            else setTranslateY(Y);
            for (MenuItem item : items) {
                getChildren().addAll(item);
            }
        } catch (Exception ex) {
            ex.getLocalizedMessage();
            System.exit(1);
        }
    }
}
