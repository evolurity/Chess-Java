package src.figures;

import src.chesstask.CellCores;
import src.chesstask.Logic;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public abstract class Figures {

    public enum fig {
        PAWN,
        HORSE,
        ELEPHANT,
        ROOK,
        QUEEN,
        KING
    }

    protected int x;
    protected int y;
    protected fig type;
    protected boolean own;
    protected ImageView sprite;
    protected boolean isStep = false;

    public Figures(int x, int y, fig type, boolean own) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.own = own;
    }

    public abstract ArrayList<CellCores> getMoves(Logic logic);

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        this.sprite.setX(x * 60 - 10);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        this.sprite.setY((7 - y) * 60 - 10);
    }

    public fig getType() {
        return type;
    }

    public void setType(fig type) {
        this.type = type;
    }

    public boolean isOwn() {
        return own;
    }

    public ImageView getSprite() {
        return sprite;
    }

    public void setSprite(ImageView sprite) {
        this.sprite = sprite;
    }

    public boolean isStep() {
        return isStep;
    }

    public void setStep(boolean step) {
        isStep = step;
    }
}
