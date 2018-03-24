package com.lg.command.executors;

import com.lg.command.ExecuteCommand;
import com.lg.command.domain.entities.GameSession;
import com.lg.command.domain.valueobjects.ProvideBackgammonConfig;
import com.lg.command.es.GameRepository;
import com.lg.command.messages.RequestNewGame;

import javax.inject.Inject;
import java.util.UUID;

public class RequestNewGameCommandExecutor implements ExecuteCommand<RequestNewGame> {

    private GameRepository repo;
    private ProvideBackgammonConfig config;

    @Inject
    public RequestNewGameCommandExecutor(GameRepository repo, ProvideBackgammonConfig config) {
        this.repo = repo;
        this.config = config;
    }

    @Override
    public void execute(RequestNewGame command) throws Exception {
        GameSession newGame = GameSession.startNewGameSession("game-" + UUID.randomUUID().toString(), command.getPlayerId(), config);
        this.repo.save(newGame);
    }
}
