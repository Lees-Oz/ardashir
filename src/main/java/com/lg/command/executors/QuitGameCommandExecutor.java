package com.lg.command.executors;

import com.lg.command.ExecuteCommand;
import com.lg.command.es.GameRepository;
import com.lg.command.messages.QuitGame;

import javax.inject.Inject;

public class QuitGameCommandExecutor implements ExecuteCommand<QuitGame> {

    private GameRepository repo;

    @Inject
    public QuitGameCommandExecutor(GameRepository repo) {
        this.repo = repo;
    }

    @Override
    public void execute(QuitGame command) throws Exception {
//        Game game = repo.get(command.getGameId().toString());
//        game.quitGame(command.getPlayerId());
//        this.repo.save(game);
        System.out.println(command.toString());
    }
}
