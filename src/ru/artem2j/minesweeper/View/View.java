package ru.artem2j.minesweeper.View;

import ru.artem2j.minesweeper.Controller.Controller;
import ru.artem2j.minesweeper.Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Класс представления. Отрисовывает игровое поле вызывает методы у контроллера.

public class View extends JFrame implements IView {
    int cols;
    int rows;
    int imageSize;
    Model model;
    JPanel panel;
    JLabel bombLabel;
    JLabel timerLabel;
    JDialog dialog;
    Board board;
    Controller controller;


    public View(Model model, Controller controller){
        this.model = model;
        this.board = model.getBoard();
        this.controller = controller;
        this.cols = model.getCols();
        this.rows= model.getRows();
        this.imageSize = model.getImageSize();
        this.timerLabel = new JLabel();
        this.bombLabel = new JLabel();

    }

    @Override
    public void startView(){
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    @Override
    public void restartView(Board board, Model model){
        this.board = board;
        this.model = model;
        startView();
    }

    private void initLabel() {

        timerLabel.setText("Время: " +  controller.getTimerThread().getSeconds());
        bombLabel.setText("  Мины: " + (model.getBombs() - controller.getFlagsCount()));

        bombLabel.setPreferredSize(new Dimension(150, 40));
        timerLabel.setPreferredSize(new Dimension(150, 40));

        bombLabel.setLocation(160,0);
        add(bombLabel, BorderLayout.NORTH);

        timerLabel.setSize(new Dimension(150,40));
        bombLabel.setSize(new Dimension(150,40));

        bombLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD,20));
        timerLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD,20));



        timerLabel.setLocation(160,0);
        add(timerLabel, FlowLayout.LEFT);

    }
    @Override
    public void updateLabel(){
        bombLabel.setText("  Мины: " + (model.getBombs() - controller.getFlagsCount()));
        timerLabel.setText( "Время: " + controller.getTimerThread().getSeconds());
        bombLabel.repaint();
        timerLabel.repaint();

    }
    //Отрисовка игрового поля
    private void initPanel() {
        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = null;
                for (Cell cell: board.getCells()){
                        Coord coord = cell.getCoord();
                        if (!cell.isOpen()) {
                            if (cell.isFlagged()) img = (Image) CellState.FLAGGED.image;
                            else img = (Image) CellState.CLOSED.image;
                        } else {
                            img = (Image) cell.getState().image;
                        }
                        g.drawImage(img, coord.getX() * imageSize, coord.getY() * imageSize, this);
                    }
            }
        };
        //Обработка событий мыши
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (controller.getGameState() == GameState.PLAYED){
                    int x = e.getX()/imageSize;
                    int y = e.getY()/imageSize;
                    Cell cell = board.getCell(board.getCoord(x,y));
                    if (e.getButton() == MouseEvent.BUTTON1){
                        if (e.getClickCount() > 1) controller.doubleClick(cell);
                        controller.clickLeftButton(cell);
                    }
                    if (e.getButton() == MouseEvent.BUTTON3){
                        controller.clickRightButton(cell);
                    }
                }
                else if (e.getClickCount() > 1) controller.restartGame();
                updateLabel();
                panel.repaint();
            }
        });

        panel.setPreferredSize(new Dimension(cols * imageSize, rows * imageSize));
        add(panel);
    }

    //Диалоговое окно в конце игры
    public void endGame(){

        String message = " ";
        switch (controller.getGameState()){
            case BOMBED: message = "<html><p><h2>&nbsp &nbsp Вы проиглали.</h2></p>" +
                    " <p>&nbsp Используйте двойной клик для начала новой игры.</p></html>";break;
            case WIN: message = "<html><p><h2>&nbsp &nbsp Поздравляем, вы открыли  поле за " +
                    controller.getTimerThread().getSeconds() + " секунд </h2></p>" +
                    "<p>&nbsp Используйте двойной клик для начала новой игры.</p></html>";break;
        }

        dialog = new JDialog(this);

        JLabel dialogLable = new JLabel("",SwingConstants.CENTER);
        dialogLable.setText(message);
        dialog.add(dialogLable);
        dialog.setLocationRelativeTo(panel);
        dialog.setSize(280,200);
        dialog.setVisible(true);
    }

    private void initFrame ()
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        setResizable(false);
        setVisible(true);
        pack();
        setIconImage(getImageFromResource("icon"));
        if (controller.isFirstStart()){
            setLocationRelativeTo(null);
            controller.setFirstStart(false);
        }
    }


    //Установка изображений
    private void setImages() {
        for (CellState cellState: CellState.values())
            cellState.image = getImageFromResource(cellState.name().toLowerCase());
    }

    private Image getImageFromResource(String name) {
        String filename = "/img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
}
