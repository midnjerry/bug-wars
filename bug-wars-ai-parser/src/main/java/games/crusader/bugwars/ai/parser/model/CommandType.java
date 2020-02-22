package games.crusader.bugwars.ai.parser.model;

public enum CommandType {
    MOVE_FORWARD("moveForward"),
    MOVE_BACK("moveBack"),
    TURN_RIGHT("turnRight"),
    TURN_LEFT("turnLeft"),
    ATTACK("attack"),
    DELAY("delay"),
    GOTO("goto"),
    IF_EMPTY("ifEmpty"),
    IF_ENEMY("ifEnemy"),
    IF_FRIEND("ifFriend"),
    IF_RANDOM("ifRandom"),
    IF_EDIBLE("ifEdible"),
    IF_SUCCESSFUL("ifSuccessful"),
    IF_WALL("ifWall"),
    LOOK("look"),
    EAT("eat");

    private String commandText;

    CommandType(String text){
        commandText = text;
    }

    public String getCommandText(){
        return commandText;
    }
}

