package games.crusader.bugwars.ai.parser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Command {
    private CommandType commandType;
    private Integer parameter;

    public Command(CommandType commandType) {
        this.commandType = commandType;
    }
}
