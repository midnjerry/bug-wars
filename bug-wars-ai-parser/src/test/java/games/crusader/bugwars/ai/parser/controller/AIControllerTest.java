package games.crusader.bugwars.ai.parser.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import games.crusader.bugwars.ai.parser.model.Command;
import games.crusader.bugwars.ai.parser.model.CommandType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AIControllerTest {
    private static Command ATTACK = new Command(CommandType.ATTACK);
    private static Command DELAY = new Command(CommandType.DELAY);
    private static Command TURN_RIGHT = new Command(CommandType.TURN_RIGHT);

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void parseCodeReturnsOK() throws Exception {
        String code = ":START attack\nturnRight\ngoto START";
        List<Command> commands = Arrays.asList(
                ATTACK,
                DELAY,
                DELAY,
                DELAY,
                DELAY,
                DELAY,
                TURN_RIGHT,
                new Command(CommandType.GOTO, 0));

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/ai/parse")
                .content(code)
                .contentType(MediaType.TEXT_PLAIN)).
                andExpect(status().isOk())
                .andReturn().getResponse();

        List<Command> actual = mapper.readValue(result.getContentAsString(), new TypeReference<List<Command>>() {});
        assertEquals(commands, actual);
    }

    @Test
    public void labelError_returnsBadRequest() throws Exception {
        String code = ": attack\nturnRight\ngoto START";
        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/ai/parse")
                .content(code)
                .contentType(MediaType.TEXT_PLAIN)).
                andExpect(status().isBadRequest())
                .andReturn().getResponse();

        System.out.println(result.getContentAsString());
    }

}
