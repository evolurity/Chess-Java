package src.figures;

import src.chesstask.CellCores;
import src.chesstask.Logic;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Horse extends Figures {

    public Horse(int x, int y, boolean own) {
        super(x, y, fig.HORSE, own);
        if (isOwn()) {
            sprite = new ImageView(new Image("org/kore/myjavafx/fxml/sprites/wN.png"));
        } else {
            sprite = new ImageView(new Image("org/kore/myjavafx/fxml/sprites/bN.png"));
        }
        sprite.setX(x * 60 - 10);
        sprite.setY(410 - y * 60);
        sprite.setScaleX(0.75);
        sprite.setScaleY(0.75);
    }

    @Override
    public ArrayList<CellCores> getMoves(Logic logic) {
        ArrayList<CellCores> moves = new ArrayList<>();
        if (x < 7 && y < 6) {
            moves.add(new CellCores(x + 1, y + 2));
        }
        if (x < 6 && y < 7) {
            moves.add(new CellCores(x + 2, y + 1));
        }
        if (x < 6 && y > 0) {
            moves.add(new CellCores(x + 2, y - 1));
        }
        if (x < 7 && y > 1) {
            moves.add(new CellCores(x + 1, y - 2));
        }
        if (x > 0 && y > 1) {
            moves.add(new CellCores(x - 1, y - 2));
        }
        if (x > 1 && y > 0) {
            moves.add(new CellCores(x - 2, y - 1));
        }
        if (x > 1 && y < 7) {
            moves.add(new CellCores(x - 2, y + 1));
        }
        if (x > 0 && y < 6) {
            moves.add(new CellCores(x - 1, y + 2));
        }
        for (int i = 0; i < moves.size(); i++) {
            if ((logic.figureOwnCheck(moves.get(i).getX(), moves.get(i).getY()) && logic.isGo)
                    || (logic.figureCheck(moves.get(i).getX(), moves.get(i).getY())
                    && !logic.figureOwnCheck(moves.get(i).getX(), moves.get(i).getY()) && !logic.isGo)) {
                moves.remove(i);
                i--;
            }
        }
        return moves;
    }

}
