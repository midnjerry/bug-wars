package games.crusader.bugwars.ai.parser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParseToken {
    private String label;
    private String command;
    private String parameter;
}
