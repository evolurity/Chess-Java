package src.figures;

import src.chesstask.CellCores;
import src.chesstask.Logic;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Elephant extends Figures {

    public Elephant(int x, int y, boolean own) {
        super(x, y, fig.ELEPHANT, own);
        if (isOwn()) {
            sprite = new ImageView(new Image("org/kore/myjavafx/fxml/sprites/wB.png"));
        } else {
            sprite = new ImageView(new Image("org/kore/myjavafx/fxml/sprites/bB.png"));
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
        while (x2 < 7 && y2 < 7) {
            if (!logic.figureCheck(x2 + 1, y2 + 1)) {
                moves.add(new CellCores(x2 + 1, y2 + 1));
                x2++;
                y2++;
            } else {
                if ((logic.figureCheck(x2 + 1, y2 + 1) && !logic.figureOwnCheck(x2 + 1, y2 + 1) && logic.isGo)
                        || (logic.figureOwnCheck(x2 + 1, y2 + 1) && !logic.isGo)) {
                    moves.add(new CellCores(x2 + 1, y2 + 1));
                }
                break;
            }
        }
        x2 = x;
        y2 = y;
        while (x2 < 7 && y2 > 0) {
            if (!logic.figureCheck(x2 + 1, y2 - 1)) {
                moves.add(new CellCores(x2 + 1, y2 - 1));
                x2++;
                y2--;
            } else {
                if ((logic.figureCheck(x2 + 1, y2 - 1) && !logic.figureOwnCheck(x2 + 1, y2 - 1) && logic.isGo)
                        || (logic.figureOwnCheck(x2 + 1, y2 - 1) && !logic.isGo)) {
                    moves.add(new CellCores(x2 + 1, y2 - 1));
                }
                break;
            }
        }
        x2 = x;
        y2 = y;
        while (x2 > 0 && y2 > 0) {
            if (!logic.figureCheck(x2 - 1, y2 - 1)) {
                moves.add(new CellCores(x2 - 1, y2 - 1));
                x2--;
                y2--;
            } else {
                if ((logic.figureCheck(x2 - 1, y2 - 1) && !logic.figureOwnCheck(x2 - 1, y2 - 1) && logic.isGo)
                        || (logic.figureOwnCheck(x2 - 1, y2 - 1) && !logic.isGo)) {
                    moves.add(new CellCores(x2 - 1, y2 - 1));
                }
                break;
            }
        }
        x2 = x;
        y2 = y;
        while (x2 > 0 && y2 < 7) {
            if (!logic.figureCheck(x2 - 1, y2 + 1)) {
                moves.add(new CellCores(x2 - 1, y2 + 1));
                x2--;
                y2++;
            } else {
                if ((logic.figureCheck(x2 - 1, y2 + 1) && !logic.figureOwnCheck(x2 - 1, y2 + 1) && logic.isGo)
                        || (logic.figureOwnCheck(x2 - 1, y2 + 1) && !logic.isGo)) {
                    moves.add(new CellCores(x2 - 1, y2 + 1));
                }
                break;
            }
        }
        return moves;
    }

}
