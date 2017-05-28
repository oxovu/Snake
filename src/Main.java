import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.util.Random;

public class Main {
    private static final int cellsNumberX = 20;
    private static final int cellsNumberY = 20;
    private static boolean needToExit = false;
    private static int x = -1, y = 0, direction = 1, length = 3;
    private static boolean decrease = true;

    public static void main(String[] args) {
        GUI.init();
        newObj();

        while (!needToExit) {
            input();
            move();
            GUI.draw();
            GUI.update(decrease);

        }
    }

    private static void move() {
        decrease = true;

        switch (direction) {
            case 0:
                y++;
                break;
            case 1:
                x++;
                break;
            case 2:
                y--;
                break;
            case 3:
                x--;
                break;
        }

        if (x < 0 || x >= cellsNumberX || y < 0 || y >= cellsNumberY) {
            System.exit(1);
        }

        int nextCellState = GUI.getState(x, y);

        if (nextCellState > 0) {
            System.exit(1);

        } else {
            if (nextCellState < 0) {
                length++;
                newObj();
                decrease = false;
            }
            GUI.setState(x, y, length);
        }
    }

    private static void newObj() {
        int point = new Random().nextInt(cellsNumberX * cellsNumberY - length);

        for (int i = 0; i < cellsNumberX; i++) {
            for (int j = 0; j < cellsNumberY; j++) {
                if (GUI.getState(i, j) <= 0) {
                    if (point == 0) {
                        GUI.setState(i, j, -1);
                        return;
                    } else {
                        point--;
                    }
                }
            }
        }

    }


    private static void input() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                switch (Keyboard.getEventKey()) {
                    case Keyboard.KEY_UP:
                        if (direction != 2) direction = 0;
                        break;
                    case Keyboard.KEY_RIGHT:
                        if (direction != 3) direction = 1;
                        break;
                    case Keyboard.KEY_DOWN:
                        if (direction != 0) direction = 2;
                        break;
                    case Keyboard.KEY_LEFT:
                        if (direction != 1) direction = 3;
                        break;
                }
            }
        }
        Display.isCloseRequested();
    }

}
