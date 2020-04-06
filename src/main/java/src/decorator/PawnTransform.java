package src.decorator;

import src.figures.Figures;
import src.figures.Queen;

public class PawnTransform extends Decorator {
    public PawnTransform(Figures figures){
        this.figures = figures;
    }

    public Figures transform() {
        figures = new Queen(figures.getX(), figures.getY(), figures.isOwn());
        return figures;
    }
}
