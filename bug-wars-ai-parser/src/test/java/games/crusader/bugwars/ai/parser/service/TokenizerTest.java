package games.crusader.bugwars.ai.parser.service;

import games.crusader.bugwars.ai.parser.exception.InvalidInputException;
import games.crusader.bugwars.ai.parser.model.ParseToken;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TokenizerTest {
    private Tokenizer tokenizer;

    @Before
    public void setup() {
        tokenizer = new Tokenizer();
    }

    @Test
    public void read_inputIsEmptyOrWhiteSpace_returnEmptyList() {
        assertEquals(Collections.EMPTY_LIST, tokenizer.read(""));
        assertEquals(Collections.EMPTY_LIST, tokenizer.read("        "));
        assertEquals(Collections.EMPTY_LIST, tokenizer.read("\t\t\t"));
        assertEquals(Collections.EMPTY_LIST, tokenizer.read("\n\n\n\n\r\n"));
    }

    @Test
    public void read_inputHasCommand_returnSingleParseToken() {
        List<ParseToken> expected = Arrays.asList(new ParseToken(null, "command", null));
        assertEquals(expected, tokenizer.read("command"));
        assertEquals(expected, tokenizer.read("     command   "));
        assertEquals(expected, tokenizer.read("\t\t\tcommand"));
        assertEquals(expected, tokenizer.read("\n\n\ncommand\n\r\n"));
    }

    @Test
    public void read_inputHasLabel_returnSingleParseToken() {
        List<ParseToken> expected = Arrays.asList(new ParseToken("label", "command", null));
        assertEquals(expected, tokenizer.read(":label command"));
        assertEquals(expected, tokenizer.read(":label     command   "));
        assertEquals(expected, tokenizer.read(":label\t\t\tcommand"));
    }

    @Test(expected = InvalidInputException.class)
    public void read_inputIsNull_throwError() {
        tokenizer.read(null);
    }


    @Test
    public void read_WhenInputHasAttack_ReturnOneToken() {
        List<ParseToken> expected = Arrays.asList(new ParseToken(null, "attack", null));
        assertEquals(expected, tokenizer.read("attack"));
    }

    @Test
    public void read_twoLinesOfMoveForward() {
        List<ParseToken> expected = Arrays.asList(
                new ParseToken(null, "moveForward", null),
                new ParseToken(null, "moveForward", null));

        assertEquals(expected, tokenizer.read("moveForward\nmoveForward"));
    }

    @Test
    public void read_inputHasIfEmpty_returnsListWithParameter() {
        List<ParseToken> expected = Arrays.asList(
                new ParseToken(null, "attack", null),
                new ParseToken("START", "moveForward", null),
                new ParseToken(null, "ifEmpty", "START"));

        assertEquals(expected, tokenizer.read("attack\t\t\n:START\t     moveForward\n\t\tifEmpty\t\tSTART"));
    }

    @Test
    public void read_inputHasMultpleLabels_returnsList(){
        List<ParseToken> expected = Arrays.asList(
                new ParseToken("start", "moveForward", null),
                new ParseToken(null, "ifWall", "move"),
                new ParseToken(null, "moveForward", null),
                new ParseToken("move", "moveForward", null),
                new ParseToken(null, "goto", "start"));
        assertEquals(expected, tokenizer.read(":start\tmoveForward\n\t\tifWall\t\tmove\n\t\tmoveForward\n\t\t:move\tmoveForward\n\t\tgoto\tstart"));
    }

    @Test
    public void read_threeLinesWithTabs_returnsThreeTokens(){
        List<ParseToken> expected = Arrays.asList(
                new ParseToken(null, "moveForward", null),
                new ParseToken(null, "attack", null),
                new ParseToken(null, "moveForward", null));
        assertEquals(expected, tokenizer.read("\t\tmoveForward\n\t\tattack\n\t\tmoveForward"));
    }

    @Test
    public void read_WhiteSpaceInFrontOfLabel_returnsTwoTokens(){
        List<ParseToken> expected = Arrays.asList(
                new ParseToken("START", "moveForward", null),
                new ParseToken("END", "goto", "START"));
        assertEquals(expected, tokenizer.read("\t\t:START\tmoveForward\n    :END goto\t\tSTART"));
    }

    @Test(expected = InvalidInputException.class)
    public void readLine_inputIsNull_ThenThrowError() {
        tokenizer.readLine(null);
    }

    @Test
    public void readLine_EmptyString_returnsNull() {
        assertEquals(null, tokenizer.readLine(""));
    }

    @Test
    public void readLine_WhiteSpaceString_returnsNull() {
        assertEquals(null, tokenizer.readLine("   \t    "));
    }

    @Test
    public void readLine_WithComments_returnsNull() {
        assertEquals(null, tokenizer.readLine("#This is a comment"));
    }

    @Test
    public void readLine_WhiteSpaceWithComments_returnsNull() {
        assertEquals(null, tokenizer.readLine("   \t    #This is a comment"));
    }

    @Test
    public void readLine_inputHasLabelAndCommandAndComments_ReturnParseTokenWithLabelAndCommand() {
        ParseToken expected = new ParseToken("label", "cmd", null);
        assertEquals(expected, tokenizer.readLine(":label cmd #parameter"));
    }

    @Test
    public void readLine_inputHasLabelCommandAndParameter_ReturnParseToken() {
        ParseToken expected = new ParseToken("label", "cmd", "parameter");
        assertEquals(expected, tokenizer.readLine(":label cmd parameter"));
    }

    @Test
    public void readLine_inputHasLabelAndCommandWithWhitespace_ReturnParseToken() {
        ParseToken expected = new ParseToken("label", "command", null);
        assertEquals(expected, tokenizer.readLine(":label     command   "));
    }

    @Test
    public void readLine_inputHasLabelAndCommandWithTabs_ReturnParseToken() {
        ParseToken expected = new ParseToken("label", "command", null);
        assertEquals(expected, tokenizer.readLine(":label\t\t\tcommand"));
    }

    @Test
    public void readLine_inputHasCommandAndParameter_ReturnParseToken() {
        ParseToken expected = new ParseToken(null, "cmd", "parameter");
        assertEquals(expected, tokenizer.readLine("cmd parameter"));
    }

    @Test
    public void readLine_inputHasComments_ReturnParseToken() {
        ParseToken expected = new ParseToken(null, "cmd", "parameter");
        assertEquals(expected, tokenizer.readLine("cmd parameter comments"));
    }

    @Test
    public void readLine_inputHasWhiteSpace_ReturnParseToken() {
        ParseToken expected = new ParseToken(null, "cmd", "parameter");
        assertEquals(expected, tokenizer.readLine("       cmd parameter comments"));
    }

    @Test
    public void readLine_inputHasSingleColon_ThenAssignLabelEmptyString() {
        ParseToken expected = new ParseToken("", "cmd", "parameter");
        assertEquals(expected, tokenizer.readLine(": cmd parameter comments"));
    }

    @Test
    public void readLine_WhenCommentsNotAlphaNumeric_Ignore() {
        ParseToken expected = new ParseToken("SD", "cmd", "parameter");
        assertEquals(expected, tokenizer.readLine(":SD cmd parameter com!ments"));
    }

}
