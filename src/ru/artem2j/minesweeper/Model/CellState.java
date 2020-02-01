package ru.artem2j.minesweeper.Model;

//Перечисление состояний ячейки для отображения на игровом поле

public enum CellState {
    ZERO,
    NUM1,
    NUM2,
    NUM3,
    NUM4,
    NUM5,
    NUM6,
    NUM7,
    NUM8,
    BOMB,
    BOMBED,
    CLOSED,
    FLAGGED,
    INFORM,
    NO_BOMB,
    OPENED;

    public Object image;

}
