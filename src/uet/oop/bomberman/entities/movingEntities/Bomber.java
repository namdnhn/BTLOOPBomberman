package uet.oop.bomberman.entities.movingEntities;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.GameMap;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.movingEntities.movingEntity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.GameMap;

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


    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        wallPass = false;
        SPEED = 3;
        valX = 0;
        valY = 0;
        w = Sprite.SCALED_SIZE * 5 / 6;
        h = Sprite.SCALED_SIZE * 5 / 6;
        time = 0;
        bombLimit = 1;
        heart = 1;
    }

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
            Bomb bomb = new Bomb(newX, newY, Sprite.bomb.getFxImage());
            bombs.add(bomb);
            BombermanGame.map.setMAP_ENTITY(newY, newX, 'b');
            for (int i = 0; i < BombermanGame.HEIGHT; i++) {
                for (int j = 0; j < BombermanGame.WIDTH; j++) {
                    System.out.print(BombermanGame.map.getMAP_ENTITIES()[i][j]);
                }
                System.out.print("\n");
            }
        }
    }

    @Override
    public void move() {
        valX = 0;
        valY = 0;
        if (goUp) {
            valY -= SPEED;
            animation();
        }

        else if (goDown) {
            valY += SPEED;
            animation();
        }

        else if (goLeft) {
            valX -= SPEED;
            animation();
        }

        else if (goRight) {
            valX += SPEED;
            animation();
        }

        BombermanGame.map.mapCollision(this);
        x += valX;
        y += valY;
    }

    @Override
    public void update() {
        move();
        bombs.removeIf(Bomb::isRemoved);
    }

    /** Moving Key. */

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
