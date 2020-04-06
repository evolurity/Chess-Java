package src.command;

import src.chesstask.Logic;

public abstract class Command {
    protected Logic logic;
    public void boardClick(int x, int y){
        logic.boardClick(x,y);
    }
}
