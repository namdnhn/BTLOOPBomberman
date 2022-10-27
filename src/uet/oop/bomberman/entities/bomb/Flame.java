package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Entity {
    public enum FLAME_TYPES {
        VER, TOPLAST, DOWNLAST, HORI, LEFTLAST, RIGHTLAST;
    }

    public FLAME_TYPES flame_status;
    public Flame(int xUnit, int yUnit, FLAME_TYPES flame_status) {
        super(xUnit, yUnit);
        this.flame_status = flame_status;
    }

    @Override
    public void update() {
    }

    public void animation(int time) {
        if (flame_status == FLAME_TYPES.TOPLAST)
            img = Sprite.movingSprite(Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2, time, 9).getFxImage();
        else if (flame_status == FLAME_TYPES.VER) {
            img = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, time, 9).getFxImage();
        } else if (flame_status == FLAME_TYPES.DOWNLAST) {
            img = Sprite.movingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2, time, 9).getFxImage();
        } else if (flame_status == FLAME_TYPES.LEFTLAST) {
            img = Sprite.movingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2, time, 9).getFxImage();
        } else if (flame_status == FLAME_TYPES.RIGHTLAST) {
            img = Sprite.movingSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2, time, 9).getFxImage();
        } else if (flame_status == FLAME_TYPES.HORI)
            img = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, time, 9).getFxImage();
    }
}
