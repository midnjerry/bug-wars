package games.crusader.bugwars.ai.parser.service;

import games.crusader.bugwars.ai.parser.exception.*;
import games.crusader.bugwars.ai.parser.model.Command;
import games.crusader.bugwars.ai.parser.model.CommandType;
import games.crusader.bugwars.ai.parser.model.ParseToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class Parser {
    private static Command ATTACK = new Command(CommandType.ATTACK);
    private static Command DELAY = new Command(CommandType.DELAY);
    private static Command EAT = new Command(CommandType.EAT);
    private static Command TURN_RIGHT = new Command(CommandType.TURN_RIGHT);
    private static Command TURN_LEFT = new Command(CommandType.TURN_LEFT);
    private static Command MOVE_FORWARD = new Command(CommandType.MOVE_FORWARD);
    private static Command MOVE_BACK = new Command(CommandType.MOVE_BACK);

    public static final String LABEL_VALIDATION_REGEX = "^[\\w]+$";
    private Pattern pattern = Pattern.compile(LABEL_VALIDATION_REGEX);


    public List<Command> parse(List<ParseToken> tokens) {
        if (tokens == null) {
            throw new InvalidInputException("Error: no tokens to parse");
        }

        List<Command> commands = new ArrayList<>();
        Map<String, Integer> labelMap = new HashMap<>();

        for (ParseToken token : tokens) {
            String label = token.getLabel();
            if (label != null) {
                validateLabel(label, labelMap);
                labelMap.put(label, commands.size());
            }

            String command = token.getCommand();
            validateCommand(command);
            switch (command) {
                case ("attack"):
                    commands.add(ATTACK);
                    commands.add(DELAY);
                    commands.add(DELAY);
                    commands.add(DELAY);
                    break;
                case ("eat"):
                    commands.add(DELAY);
                    commands.add(DELAY);
                    commands.add(DELAY);
                    commands.add(EAT);
                    break;
                case ("turnRight"):
                    commands.add(DELAY);
                    commands.add(DELAY);
                    commands.add(TURN_RIGHT);
                    break;
                case ("turnLeft"):
                    commands.add(DELAY);
                    commands.add(DELAY);
                    commands.add(TURN_LEFT);
                    break;
                case ("moveForward"):
                    commands.add(DELAY);
                    commands.add(MOVE_FORWARD);
                    break;
                case ("moveBack"):
                    commands.add(DELAY);
                    commands.add(DELAY);
                    commands.add(DELAY);
                    commands.add(MOVE_BACK);
                    break;
                case ("goto"):
                    commands.add(createCommandWithParameter(token, CommandType.GOTO, labelMap));
                    break;
                case ("ifEmpty"):
                    commands.add(createCommandWithParameter(token, CommandType.IF_EMPTY, labelMap));
                    break;
                case ("ifSuccessful"):
                    commands.add(createCommandWithParameter(token, CommandType.IF_SUCCESSFUL, labelMap));
                    break;
                case ("ifEnemy"):
                    commands.add(createCommandWithParameter(token, CommandType.IF_ENEMY, labelMap));
                    break;
                case ("ifEdible"):
                    commands.add(createCommandWithParameter(token, CommandType.IF_EDIBLE, labelMap));
                    break;
                case ("ifRandom"):
                    commands.add(createCommandWithParameter(token, CommandType.IF_RANDOM, labelMap));
                    break;
                case ("ifWall"):
                    commands.add(createCommandWithParameter(token, CommandType.IF_WALL, labelMap));
                    break;
                case ("ifFriend"):
                    commands.add(createCommandWithParameter(token, CommandType.IF_FRIEND, labelMap));
                    break;
                default:
                    throw new InvalidCommandException("Unknown Command: " + command);
            }
        }
        return commands;
    }

    private void validateCommand(String command) {
        if (command == null) {
            throw new MissingCommandException("No Command Defined.");
        }
    }

    private void validateLabel(String label, Map<String, Integer> labelMap) {
        if (!pattern.matcher(label).matches()) {
            throw new InvalidLabelException("Label must be alphanumeric");
        }

        if (labelMap.containsKey(label)) {
            throw new DuplicateLabelException("Error: " + label + " has already been defined.");
        }
    }

    private Command createCommandWithParameter(ParseToken token, CommandType commandType, Map<String, Integer> labelMap) {
        if (!labelMap.containsKey(token.getParameter())) {
            throw new LabelNotDefinedException("Label: " + token.getParameter() + " not defined.");
        }
        Integer parameter = labelMap.get(token.getParameter());
        return new Command(commandType, parameter);
    }
}
