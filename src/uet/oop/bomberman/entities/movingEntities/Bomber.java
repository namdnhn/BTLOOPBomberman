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
import uet.oop.bomberman.entities.movingEntities.movingEntity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.GameMap;

public class Bomber extends movingEntity {
    private static int SPEED = 2;

    private int time = 0;

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

    public WALK_TYPE status;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        wallPass = false;
        valX = 0;
        valY = 0;
        w = 28;
        h = 28;
    }

    public boolean canMove(int x, int y) {
        return BombermanGame.getStillEntity(x, y) == null;
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

        x += valX;
        y += valY;
    }

    @Override
    public void update() {
        move();
    }

    /** Moving Key. */

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

}
