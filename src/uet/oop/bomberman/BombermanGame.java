package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import javax.security.auth.kerberos.KerberosTicket;
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
    Scene scene;
    public int LEVEL;

    int countRow = 0;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();


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
        entities.add(bomberman);

        /** Moving Key. */
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        bomberman.setGoUp(true);
                        break;
                    case DOWN:
                        bomberman.setGoDown(true);
                        break;
                    case LEFT:
                        bomberman.setGoLeft(true);
                        break;
                    case RIGHT:
                        bomberman.setGoRight(true);
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
//        for (int i = 0; i < WIDTH; i++) {
//            for (int j = 0; j < HEIGHT; j++) {
//                Entity object;
//                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
//                    object = new Wall(i, j, Sprite.wall.getFxImage());
//                }
//                else {
//                    object = new Grass(i, j, Sprite.grass.getFxImage());
//                }
//                stillObjects.add(object);
//            }
//        }

        File file = new File("res/levels/Level1.txt");
        try {
            Scanner sc = new Scanner(file);
            LEVEL = sc.nextInt();
            HEIGHT = sc.nextInt();
            WIDTH = sc.nextInt();

            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                System.out.println(s);
                char[] c = s.toCharArray();
                for (int i = 0; i < c.length; ++i) {
                    Entity entity;
                    if (c[i] == '#') {
                        entity = new Wall(i, countRow, Sprite.wall.getFxImage());
                    } else if (c[i] == '*') {
                        entity = new Brick(i, countRow, Sprite.brick.getFxImage());
                    } else {
                        entity = new Grass(i, countRow, Sprite.grass.getFxImage());
                    }
                    stillObjects.add(entity);
                }
                ++countRow;
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }




}
