package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.graphics.Sprite;

public class KillingArea {
    public int getX_left() {
        return x_left;
    }

    public void setX_left(int x_left) {
        this.x_left = x_left;
    }

    public int getX_right() {
        return x_right;
    }

    public void setX_right(int x_right) {
        this.x_right = x_right;
    }

    public int getY_up() {
        return y_up;
    }

    public void setY_up(int y_up) {
        this.y_up = y_up;
    }

    public int getY_down() {
        return y_down;
    }

    public void setY_down(int y_down) {
        this.y_down = y_down;
    }

    protected int x_left;
    protected int x_right;
    protected int y_up;
    protected int y_down;

    public KillingArea(Bomb bomb) {
        this.x_left = bomb.getX() - 5;
        this.x_right = bomb.getX() + Sprite.SCALED_SIZE - 5;
        this.y_up = bomb.getY() - 5;
        this.y_down = bomb.getY() + Sprite.SCALED_SIZE - 5;
    }


}
