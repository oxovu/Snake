import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class GUI {
    private static final int cellsNumberX = 20;
    private static final int cellsNumberY = 20;
    private static final int cellSize = 40;
    private static final int screenWidth = cellsNumberX * cellSize;
    private static final int screenHeigth = cellsNumberY * cellSize;
    private static final String screenName = "Snake";
    private static Cell[][] cells;


    public static void init() {
        initializeOpenGL();

        cells = new Cell[cellsNumberX][cellsNumberY];

        Random rnd = new Random();

        for (int i = 0; i < cellsNumberX; i++) {
            for (int j = 0; j < cellsNumberY; j++) {
                cells[i][j] = new Cell(i * cellSize, j * cellSize, rnd.nextInt(100) < 0.05F ? -1 : 0);
            }
        }
    }


    public static void update(boolean decrease) {
        updateOpenGL();

        for (Cell[] line : cells) {
            for (Cell cell : line) {
                cell.update(decrease);
            }
        }
    }


    public static void draw() {
        glClear(GL_COLOR_BUFFER_BIT);

        for (Cell[] line : cells) {
            for (Cell cell : line) {
                drawElem(cell);
            }
        }
    }


    private static void drawElem(Cell elem) {
        if (elem.getSprite() == null) return;

        elem.getSprite().getTexture().bind();

        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(elem.getX(), elem.getY() + elem.getHeight());
        glTexCoord2f(1, 0);
        glVertex2f(elem.getX() + elem.getWidth(), elem.getY() + elem.getHeight());
        glTexCoord2f(1, 1);
        glVertex2f(elem.getX() + elem.getWidth(), elem.getY());
        glTexCoord2f(0, 1);
        glVertex2f(elem.getX(), elem.getY());
        glEnd();
    }

    public static int getState(int x, int y) {
        return cells[x][y].getState();
    }

    public static void setState(int x, int y, int state) {
        cells[x][y].setState(state);
    }


    private static void updateOpenGL() {
        Display.update();
        Display.sync(5);
    }

    private static void initializeOpenGL() {

        try {
            Display.setDisplayMode(new DisplayMode(screenWidth, screenHeigth));
            Display.setTitle(screenName);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, screenWidth, 0, screenHeigth, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glClearColor(1, 1, 1, 1);
    }
}
