package games.crusader.bugwars.ai.parser.service;

import games.crusader.bugwars.ai.parser.model.Command;
import games.crusader.bugwars.ai.parser.model.ParseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScriptCompiler {
    private Parser parser;
    private Tokenizer tokenizer;

    @Autowired
    public ScriptCompiler(Tokenizer tokenizer, Parser parser) {
        this.tokenizer = tokenizer;
        this.parser = parser;
    }

    public ScriptCompiler() {
    }

    public List<Command> compile(String sourceCode) {
        List<ParseToken> tokens = tokenizer.read(sourceCode);
        return (parser.parse(tokens));
    }
}
