package uet.oop.bomberman.menu;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.movingEntities.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static uet.oop.bomberman.BombermanGame.a;

public class BackGroundMenu {
    private final InputStream inFont1 = Files.newInputStream(Paths.get("res/fonts/PokemonHollow.ttf"));
    private final InputStream inFont2 = Files.newInputStream(Paths.get("res/fonts/PokemonSolid.ttf"));
    private final InputStream inFont3 = Files.newInputStream(Paths.get("res/fonts/PokemonSolid.ttf"));
    private final Font font2 = Font.loadFont(inFont2, 90);
    private final Font font3 = Font.loadFont(inFont3, 30);

    private final InputStream BackGround = Files.newInputStream(Paths.get("res/images/backgroundMenu.jpg"));

    private Text title, startButton, exitButton, nextButton, win, gameOver, newGame;

    private Image img = new Image(BackGround);

    private ImageView imgview = new ImageView(img);

    public BackGroundMenu() throws IOException {
    }

    public void generate() {
        imgview.setFitWidth(Sprite.SCALED_SIZE * 31);
        imgview.setFitHeight(Sprite.SCALED_SIZE * 13);
        BombermanGame.root.getChildren().add(imgview);

        title = new Text("Bomberman");
        title.setX((double) (Sprite.SCALED_SIZE * 31 / 12));
        title.setY((double) (Sprite.SCALED_SIZE * 13 * 1.5 / 5));
        title.setFont(font2);
        title.setFill(Color.WHITE);

        startButton = new Text("Start");
        startButton.setX((double) (Sprite.SCALED_SIZE * 31 / 12));
        startButton.setY((double) (Sprite.SCALED_SIZE * 13 * 2.5 / 5));
        startButton.setFont(font3);
        startButton.setFill(Color.RED);

        exitButton = new Text("Exit");
        exitButton.setX((double) (Sprite.SCALED_SIZE * 31 / 12));
        exitButton.setY((double) (Sprite.SCALED_SIZE * 13 * 3 / 5));
        exitButton.setFont(font3);
        exitButton.setFill(Color.RED);

        BombermanGame.root.getChildren().add(title);
        BombermanGame.root.getChildren().add(startButton);
        BombermanGame.root.getChildren().add(exitButton);

        handleStart();
        handleExit();
    }

    public void handleStart() {
        startButton.setOnMouseClicked(event -> {
            BombermanGame.root.getChildren().remove(imgview);
            BombermanGame.root.getChildren().remove(title);
            BombermanGame.root.getChildren().remove(startButton);
            BombermanGame.root.getChildren().remove(exitButton);
            BombermanGame.setRunning(true);
            // chay nhac nen
            a.setOnEndOfMedia(new Runnable() {
                public void run() {
                    a.seek(Duration.ZERO);
                }
            });
            if (BombermanGame.isRunning())
                a.play();
        });
        startButton.setOnMouseEntered(e -> startButton.setFill(Color.ORANGE));
        startButton.setOnMouseExited(e -> startButton.setFill(Color.RED));
    }

    public void handleExit() {
        exitButton.setOnMouseClicked(event -> Platform.exit());
        exitButton.setOnMouseEntered(e -> exitButton.setFill(Color.ORANGE));
        exitButton.setOnMouseExited(e -> exitButton.setFill(Color.RED));
    }

    public void handleNext() {
        nextButton.setOnMouseClicked(event -> {
            BombermanGame.root.getChildren().remove(imgview);
            BombermanGame.root.getChildren().remove(win);
            BombermanGame.root.getChildren().remove(nextButton);
            BombermanGame.setRunning(true);
            BombermanGame.grassEntities.clear();
            BombermanGame.Bricks.clear();
            BombermanGame.Wall.clear();
            BombermanGame.enemies.clear();
            BombermanGame.items.clear();
            BombermanGame.createMap();
            BombermanGame.bomberman.setX(Sprite.SCALED_SIZE);
            BombermanGame.bomberman.setY(Sprite.SCALED_SIZE);
            BombermanGame.b.stop();
            BombermanGame.a.play();
        });
        nextButton.setOnMouseEntered(e -> nextButton.setFill(Color.ORANGE));
        nextButton.setOnMouseExited(e -> nextButton.setFill(Color.RED));


    }

    public void LevelUp() {
        BombermanGame.setRunning(false);
        BombermanGame.LEVEL++;
        imgview.setFitWidth(Sprite.SCALED_SIZE * 31);
        imgview.setFitHeight(Sprite.SCALED_SIZE * 13);
        BombermanGame.root.getChildren().add(imgview);
        win = new Text("WIN");
        win.setX((double) (Sprite.SCALED_SIZE * 31 / 12));
        win.setY((double) (Sprite.SCALED_SIZE * 13 * 1.5 / 5));
        win.setFont(font2);
        win.setFill(Color.WHITE);
        BombermanGame.root.getChildren().add(win);

        nextButton = new Text("Next");
        nextButton.setX((double) (Sprite.SCALED_SIZE * 31 / 12));
        nextButton.setY((double) (Sprite.SCALED_SIZE * 13 * 3 / 5));
        nextButton.setFont(font3);
        nextButton.setFill(Color.RED);
        BombermanGame.root.getChildren().add(nextButton);
        handleNext();
    }

    public void GameOver() {
        BombermanGame.setRunning(false);
        imgview.setFitWidth(Sprite.SCALED_SIZE * 31);
        imgview.setFitHeight(Sprite.SCALED_SIZE * 13);
        BombermanGame.root.getChildren().add(imgview);
        gameOver = new Text("GAME OVER");
        gameOver.setX((double) (Sprite.SCALED_SIZE * 31 / 12));
        gameOver.setY((double) (Sprite.SCALED_SIZE * 13 * 1.5 / 5));
        gameOver.setFont(font2);
        gameOver.setFill(Color.BLACK);
        BombermanGame.root.getChildren().add(gameOver);

        exitButton = new Text("Exit");
        exitButton.setX((double) (Sprite.SCALED_SIZE * 31 / 12));
        exitButton.setY((double) (Sprite.SCALED_SIZE * 13 * 3 / 5));
        exitButton.setFont(font3);
        exitButton.setFill(Color.RED);
        BombermanGame.root.getChildren().add(exitButton);
        handleExit();

        newGame = new Text("New Game");
        newGame.setX((double) (Sprite.SCALED_SIZE * 31 / 12));
        newGame.setY((double) (Sprite.SCALED_SIZE * 13 * 4 / 5));
        newGame.setFont(font3);
        newGame.setFill(Color.RED);
        BombermanGame.root.getChildren().add(newGame);
        handleNewGame();
    }

    public void handleNewGame() {
        BombermanGame.LEVEL = 1;
        newGame.setOnMouseClicked(event -> {
            BombermanGame.root.getChildren().remove(imgview);
            BombermanGame.root.getChildren().remove(gameOver);
            BombermanGame.root.getChildren().remove(newGame);
            BombermanGame.root.getChildren().remove(exitButton);
            BombermanGame.setRunning(true);
            BombermanGame.grassEntities.clear();
            BombermanGame.Bricks.clear();
            BombermanGame.Wall.clear();
            BombermanGame.enemies.clear();
            BombermanGame.items.clear();
            Bomber.bombs.clear();
            BombermanGame.createMap();
            BombermanGame.bomberman = null;
            BombermanGame.bomberman = new Bomber(Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, Sprite.player_right.getFxImage());
            a.setOnEndOfMedia(new Runnable() {
                public void run() {
                    a.seek(Duration.ZERO);
                }
            });
            if (BombermanGame.isRunning())
                a.play();
        });
        newGame.setOnMouseEntered(e -> newGame.setFill(Color.ORANGE));
        newGame.setOnMouseExited(e -> newGame.setFill(Color.RED));
    }
}
