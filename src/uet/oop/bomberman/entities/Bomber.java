package uet.oop.bomberman.entities;

import javafx.event.Event;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Bomber extends Entity {
    private static double SPEED = 2;

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
        super( x, y, img);
    }

    @Override
    public void update() {
        if (goUp) {
            this.y -= SPEED;
        }

        if (goDown) {
            this.y += SPEED;
        }

        if (goLeft) {
            this.x -= SPEED;
        }

        if (goRight) {
            this.x += SPEED;
        }
    }


}
