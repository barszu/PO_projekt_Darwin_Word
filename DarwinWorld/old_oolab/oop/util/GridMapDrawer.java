package agh.ics.oop.util;

import project.backend.model.maps.mapsUtil.RectangleBoundary;
import agh.ics.oop.model.WorldMap_able;
import project.backend.model.models.Vector2d;
import project.backend.model.sprites.WorldElementable;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class GridMapDrawer {
    private static final int CELL_WIDTH = 20;
    private static final int CELL_HEIGHT = 20;

    private final GridPane mapGrid;
    private final WorldMap_able map;

    private RectangleBoundary rectangleBoundary;
    public void updateBoundary() {
        this.rectangleBoundary = map.getCurrentBounds();
    }
    public GridMapDrawer(GridPane mapGrid, WorldMap_able map) {
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
        int gridHeight = rectangleBoundary.height() + 1;
        int gridWidth = rectangleBoundary.width() + 1;
        for (int i = 0; i < gridHeight; i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }
        for (int i = 0; i < gridWidth; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }
    }


    private void drawAxis() {
        addToMapGrid("y/x", 0, 0);
        for (int x = rectangleBoundary.lowerLeft().getX(); x <= rectangleBoundary.upperRight().getX(); x++) {
            addToMapGrid(Integer.toString(x), 1- rectangleBoundary.lowerLeft().getX() + x, 0);
        }
        for (int y = rectangleBoundary.upperRight().getY(); y >= rectangleBoundary.lowerLeft().getY(); y--) {
            addToMapGrid(Integer.toString(y), 0, rectangleBoundary.upperRight().getY() - y + 1);
        }
    }

    private void drawAllWorldElements() {
        for (int x = rectangleBoundary.lowerLeft().getX(); x <= rectangleBoundary.upperRight().getX(); x++) {
            for (int y = rectangleBoundary.lowerLeft().getY(); y <= rectangleBoundary.upperRight().getY(); y++) {
                WorldElementable element = map.objectAt(new Vector2d(x, y));
                String labelText = element != null ? element.toString() : "";
                addToMapGrid(labelText,
                        1 - rectangleBoundary.lowerLeft().getX() + x,
                        1 + rectangleBoundary.upperRight().getY() -  y
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
