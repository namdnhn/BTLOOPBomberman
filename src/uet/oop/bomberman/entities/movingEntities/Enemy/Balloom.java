package uet.oop.bomberman.entities.movingEntities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.movingEntities.Bomber;
import uet.oop.bomberman.entities.movingEntities.movingEntity;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends Enemy {

    public Balloom(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        time = 0;
        SPEED = 1;
        w = 31;
        h = 31;
        inBomb = false;
    }

    // animation cho ballom
    @Override
    public void animation() {
        if (!isDead && status != null) {
            switch (status) {
                case UP:
                case RIGHT:
                    img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, time, 9).getFxImage();
                    time++;
                    break;
                case DOWN:
                case LEFT:
                    img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, time, 9).getFxImage();
                    time++;
                    break;
            }
        }
    }

    // se di theo huong nao
    private boolean goUp, goDown, goLeft, goRight;

    // co di chuyen duoc khong
    private boolean canMove = true;
    @Override
    public void move() {
        // Chon huong di bang cach random huong. Khi nao khong di duoc thi moi phai doi huong
        double rand;
        if (!canMove) {
            rand = Math.random();
            if (rand < 0.25) {
                goUp = true;
                goDown = false;
                goLeft = false;
                goRight = false;
                status = WALK_TYPE.UP;
            } else if (rand < 0.5) {
                goDown = true;
                goUp = false;
                goLeft = false;
                goRight = false;
                status = WALK_TYPE.DOWN;
            } else if (rand < 0.75) {
                goRight = true;
                goDown = false;
                goLeft = false;
                goUp = false;
                status = WALK_TYPE.RIGHT;
            } else {
                goLeft = true;
                goDown = false;
                goUp = false;
                goRight = false;
                status = WALK_TYPE.LEFT;
            }
        }

        // neu di duoc thi canMove = true
        if (!canMove) {
            canMove = true;
        }

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
        // neu nhu van toc bang 0 tuc la enemy khong di duoc -> chuyen canMove ve false de ham random chay duoc
        if (valX == 0 && valY == 0) {
            canMove = false;
        }

        x += valX;
        y += valY;
    }

    // update lien tuc tinh trang cua entity
    @Override
    public void update() {
        if(!isDead) {
            move();
            kill();
        }
        else if (reversedTime > 0) {
            img = Sprite.balloom_dead.getFxImage();
            reversedTime--;
        } else
            removed = true;
        animation();
    }
}
