package ru.artem2j.minesweeper.View;

import ru.artem2j.minesweeper.Model.Board;
import ru.artem2j.minesweeper.Model.Model;

public interface IView {
    void startView();

    void restartView(Board board, Model model);

    void updateLabel();

    //void endGame();
}
