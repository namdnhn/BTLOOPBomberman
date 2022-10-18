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
import uet.oop.bomberman.entities.movingEntities.movingEntity;
import uet.oop.bomberman.graphics.Sprite;

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
    public enum WALK_TYPE {
        RIGHT, LEFT, UP, DOWN
    }

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        if (goUp) {
            this.y = y - SPEED;
            animation();
        }

        if (goDown) {
            this.y = y + SPEED;
            animation();
        }

        if (goLeft) {
            this.x = x - SPEED;
            animation();
        }

        if (goRight) {
            this.x = x + SPEED;
            animation();
        }
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
