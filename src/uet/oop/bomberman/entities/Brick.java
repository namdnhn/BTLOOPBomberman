package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {
    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    private boolean removed = false;

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean broken) {
        isBroken = broken;
    }

    private boolean isBroken = false;
    private int animationTime = 45;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
    }

    public void animation(int time) {
        img = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, time, 9).getFxImage();
        System.out.println("" + this.getX() / Sprite.SCALED_SIZE + ", " + this.getY() / Sprite.SCALED_SIZE);
    }
}
