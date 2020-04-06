package src.figures;

import src.chesstask.CellCores;
import src.chesstask.Logic;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class King extends Figures {

    public King(int x, int y, boolean own) {
        super(x, y, fig.KING, own);
        if (isOwn()) {
            sprite = new ImageView(new Image("org/kore/myjavafx/fxml/sprites/wK.png"));
        } else {
            sprite = new ImageView(new Image("org/kore/myjavafx/fxml/sprites/bK.png"));
        }
        sprite.setX(x * 60 - 10);
        sprite.setY(410 - y * 60);
        sprite.setScaleX(0.75);
        sprite.setScaleY(0.75);
    }

    @Override
    public ArrayList<CellCores> getMoves(Logic logic) {
        ArrayList<CellCores> moves = new ArrayList<>();
        if (x < 7) {
            moves.add(new CellCores(x + 1, y));
        }
        if (x < 7 && y > 0) {
            moves.add(new CellCores(x + 1, y - 1));
        }
        if (y > 0) {
            moves.add(new CellCores(x, y - 1));
        }
        if (x > 0 && y > 0) {
            moves.add(new CellCores(x - 1, y - 1));
        }
        if (x > 0) {
            moves.add(new CellCores(x - 1, y));
        }
        if (x > 0 && y < 7) {
            moves.add(new CellCores(x - 1, y + 1));
        }
        if (y < 7) {
            moves.add(new CellCores(x, y + 1));
        }
        if (x < 7 && y < 7) {
            moves.add(new CellCores(x + 1, y + 1));
        }
        for (int i = 0; i < moves.size(); i++) {
            if ((logic.figureOwnCheck(moves.get(i).getX(), moves.get(i).getY()) && logic.isGo)
                    || (logic.figureCheck(moves.get(i).getX(), moves.get(i).getY())
                    && !logic.figureOwnCheck(moves.get(i).getX(), moves.get(i).getY()) && !logic.isGo)) {
                moves.remove(i);
                i--;
            }
        }
        if(!this.isStep && logic.figureCheck(4,(own) ? 0 : 7) && logic.getFigure(4, (own) ? 0 : 7).type == fig.KING && !logic.getFigure(4, (own) ? 0 : 7).isStep() && ((own) ? (!logic.figureCheck(5,0)
                && !logic.figureCheck(6,0) && logic.figureCheck(7,0)) : (!logic.figureCheck(5,7))
                && !logic.figureCheck(6,7) && logic.figureCheck(7,7)) && ((own) ? ((logic.getFigure(7,0).type == fig.ROOK) && (!logic.getFigure(7,0).isStep())) :
                ((logic.getFigure(7,7).type == fig.ROOK) && (!logic.getFigure(7,7).isStep())))){
            System.out.println(own);
                moves.add(new CellCores(x+2, y));
        }
        if(!this.isStep && logic.figureCheck(4,(own) ? 0 : 7) && logic.getFigure(4, (own) ? 0 : 7).type == fig.KING && !logic.getFigure(4, (own) ? 0 : 7).isStep() && ((own) ? (!logic.figureCheck(1,0)
                && !logic.figureCheck(2,0)  && !logic.figureCheck(3,0) && logic.figureCheck(0,0)) : (!logic.figureCheck(1,7)
                && !logic.figureCheck(2,7) && !logic.figureCheck(3,7)) && logic.figureCheck(0,7)) && ((own) ? ((logic.getFigure(0,0).type == fig.ROOK) && (!logic.getFigure(0,0).isStep())) :
                ((logic.getFigure(0,7).type == fig.ROOK) && (!logic.getFigure(0,7).isStep())))){
            moves.add(new CellCores(x-2, y));
        }
        return moves;
    }

}
