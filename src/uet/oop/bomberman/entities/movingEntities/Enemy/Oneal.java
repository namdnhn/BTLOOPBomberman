package uet.oop.bomberman.entities.movingEntities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {

    protected int[][] distance = new int[BombermanGame.HEIGHT][BombermanGame.WIDTH];

    public Oneal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        time = 0;
        SPEED = 1;
        w = 28;
        h = 28;
        inBomb = false;
    }

    public void CreateDistance() {
        for (int i = 0; i < BombermanGame.HEIGHT; i++) {
            for (int j = 0; j < BombermanGame.WIDTH; j++) {
                if (i == BombermanGame.bomberman.getCurrY1() && j == BombermanGame.bomberman.getCurrX1()) {
                    distance[i][j] = 0;
                } else {
                    distance[i][j] = Integer.MAX_VALUE;
                }
            }
        }
    }

    public void setDistance(int _x, int _y, int[][] distance, int value) {
        if (_x - 1 >= 0 && value < distance[_y][_x - 1]
                && BombermanGame.map.getMAP_ENTITIES()[_y][_x - 1] != '#'
                 && BombermanGame.map.getMAP_ENTITIES()[_y][_x - 1] != '*'
                && BombermanGame.map.getMAP_ENTITIES()[_y][_x - 1] != 'b') {
            distance[_y][_x - 1] = distance[_y][_x] + 1;
            setDistance(_x - 1, _y, distance, distance[_y][_x - 1] + 1);
        }
        if (_y - 1 >= 0 && value < distance[_y - 1][_x]
                && BombermanGame.map.getMAP_ENTITIES()[_y - 1][_x] != '#'
                && BombermanGame.map.getMAP_ENTITIES()[_y - 1][_x] != '*'
                && BombermanGame.map.getMAP_ENTITIES()[_y - 1][_x] != 'b') {
            distance[_y - 1][_x] = distance[_y][_x] + 1;
            setDistance(_x, _y - 1, distance, distance[_y - 1][_x] + 1);
        }
        if (_x + 1 < BombermanGame.WIDTH && value < distance[_y][_x + 1]
                && BombermanGame.map.getMAP_ENTITIES()[_y][_x + 1] != '#'
                && BombermanGame.map.getMAP_ENTITIES()[_y][_x + 1] != '*'
                && BombermanGame.map.getMAP_ENTITIES()[_y][_x + 1] != 'b') {
            distance[_y][_x + 1] = distance[_y][_x] + 1;
            setDistance(_x + 1, _y, distance, distance[_y][_x + 1] + 1);
        }
        if (_y + 1 < BombermanGame.HEIGHT && value < distance[_y + 1][_x]
                && BombermanGame.map.getMAP_ENTITIES()[_y + 1][_x] != '#'
                && BombermanGame.map.getMAP_ENTITIES()[_y + 1][_x] != '*'
                && BombermanGame.map.getMAP_ENTITIES()[_y + 1][_x] != 'b') {
            distance[_y + 1][_x] = distance[_y][_x] + 1;
            setDistance(_x, _y + 1, distance, distance[_y + 1][_x] + 1);
        }
    }

    @Override
    public void animation() {
        if (!isDead) {
            if (status == null) {
                img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_left1, time, 9).getFxImage();
                time++;
            }
            else {
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
    }

    public WALK_TYPE chooseDirection() {
        CreateDistance();
        setDistance(BombermanGame.bomberman.getCurrX1(), BombermanGame.bomberman.getCurrY1(), this.distance, 1);
        int Cx = (int) (this.getX() + this.getW() / 2) / Sprite.SCALED_SIZE;
        int Cy = (int) (this.getY() + this.getH() / 2) / Sprite.SCALED_SIZE;
//        System.out.println("" + Cy + " " + Cx + " " + distance[Cy][Cx] + "\n" + (Cy + 1) + " " + Cx + " " + distance[Cy + 1][Cx]
//                        + "\n" + (Cy - 1) + " " + Cx + " " + distance[Cy - 1][Cx]
//                        + "\n" + Cy + " " + (Cx - 1) + " " + distance[Cy][Cx - 1]
//                        + "\n" + Cy + " " + (Cx + 1) + " " +  distance[Cy][Cx + 1]);
        // now - down - up - left - right
        if(distance[Cy][Cx] == Integer.MAX_VALUE) {
            return null;
        } else {
            int a = distance[Cy][Cx] - 1;
            if(distance[Cy - 1][Cx] == a) {
                return WALK_TYPE.UP;
            } else if (distance[Cy + 1][Cx] == a) {
                return WALK_TYPE.DOWN;
            } else if (distance[Cy][Cx - 1] == a) {
                return WALK_TYPE.LEFT;
            } else if (distance[Cy][Cx + 1] == a) {
                return WALK_TYPE.RIGHT;
            } else return null;
        }
    }

    private boolean goUp, goDown, goLeft, goRight;
    @Override
    public void move() {
        WALK_TYPE _status = chooseDirection();
        if (_status == null) {
            goUp = false;
            goDown = false;
            goLeft = false;
            goRight = false;
            animation();
        } else {
            switch (_status) {
                case UP:
                    goUp = true;
                    goDown = false;
                    goLeft = false;
                    goRight = false;
                    status = WALK_TYPE.UP;
                    break;
                case DOWN:
                    goDown = true;
                    goUp = false;
                    goLeft = false;
                    goRight = false;
                    status = WALK_TYPE.DOWN;
                    break;
                case RIGHT:
                    goRight = true;
                    goDown = false;
                    goLeft = false;
                    goUp = false;
                    status = WALK_TYPE.RIGHT;
                    break;
                case LEFT:
                    goLeft = true;
                    goDown = false;
                    goUp = false;
                    goRight = false;
                    status = WALK_TYPE.LEFT;
                    break;
            }
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

        x += valX;
        y += valY;
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
        animation();
    }
}
