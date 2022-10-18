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
import uet.oop.bomberman.entities.movingEntities.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.movingEntities.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {

    public static int WIDTH = 31;
    public static int HEIGHT = 13;
    
    private GraphicsContext gc;
    private Canvas canvas;
    public Scene scene;
    public int LEVEL;

    private List<movingEntity> movingEntities = new ArrayList<>();

    private List<Grass> grassEntities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();


//    public Entity getStillEntity (int x, int y) {
//        for (Entity e : stillObjects) {
//            if (e.getX() == x && e.getY() == y) {
//                return e;
//            }
//        }
//    }
    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
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

        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        movingEntities.add(bomberman);

        /** Moving Key. */
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        bomberman.setGoUp(true);
                        bomberman.status = Bomber.WALK_TYPE.UP;
                        break;
                    case DOWN:
                        bomberman.setGoDown(true);
                        bomberman.status = Bomber.WALK_TYPE.DOWN;
                        break;
                    case LEFT:
                        bomberman.setGoLeft(true);
                        bomberman.status = Bomber.WALK_TYPE.LEFT;
                        break;
                    case RIGHT:
                        bomberman.setGoRight(true);
                        bomberman.status = Bomber.WALK_TYPE.RIGHT;
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



    public void createMap() {

        File file = new File("res/levels/Level1.txt");
        try {
            int countRow = -1;
            Scanner sc = new Scanner(file);
            LEVEL = sc.nextInt();
            HEIGHT = sc.nextInt();
            WIDTH = sc.nextInt();

            for (int i = 0; i < HEIGHT; ++i) {
                for (int j = 0; j < WIDTH; ++j) {
                    Grass grass;
                    grass = new Grass(j, i, Sprite.grass.getFxImage());
                    grassEntities.add(grass);
                    System.out.println("make grass");
                }
            }

            sc.skip("");

            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                System.out.println(s);
                char[] c = s.toCharArray();
                for (int i = 0; i < c.length; ++i) {
                    Entity entity;
                    if (c[i] == '#') {
                        entity = new Wall(i, countRow, Sprite.wall.getFxImage());
                        stillObjects.add(entity);
                        System.out.println("make wall");
                    } else if (c[i] == '*') {
                        entity = new Brick(i, countRow, Sprite.brick.getFxImage());
                        stillObjects.add(entity);
                        System.out.println("make break");
                    }
                }
                countRow++;
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void update() {
        movingEntities.forEach(movingEntity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        grassEntities.forEach(g -> g.render(gc));
        stillObjects.forEach(g -> g.render(gc));
        movingEntities.forEach(g -> g.render(gc));
    }


}
