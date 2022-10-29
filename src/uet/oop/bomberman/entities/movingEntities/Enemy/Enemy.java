package uet.oop.bomberman.entities.movingEntities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.movingEntities.Bomber;
import uet.oop.bomberman.entities.movingEntities.movingEntity;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends movingEntity {
    protected boolean isDead;

    public boolean isRemoved() {
        return removed;
    }

    protected boolean removed;
    protected int reversedTime = 30;

    public void kill() {
        for (Bomb b : Bomber.bombs) {
            if (b.isBoom() && (checkKilled(b.verticalKillingArea) || checkKilled(b.horizontalKillingArea))) {
                isDead = true;
            }
        }
    }

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
    }

}
