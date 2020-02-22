package games.crusader.bugwars.ai.parser.controller;


import games.crusader.bugwars.ai.parser.model.Command;
import games.crusader.bugwars.ai.parser.service.ScriptCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/ai")
public class AIController {

    @Autowired
    ScriptCompiler compiler;

    @PostMapping("/parse")
    public List<Command> parse(@RequestBody String sourceCode) {
        try {
            return compiler.compile(sourceCode);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

//        @GetMapping("/users/{userId}/ai")
//        public ResponseEntity<List<AI>> getAIsForUser(@PathVariable long userId){
//            return service.getAIsForUser(userId);
//        }
//
//        @PostMapping("/save")
//        public ResponseEntity<AI> saveAI(@RequestBody AI ai){
//            return service.addAi(ai);
//        }
//
//        @PutMapping("/{id}")
//        public ResponseEntity updateAi(@PathVariable  Long id, @RequestBody AI ai) {
//            service.updateAi(id, ai);
//            return new ResponseEntity(HttpStatus.OK);
//        }
//
//        @DeleteMapping("/{id}")
//        public ResponseEntity deleteAI(@PathVariable Long id) {
//            try {
//                service.deleteAi(id);
//                return new ResponseEntity<>(id, HttpStatus.OK);
//            } catch(NotFoundException e) {
//                return new ResponseEntity(HttpStatus.NOT_FOUND);
//            }
//        }
}


