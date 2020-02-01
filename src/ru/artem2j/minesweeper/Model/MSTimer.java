package ru.artem2j.minesweeper.Model;

import ru.artem2j.minesweeper.View.View;

//Реализует отждельный поток с таймером. Обновляет Label в представлении каждую секунду

public class MSTimer extends Thread implements Runnable{
    int seconds = 0;
    View view;
    public MSTimer(View view){
        this.view = view;
    }

    @Override
    public void run() {

        while (true){
            seconds++;
            try {
                new Thread().sleep(1000);
                view.updateLabel();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getSeconds() {
        return seconds;
    }
}
