package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import javax.sound.midi.Soundbank;

public class Bomb extends Entity {

    private int time = 0;

    public boolean isRemoved() {
        return isRemoved;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }

    private boolean isRemoved;

    private double timeLimit = 120;
    private boolean isBoom;

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        isBoom = false;
        isRemoved = false;
    }


    @Override
    public void update() {
        if (timeLimit > 0)
            timeLimit--;
        else {
            exploding();
            setRemoved(true);
        }
        animation();
    }

    public void animation() {
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, time, 9).getFxImage();
        time++;
    }

    public void exploding() {

    }
}
