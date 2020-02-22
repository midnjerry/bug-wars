package games.crusader.bugwars.ai.parser.service;

import games.crusader.bugwars.ai.parser.exception.*;
import games.crusader.bugwars.ai.parser.model.Command;
import games.crusader.bugwars.ai.parser.model.CommandType;
import games.crusader.bugwars.ai.parser.model.ParseToken;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParserTest {
    Parser codeParser = new Parser();

    private List<ParseToken> generateTokenList(String... commands) {
        List<ParseToken> tokenList = new ArrayList<>();
        for (String command : commands) {
            tokenList.add(new ParseToken(null, command, null));
        }
        return tokenList;
    }

    @Test(expected = InvalidInputException.class)
    public void parse_WhenNullInputThrowsError() {
        codeParser.parse(null);
    }

    @Test
    public void parse_InputIsEmpty_returnsEmptyCommandList() {

    }

    @Test
    public void parse_WhenInputEmpty_ReturnsEmptyCommandList() {
        assertEquals(Collections.EMPTY_LIST, codeParser.parse(Collections.EMPTY_LIST));
    }

    @Test
    public void parse_WhenInputHasAttack_ReturnAttackAndThreeDelays() {
        List<ParseToken> input = generateTokenList("attack");
        List<Command> expected = Arrays.asList(
                new Command(CommandType.ATTACK),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY));

        assertEquals(expected, codeParser.parse(input));
    }

    @Test
    public void parse_WhenInputHasEat_ReturnThreeDelaysAndEat() {
        List<ParseToken> input = generateTokenList("eat");
        List<Command> expected = Arrays.asList(
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.EAT));

        assertEquals(expected, codeParser.parse(input));
    }

    @Test
    public void parse_WhenInputHasTurnRight_ReturnTwoDelaysAnd_TURN_RIGHT() {
        List<ParseToken> input = generateTokenList("turnRight");
        List<Command> expected = Arrays.asList(
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.TURN_RIGHT));

        assertEquals(expected, codeParser.parse(input));
    }

    @Test
    public void parse_WhenInputHasTurnLeft_ReturnTwoDelaysAnd_TURN_LEFT() {
        List<ParseToken> input = generateTokenList("turnLeft");
        List<Command> expected = Arrays.asList(
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.TURN_LEFT));

        assertEquals(expected, codeParser.parse(input));
    }

    @Test
    public void parse_WhenInputHasMoveForward_ReturnOneDelaysAnd_MOVE_FORWARD() {
        List<ParseToken> input = generateTokenList("moveForward");
        List<Command> expected = Arrays.asList(
                new Command(CommandType.DELAY),
                new Command(CommandType.MOVE_FORWARD));
        assertEquals(expected, codeParser.parse(input));
    }

    @Test
    public void parse_WhenInputHasMoveBack_ReturnThreeDelaysAnd_MOVE_BACK() {
        List<ParseToken> input = generateTokenList("moveBack");
        List<Command> expected = Arrays.asList(
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.MOVE_BACK));
        assertEquals(expected, codeParser.parse(input));
    }

    @Test
    public void parse_twoLinesOfMoveForward() {
        List<ParseToken> input = generateTokenList("moveForward", "moveForward");
        List<Command> expected = Arrays.asList(
                new Command(CommandType.DELAY),
                new Command(CommandType.MOVE_FORWARD),
                new Command(CommandType.DELAY),
                new Command(CommandType.MOVE_FORWARD));
        assertEquals(expected, codeParser.parse(input));
    }

    @Test
    public void parse_GOTO_pointsToIndex0() {
        List<ParseToken> input = Arrays.asList(
                new ParseToken("START", "moveForward", null),
                new ParseToken(null, "goto", "START"));
        List<Command> expected = Arrays.asList(
                new Command(CommandType.DELAY),
                new Command(CommandType.MOVE_FORWARD),
                new Command(CommandType.GOTO, 0));
        assertEquals(expected, codeParser.parse(input));
    }

    @Test
    public void parse_inputHasIfEmpty_pointsToAppropriateIndex() {
        List<ParseToken> input = Arrays.asList(
                new ParseToken(null, "attack", null),
                new ParseToken("START", "moveForward", null),
                new ParseToken(null, "ifEmpty", "START")
        );
        List<Command> expected = Arrays.asList(
                new Command(CommandType.ATTACK),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.MOVE_FORWARD),
                new Command(CommandType.IF_EMPTY, 4));
        assertEquals(expected, codeParser.parse(input));
    }

    @Test
    public void parse_inputHasThreeTokens_returnsParsedCommandList() {
        List<ParseToken> input = generateTokenList("moveForward", "attack", "moveForward");
        List<Command> expected = Arrays.asList(
                new Command(CommandType.DELAY),
                new Command(CommandType.MOVE_FORWARD),
                new Command(CommandType.ATTACK),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.MOVE_FORWARD));
        assertEquals(expected, codeParser.parse(input));
    }

    @Test
    public void parse_ifSuccessful_redirectsToAppropriatePlace() {
        List<ParseToken> input = Arrays.asList(
                new ParseToken(null, "attack", null),
                new ParseToken("START", "moveForward", null),
                new ParseToken(null, "ifSuccessful", "START")
        );
        List<Command> expected = Arrays.asList(
                new Command(CommandType.ATTACK),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.MOVE_FORWARD),
                new Command(CommandType.IF_SUCCESSFUL, 4));
        assertEquals(expected, codeParser.parse(input));
    }

    @Test
    public void parse_ifEnemy_redirectsToAppropriatePlace() {
        List<ParseToken> input = Arrays.asList(
                new ParseToken(null, "attack", null),
                new ParseToken("START", "moveForward", null),
                new ParseToken(null, "ifEnemy", "START")
        );
        List<Command> expected = Arrays.asList(
                new Command(CommandType.ATTACK),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.MOVE_FORWARD),
                new Command(CommandType.IF_ENEMY, 4));
        assertEquals(expected, codeParser.parse(input));
    }

    @Test
    public void parse_ifEdible_redirectsToAppropriatePlace() {
        List<ParseToken> input = Arrays.asList(
                new ParseToken(null, "attack", null),
                new ParseToken("START", "moveForward", null),
                new ParseToken(null, "ifEdible", "START")
        );
        List<Command> expected = Arrays.asList(
                new Command(CommandType.ATTACK),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.MOVE_FORWARD),
                new Command(CommandType.IF_EDIBLE, 4));
        assertEquals(expected, codeParser.parse(input));
    }

    @Test
    public void parse_ifRandom_redirectsToAppropriatePlace() {
        List<ParseToken> input = Arrays.asList(
                new ParseToken(null, "attack", null),
                new ParseToken("START", "moveForward", null),
                new ParseToken(null, "ifRandom", "START")
        );
        List<Command> expected = Arrays.asList(
                new Command(CommandType.ATTACK),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.MOVE_FORWARD),
                new Command(CommandType.IF_RANDOM, 4));
        assertEquals(expected, codeParser.parse(input));
    }

    @Test
    public void parse_ifFriend_redirectsToAppropriatePlace() {
        List<ParseToken> input = Arrays.asList(
                new ParseToken(null, "attack", null),
                new ParseToken("START", "moveForward", null),
                new ParseToken(null, "ifFriend", "START")
        );
        List<Command> expected = Arrays.asList(
                new Command(CommandType.ATTACK),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.MOVE_FORWARD),
                new Command(CommandType.IF_FRIEND, 4));
        assertEquals(expected, codeParser.parse(input));
    }

    @Test
    public void parse_ifWall_redirectsToAppropriatePlace() {
        List<ParseToken> input = Arrays.asList(
                new ParseToken(null, "attack", null),
                new ParseToken("START", "moveForward", null),
                new ParseToken(null, "ifWall", "START")
        );
        List<Command> expected = Arrays.asList(
                new Command(CommandType.ATTACK),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.MOVE_FORWARD),
                new Command(CommandType.IF_WALL, 4));
        assertEquals(expected, codeParser.parse(input));
    }

    @Test(expected = LabelNotDefinedException.class)
    public void parse_LabelDeclarationAfterInvocation_ThrowsError() {
        List<ParseToken> input = Arrays.asList(
                new ParseToken("start", "moveForward", null),
                new ParseToken(null, "ifWall", "move"),
                new ParseToken(null, "moveForward", null),
                new ParseToken("move", "moveForward", null),
                new ParseToken(null, "goto", "start"));
        codeParser.parse(input);
    }

    @Test(expected = DuplicateLabelException.class)
    public void parse_DuplicateLabelsThrowErrors_ThrowsError() {
        List<ParseToken> input = Arrays.asList(
                new ParseToken("start", "moveForward", null),
                new ParseToken("start", "moveForward", null),
                new ParseToken(null, "goto", "start"));
        codeParser.parse(input);
    }

    @Test(expected = InvalidLabelException.class)
    public void parse_LabelIsEmptyString_ThrowsError() {
        List<ParseToken> input = Arrays.asList(
                new ParseToken("", "moveForward", null));
        codeParser.parse(input);
    }

    @Test(expected = InvalidLabelException.class)
    public void parse_LabelIsNotAlphaNumeric_ThrowsError() {
        List<ParseToken> input = Arrays.asList(
                new ParseToken("!asdf", "moveForward", null));
        codeParser.parse(input);
    }

    @Test (expected= MissingCommandException.class)
    public void parse_LabelInMiddle_ThrowsError(){
        List<ParseToken> input = Arrays.asList(
                new ParseToken("START", null, null));
        codeParser.parse(input);
    }

    @Test (expected= InvalidCommandException.class)
    public void parse_UnknownCommand_ThrowsError(){
        List<ParseToken> input = Arrays.asList(
                new ParseToken("START", "notACommand", null));
        codeParser.parse(input);
    }

    @Test
    public void parse_labelMatchesCommand_redirectsAppropriately(){
        List<ParseToken> input = Arrays.asList(
                new ParseToken("attack", "attack", null),
                new ParseToken(null, "ifRandom", "attack"));
        List<Command> expected = Arrays.asList(
                new Command(CommandType.ATTACK),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.DELAY),
                new Command(CommandType.IF_RANDOM, 0));
        assertEquals(expected, codeParser.parse(input));
    }
}