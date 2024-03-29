package uet.oop.bomberman.entities.Items;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.movingEntities.Bomber;

public class FlameItem extends Item {
    public FlameItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public FlameItem(int xUnit, int yUnit) {
        super(xUnit, yUnit);
    }

    @Override
    public void update() {
        Bomber.set_area(Bomber.get_area() + 1);
    }
}
