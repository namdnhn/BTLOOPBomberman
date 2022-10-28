package uet.oop.bomberman.entities.Items;

import javafx.scene.image.Image;

public class LifeItem extends Item {
    public LifeItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public LifeItem(int xUnit, int yUnit) {
        super(xUnit, yUnit);
    }

    @Override
    public void update() {
    }
}
