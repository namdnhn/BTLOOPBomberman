package uet.oop.bomberman.entities.Items;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.movingEntities.Bomber;

public class SpeedItem extends Item {
    public SpeedItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public SpeedItem(int xUnit, int yUnit) {
        super(xUnit, yUnit);
    }

    @Override
    public void update() {
        BombermanGame.bomberman.setSPEED(BombermanGame.bomberman.getSPEED() + 1);
    }
}
