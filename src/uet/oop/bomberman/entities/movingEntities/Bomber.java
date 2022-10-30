package uet.oop.bomberman.entities.movingEntities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Sound;
import uet.oop.bomberman.entities.Items.Item;
import uet.oop.bomberman.entities.Portal;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.movingEntities.Enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;


public class Bomber extends movingEntity {
    public static List<Bomb> bombs = new ArrayList<>();

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    private int heart;

    public void setBombLimit(int bombLimit) {
        this.bombLimit = bombLimit;
    }

    public int getBombLimit() {
        return bombLimit;
    }

    private int bombLimit;

    public void setGoUp(boolean goUp) {
        this.goUp = goUp;
    }

    public void setGoDown(boolean goDown) {
        this.goDown = goDown;
    }

    public void setGoLeft(boolean goLeft) {
        this.goLeft = goLeft;
    }

    public void setGoRight(boolean goRight) {
        this.goRight = goRight;
    }

    private boolean goUp, goDown, goLeft, goRight;

    public static int get_area() {
        return _area;
    }

    public static void set_area(int _area) {
        Bomber._area = _area;
    }

    private static int _area;


    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        wallPass = false;
        SPEED = 3;
        valX = 0;
        valY = 0;
        w = Sprite.SCALED_SIZE * 5 / 6;
        h = Sprite.SCALED_SIZE * 5 / 6;
        time = 0;
        bombLimit = 3;
        heart = 1;
        _area = 1;
    }

    // dat bom
    public void placeBomb(int x, int y) {
        if (bombs.size() < bombLimit) {
            int newX, newY;
            if (this.x % Sprite.SCALED_SIZE < Sprite.SCALED_SIZE / 2) {
                newX = this.x / Sprite.SCALED_SIZE;
            } else {
                newX = this.x / Sprite.SCALED_SIZE + 1;
            }

            if (this.y % Sprite.SCALED_SIZE < Sprite.SCALED_SIZE / 2) {
                newY = this.y / Sprite.SCALED_SIZE;
            } else {
                newY = this.y / Sprite.SCALED_SIZE + 1;
            }
            if (BombermanGame.map.getMAP_ENTITIES()[newY][newX] != 'b') {
                Bomb bomb = new Bomb(newX, newY, Sprite.bomb.getFxImage());
                bombs.add(bomb);
                Sound.play("res/sounds/placeBomb.wav");
                BombermanGame.map.setMAP_ENTITY(newY, newX, 'b');
                if (this.checkInBomb(bomb)) {
                    setInBomb(true);
                }
                for (int i = 0; i < BombermanGame.HEIGHT; i++) {
                    for (int j = 0; j < BombermanGame.WIDTH; j++) {
                        System.out.print(BombermanGame.map.getMAP_ENTITIES()[i][j]);
                    }
                    System.out.print("\n");
                }
            }
        }
    }

    // an item
    public void gotItem(Item item) {
        int left1, left2, right1, right2, up1, up2, down1, down2;
        left1 = this.getX();
        right1 = this.getX() + this.getW();
        up1 = this.getY();
        down1 = this.getY() + this.getH();
        left2 = item.getX() + 2;
        right2 = item.getX() + Sprite.SCALED_SIZE - 2;
        up2 = item.getY() + 2;
        down2 = item.getY() + Sprite.SCALED_SIZE - 2;

        boolean b = (up1 <= up2 && down1 >= up2) || (up1 >= up2 && down2 >= up1);
        if (left1 <= left2 && right1 >= left2) {
            if (b) {
                item.update();
                item.setHasGot(true);
                Sound.play("res/sounds/eatItem.wav");
            }
        }
        else if (left2 <= left1 && right2 >= left1) {
            if (b) {
                item.update();
                item.setHasGot(true);
                Sound.play("res/sounds/eatItem.wav");
            }
        }
    }

    // vao portal
    public boolean inPortal() {
        Portal portal = BombermanGame.portal;
        int left1, left2, right1, right2, up1, up2, down1, down2;
        left1 = this.getX();
        right1 = this.getX() + Sprite.SCALED_SIZE;
        up1 = this.getY();
        down1 = this.getY() + Sprite.SCALED_SIZE;
        left2 = portal.getX();
        right2 = portal.getX() + Sprite.SCALED_SIZE;;
        up2 = portal.getY();
        down2 = portal.getY() + Sprite.SCALED_SIZE;;

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

    // control di chuyen
    @Override
    public void move() {
        valX = 0;
        valY = 0;
        if (goUp) {
            valY -= SPEED;
            animation();
        } else if (goDown) {
            valY += SPEED;
            animation();
        } else if (goLeft) {
            valX -= SPEED;
            animation();
        } else if (goRight) {
            valX += SPEED;
            animation();
        }

        BombermanGame.map.mapCollision(this);
        x += valX;
        y += valY;
    }

    // kiem soat khi bomber bi giet
    public void kill() {
        for (Enemy e : BombermanGame.enemies) {
            if (checkCollision(e)) {
                setHeart(getHeart() - 1);
                Sound.play("res/sounds/die.wav");
                break;
            }
        }
        for (Bomb b : bombs) {
            if (b.isBoom() && checkKilled(b.verticalKillingArea)) {
                setHeart(getHeart() - 1);
                Sound.play("res/sounds/die.wav");
                break;
            }
            if (b.isBoom() && checkKilled(b.horizontalKillingArea)) {
                setHeart(getHeart() - 1);
                Sound.play("res/sounds/die.wav");
                break;
            }
        }
    }

    // update lien tuc bomber
    @Override
    public void update() {
        if(getHeart() > 0) {
            for (Bomb b : Bomber.bombs)
                if (!this.checkInBomb(b))
                    setInBomb(false);
            for (Item i : BombermanGame.items)
                gotItem(i);
            move();
            kill();
        }
        else {
            img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, time, 9).getFxImage();
            time++;
        }
        bombs.removeIf(Bomb::isRemoved);
    }

    // animation cua bomber
    @Override
    public void animation() {
        switch (status) {
            case UP:
                img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, time, 9).getFxImage();
                time++;
                break;
            case DOWN:
                img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, time, 9).getFxImage();
                time++;
                break;
            case LEFT:
                img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, time, 9).getFxImage();
                time++;
                break;
            case RIGHT:
                img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, time, 9).getFxImage();
                time++;
                break;
        }
    }


    // render bomb len man hinh
    public void renderBomb(GraphicsContext gc) {
        for (Bomb b : bombs) {
            b.render(gc);
            if (b.isBoom() && !b.isRemoved()) {
                for (Flame f : b.getFlames()) {
                    f.render(gc);
                }
            }
        }
    }
}
