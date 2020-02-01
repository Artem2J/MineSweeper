package ru.artem2j.minesweeper.Controller;

import ru.artem2j.minesweeper.Model.Cell;
import ru.artem2j.minesweeper.Model.GameState;
import ru.artem2j.minesweeper.Model.MSTimer;

public interface IController {
    void startGame();

    void restartGame();

    void clickLeftButton(Cell cell);

    void clickRightButton(Cell cell);

    void doubleClick(Cell cell);

    void endGame();

    int getFlagsCount();

    GameState getGameState();

    boolean isFirstStart();

    void setFirstStart(boolean firstStart);

    MSTimer getTimerThread();
}
