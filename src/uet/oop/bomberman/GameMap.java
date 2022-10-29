package uet.oop.bomberman;

import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Items.BombItem;
import uet.oop.bomberman.entities.Items.FlameItem;
import uet.oop.bomberman.entities.Items.Item;
import uet.oop.bomberman.entities.Items.SpeedItem;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.movingEntities.Enemy.Balloom;
import uet.oop.bomberman.entities.movingEntities.Enemy.Enemy;
import uet.oop.bomberman.entities.movingEntities.Enemy.Oneal;
import uet.oop.bomberman.entities.movingEntities.movingEntity;
import uet.oop.bomberman.entities.movingEntities.Bomber;
import uet.oop.bomberman.graphics.Sprite;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameMap {
    public char[][] getMAP_ENTITIES() {
        return MAP_ENTITIES;
    }

    public void setMAP_ENTITY(int i, int j, char c) {
        MAP_ENTITIES[i][j] = c;
    }

    private final char[][] MAP_ENTITIES = new char[BombermanGame.HEIGHT][BombermanGame.WIDTH];

    public void readMap() {
        File file = new File("res/levels/Level2.txt");
        try {
            Scanner sc = new Scanner(file);
            for (int i = 0; i < BombermanGame.HEIGHT; i++) {
                String string = sc.nextLine();
                for (int j = 0; j < BombermanGame.WIDTH; j++) {
                    MAP_ENTITIES[i][j] = string.charAt(j);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        }
    }

    public void loadMap() {

        for (int i = 0; i < BombermanGame.HEIGHT; ++i) {
            for (int j = 0; j < BombermanGame.WIDTH; ++j) {
                Grass grass;
                grass = new Grass(j, i, Sprite.grass.getFxImage());
                BombermanGame.grassEntities.add(grass);
            }
        }

        for (int i = 0; i < BombermanGame.WIDTH; i++) {
            for (int j = 0; j < BombermanGame.HEIGHT; j++) {
                if (MAP_ENTITIES[j][i] == '#') {
                    Wall object = new Wall(i, j, Sprite.wall.getFxImage());
                    BombermanGame.Wall.add(object);
                } else if (MAP_ENTITIES[j][i] == '*') {
                    Brick object = new Brick(i, j, Sprite.brick.getFxImage());
                    BombermanGame.Bricks.add(object);
                } else if (MAP_ENTITIES[j][i] == '1') {
                    Enemy enemy = new Balloom(i, j, Sprite.balloom_right1.getFxImage());
                    BombermanGame.enemies.add(enemy);
                } else if (MAP_ENTITIES[j][i] == '2') {
                    Enemy enemy = new Oneal(i, j, Sprite.oneal_right1.getFxImage());
                    BombermanGame.enemies.add(enemy);
                } else if (MAP_ENTITIES[j][i] == 'x') {
                    BombermanGame.portal = new Portal(i, j, Sprite.portal.getFxImage());
                    Brick object = new Brick(i, j, Sprite.brick.getFxImage());
                    BombermanGame.Bricks.add(object);
                } else if (MAP_ENTITIES[j][i] == 'i') {
                    double a = Math.random();
                    Item item;
                    if (a <= 0.5) {
                        item = new SpeedItem(i, j, Sprite.powerup_speed.getFxImage());
                    } else if (a <= 0.75) {
                        item = new FlameItem(i, j, Sprite.powerup_flames.getFxImage());
                    } else {
                        item = new BombItem(i, j, Sprite.powerup_bombs.getFxImage());
                    }
                    Brick object = new Brick(i, j, Sprite.brick.getFxImage());
                    BombermanGame.items.add(item);
                    BombermanGame.Bricks.add(object);
                }
            }
        }
    }

    public void mapCollision(movingEntity entity) {
        int x1;
        int x2;
        int y1;
        int y2;
        int s1 = Sprite.SCALED_SIZE * 5 / 6;
        int s2 = Sprite.SCALED_SIZE / 2;

        x1 = (int) (entity.getX() + entity.getValX()) / Sprite.SCALED_SIZE;
        x2 = (int) (entity.getX() + entity.getValX() + entity.getW() - 1) / Sprite.SCALED_SIZE;
        y1 = entity.getY() / Sprite.SCALED_SIZE;
        y2 = (entity.getY() + entity.getH() - 1) / Sprite.SCALED_SIZE;

        //chech ngang
        if (entity.getValX() > 0) {
            if (MAP_ENTITIES[y1][x2] == '*' || MAP_ENTITIES[y2][x2] == '*'
                    || MAP_ENTITIES[y1][x2] == '#' || MAP_ENTITIES[y2][x2] == '#'
                    || MAP_ENTITIES[y1][x2] == 'i' || MAP_ENTITIES[y2][x2] == 'i'
                    || MAP_ENTITIES[y1][x2] == 'x' || MAP_ENTITIES[y2][x2] == 'x'
                    || (!entity.isInBomb() && (MAP_ENTITIES[y1][x2] == 'b' || MAP_ENTITIES[y2][x2] == 'b'))) {
                if (!entity.isWallPass()) {
                    entity.setValX(0);
                }
            }

            if (entity instanceof Bomber) {
                if ((MAP_ENTITIES[y1][x2] == '*' && MAP_ENTITIES[y2][x2] != '*' && MAP_ENTITIES[y2][x2] != '#')
                        || (MAP_ENTITIES[y1][x2] == '#' && MAP_ENTITIES[y2][x2] != '#' && MAP_ENTITIES[y2][x2] != '*')
                        || (MAP_ENTITIES[y2][x2] != '#' && MAP_ENTITIES[y2][x2] != '*')) {
                    if (y1 * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE - entity.getY() < s2) {
                        entity.raiseVY();
                        entity.raiseVX();
                    }
                } else if ((MAP_ENTITIES[y1][x2] != '*' && MAP_ENTITIES[y1][x2] != '#' && MAP_ENTITIES[y2][x2] == '*')
                        || (MAP_ENTITIES[y1][x2] != '#' && MAP_ENTITIES[y1][x2] != '*' && MAP_ENTITIES[y2][x2] == '#')
                        || (MAP_ENTITIES[y1][x2] != '#' && MAP_ENTITIES[y1][x2] != '*')) {
                    if (y2 * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE - (entity.getY() + entity.getH()) > s1) {
                        entity.lowVY();
                        entity.raiseVX();
                    }
                }
            }
        } else if (entity.getValX() < 0) {
            if (MAP_ENTITIES[y1][x1] == '*' || MAP_ENTITIES[y2][x1] == '*'
                    || MAP_ENTITIES[y1][x1] == '#' || MAP_ENTITIES[y2][x1] == '#'
                    || MAP_ENTITIES[y1][x1] == 'i' || MAP_ENTITIES[y2][x1] == 'i'
                    || MAP_ENTITIES[y1][x1] == 'x' || MAP_ENTITIES[y2][x1] == 'x'
                    || (!entity.isInBomb() && (MAP_ENTITIES[y1][x1] == 'b' || MAP_ENTITIES[y2][x1] == 'b'))) {
                if (!entity.isWallPass()) {
                    entity.setValX(0);
                }
            }
            if (entity instanceof Bomber) {
                if ((MAP_ENTITIES[y1][x1] == '*' && MAP_ENTITIES[y2][x1] != '*' && MAP_ENTITIES[y2][x1] != '#')
                        || (MAP_ENTITIES[y1][x1] == '#' && MAP_ENTITIES[y2][x1] != '#' && MAP_ENTITIES[y2][x1] != '*')
                        || (MAP_ENTITIES[y2][x1] != '#' && MAP_ENTITIES[y2][x1] != '*')) {
                    if (y1 * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE - entity.getY() < s2) {
                        entity.raiseVY();
                        entity.lowVX();
                    }
                } else if ((MAP_ENTITIES[y1][x1] != '*' && MAP_ENTITIES[y1][x1] != '#' && MAP_ENTITIES[y2][x1] == '*')
                        || (MAP_ENTITIES[y1][x1] != '#' && MAP_ENTITIES[y1][x1] != '*' && MAP_ENTITIES[y2][x1] == '#')
                        || (MAP_ENTITIES[y1][x1] != '#' && MAP_ENTITIES[y1][x1] != '*')) {
                    if (y2 * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE - (entity.getY() + entity.getH()) > s1) {
                        entity.lowVY();
                        entity.lowVX();
                    }
                }
            }

        }


        //Check doc
        x1 = entity.getX() / Sprite.SCALED_SIZE;
        x2 = (entity.getX() + entity.getW()) / Sprite.SCALED_SIZE;

        y1 = (int) (entity.getY() + entity.getValY()) / Sprite.SCALED_SIZE;
        y2 = (int) (entity.getY() + entity.getValY() + entity.getH() - 1) / Sprite.SCALED_SIZE;

        if (x1 >= 0 && y1 >= 0) {
            if (entity.getValY() > 0) {
                if (MAP_ENTITIES[y2][x1] == '*' || MAP_ENTITIES[y2][x2] == '*'
                    || MAP_ENTITIES[y2][x1] == '#' || MAP_ENTITIES[y2][x2] == '#'
                    || MAP_ENTITIES[y2][x1] == 'i' || MAP_ENTITIES[y2][x2] == 'i'
                    || MAP_ENTITIES[y2][x1] == 'x' || MAP_ENTITIES[y2][x2] == 'x'
                    || (!entity.isInBomb() && (MAP_ENTITIES[y2][x1] == 'b' || MAP_ENTITIES[y2][x2] == 'b'))) {
                    if (!entity.isWallPass()) {
                        entity.setValY(0);
                    }
                }
                if (MAP_ENTITIES[y2][x1] == '#' || MAP_ENTITIES[y2][x2] == '#'
                        || MAP_ENTITIES[y2][x1] == '*' || MAP_ENTITIES[y2][x2] == '*'
                        || MAP_ENTITIES[y2][x1] == 'i' || MAP_ENTITIES[y2][x2] == 'i'
                        || MAP_ENTITIES[y2][x1] == 'x' || MAP_ENTITIES[y2][x2] == 'x'
                        || (!entity.isInBomb() && (MAP_ENTITIES[y2][x1] == 'b' || MAP_ENTITIES[y2][x2] == 'b'))) {
                    if (entity instanceof Bomber) {

                        if ((MAP_ENTITIES[y2][x1] == '*' && MAP_ENTITIES[y2][x2] != '*' && MAP_ENTITIES[y2][x2] != '#')
                                || (MAP_ENTITIES[y2][x1] == '#' && MAP_ENTITIES[y2][x2] != '#' && MAP_ENTITIES[y2][x2] != '*')
                                || (MAP_ENTITIES[y2][x2] != '#' && MAP_ENTITIES[y2][x2] != '*')) {
                            if (x1 * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE - entity.getX() < s2) {
                                entity.raiseVX();
                                entity.raiseVX();
                            }
                        } else if ((MAP_ENTITIES[y2][x1] != '*' && MAP_ENTITIES[y2][x1] != '#' && MAP_ENTITIES[y2][x2] == '*')
                                || (MAP_ENTITIES[y2][x1] != '#' && MAP_ENTITIES[y2][x1] != '*' && MAP_ENTITIES[y2][x2] == '#')
                                || (MAP_ENTITIES[y2][x1] != '#' && MAP_ENTITIES[y2][x1] != '*')) {
                            if (x2 * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE - (entity.getX() + entity.getW()) > s1) {
                                entity.lowVX();
                                entity.raiseVX();
                            }
                        }
                    }
                }
            } else if (entity.getValY() < 0) {
                if (MAP_ENTITIES[y1][x1] == '*' || MAP_ENTITIES[y1][x2] == '*'
                    || MAP_ENTITIES[y1][x1] == '#' || MAP_ENTITIES[y1][x2] == '#'
                    || MAP_ENTITIES[y1][x1] == 'i' || MAP_ENTITIES[y1][x2] == 'i'
                    || MAP_ENTITIES[y1][x1] == 'x' || MAP_ENTITIES[y1][x2] == 'x'
                    || (!entity.isInBomb() && (MAP_ENTITIES[y1][x1] == 'b' || MAP_ENTITIES[y1][x2] == 'b'))) {
                    if (!entity.isWallPass()) {
                        entity.setValY(0);
                    }
                }
                if (MAP_ENTITIES[y1][x1] == '#' || MAP_ENTITIES[y1][x2] == '#'
                        || MAP_ENTITIES[y1][x1] == '*' || MAP_ENTITIES[y1][x2] == '*'
                        || MAP_ENTITIES[y1][x1] == 'i' || MAP_ENTITIES[y1][x2] == 'i'
                        || MAP_ENTITIES[y1][x1] == 'x' || MAP_ENTITIES[y1][x2] == 'x'
                        || (!entity.isInBomb() && (MAP_ENTITIES[y1][x1] == 'b' || MAP_ENTITIES[y1][x2] == 'b'))) {
                    if (entity instanceof Bomber) {
                        if ((MAP_ENTITIES[y1][x1] != '*' && MAP_ENTITIES[y1][x1] != '#' && MAP_ENTITIES[y1][x2] == '*')
                                || (MAP_ENTITIES[y1][x1] != '#' && MAP_ENTITIES[y1][x1] != '*' && MAP_ENTITIES[y1][x2] == '#')
                                || (MAP_ENTITIES[y1][x1] != '#' && MAP_ENTITIES[y1][x1] != '*')) {
                            if (x2 * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE - (entity.getX() + entity.getW()) > s1) {
                                entity.lowVX();
                                entity.lowVY();
                            }
                        } else if ((MAP_ENTITIES[y1][x1] == '*' && MAP_ENTITIES[y1][x2] != '*' && MAP_ENTITIES[y1][x2] != '#')
                                || (MAP_ENTITIES[y1][x1] == '#' && MAP_ENTITIES[y1][x2] != '#' && MAP_ENTITIES[y1][x2] != '*')
                                || (MAP_ENTITIES[y1][x2] != '#' && MAP_ENTITIES[y1][x2] != '*')) {
                            if (x1 * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE - entity.getX() < s2) {
                                entity.raiseVX();
                                entity.lowVY();
                            }
                        }
                    }
                }
            }
        }
    }
}
