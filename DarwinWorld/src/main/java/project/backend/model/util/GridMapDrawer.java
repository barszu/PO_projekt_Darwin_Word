package project.backend.model.util;

import project.backend.model.Boundary;
import project.backend.model.maps.WorldMap;
import project.backend.model.models.Vector2d;
import project.backend.model.models.WorldElement;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class GridMapDrawer {
    private static final int CELL_WIDTH = 20;
    private static final int CELL_HEIGHT = 20;

    private final GridPane mapGrid;
    private final WorldMap map;

    private Boundary boundary;
    public void updateBoundary() {
        this.boundary = map.getCurrentBounds();
    }
    public GridMapDrawer(GridPane mapGrid, WorldMap map) {
        this.mapGrid = mapGrid;
        this.map = map;
    }

    public void draw() {
        updateBoundary();
        System.out.print(map);

        clearGrid();
        setCellsSizes();
        drawAxis();
        drawAllWorldElements();
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void setCellsSizes() {
        int gridHeight = boundary.height() + 1;
        int gridWidth = boundary.width() + 1;
        for (int i = 0; i < gridHeight; i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }
        for (int i = 0; i < gridWidth; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }
    }


    private void drawAxis() {
        addToMapGrid("y/x", 0, 0);
        for (int x = boundary.lowerLeft().getX(); x <= boundary.upperRight().getX(); x++) {
            addToMapGrid(Integer.toString(x), 1-boundary.lowerLeft().getX() + x, 0);
        }
        for (int y = boundary.upperRight().getY(); y >= boundary.lowerLeft().getY(); y--) {
            addToMapGrid(Integer.toString(y), 0, boundary.upperRight().getY() - y + 1);
        }
    }

    private void drawAllWorldElements() {
        for (int x = boundary.lowerLeft().getX(); x <= boundary.upperRight().getX(); x++) {
            for (int y = boundary.lowerLeft().getY(); y <= boundary.upperRight().getY(); y++) {
                WorldElement element = map.objectAt(new Vector2d(x, y));
                String labelText = element != null ? element.toString() : "";
                addToMapGrid(labelText,
                        1 - boundary.lowerLeft().getX() + x,
                        1 + boundary.upperRight().getY() -  y
                );
            }
        }
    }


    private void addToMapGrid(String text, int columnIndex, int rowIndex) {
        Label label = new Label(text);
        GridPane.setHalignment(label, HPos.CENTER);
        mapGrid.add(label, columnIndex, rowIndex );
    }

}
