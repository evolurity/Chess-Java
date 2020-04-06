package src.chesstask;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import src.chesstask.Logic;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Pane pane;

    @FXML
    private Stage stage;

    private Pane drawPane;

    private Logic logic;

    private void drawBoard(boolean own) {
        ObservableList<Node> elems = FXCollections.observableArrayList();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                elems.add(createCell(j, i, ((i + j + 1) % 2)));
            }
        }
        drawPane.getChildren().addAll(elems);
    }
    @FXML
    void save(ActionEvent event) {
        logic.saveFromJson();
    }

    @FXML
    void load(ActionEvent event) {
        logic.readFromJson();
    }

    private Node createCell(int x, int y, int color) {
        Rectangle rect = new Rectangle();
        rect.setX(x * 60);
        rect.setY(y * 60);
        if (color == 0) {
            rect.setFill(Color.LIGHTGREY);
        } else {
            rect.setFill(Color.SIENNA);
        }
        rect.setWidth(60);
        rect.setHeight(60);
        return rect;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        drawPane = new Pane();
        drawPane.setOnMouseClicked((MouseEvent event) -> {
            logic.command.boardClick((int)event.getX(), (int)event.getY());
        });

        drawPane.setPrefSize(480, 480);
        drawPane.setLayoutX(30);
        drawPane.setLayoutY(30);
        pane.getChildren().add(drawPane);
        drawBoard(true);
        logic = new Logic(stage);
        logic.generate(true, drawPane);
        logic.start();
    }

}
