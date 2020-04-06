package src.chesstask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import src.command.BoardClickCommand;
import src.command.Command;
import src.decorator.Decorator;
import src.decorator.PawnTransform;
import src.figures.*;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import src.saveInfo.SaveInfo;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Logic extends Thread{

    private Cell[][] board = new Cell[8][8];
    private List<Figures> list = new ArrayList<>();
    private Pane drawPane;
    public boolean isGo;
    private List<Node> lightCells = new ArrayList<>();
    private List<CellCores> cellCores = new ArrayList<>();
    private Figures figureCh;
    private int kx = -1;
    private int ky = -1;
    private int mx = -1;
    private int my = -1;
    private boolean isCheck = false;
    private boolean isOwnCheck = false;
    private Stage stage;
    public Command command = new BoardClickCommand(this);

    @Override
    public void run(){
        this.drawFigures();
    }

    public Logic(Stage stage) {
        this.isGo = true;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell(i, j);
            }
        }
    }

    public void saveFromJson() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(stage);
        Figures.fig[][] arr = new Figures.fig[8][8];
        int[][] arr1 = new int[8][8];
        for(int i = 0; i < list.size(); i++){
            Figures f = list.get(i);
            arr[f.getX()][f.getY()] = f.getType();
            arr1[f.getX()][f.getY()] = (f.isOwn()) ? 1 : 0;
        }
        SaveInfo saveInfo = new SaveInfo(arr,arr1);
        Gson gsons = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fileWriter = new FileWriter(file)) {
            gsons.toJson(saveInfo, fileWriter);
            System.out.println("Successful!");
        } catch (IOException e) {
            System.out.println("Exception,try again please");
            e.printStackTrace();
        }
    }

    public void readFromJson(){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        try (Reader reader = new FileReader(file)) {
            Gson gson = new Gson();
            SaveInfo saveInfo = gson.fromJson(reader, SaveInfo.class);
            list.clear();
            Figures.fig[][] arr = saveInfo.getArr();
            int[][] arr1 = saveInfo.getOwn();
            for(int i = 0; i < arr.length; i++){
                for(int j = 0; j < arr[i].length; j++){
                    if(arr[i][j] != null){
                        addFigure(arr[i][j], i, j, (arr1[i][j] == 1) ? true : false);
                        reDrawFigures();
                        drawFigures();
                    }
                }
            }
            reader.close();
            System.out.println("Successful!");
        } catch (IOException e) {

            System.out.println("Exception,try again please");
            readFromJson();
        }
    }

    private boolean checkCheck() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType() == Figures.fig.KING && ((!list.get(i).isOwn() && isGo) || (list.get(i).isOwn() && !isGo))) {
                kx = list.get(i).getX();
                ky = list.get(i).getY();
            }
        }
        if (figureCh.getType() == Figures.fig.KING && ((!figureCh.isOwn() && isGo) || (figureCh.isOwn() && !isGo))) {
            kx = figureCh.getX();
            ky = figureCh.getY();
        }
        for (int i = 0; i < list.size (); i++) {
            if (i == 0) {
                Figures fig = figureCh;
                 if (isGo && fig.isOwn() || !isGo && !fig.isOwn()) {
                    ArrayList<CellCores> cellCoresLocal = fig.getMoves(this);
                    for (int j = 0; j < cellCoresLocal.size(); j++) {
                        //System.out.println(cellCoresLocal.get(j).getX() + " " + cellCoresLocal.get(j).getY());
                        if (cellCoresLocal.get(j).getX() == kx && cellCoresLocal.get(j).getY() == ky) {

                            //isCheck = true;
                            lightCheck();
                            isOwnCheck = isGo;
                            return true;
                        }
                    }
                }
            }
            Figures fig = list.get(i);
            if (isGo && fig.isOwn() || !isGo && !fig.isOwn()) {
                ArrayList<CellCores> cellCoresLocal = fig.getMoves(this);
                for (int j = 0; j < cellCoresLocal.size(); j++) {
                    //System.out.println(cellCoresLocal.get(j).getX() + " " + cellCoresLocal.get(j).getY());
                    if (cellCoresLocal.get(j).getX() == kx && cellCoresLocal.get(j).getY() == ky) {
                        isCheck = true;
                        lightCheck();
                        isOwnCheck = isGo;
                        return true;
                    }
                }
            }
        }
        //isCheck = false;
        return false;
    }

    public void boardClick(double x, double y) {
        int x1 = (int) x / 60;
        int y1 = 7 - ((int) y / 60);
        if (coresCheck(x1, y1) && ((!figureOwnCheck(x1, y1) && isGo) || ((figureOwnCheck(x1, y1) || !figureCheck(x1, y1)) && !isGo))) {
//            int x2 = figureCh.getX();
//            int y2 = figureCh.getY();
//            figureCh.setX(x1);
//            figureCh.setY(y1);
//            if (checkCheck() && ((!isGo && isOwnCheck) || (isGo && !isOwnCheck))) {
//                System.out.println("213");
//                figureCh.setX(x2);
//                figureCh.setY(y2);
//                return;
//            }
//            figureCh.setX(x2);
//            figureCh.setY(y2);
            Figures figMem = null;
            if (figureCheck(x1, y1)) {
                figMem = getFigure(x1,y1);
                removeFigure(x1, y1);
                reDrawFigures();
            }
            if(figureCh.getType() == Figures.fig.KING && (Math.abs(x1 - figureCh.getX()) == 2)){
                if(figureCh.isOwn()){
                    if(x1 > figureCh.getX()){
                        this.getFigure(7,0).setX(5);
                    }else{
                        this.getFigure(0,0).setX(3);
                    }
                }else{
                    if(x1 > figureCh.getX()){
                        this.getFigure(7,7).setX(5);
                    }else{
                        this.getFigure(0,7).setX(3);
                    }
                }
            }
            mx = figureCh.getX();
            my = figureCh.getY();
            figureCh.setX(x1);
            figureCh.setY(y1);
            isGo = !isGo;
            if (checkCheck()){
                figureCh.setX(mx);
                figureCh.setY(my);
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getType() == Figures.fig.KING && ((!list.get(i).isOwn() && isGo) || (list.get(i).isOwn() && !isGo))) {
                        kx = list.get(i).getX();
                        ky = list.get(i).getY();
                    }
                }
                if (figureCh.getType() == Figures.fig.KING && ((!figureCh.isOwn() && isGo) || (figureCh.isOwn() && !isGo))) {
                    kx = figureCh.getX();
                    ky = figureCh.getY();
                }
                lightCell(new ArrayList<>());
                lightCheck();
                isGo = !isGo;
                if(figMem != null){
                    board[figMem.getX()][figMem.getY()].setFigure(figMem);
                    list.add(figMem);
                    drawPane.getChildren().add(figMem.getSprite());
                }
                return;
            }
            if(figureCh.getType() == Figures.fig.PAWN && ((!isGo) ? (figureCh.getY() == 7) : (figureCh.getY() == 0))){
                drawPane.getChildren().remove(figureCh.getSprite());
                list.remove(figureCh);
                PawnTransform pawnTransform = new PawnTransform(figureCh);
                figureCh = pawnTransform.transform();
                board[figureCh.getX()][figureCh.getY()].setFigure(figureCh);
                list.add(figureCh);
                drawPane.getChildren().add(figureCh.getSprite());
            }
            isGo = !isGo;
            cellCores = new ArrayList<>();
            lightCell(new ArrayList<>());
            isCheck = false;
            checkCheck();

            isGo = !isGo;
            return;
        }
        if (figureCheck(x1, y1)) {
            if ((figureOwnCheck(x1, y1) && isGo) || (!figureOwnCheck(x1, y1) && !isGo)) {
                figureCh = getFigure(x1, y1);
                ArrayList<CellCores> cores = new ArrayList<>();
                cores.add(new CellCores(x1, y1));
                ArrayList<CellCores> moves = getFigure(x1, y1).getMoves(this);

                cores.addAll(moves);
                cellCores = (ArrayList) moves.clone();
                lightCell(cores);
            }
        }
        if (isCheck) {
            lightCheck();
        }
    }

    private void removeFigure(int x, int y) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getX() == x && list.get(i).getY() == y) {
                list.remove(i);
                return;
            }
        }
    }

    public boolean coresCheck(int x, int y) {
        for (int i = 0; i < cellCores.size(); i++) {
            if (cellCores.get(i).getX() == x && cellCores.get(i).getY() == y) {
                return true;
            }
        }
        return false;
    }

    public Figures getFigure(int x, int y) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getX() == x && list.get(i).getY() == y) {
                return list.get(i);
            }
        }
        return null;
    }

    public boolean figureCheck(int x, int y) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getX() == x && list.get(i).getY() == y) {
                return true;
            }
        }
        return false;
    }

    public boolean figureOwnCheck(int x, int y) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getX() == x && list.get(i).getY() == y & list.get(i).isOwn()) {
                return true;
            }
        }
        return false;
    }

    private void lightCell(ArrayList<CellCores> list) {
        for (int i = 0; i < lightCells.size(); i++) {
            if (drawPane.getChildren().contains(lightCells.get(i))) {
                drawPane.getChildren().remove(lightCells.get(i));
            }
        }
        lightCells = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int x = list.get(i).getX();
            int y = list.get(i).getY();
            Rectangle r = new Rectangle();
            r.setX(x * 60);
            r.setY((7 - y) * 60);
            r.setWidth(60);
            r.setHeight(60);
            r.setStrokeWidth(2);
            r.setStroke(Color.BLACK);
            r.setStrokeType(StrokeType.INSIDE);
            r.setFill(Color.TRANSPARENT);
            lightCells.add(r);
        }
        drawPane.getChildren().addAll(lightCells);
    }

    private void lightCheck() {
        Rectangle r = new Rectangle();
        r.setX(kx * 60);
        r.setY((7 - ky) * 60);
        r.setWidth(60);
        r.setHeight(60);
        r.setStrokeWidth(3);
        r.setStroke(Color.RED);
        r.setStrokeType(StrokeType.INSIDE);
        r.setFill(Color.TRANSPARENT);
        lightCells.add(r);
        drawPane.getChildren().add(r);
    }

    public void generate(boolean mode, Pane pane) {
        this.drawPane = pane;
        if (mode) {
            for (int i = 0; i < 8; i++) {
                addFigure(0, i, 1, true);
            }
            for (int i = 0; i < 8; i++) {
                addFigure(0, i, 6, false);
            }
            addFigure(1, 1, 0, true);
            addFigure(1, 6, 0, true);
            addFigure(1, 1, 7, false);
            addFigure(1, 6, 7, false);
            addFigure(2, 2, 0, true);
            addFigure(2, 5, 0, true);
            addFigure(2, 2, 7, false);
            addFigure(2, 5, 7, false);
            addFigure(3, 0, 0, true);
            addFigure(3, 7, 0, true);
            addFigure(3, 0, 7, false);
            addFigure(3, 7, 7, false);
            addFigure(4, 3, 0, true);
            addFigure(4, 3, 7, false);
            addFigure(5, 4, 0, true);
            addFigure(5, 4, 7, false);
        } else {

        }
    }

    private void addFigure(int figureNumber, int x, int y, boolean mode) {
        switch (figureNumber) {
            case 0: {
                Figures figure = new Pawn(x, y, mode);
                board[x][y].setFigure(figure);
                list.add(figure);
                return;
            }
            case 1: {
                Figures figure = new Horse(x, y, mode);
                board[x][y].setFigure(figure);
                list.add(figure);
                return;
            }
            case 2: {
                Figures figure = new Elephant(x, y, mode);
                board[x][y].setFigure(figure);
                list.add(figure);
                return;
            }
            case 3: {
                Figures figure = new Rook(x, y, mode);
                board[x][y].setFigure(figure);
                list.add(figure);
                return;
            }
            case 4: {
                Figures figure = new Queen(x, y, mode);
                board[x][y].setFigure(figure);
                list.add(figure);
                return;
            }
            case 5: {
                Figures figure = new King(x, y, mode);
                board[x][y].setFigure(figure);
                list.add(figure);
                return;
            }
        }
    }

    private void addFigure(Figures.fig type, int x, int y, boolean mode) {
        switch (type) {
            case PAWN: {
                Figures figure = new Pawn(x, y, mode);
                board[x][y].setFigure(figure);
                list.add(figure);
                return;
            }
            case HORSE: {
                Figures figure = new Horse(x, y, mode);
                board[x][y].setFigure(figure);
                list.add(figure);
                return;
            }
            case ELEPHANT: {
                Figures figure = new Elephant(x, y, mode);
                board[x][y].setFigure(figure);
                list.add(figure);
                return;
            }
            case ROOK: {
                Figures figure = new Rook(x, y, mode);
                board[x][y].setFigure(figure);
                list.add(figure);
                return;
            }
            case QUEEN: {
                Figures figure = new Queen(x, y, mode);
                board[x][y].setFigure(figure);
                list.add(figure);
                return;
            }
            case KING: {
                Figures figure = new King(x, y, mode);
                board[x][y].setFigure(figure);
                list.add(figure);
                return;
            }
        }
    }

    public void reDrawFigures() {
        ObservableList<Node> listNodes = drawPane.getChildren();
        for (int i = 0; i < listNodes.size(); i++) {
            boolean del = true;
            for (int j = 0; j < list.size(); j++) {
                if (listNodes.get(i) == list.get(j).getSprite()) {
                    del = false;
                    break;
                };
            }
            if (del && listNodes.get(i) instanceof ImageView) {
                listNodes.remove(i);
                i--;
            }
        }
    }

    public void drawFigures() {
        for (int i = 0; i < list.size(); i++) {
            ImageView iv = list.get(i).getSprite();
            if (drawPane.getChildren().contains(iv)) {
                iv = (ImageView) drawPane.getChildren().get(drawPane.getChildren().indexOf(iv));
                iv.setX(list.get(i).getX()*60-10);
                iv.setY((7-list.get(i).getY())*60-10);
            } else {
                drawPane.getChildren().add(iv);
            }
        }
    }

    public Cell[][] getBoard() {
        return board;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    public List<Figures> getList() {
        return list;
    }

    public void setList(ArrayList<Figures> list) {
        this.list = list;
    }

}
