package com.github.sebastiancegielka.checkers;

import com.github.sebastiancegielka.checkers.controller.GameController;
import com.github.sebastiancegielka.checkers.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        GameController controller = new GameController();
        ConsoleView game = new ConsoleView(controller);
        game.run();
    }
}
