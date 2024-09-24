package fcm.fcm.Controller;

import fcm.fcm.Entity.BoardEntity;
import fcm.fcm.Service.BoardService;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class BoardController {

    private final BoardService boardService;
    private final OllamaChatModel chatModel;

    // 생성자에서 BoardService와 ChatClient를 주입받도록 수정
    @Autowired
    public BoardController(BoardService boardService, OllamaChatModel chatModel) {
        this.boardService = boardService;
        this.chatModel = chatModel;
    }

    @GetMapping("/ai/generate")
    public Map<String,String> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", chatModel.call("한국어로 해줘"+message));
    }

    @PostMapping("/board/{id}")
    public Optional<BoardEntity> board(@PathVariable Long id) {
        return boardService.findById(id);
    }

    @PostMapping("/board/{boardname}")
    public void board(@PathVariable String boardname, @RequestBody BoardEntity boardEntity) {
        BoardEntity entity = new BoardEntity();

        entity.setTitle(boardEntity.getTitle());
        entity.setContent(boardEntity.getContent());
    }

    @PostMapping("/board")
    public List<BoardEntity> board(@RequestBody String boardName) {
        return boardService.findByAll(boardName);
    }
}
