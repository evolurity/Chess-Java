package src.figures;

import src.chesstask.CellCores;
import src.chesstask.Logic;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Rook extends Figures {

    public Rook(int x, int y, boolean own) {
        super(x, y, fig.ROOK, own);
        if (isOwn()) {
            sprite = new ImageView(new Image("org/kore/myjavafx/fxml/sprites/wR.png"));
        } else {
            sprite = new ImageView(new Image("org/kore/myjavafx/fxml/sprites/bR.png"));
        }
        sprite.setX(x * 60 - 10);
        sprite.setY(410 - y * 60);
        sprite.setScaleX(0.75);
        sprite.setScaleY(0.75);
    }

    @Override
    public ArrayList<CellCores> getMoves(Logic logic) {
        ArrayList<CellCores> moves = new ArrayList<>();
        int x2 = x;
        int y2 = y;
        while (x2 < 7) {
            if (!logic.figureCheck(x2 + 1, y2)) {
                moves.add(new CellCores(x2 + 1, y2));
                x2++;
            } else {
                if ((logic.figureCheck(x2 + 1, y2) && !logic.figureOwnCheck(x2 + 1, y2) && logic.isGo)
                        || (logic.figureOwnCheck(x2 + 1, y2) && !logic.isGo)) {
                    moves.add(new CellCores(x2 + 1, y2));
                }
                break;
            }
        }
        x2 = x;
        y2 = y;
        while (y2 < 7) {
            if (!logic.figureCheck(x2, y2 + 1)) {
                moves.add(new CellCores(x2, y2 + 1));
                y2++;
            } else {
                if ((logic.figureCheck(x2, y2 + 1) && !logic.figureOwnCheck(x2, y2 + 1) && logic.isGo)
                        || (logic.figureOwnCheck(x2, y2 + 1) && !logic.isGo)) {
                    moves.add(new CellCores(x2, y2 + 1));
                }
                break;
            }
        }
        x2 = x;
        y2 = y;
        while (x2 > 0) {
            if (!logic.figureCheck(x2 - 1, y2)) {
                moves.add(new CellCores(x2 - 1, y2));
                x2--;
            } else {
                if ((logic.figureCheck(x2 - 1, y2) && !logic.figureOwnCheck(x2 - 1, y2) && logic.isGo)
                        || (logic.figureOwnCheck(x2 - 1, y2) && !logic.isGo)) {
                    moves.add(new CellCores(x2 - 1, y2));
                }
                break;
            }
        }
        x2 = x;
        y2 = y;
        while (y2 > 0) {
            if (!logic.figureCheck(x2, y2 - 1)) {
                moves.add(new CellCores(x2, y2 - 1));
                y2--;
            } else {
                if ((logic.figureCheck(x2, y2 - 1) && !logic.figureOwnCheck(x2, y2 - 1) && logic.isGo)
                        || (logic.figureOwnCheck(x2, y2 - 1) && !logic.isGo)) {
                    moves.add(new CellCores(x2, y2 - 1));
                }
                break;
            }
        }
        return moves;
    }

}
