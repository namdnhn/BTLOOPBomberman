package uet.oop.bomberman.entities.movingEntities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.bomb.KillingArea;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public abstract class movingEntity {
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSPEED() {
        return SPEED;
    }

    public void setSPEED(int SPEED) {
        this.SPEED = SPEED;
    }

    protected int SPEED;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

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

    protected int w;
    protected int h;

    public void setInBomb(boolean inBomb) {
        this.inBomb = inBomb;
    }

    public boolean isInBomb() {
        return inBomb;
    }

    protected boolean inBomb;

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

    public boolean checkCollision(movingEntity object) {
        int left1, left2, right1, right2, up1, up2, down1, down2;
        left1 = this.getX();
        right1 = this.getX() + this.getW();
        up1 = this.getY();
        down1 = this.getY() + this.getH();
        left2 = object.getX();
        right2 = object.getX() + object.getW();
        up2 = object.getY();
        down2 = object.getY() + object.getH();

        if (left1 <= left2 && right1 >= left2) {
            if (up1 <= up2 && down1 >= up2)          return true;
            else return up1 >= up2 && down2 >= up1;
        }
        else if (left2 <= left1 && right2 >= left1) {
            if (up1 <= up2 && down1 >= up2) return true;
            else return up1 >= up2 && down2 >= up1;
        }
        else return false;
    }

    public boolean checkKilled(KillingArea killingArea) {
        int left1, left2, right1, right2, up1, up2, down1, down2;
        left1 = this.getX();
        right1 = this.getX() + this.getW();
        up1 = this.getY();
        down1 = this.getY() + this.getH();
        left2 = killingArea.getX_left();
        right2 = killingArea.getX_right();
        up2 = killingArea.getY_up();
        down2 = killingArea.getY_down();

        if (left1 <= left2 && right1 >= left2) {
            if (up1 <= up2 && down1 >= up2)          return true;
            else return up1 >= up2 && down2 >= up1;
        }
        else if (left2 <= left1 && right2 >= left1) {
            if (up1 <= up2 && down1 >= up2) return true;
            else return up1 >= up2 && down2 >= up1;
        }
        else return false;
    }

    public boolean checkInBomb(Bomb object) {
        int left1, left2, right1, right2, up1, up2, down1, down2;
        left1 = this.getX() + 2;
        right1 = this.getX() + this.getW() - 2;
        up1 = this.getY() + 2;
        down1 = this.getY() + this.getH() - 2;
        left2 = object.getX() + 2;
        right2 = object.getX() + Sprite.SCALED_SIZE - 2;
        up2 = object.getY() + 2;
        down2 = object.getY() + Sprite.SCALED_SIZE - 2;

        if (left1 <= left2 && right1 >= left2) {
            if (up1 <= up2 && down1 >= up2)          return true;
            else return up1 >= up2 && down2 >= up1;
        }
        else if (left2 <= left1 && right2 >= left1) {
            if (up1 <= up2 && down1 >= up2) return true;
            else return up1 >= up2 && down2 >= up1;
        }
        else return false;
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