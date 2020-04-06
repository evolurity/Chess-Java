package src.figures;

import src.chesstask.CellCores;
import src.chesstask.Logic;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Pawn extends Figures {

    public Pawn(int x, int y, boolean own) {
        super(x, y, fig.PAWN, own);
        if (isOwn()) {
            sprite = new ImageView(new Image("org/kore/myjavafx/fxml/sprites/wP.png"));
        } else {
            sprite = new ImageView(new Image("org/kore/myjavafx/fxml/sprites/bP.png"));
        }
        sprite.setX(x * 60 - 10);
        sprite.setY(410 - y * 60);
        sprite.setScaleX(0.75);
        sprite.setScaleY(0.75);
    }

    @Override
    public ArrayList<CellCores> getMoves(Logic logic) {
        ArrayList<CellCores> moves = new ArrayList<>();
        int n = 1;
        if (!isOwn()) {
            n = -1;
        }
        if ((isOwn() && y < 7) || (!isOwn() && y > 0)) {
            moves.add(new CellCores(x, y + 1 * n));
        }
        if ((isOwn() && y == 1) || (!isOwn() && y == 6)) {
            moves.add(new CellCores(x, y + 2 * n));
        }
        for (int i = 0; i < moves.size(); i++) {
            if (logic.figureCheck(moves.get(i).getX(), moves.get(i).getY())) {
                moves.remove(i);
                i--;
            }
        }
        if (logic.figureCheck(x + 1, y + 1) && !logic.figureOwnCheck(x + 1, y + 1) && logic.isGo) {
            moves.add(new CellCores(x + 1, y + 1));
        }
        if (logic.figureCheck(x - 1, y + 1) && !logic.figureOwnCheck(x - 1, y + 1) && logic.isGo) {
            moves.add(new CellCores(x - 1, y + 1));
        }
        if (logic.figureOwnCheck(x + 1, y - 1) && !logic.isGo) {
            moves.add(new CellCores(x + 1, y - 1));
        }
        if (logic.figureOwnCheck(x - 1, y - 1) && !logic.isGo) {
            moves.add(new CellCores(x - 1, y - 1));
        }
        return moves;
    }

}
