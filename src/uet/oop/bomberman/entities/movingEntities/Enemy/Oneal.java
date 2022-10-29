package uet.oop.bomberman.entities.movingEntities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {

    public Oneal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        time = 0;
        SPEED = 1;
        w = 31;
        h = 31;
        inBomb = false;
    }

    @Override
    public void animation() {
        if (!isDead) {
            switch (status) {
                case UP:
                case RIGHT:
                    img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, time, 9).getFxImage();
                    time++;
                    break;
                case DOWN:
                case LEFT:
                    img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, time, 9).getFxImage();
                    time++;
                    break;
            }
        }
    }

    @Override
    public void move() {

    }

    @Override
    public void update() {
        if(!isDead) {
            move();
            kill();
        }
        else if (reversedTime > 0) {
            img = Sprite.oneal_dead.getFxImage();
            reversedTime--;
        } else
            removed = true;
//        animation();
    }
}
