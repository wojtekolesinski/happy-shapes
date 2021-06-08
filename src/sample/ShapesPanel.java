package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.util.Arrays;


public class ShapesPanel extends Canvas {

    public final static int RECTANGLE = 0;
    public final static int TRIANGLE = 1;
    public final static int OVAL = 2;

    private int mode;
    private double x1;
    private double x2;
    private double y1;
    private double y2;
    private IntegerProperty area = new SimpleIntegerProperty();
    public Text text = new Text("0 px\u00B2");
    private StringProperty areaLabel = new SimpleStringProperty("0 px");

    public String getAreaLabel() {
        return areaLabel.get();
    }

    public StringProperty areaLabelProperty() {
        return areaLabel;
    }

    public void setAreaLabel(String areaLabel) {
        this.areaLabel.set(areaLabel);
    }

    public ShapesPanel(double v, double v1) {
        super(v, v1);

        GraphicsContext g = getGraphicsContext2D();
        g.setLineWidth(5);
        g.setStroke(Color.RED);

        area.addListener(
                (src, oldval, newval) -> {
                    text.setText(String.format("%d px\u00B2", src.getValue().intValue()));
                }
        );

        setOnMouseDragged(e -> {
            x2 = e.getX();
            y2 = e.getY();
            drawShape();
        });

        setOnMousePressed(e -> {
            x1 = e.getX();
            y1 = e.getY();
        });

    }

    private void drawShape() {
        GraphicsContext g = getGraphicsContext2D();
        g.clearRect(0, 0, getWidth(), getHeight());
        boolean flipped = y1 > y2;

        double ULx = Math.min(x1, x2);
        double ULy = Math.min(y1, y2);
        double LRx = Math.max(x1, x2);
        double LRy = Math.max(y1, y2);

        double width = LRx - ULx;
        double height = LRy - ULy;

        setArea(calculateArea());

        switch (mode) {
            case RECTANGLE -> g.strokeRect(ULx, ULy, width, height);
            case TRIANGLE -> g.strokePolygon(
                                new double[]{ULx, LRx, ULx + width / 2},
                                flipped ? new double[]{ULy, ULy, LRy} : new double[]{LRy, LRy, ULy},
                                3);
            case OVAL -> g.strokeOval(ULx, ULy, width, height);
            default -> throw new IllegalArgumentException();
        }
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    private int calculateArea() {
        return switch (mode) {
            case RECTANGLE -> (int) (Math.abs(x1 - x2)  * Math.abs(y1 - y2));
            case TRIANGLE -> (int) (Math.abs(x1 - x2) * Math.abs(y1 - y2) / 2);
            case OVAL -> (int) (Math.abs(x1 - x2) * Math.abs(y1 - y2) * Math.PI / 4);
            default -> 0;
        };
    }

    public int getArea() {
        return area.get();
    }

    public IntegerProperty areaProperty() {
        return area;
    }

    public void setArea(int area) {
        this.area.set(area);
    }
}
