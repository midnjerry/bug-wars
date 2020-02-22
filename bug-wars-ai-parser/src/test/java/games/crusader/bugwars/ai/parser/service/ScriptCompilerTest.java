package games.crusader.bugwars.ai.parser.service;

import games.crusader.bugwars.ai.parser.model.ParseToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScriptCompilerTest {
    @Mock
    Tokenizer tokenizer;

    @Mock
    Parser parser;

    @InjectMocks
    ScriptCompiler compiler;

    @Test
    public void compile_callsTokenizerAndParser() {
        List<ParseToken> tokens = Arrays.asList(
                new ParseToken(null, "text", null));
        when(tokenizer.read("text")).thenReturn(tokens);

        compiler.compile("text");

        verify(tokenizer).read("text");
        verify(parser).parse(tokens);
    }
}