package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.Arrays;
import java.util.List;

public class Controller {

    private Label label;


    public Controller() {

    }

    public Scene getView() {
        HBox root = new HBox();
        ShapesPanel shapesPanel = new ShapesPanel(600, 300);
        shapesPanel.getStyleClass().add("canvas");
        VBox buttons = new VBox();
        buttons.setSpacing(5);
        buttons.setStyle("-fx-padding : 10");

        label = new Label();
        label.setStyle("-fx-font-weight: bold");
        label.setFont(new Font(40));
        label.setAlignment(Pos.BASELINE_RIGHT);
        label.setMinWidth(300);
        label.setMinHeight(120);
        label.textProperty().bind(shapesPanel.text.textProperty());

        ToggleButton rectangleButton = new ToggleButton("Rectangle");
        ToggleButton triangleButton = new ToggleButton("Triangle");
        ToggleButton ovalButton = new ToggleButton("Oval");

        List<ToggleButton> buttonsList = Arrays.asList(rectangleButton, triangleButton, ovalButton);
        buttonsList.forEach(b -> {
            b.setMinHeight(50);
            b.setMinWidth(400);
        });
        resetButtons(buttonsList);
        rectangleButton.setSelected(true);

        rectangleButton.setOnAction(e -> {
            shapesPanel.setMode(ShapesPanel.RECTANGLE);
            resetButtons(buttonsList);
            rectangleButton.setSelected(true);
        });
        triangleButton.setOnAction(e -> {
            shapesPanel.setMode(ShapesPanel.TRIANGLE);
            resetButtons(buttonsList);
            triangleButton.setSelected(true);
        });
        ovalButton.setOnAction(e -> {
            shapesPanel.setMode(ShapesPanel.OVAL);
            resetButtons(buttonsList);
            ovalButton.setSelected(true);
        });


        buttons.getChildren().addAll(label, rectangleButton, triangleButton, ovalButton);
        root.getChildren().addAll(shapesPanel, buttons);
        root.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return new Scene(root);
    }

    private void resetButtons(List<ToggleButton> buttons) {
        buttons.forEach(b -> b.setSelected(false));
    }
}
