
public class Cell {

    private static final int cellSize = 40;
    private int x;
    private int y;
    private int state;


    public Cell(int x, int y, int state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return cellSize;
    }

    public int getWidth() {
        return cellSize;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void update(boolean decrease) {
        if (decrease && this.state > 0) {
            this.state--;
        }
    }

    public Sprite getSprite() {
        if (this.state > 0) {
            return Sprite.BODY;
        } else if (this.state == 0) {
            return null;
        } else {
            return Sprite.APPLE;
        }
    }
}
