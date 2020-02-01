package ru.artem2j.minesweeper.Model;

import ru.artem2j.minesweeper.Controller.Controller;

//Класс бомбы. Вызывает в контроллере метод завершения игры

public class Bomb implements IBomb {
    Controller controller;

    public Bomb(Controller controller) {
        this.controller = controller;
    }
    @Override
    public void detonate(){
        controller.endGame();
    }
}
