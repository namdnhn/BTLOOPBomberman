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

    private boolean isBlockUp, isBlockDown, isBlockRight, isBlockLeft;

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        isBoom = false;
        isRemoved = false;
        isBlockUp = false;
        isBlockDown = false;
        isBlockLeft = false;
        isBlockRight = false;
        for (int i = 0; i < area; i++) {
            if (i < area - 1) {
                // up
                if (BombermanGame.map.getMAP_ENTITIES()[yUnit - i - 1][xUnit] == '*'
                    || BombermanGame.map.getMAP_ENTITIES()[yUnit - i - 1][xUnit] == '#') {
                    isBlockUp = true;
                }
                if (!isBlockUp)
                    flames.add(new Flame(xUnit, yUnit - i - 1, Sprite.explosion_vertical.getFxImage(), Flame.FLAME_TYPES.TOP));

                // down
                if (BombermanGame.map.getMAP_ENTITIES()[yUnit + i + 1][xUnit] == '*'
                    || BombermanGame.map.getMAP_ENTITIES()[yUnit + i + 1][xUnit] == '#') {
                    isBlockDown = true;
                }
                if (!isBlockDown)
                    flames.add(new Flame(xUnit, yUnit + i + 1, Sprite.explosion_vertical.getFxImage(), Flame.FLAME_TYPES.DOWN));

                // left
                if (BombermanGame.map.getMAP_ENTITIES()[yUnit][xUnit - i - 1] == '*'
                    || BombermanGame.map.getMAP_ENTITIES()[yUnit][xUnit - i - 1] == '#') {
                    isBlockLeft = true;
                }
                if (!isBlockLeft)
                    flames.add(new Flame(xUnit - i - 1, yUnit, Sprite.explosion_horizontal.getFxImage(), Flame.FLAME_TYPES.LEFT));

                // right
                if (BombermanGame.map.getMAP_ENTITIES()[yUnit][xUnit + i + 1] == '*'
                    || BombermanGame.map.getMAP_ENTITIES()[yUnit][xUnit + i + 1] == '#') {
                    isBlockRight = true;
                }
                if (!isBlockRight)
                    flames.add(new Flame(xUnit + i + 1, yUnit, Sprite.explosion_horizontal.getFxImage(), Flame.FLAME_TYPES.RIGHT));
            } else {
                // up
                if (BombermanGame.map.getMAP_ENTITIES()[yUnit - i - 1][xUnit] == '*'
                        || BombermanGame.map.getMAP_ENTITIES()[yUnit - i - 1][xUnit] == '#') {
                    isBlockUp = true;
                }
                if (!isBlockUp)
                    flames.add(new Flame(xUnit, yUnit - i - 1, Sprite.explosion_vertical_top_last.getFxImage(), Flame.FLAME_TYPES.TOPLAST));

                // down
                if (BombermanGame.map.getMAP_ENTITIES()[yUnit + i + 1][xUnit] == '*'
                        || BombermanGame.map.getMAP_ENTITIES()[yUnit + i + 1][xUnit] == '#') {
                    isBlockDown = true;
                }
                if (!isBlockDown)
                    flames.add(new Flame(xUnit, yUnit + i + 1, Sprite.explosion_vertical_down_last.getFxImage(), Flame.FLAME_TYPES.DOWNLAST));

                // left
                if (BombermanGame.map.getMAP_ENTITIES()[yUnit][xUnit - i - 1] == '*'
                        || BombermanGame.map.getMAP_ENTITIES()[yUnit][xUnit - i - 1] == '#') {
                    isBlockLeft = true;
                }
                if (!isBlockLeft)
                    flames.add(new Flame(xUnit - i - 1, yUnit, Sprite.explosion_horizontal_left_last.getFxImage(), Flame.FLAME_TYPES.LEFTLAST));

                // right
                if (BombermanGame.map.getMAP_ENTITIES()[yUnit][xUnit + i + 1] == '*'
                        || BombermanGame.map.getMAP_ENTITIES()[yUnit][xUnit + i + 1] == '#') {
                    isBlockRight = true;
                }
                if (!isBlockRight)
                    flames.add(new Flame(xUnit + i + 1, yUnit, Sprite.explosion_horizontal_right_last.getFxImage(), Flame.FLAME_TYPES.RIGHTLAST));
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
                boolean _isBlockLeft = false;
                boolean _isBlockRight = false;
                boolean _isBlockUp  = false;
                boolean _isBlockDown = false;
                // remove breaks
                for (int i = 0; i < area; i++) {
                    for (Brick b : BombermanGame.Bricks) {
                        // left
                        if (BombermanGame.map.getMAP_ENTITIES()[this.y / Sprite.SCALED_SIZE][this.x / Sprite.SCALED_SIZE - i - 1] == '#') {
                            _isBlockLeft = true;
                        }
                        if (b.getX() / Sprite.SCALED_SIZE == this.getX() / Sprite.SCALED_SIZE - 1 - i
                                && b.getY() / Sprite.SCALED_SIZE == this.getY() / Sprite.SCALED_SIZE && !_isBlockLeft) {
                            b.setRemoved(true);
                            BombermanGame.map.setMAP_ENTITY(b.getY() / Sprite.SCALED_SIZE, b.getX() / Sprite.SCALED_SIZE, ' ');
                            _isBlockLeft = true;
                        }
                        // right
                        if (BombermanGame.map.getMAP_ENTITIES()[this.y / Sprite.SCALED_SIZE][this.x / Sprite.SCALED_SIZE + i + 1] == '#') {
                            _isBlockRight = true;
                        }
                        if (b.getX() / Sprite.SCALED_SIZE == this.getX() / Sprite.SCALED_SIZE + 1 + i
                                && b.getY() / Sprite.SCALED_SIZE == this.getY() / Sprite.SCALED_SIZE && !_isBlockRight) {
                            b.setRemoved(true);
                            BombermanGame.map.setMAP_ENTITY(b.getY() / Sprite.SCALED_SIZE, b.getX() / Sprite.SCALED_SIZE, ' ');
                            _isBlockRight = true;
                        }
                        // up
                        if (BombermanGame.map.getMAP_ENTITIES()[this.y / Sprite.SCALED_SIZE - i - 1][this.x / Sprite.SCALED_SIZE] == '#') {
                            _isBlockUp = true;
                        }
                        if (b.getX() / Sprite.SCALED_SIZE == this.getX() / Sprite.SCALED_SIZE
                                && b.getY() / Sprite.SCALED_SIZE == this.getY() / Sprite.SCALED_SIZE - 1 - i && !_isBlockUp) {
                            b.setRemoved(true);
                            BombermanGame.map.setMAP_ENTITY(b.getY() / Sprite.SCALED_SIZE, b.getX() / Sprite.SCALED_SIZE, ' ');
                            _isBlockUp = true;
                        }
                        // down
                        if (BombermanGame.map.getMAP_ENTITIES()[this.y / Sprite.SCALED_SIZE + i + 1][this.x / Sprite.SCALED_SIZE] == '#') {
                            _isBlockDown = true;
                        }
                        if (b.getX() / Sprite.SCALED_SIZE == this.getX() / Sprite.SCALED_SIZE
                                && b.getY() / Sprite.SCALED_SIZE == this.getY() / Sprite.SCALED_SIZE + 1 + i && !_isBlockDown) {
                            b.setRemoved(true);
                            BombermanGame.map.setMAP_ENTITY(b.getY() / Sprite.SCALED_SIZE, b.getX() / Sprite.SCALED_SIZE, ' ');
                            _isBlockDown = true;
                        }
                    }
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
                for (Brick b : BombermanGame.Bricks) {
                    if (b.getX() / Sprite.SCALED_SIZE == this.getX() / Sprite.SCALED_SIZE
                            && b.getY() / Sprite.SCALED_SIZE == this.getY() / Sprite.SCALED_SIZE - 1 - i) {
                        b.animation(time);
                        _IsBlockUp = true;
                    }
                }
            }
            for (int i = 0; i < area && !_IsBlockDown; i++) {
                for (Brick b : BombermanGame.Bricks) {
                    if (b.getX() / Sprite.SCALED_SIZE == this.getX() / Sprite.SCALED_SIZE
                            && b.getY() / Sprite.SCALED_SIZE == this.getY() / Sprite.SCALED_SIZE + 1 + i) {
                        b.animation(time);
                        _IsBlockDown = true;
                    }
                }
            }
            for (int i = 0; i < area && !_IsBlockLeft; i++) {
                for (Brick b : BombermanGame.Bricks) {
                    if (b.getX() / Sprite.SCALED_SIZE == this.getX() / Sprite.SCALED_SIZE - 1 - i
                            && b.getY() / Sprite.SCALED_SIZE == this.getY() / Sprite.SCALED_SIZE) {
                        b.animation(time);
                        _IsBlockLeft = true;
                    }
                }
            }
            for (int i = 0; i < area && !_IsBlockRight; i++) {
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
