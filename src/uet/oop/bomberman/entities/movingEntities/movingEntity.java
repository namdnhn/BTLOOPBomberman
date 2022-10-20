package uet.oop.bomberman.entities.movingEntities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

public abstract class movingEntity {
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected int SPEED;

    protected int x;

    protected int y;

    public int getValX() {
        return valX;
    }

    public void setValX(int valX) {
        this.valX = valX;
    }

    public int getValY() {
        return valY;
    }

    public void setValY(int valY) {
        this.valY = valY;
    }

    protected int valX;

    public void raiseVX() {
        this.valX++;
    }

    public void raiseVY() {
        this.valY++;
    }

    public void lowVX() {
        this.valX--;
    }

    public void lowVY() {
        this.valY--;
    }
    protected int valY;

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    protected int w; // chieu rong nhan vat.
    protected int h; // chieu cao nhan vat.

    public int getCurrX1() {
        return getX() / Sprite.SCALED_SIZE;
    }

    public int getCurrX2() {
        return (getX() + getW()) / Sprite.SCALED_SIZE;
    }

    public int getCurrY1() {
        return getY() / Sprite.SCALED_SIZE;
    }

    public int getCurrY2() {
        return (getY() + getH()) / Sprite.SCALED_SIZE;
    }

    public enum WALK_TYPE {
        RIGHT, LEFT, UP, DOWN
    }

    protected Image img;

    public void setWallPass(boolean wallPass) {
        this.wallPass = wallPass;
    }

    public boolean isWallPass() {
        return wallPass;
    }

    protected boolean wallPass;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public movingEntity(int xUnit, int yUnit, Image img) {
        this.x = xUnit;
        this.y = yUnit;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    protected int time = 0;
    public WALK_TYPE status;
    public abstract void animation();
    public abstract void move();

    public abstract void update();
}