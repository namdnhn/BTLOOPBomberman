package uet.oop.bomberman.entities.movingEntities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.movingEntities.movingEntity;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends movingEntity {
    protected boolean isDead;

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
    }

}
