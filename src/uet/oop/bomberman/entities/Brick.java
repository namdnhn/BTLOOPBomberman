package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Brick extends Entity {
    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    private boolean removed = false;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }
}
