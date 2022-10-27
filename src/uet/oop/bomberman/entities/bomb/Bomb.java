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

    private int area = 3;

    public List<Flame> getFlames() {
        return flames;
    }

    private List<Flame> flames = new ArrayList<>();


    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        isBoom = false;
        isRemoved = false;
        addFlameUp();
        addFlameDown();
        addFlameRight();
        addFlameLeft();
    }

    public void addFlameUp() {
        int spread = -1;
        while (spread >= -area && this.yCoordinate + spread >= 0
                && BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate + spread][this.xCoordinate] != '#'
                && BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate + spread][this.xCoordinate] != '*') {
            if (spread == -area) {
                flames.add(new Flame(this.xCoordinate, this.yCoordinate + spread, Flame.FLAME_TYPES.TOPLAST));
                break;
            } else {
                flames.add(new Flame(this.xCoordinate, this.yCoordinate + spread, Flame.FLAME_TYPES.VER));
                spread--;
            }
        }
    }

    public void addFlameDown() {
        int spread = 1;
        while (spread <= area && this.yCoordinate + spread < BombermanGame.HEIGHT
                && BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate + spread][this.xCoordinate] != '#'
                && BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate + spread][this.xCoordinate] != '*') {
            if (spread == area) {
                flames.add(new Flame(this.xCoordinate, this.yCoordinate + spread, Flame.FLAME_TYPES.DOWNLAST));
                break;
            } else {
                flames.add(new Flame(this.xCoordinate, this.yCoordinate + spread, Flame.FLAME_TYPES.VER));
                spread++;
            }
        }
    }

    public void addFlameRight() {
        int spread = 1;
        while (spread <= area && this.xCoordinate + spread < BombermanGame.WIDTH
                && BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate][this.xCoordinate + spread] != '#'
                && BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate][this.xCoordinate + spread] != '*') {
            if (spread == area) {
                flames.add(new Flame(this.xCoordinate + spread, this.yCoordinate, Flame.FLAME_TYPES.RIGHTLAST));
                break;
            } else {
                flames.add(new Flame(this.xCoordinate + spread, this.yCoordinate, Flame.FLAME_TYPES.HORI));
                spread++;
            }
        }
    }

    public void addFlameLeft() {
        int spread = -1;
        while (spread >= -area && this.xCoordinate + spread >= 0
                && BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate][this.xCoordinate + spread] != '#'
                && BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate][this.xCoordinate + spread] != '*') {
            if (spread == -area) {
                flames.add(new Flame(this.xCoordinate + spread, this.yCoordinate, Flame.FLAME_TYPES.LEFTLAST));
                break;
            } else {
                flames.add(new Flame(this.xCoordinate + spread, this.yCoordinate, Flame.FLAME_TYPES.HORI));
                spread--;
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
            else {
                setRemoved(true);
                int spread = -1;
                while (spread >= -area && this.yCoordinate + spread >= 0
                        && BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate + spread][this.xCoordinate] != '#') {
                    if (BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate + spread][this.xCoordinate] == '*') {
                        for (Brick b : BombermanGame.Bricks) {
                            if (b.getxCoordinate() == this.xCoordinate && b.getyCoordinate() == this.yCoordinate + spread) {
                                b.setRemoved(true);
                                BombermanGame.map.setMAP_ENTITY(b.getyCoordinate(), b.getxCoordinate(), ' ');
                                break;
                            }
                        }
                        break;
                    }
                    --spread;
                }

                spread = -1;
                while (spread >= -area && this.xCoordinate + spread >= 0
                        && BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate][this.xCoordinate + spread] != '#') {
                    if (BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate][this.xCoordinate + spread] == '*') {
                        for (Brick b : BombermanGame.Bricks) {
                            if (b.getxCoordinate() == this.xCoordinate + spread && b.getyCoordinate() == this.yCoordinate) {
                                b.setRemoved(true);
                                BombermanGame.map.setMAP_ENTITY(b.getyCoordinate(), b.getxCoordinate(), ' ');
                                break;
                            }
                        }
                        break;
                    }
                    --spread;
                }

                spread = 1;
                while (spread <= area && this.xCoordinate + spread < BombermanGame.WIDTH
                        && BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate][this.xCoordinate + spread] != '#') {
                    if (BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate][this.xCoordinate + spread] == '*') {
                        for (Brick b : BombermanGame.Bricks) {
                            if (b.getxCoordinate() == this.xCoordinate + spread && b.getyCoordinate() == this.yCoordinate) {
                                b.setRemoved(true);
                                BombermanGame.map.setMAP_ENTITY(b.getyCoordinate(), b.getxCoordinate(), ' ');
                                break;
                            }
                        }
                        break;
                    }
                    ++spread;
                }

                spread = 1;
                while (spread <= area && this.yCoordinate + spread < BombermanGame.HEIGHT
                        && BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate + spread][this.xCoordinate] != '#') {
                    if (BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate + spread][this.xCoordinate] == '*') {
                        for (Brick b : BombermanGame.Bricks) {
                            if (b.getxCoordinate() == this.xCoordinate && b.getyCoordinate() == this.yCoordinate + spread) {
                                b.setRemoved(true);
                                BombermanGame.map.setMAP_ENTITY(b.getyCoordinate(), b.getxCoordinate(), ' ');
                                break;
                            }
                        }
                        break;
                    }
                    ++spread;
                }
            }
        }
        animation();
    }

    public void animation() {
        if (!isBoom)
            img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, time, 9).getFxImage();
        else {
            img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, time, 9).getFxImage();
            for (Flame f : flames) {
                f.animation(time);
            }
            boolean _IsBlockLeft = false;
            boolean _IsBlockRight = false;
            boolean _IsBlockUp  = false;
            boolean _IsBlockDown = false;

            for (int i = 0; i < area && !_IsBlockUp; i++) {
                if (BombermanGame.map.getMAP_ENTITIES()[this.getY() / Sprite.SCALED_SIZE - 1 - i][this.getX() / Sprite.SCALED_SIZE] == '#') {
                    break;
                }
                for (Brick b : BombermanGame.Bricks) {
                    if (b.getX() / Sprite.SCALED_SIZE == this.getX() / Sprite.SCALED_SIZE
                            && b.getY() / Sprite.SCALED_SIZE == this.getY() / Sprite.SCALED_SIZE - 1 - i) {
                        b.animation(time);
                        _IsBlockUp = true;
                    }
                }
            }
            for (int i = 0; i < area && !_IsBlockDown; i++) {
                if (BombermanGame.map.getMAP_ENTITIES()[this.getY() / Sprite.SCALED_SIZE + 1 + i][this.getX() / Sprite.SCALED_SIZE] == '#') {
                    break;
                }
                for (Brick b : BombermanGame.Bricks) {
                    if (b.getX() / Sprite.SCALED_SIZE == this.getX() / Sprite.SCALED_SIZE
                            && b.getY() / Sprite.SCALED_SIZE == this.getY() / Sprite.SCALED_SIZE + 1 + i) {
                        b.animation(time);
                        _IsBlockDown = true;
                    }
                }
            }
            for (int i = 0; i < area && !_IsBlockLeft; i++) {
                if (BombermanGame.map.getMAP_ENTITIES()[this.getY() / Sprite.SCALED_SIZE][this.getX() / Sprite.SCALED_SIZE - 1 - i] == '#') {
                    break;
                }
                for (Brick b : BombermanGame.Bricks) {
                    if (b.getX() / Sprite.SCALED_SIZE == this.getX() / Sprite.SCALED_SIZE - 1 - i
                            && b.getY() / Sprite.SCALED_SIZE == this.getY() / Sprite.SCALED_SIZE) {
                        b.animation(time);
                        _IsBlockLeft = true;
                    }
                }
            }
            for (int i = 0; i < area && !_IsBlockRight; i++) {
                if (BombermanGame.map.getMAP_ENTITIES()[this.getY() / Sprite.SCALED_SIZE][this.getX() / Sprite.SCALED_SIZE + 1 + i] == '#') {
                    break;
                }
                for (Brick b : BombermanGame.Bricks) {
                    if (b.getX() / Sprite.SCALED_SIZE == this.getX() / Sprite.SCALED_SIZE + 1 + i
                            && b.getY() / Sprite.SCALED_SIZE == this.getY() / Sprite.SCALED_SIZE) {
                        b.animation(time);
                        _IsBlockRight = true;
                    }
                }
            }
        }
        time++;
    }

    public void exploding() {
        setBoom(true);
        BombermanGame.map.setMAP_ENTITY(this.y / Sprite.SCALED_SIZE, this.x / Sprite.SCALED_SIZE, ' ');
    }
}
