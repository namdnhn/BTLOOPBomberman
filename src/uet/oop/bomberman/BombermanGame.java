package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Items.Item;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.movingEntities.Bomber;
import uet.oop.bomberman.entities.movingEntities.Enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.movingEntities.*;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public static int WIDTH = 31;
    public static int HEIGHT = 13;
    
    private GraphicsContext gc;
    private Canvas canvas;
    public static Scene scene;
    public int LEVEL;

    public static List<movingEntity> movingEntities = new ArrayList<>();

    public static List<Grass> grassEntities = new ArrayList<>();
    public static List<Brick> Bricks = new ArrayList<>();
    public static List<Wall> Wall = new ArrayList<>();

    public static List<Enemy> enemies = new ArrayList<>();
    public static List<Item> items = new ArrayList<>();
    public static Portal portal;


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    public static Bomber bomberman;
    @Override
    public void start(Stage stage) throws UnsupportedAudioFileException, IOException {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();
        createMap();

        Sound.play("res/sounds/backSound.wav");

        bomberman = new Bomber(Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, Sprite.player_right.getFxImage());
        movingEntities.add(bomberman);

        /** Moving Key. */
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        bomberman.setGoUp(true);
                        bomberman.setGoDown(false);
                        bomberman.status = Bomber.WALK_TYPE.UP;
                        break;
                    case DOWN:
                        bomberman.setGoDown(true);
                        bomberman.setGoUp(false);
                        bomberman.status = Bomber.WALK_TYPE.DOWN;
                        break;
                    case LEFT:
                        bomberman.setGoLeft(true);
                        bomberman.setGoRight(false);
                        bomberman.status = Bomber.WALK_TYPE.LEFT;
                        break;
                    case RIGHT:
                        bomberman.setGoRight(true);
                        bomberman.setGoLeft(false);
                        bomberman.status = Bomber.WALK_TYPE.RIGHT;
                        break;
                    case SPACE:
                        bomberman.placeBomb(bomberman.getX(), bomberman.getY());
                        break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    bomberman.setGoUp(false); break;
                    case DOWN:  bomberman.setGoDown(false); break;
                    case LEFT:  bomberman.setGoLeft(false); break;
                    case RIGHT: bomberman.setGoRight(false); break;
                }
            }
        });
    }

    public static GameMap map = new GameMap();
    public void createMap() {
        map.readMap();
        map.loadMap();
    }

    public void update() {
        Bomber.bombs.forEach(Bomb::update);
        movingEntities.forEach(movingEntity::update);
        enemies.forEach(Enemy::update);
        enemies.removeIf(Enemy::isRemoved);
        Bricks.removeIf(Brick::isRemoved);
        items.removeIf(Item::isHasGot);
        portal.update();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        grassEntities.forEach(g -> g.render(gc));
        portal.render(gc);
        items.forEach(g -> g.render(gc));
        Bricks.forEach(g -> g.render(gc));
        Wall.forEach(g -> g.render(gc));
        movingEntities.forEach(g -> g.render(gc));
        enemies.forEach(g -> g.render(gc));
        bomberman.renderBomb(gc);
    }


}
