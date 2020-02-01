package ru.artem2j.minesweeper.Model;

//Класс ячейки имеет состояния открыта/закрыта, помечена/не ппомечена флагом, координаты
// и состоянием для отображения

public class Cell {
    private boolean isOpen;
    private boolean isFlagged;
    private Coord coord;
    private CellState state;


    public Cell(CellState state, Coord coord) {
        this.isOpen = false;
        this.isFlagged = false;
        this.state = state;
        this.coord = coord;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void toggleFlagged(){isFlagged = !isFlagged;}
    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public Coord getCoord() {
        return coord;
    }
}
