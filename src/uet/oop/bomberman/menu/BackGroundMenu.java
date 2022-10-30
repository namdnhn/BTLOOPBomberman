package uet.oop.bomberman.menu;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BackGroundMenu {
    private final InputStream inFont1 = Files.newInputStream(Paths.get("res/fonts/PokemonHollow.ttf"));
    private final InputStream inFont2 = Files.newInputStream(Paths.get("res/fonts/PokemonSolid.ttf"));
    private final InputStream inFont3 = Files.newInputStream(Paths.get("res/fonts/PokemonSolid.ttf"));

    private final Font font1 = Font.loadFont(inFont1, 90);
    private final Font font2 = Font.loadFont(inFont2, 90);
    private final Font font3 = Font.loadFont(inFont3, 30);

    private final InputStream BackGround = Files.newInputStream(Paths.get("res/images/backgroundMenu.jpg"));

    private Text title, startButton, exitButton;

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
        });
        startButton.setOnMouseEntered(e -> startButton.setFill(Color.ORANGE));
        startButton.setOnMouseExited(e -> startButton.setFill(Color.RED));
    }

    public void handleExit() {
        exitButton.setOnMouseClicked(event -> Platform.exit());
        exitButton.setOnMouseEntered(e -> exitButton.setFill(Color.ORANGE));
        exitButton.setOnMouseExited(e -> exitButton.setFill(Color.RED));
    }
}
