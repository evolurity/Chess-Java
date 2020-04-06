package src.figures;

public class Cell {
    private int x;
    private int y;
    private Figures figure;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Figures getFigure() {
        return figure;
    }

    public void setFigure(Figures figure) {
        this.figure = figure;
    }


}
