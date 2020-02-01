package ru.artem2j.minesweeper.Model;

import ru.artem2j.minesweeper.Controller.Controller;

import java.util.HashMap;
import java.util.Map;

//Класс модели. Содержит параметры игрового поля
//Создает коллекцию с бомбами <Ячейка, Бомба>. Создает игровое поле.
// Передает контроллет в Бомбы для взаимодействия.

public class Model {

    int cols = 8;
    int rows = 8;
    int bombs = 10;
    int imageSize = 50;
    Controller controller;

    Map<Cell, Bomb> bombMap;
    Board board;

    public Model(Controller controller) {
        bombMap = new HashMap<>();
        board = new Board(cols, rows);
        this.controller = controller;
    }

    public Bomb getBomb(Cell cell){
        return bombMap.get(cell);
    }

    public void setBomb(Cell cell){
        bombMap.put(cell, new Bomb(controller));
    }

    public Board getBoard() {
        return board;
    }

    public Map<Cell, Bomb> getBombMap() {
        return bombMap;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public int getBombs() {
        return bombs;
    }

    public int getImageSize() {
        return imageSize;
    }
}
