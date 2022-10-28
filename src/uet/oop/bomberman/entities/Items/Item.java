package uet.oop.bomberman.entities.Items;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public abstract class Item extends Entity {
    public boolean isHasGot() {
        return hasGot;
    }

    public void setHasGot(boolean hasGot) {
        this.hasGot = hasGot;
    }

    protected boolean hasGot;
    public Item(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        hasGot = false;
    }

    public Item(int xUnit, int yUnit) {
        super(xUnit, yUnit);
        hasGot = false;
    }
}
