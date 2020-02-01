package ru.artem2j.minesweeper.Model;

import java.util.ArrayList;
import java.util.List;

/*
* Класс игрового поля. Содержил коллекции координат и ячеек.
* Инициализируюся координаты и ячейки
* Реализованы геттеры и возвращение координат вокруг заданной координаты.
* */

public class Board {
    List<Coord> coords;
    List<Cell> cells;

    public Board(int cols, int rows){
        initBoard(cols, rows);
    }
    public void initBoard(int cols, int rows) {
        coords = new ArrayList<>();
        cells = new ArrayList<>();
        for (int x = 0; x < cols; x++)
            for (int y = 0; y < rows; y ++) {
                Coord coord = new Coord(x,y);
                coords.add(coord);
                cells.add(new Cell(CellState.ZERO, coord));
            }
    }

    public Coord getCoord(int x, int y){
        for (Coord coord: coords)
            if (coord.getX() == x && coord.getY() == y) return coord;

        return null;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public Cell getCell(Coord coord){
        for (Cell cell: cells){
            if (cell.getCoord() == coord) return cell;
        }
        return null;
    }
    public List<Coord> getAroundCoords(Coord coord){
        List<Coord> around = new ArrayList<>();
        for (int y = coord.getY() - 1; y <= coord.getY() + 1; y++)
            for (int x = coord.getX() - 1; x <= coord.getX() + 1; x++) {
                if (coord.getX() == x && coord.getY() == y) continue;
                Coord aroundCoord = getCoord(x, y);
                if (aroundCoord != null) around.add(aroundCoord);
            }
        return  around;
    }
}
