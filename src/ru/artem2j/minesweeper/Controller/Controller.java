package ru.artem2j.minesweeper.Controller;


import ru.artem2j.minesweeper.Model.*;
import ru.artem2j.minesweeper.View.View;

import java.util.List;
import java.util.Random;


//Контроллер, в нем реализована логика игры
//Создает представление и модель, и управляет ими.
//Передает себя в конструктор для обратного взаимодействия.


public  class Controller implements IController {

    Model model;
    View view;
    Board board;
    GameState gameState;

    int freeCell;
    int flagsCount;
    boolean firstStart = true;
    boolean firstClick;
    MSTimer timerThread;

    static Random random = new Random();

    public Controller(){ startGame();}

    @Override
    public void startGame() {

        initGame();
        view = new View(model,this);
        timerThread = new MSTimer(view);
        view.startView();
    }

    @Override
    public void restartGame() {
        flagsCount = 0;
        initGame();
        timerThread = new MSTimer(view);
        view.restartView(board,model);

    }

    private void initGame() {
        model = new Model(this);
        board = model.getBoard();
        firstClick = true;
        gameState = GameState.PLAYED;
        placeBomb();
        placeNums();

    }




    // Заполня ячейки поля бомбами и цифрами

    void placeBomb() {
    int count = model.getBombs();
    freeCell = model.getBoard().getCells().size() - count;
    while (count > 0){
        Coord coord;
        if ((coord = board.getCoord(random.nextInt(model.getCols()), random.nextInt(model.getRows()))) != null);
        Cell cell = board.getCell(coord);
        if (!model.getBombMap().containsKey(cell)){
            model.setBomb(cell);
            board.getCell(coord).setState(CellState.BOMB);
            count--;
        }
    }
}

    private void placeNums() {
        for (Cell cell: board.getCells()){
            if (!model.getBombMap().containsKey(cell)){
                int count = 0;
                List<Coord> coordList = board.getAroundCoords(cell.getCoord());
                for (Coord around: coordList)
                    if (model.getBombMap().containsKey(board.getCell(around)))count++;
                board.getCell(cell.getCoord()).setState(CellState.values()[count]);
        }}
    }




    // Обрабатывается нажатие клафишь мыши
    @Override
    public void clickLeftButton(Cell cell) {
            if (firstClick){
                timerThread.start();
                firstClick = false;
            }
        openCell(cell);
    }

    @Override
    public void clickRightButton(Cell cell) {
        if (!cell.isOpen()){
            setCellFlagged(cell);
        }
    }

    @Override
    public void doubleClick(Cell cell) {
        int flagCount = 0;
        if (cell.isOpen()){
            for (Coord around: board.getAroundCoords(cell.getCoord())){
                if (board.getCell(around).isFlagged()) flagCount++;
            }
            if (cell.getState().ordinal() == flagCount)openCellsAround(cell);

        }
    }




    //Открытие ячеек
    private void openCell(Cell cell) {
        if (!cell.isFlagged() && !cell.isOpen()) {
            if (cell.getState() == CellState.BOMB)bombed(cell);
            cell.setOpen(true);
            freeCell--;
            if (freeCell == 0) winGame();
            if (cell.getState() == CellState.ZERO) {
                openCellsAround(cell);
            }
        }
    }

    private void openCellsAround(Cell cell) {
        for (Coord around : board.getAroundCoords(cell.getCoord()))
            if (!board.getCell(around).isOpen()) openCell(board.getCell(around));
    }




    //Обработка выигрыши и проигрыша
    private void winGame() {
        gameState = GameState.WIN;
        timerThread.stop();
        view.endGame();
    }

    private void bombed(Cell cell) {
        cell.setState(CellState.BOMBED);
        model.getBomb(cell).detonate();
    }

    @Override
    public void endGame() {
        gameState = GameState.BOMBED;
        openBoard();
        timerThread.stop();
        view.endGame();
    }



    //Открытие поля
    private void openBoard() {
        for (Cell cell: board.getCells()){
            if (!cell.isOpen()){
                if (cell.isFlagged() && cell.getState() != CellState.BOMB) {
                    cell.toggleFlagged();
                    cell.setState(CellState.NO_BOMB);
                    cell.setOpen(true);
                }
                else if (!cell.isFlagged() && cell.getState() == CellState.BOMB)cell.setOpen(true);

            }
        }
    }


    private void setCellFlagged(Cell cell) {
        if (!cell.isFlagged())flagsCount++;
        else flagsCount--;
        cell.toggleFlagged();
    }



    @Override
    public int getFlagsCount() {
        return flagsCount;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public boolean isFirstStart() {
        return firstStart;
    }

    @Override
    public void setFirstStart(boolean firstStart) {
        this.firstStart = firstStart;
    }

    @Override
    public MSTimer getTimerThread() {
        return timerThread;
    }
}
