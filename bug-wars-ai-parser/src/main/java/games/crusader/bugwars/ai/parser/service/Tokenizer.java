package games.crusader.bugwars.ai.parser.service;

import games.crusader.bugwars.ai.parser.exception.InvalidInputException;
import games.crusader.bugwars.ai.parser.model.ParseToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Tokenizer {

    public List<ParseToken> read(String code) {
        if (code == null) {
            throw new InvalidInputException("Error: No code to compile");
        }
        List<ParseToken> result = new ArrayList<>();

        String[] lines = code.split("[\\n\\r]");
        for (String line : lines) {
            ParseToken token = readLine(line);
            if (token != null) {
                result.add(token);
            }
        }

        return result;
    }

    protected ParseToken readLine(String line) {
        String trimmedLine = stripCommentsAndWhiteSpace(line);
        if (trimmedLine.isEmpty()) {
            return null;
        }

        String[] words = trimmedLine.split("\\s+");
        ParseToken result = new ParseToken();

        int index = 0;
        if (words[0].startsWith(":")) {
            result.setLabel(words[0].substring(1));
            index++;
        }

        if (words.length > index) {
            result.setCommand(words[index]);
            index++;
        }

        if (words.length > index) {
            result.setParameter(words[index]);
        }

        return result;
    }

    private String stripCommentsAndWhiteSpace(String line) {
        if (line == null) {
            throw new InvalidInputException("Attempting to parse null line");
        }

        int commentIndex = line.indexOf('#');
        if (commentIndex != -1) {
            line = line.substring(0, commentIndex);
        }
        return line.trim();
    }


}
