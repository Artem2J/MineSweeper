package ru.artem2j.minesweeper.Model;

//Класс координат для обращения к ячейкам игрового поля

public class Coord {
    int x;
    int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
