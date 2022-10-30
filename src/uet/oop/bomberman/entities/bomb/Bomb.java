package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Sound;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.movingEntities.Bomber;
import uet.oop.bomberman.graphics.Sprite;

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

    private int timeEnd = 30;

    private final int area;

    public List<Flame> getFlames() {
        return flames;
    }

    private final List<Flame> flames = new ArrayList<>();

    public KillingArea verticalKillingArea = new KillingArea(this);
    public KillingArea horizontalKillingArea = new KillingArea(this);


    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        isBoom = false;
        isRemoved = false;
        area = Bomber.get_area();
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
                verticalKillingArea.setY_up(verticalKillingArea.y_up - Sprite.SCALED_SIZE);
                break;
            } else {
                flames.add(new Flame(this.xCoordinate, this.yCoordinate + spread, Flame.FLAME_TYPES.VER));
                verticalKillingArea.setY_up(verticalKillingArea.y_up - Sprite.SCALED_SIZE);
                --spread;
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
                verticalKillingArea.setY_down(verticalKillingArea.y_down + Sprite.SCALED_SIZE);
                break;
            } else {
                flames.add(new Flame(this.xCoordinate, this.yCoordinate + spread, Flame.FLAME_TYPES.VER));
                verticalKillingArea.setY_down(verticalKillingArea.y_down + Sprite.SCALED_SIZE);
                ++spread;
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
                horizontalKillingArea.setX_right(horizontalKillingArea.x_right + Sprite.SCALED_SIZE);
                break;
            } else {
                flames.add(new Flame(this.xCoordinate + spread, this.yCoordinate, Flame.FLAME_TYPES.HORI));
                horizontalKillingArea.setX_right(horizontalKillingArea.x_right + Sprite.SCALED_SIZE);
                ++spread;
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
                horizontalKillingArea.setX_left(horizontalKillingArea.x_left - Sprite.SCALED_SIZE);
                break;
            } else {
                flames.add(new Flame(this.xCoordinate + spread, this.yCoordinate, Flame.FLAME_TYPES.HORI));
                horizontalKillingArea.setX_left(horizontalKillingArea.x_left - Sprite.SCALED_SIZE);
                --spread;
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
            if (timeEnd > 0)
                timeEnd--;
            else {
                setRemoved(true);
                int spread = -1;
                while (spread >= -area && this.yCoordinate + spread >= 0
                        && BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate + spread][this.xCoordinate] != '#') {
                    if (BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate + spread][this.xCoordinate] == '*'
                        || BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate + spread][this.xCoordinate] == 'i'
                            || BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate + spread][this.xCoordinate] == 'x') {
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
                    if (BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate][this.xCoordinate + spread] == '*'
                        || BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate][this.xCoordinate + spread] == 'i'
                            || BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate][this.xCoordinate + spread] == 'x') {
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
                    if (BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate][this.xCoordinate + spread] == '*'
                        || BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate][this.xCoordinate + spread] == 'i'
                            || BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate][this.xCoordinate + spread] == 'x') {
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
                    if (BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate + spread][this.xCoordinate] == '*'
                        || BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate + spread][this.xCoordinate] == 'i'
                            || BombermanGame.map.getMAP_ENTITIES()[this.yCoordinate + spread][this.xCoordinate] == 'x') {
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

    public boolean inKillingArea(KillingArea killingArea) {
        int left1, left2, right1, right2, up1, up2, down1, down2;
        left1 = this.getX();
        right1 = this.getX() + Sprite.SCALED_SIZE;
        up1 = this.getY();
        down1 = this.getY() + Sprite.SCALED_SIZE;
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

    public void exploding() {
        setBoom(true);
        Sound.play("res/sounds/bombExplode.wav");
        BombermanGame.map.setMAP_ENTITY(this.getyCoordinate(), this.getxCoordinate(), ' ');
        for (int i = 0; i < Bomber.bombs.size(); ++i) {
            if (!(Bomber.bombs.get(i) == this)) {
                if (Bomber.bombs.get(i).inKillingArea(this.horizontalKillingArea)
                        || Bomber.bombs.get(i).inKillingArea(this.verticalKillingArea)) {
                    BombermanGame.map.setMAP_ENTITY(Bomber.bombs.get(i).getyCoordinate(),
                            Bomber.bombs.get(i).getxCoordinate(), ' ');
                    Bomber.bombs.get(i).setBoom(true);
                }
            }
        }

    }
}
