package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;

public class Portal extends Entity {

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    private boolean open;
    public Portal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        open = true;
    }

    public Portal(int xUnit, int yUnit) {
        super(xUnit, yUnit);
    }

    @Override
    public void update() {
        if (BombermanGame.enemies.isEmpty()) {
            setOpen(true);
        }
    }
}
