package uet.oop.bomberman;

import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.movingEntities.Enemy.Balloom;
import uet.oop.bomberman.entities.movingEntities.Enemy.Enemy;
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

    private char[][] MAP_ENTITIES = new char[BombermanGame.HEIGHT][BombermanGame.WIDTH];

    private int TILE_SIZE = Sprite.SCALED_SIZE;

    public void readMap() {
        File file = new File("res/levels/Level1.txt");
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
                Entity object;
                if (MAP_ENTITIES[j][i] == '#') {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                    BombermanGame.stillObjects.add(object);
                } else if (MAP_ENTITIES[j][i] == '*') {
                    object = new Brick(i, j, Sprite.brick.getFxImage());
                    BombermanGame.stillObjects.add(object);
                } else if (MAP_ENTITIES[j][i] == '1') {
                    Enemy enemy = new Balloom(i, j, Sprite.balloom_right1.getFxImage());
                    BombermanGame.enemies.add(enemy);
                }
            }
        }
    }

    public void mapCollision(movingEntity entity) {
        int x1;
        int x2;
        int y1;
        int y2;
        int Cx1 = entity.getCurrX1();
        int Cx2 = entity.getCurrX2();
        int Cy1 = entity.getCurrY1();
        int Cy2 = entity.getCurrY2();
        int s1 = Sprite.SCALED_SIZE * 5 / 6;
        int s2 = Sprite.SCALED_SIZE / 2;
        if (entity instanceof Bomber) {
            Cx1 = (int) (entity.getX() + 1) / TILE_SIZE;
            Cx2 = (int) (entity.getX() + entity.getW() - 1) / TILE_SIZE;
            Cy1 = (entity.getY() + 1) / TILE_SIZE;
            Cy2 = (entity.getY() + entity.getH() - 1) / TILE_SIZE;
        }

        x1 = (int) (entity.getX() + entity.getValX()) / TILE_SIZE;
        x2 = (int) (entity.getX() + entity.getValX() + entity.getW() - 1) / TILE_SIZE;
        y1 = entity.getY() / TILE_SIZE;
        y2 = (entity.getY() + entity.getH() - 1) / TILE_SIZE;

        //chech ngang
        if (entity.getValX() > 0) {
            if (MAP_ENTITIES[y1][x2] == '*' || MAP_ENTITIES[y2][x2] == '*'
                    || MAP_ENTITIES[y1][x2] == '#' || MAP_ENTITIES[y2][x2] == '#') {
                if (!entity.isWallPass()) {
                    entity.setValX(0);
                }
            }

            if (entity instanceof Bomber) {
                if ((MAP_ENTITIES[y1][x2] == '*' && MAP_ENTITIES[y2][x2] != '*' && MAP_ENTITIES[y2][x2] != '#')
                        || (MAP_ENTITIES[y1][x2] == '#' && MAP_ENTITIES[y2][x2] != '#' && MAP_ENTITIES[y2][x2] != '*')
                        || (MAP_ENTITIES[y2][x2] != '#' && MAP_ENTITIES[y2][x2] != '*')) {
                    if (y1 * TILE_SIZE + TILE_SIZE - entity.getY() < s2) {
                        entity.raiseVY();
                        entity.raiseVX();
                    }
                } else if ((MAP_ENTITIES[y1][x2] != '*' && MAP_ENTITIES[y1][x2] != '#' && MAP_ENTITIES[y2][x2] == '*')
                        || (MAP_ENTITIES[y1][x2] != '#' && MAP_ENTITIES[y1][x2] != '*' && MAP_ENTITIES[y2][x2] == '#')
                        || (MAP_ENTITIES[y1][x2] != '#' && MAP_ENTITIES[y1][x2] != '*')) {
                    if (y2 * TILE_SIZE + TILE_SIZE - (entity.getY() + entity.getH()) > s1) {
                        entity.lowVY();
                        entity.raiseVX();
                    }
                }
            }
        } else if (entity.getValX() < 0) {
            if (MAP_ENTITIES[y1][x1] == '*' || MAP_ENTITIES[y2][x1] == '*'
                    || MAP_ENTITIES[y1][x1] == '#' || MAP_ENTITIES[y2][x1] == '#') {
                if (!entity.isWallPass()) {
                    entity.setValX(0);
                }
            }
            if (entity instanceof Bomber) {
                if ((MAP_ENTITIES[y1][x1] == '*' && MAP_ENTITIES[y2][x1] != '*' && MAP_ENTITIES[y2][x1] != '#')
                        || (MAP_ENTITIES[y1][x1] == '#' && MAP_ENTITIES[y2][x1] != '#' && MAP_ENTITIES[y2][x1] != '*')
                        || (MAP_ENTITIES[y2][x1] != '#' && MAP_ENTITIES[y2][x1] != '*')) {
                    if (y1 * TILE_SIZE + TILE_SIZE - entity.getY() < s2) {
                        entity.raiseVY();
                        entity.lowVX();
                    }
                } else if ((MAP_ENTITIES[y1][x1] != '*' && MAP_ENTITIES[y1][x1] != '#' && MAP_ENTITIES[y2][x1] == '*')
                        || (MAP_ENTITIES[y1][x1] != '#' && MAP_ENTITIES[y1][x1] != '*' && MAP_ENTITIES[y2][x1] == '#')
                        || (MAP_ENTITIES[y1][x1] != '#' && MAP_ENTITIES[y1][x1] != '*')) {
                    if (y2 * TILE_SIZE + TILE_SIZE - (entity.getY() + entity.getH()) > s1) {
                        entity.lowVY();
                        entity.lowVX();
                    }
                }
            }

        }


        //Check doc
        x1 = entity.getX() / TILE_SIZE;
        x2 = (entity.getX() + entity.getW()) / TILE_SIZE;

        y1 = (int) (entity.getY() + entity.getValY()) / TILE_SIZE;
        y2 = (int) (entity.getY() + entity.getValY() + entity.getH() - 1) / TILE_SIZE;

        if (x1 >= 0 && y1 >= 0) {
            if (entity.getValY() > 0) {
                if (MAP_ENTITIES[y2][x1] == '*' || MAP_ENTITIES[y2][x2] == '*'
                    || MAP_ENTITIES[y2][x1] == '#' || MAP_ENTITIES[y2][x2] == '#') {
                    if (!entity.isWallPass()) {
                        entity.setValY(0);
                    }
                }
                if (MAP_ENTITIES[y2][x1] == '#' || MAP_ENTITIES[y2][x2] == '#'
                        || MAP_ENTITIES[y2][x1] == '*' || MAP_ENTITIES[y2][x2] == '*') {
                    if (entity instanceof Bomber) {

                        if ((MAP_ENTITIES[y2][x1] == '*' && MAP_ENTITIES[y2][x2] != '*' && MAP_ENTITIES[y2][x2] != '#')
                                || (MAP_ENTITIES[y2][x1] == '#' && MAP_ENTITIES[y2][x2] != '#' && MAP_ENTITIES[y2][x2] != '*')
                                || (MAP_ENTITIES[y2][x2] != '#' && MAP_ENTITIES[y2][x2] != '*')) {
                            if (x1 * TILE_SIZE + TILE_SIZE - entity.getX() < s2) {
                                entity.raiseVX();
                                entity.raiseVX();
                            }
                        } else if ((MAP_ENTITIES[y2][x1] != '*' && MAP_ENTITIES[y2][x1] != '#' && MAP_ENTITIES[y2][x2] == '*')
                                || (MAP_ENTITIES[y2][x1] != '#' && MAP_ENTITIES[y2][x1] != '*' && MAP_ENTITIES[y2][x2] == '#')
                                || (MAP_ENTITIES[y2][x1] != '#' && MAP_ENTITIES[y2][x1] != '*')) {
                            if (x2 * TILE_SIZE + TILE_SIZE - (entity.getX() + entity.getW()) > s1) {
                                entity.lowVX();
                                entity.raiseVX();
                            }
                        }
                    }
                }
            } else if (entity.getValY() < 0) {
                if (MAP_ENTITIES[y1][x1] == '*' || MAP_ENTITIES[y1][x2] == '*'
                    || MAP_ENTITIES[y1][x1] == '#' || MAP_ENTITIES[y1][x2] == '#') {
                    if (!entity.isWallPass()) {
                        entity.setValY(0);
                    }
                }
                if (MAP_ENTITIES[y1][x1] == '#' || MAP_ENTITIES[y1][x2] == '#'
                        || MAP_ENTITIES[y1][x1] == '*' || MAP_ENTITIES[y1][x2] == '*') {
                    if (entity instanceof Bomber) {
                        if ((MAP_ENTITIES[y1][x1] != '*' && MAP_ENTITIES[y1][x1] != '#' && MAP_ENTITIES[y1][x2] == '*')
                                || (MAP_ENTITIES[y1][x1] != '#' && MAP_ENTITIES[y1][x1] != '*' && MAP_ENTITIES[y1][x2] == '#')
                                || (MAP_ENTITIES[y1][x1] != '#' && MAP_ENTITIES[y1][x1] != '*')) {
                            if (x2 * TILE_SIZE + TILE_SIZE - (entity.getX() + entity.getW()) > s1) {
                                entity.lowVX();
                                entity.lowVY();
                            }
                        } else if ((MAP_ENTITIES[y1][x1] == '*' && MAP_ENTITIES[y1][x2] != '*' && MAP_ENTITIES[y1][x2] != '#')
                                || (MAP_ENTITIES[y1][x1] == '#' && MAP_ENTITIES[y1][x2] != '#' && MAP_ENTITIES[y1][x2] != '*')
                                || (MAP_ENTITIES[y1][x2] != '#' && MAP_ENTITIES[y1][x2] != '*')) {
                            if (x1 * TILE_SIZE + TILE_SIZE - entity.getX() < s2) {
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
