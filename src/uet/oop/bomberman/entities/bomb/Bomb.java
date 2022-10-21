package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import javax.sound.midi.Soundbank;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {

    private int time = 0;

    public boolean isRemoved() {
        return isRemoved;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }

    private boolean isRemoved;

    private int timeLimit = 120;

    public boolean isBoom() {
        return isBoom;
    }

    public void setBoom(boolean boom) {
        isBoom = boom;
    }

    private boolean isBoom;

    private int timeEnd = 45;

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    private int area = 1;
    private List<Flame> flames = new ArrayList<>();

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        isBoom = false;
        isRemoved = false;
        for (int i = 0; i < area; i++) {
            if (i < area - 1) {
                flames.add(new Flame(xUnit, yUnit - 1, Sprite.explosion_vertical.getFxImage()));
            } else {
                flames.add(new Flame(xUnit, yUnit - 1, Sprite.explosion_vertical_top_last.getFxImage()));
            }
        }
    }


    @Override
    public void update() {
        if (timeLimit > 0)
            timeLimit--;
        else {
            if (!isBoom)
                exploding();
            else if (timeEnd > 0)
                timeEnd--;
            else
                setRemoved(true);
        }
        animation();
    }

    public void animation() {
        if (!isBoom)
            img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, time, 9).getFxImage();
        else
            img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, time, 9).getFxImage();
        time++;
    }

    public void exploding() {
        setBoom(true);
    }
}
