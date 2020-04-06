package src.saveInfo;

import src.figures.Figures;

public class SaveInfo {
    private Figures.fig[][] arr;
    private int[][] own;

    public SaveInfo(Figures.fig[][] arr, int[][] own) {
        this.arr = arr;
        this.own = own;
    }

    public Figures.fig[][] getArr() {
        return arr;
    }

    public void setArr(Figures.fig[][] arr) {
        this.arr = arr;
    }

    public int[][] getOwn() {
        return own;
    }

    public void setOwn(int[][] own) {
        this.own = own;
    }
}
