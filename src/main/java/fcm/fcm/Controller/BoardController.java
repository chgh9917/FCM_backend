package fcm.fcm.Controller;

import fcm.fcm.Entity.BoardEntity;
import fcm.fcm.Entity.grade.BoardGrade;
import fcm.fcm.Service.BoardService;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5173")
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
        return Map.of("generation", chatModel.call("한국어로 해줘 "+message));
    }

    @PostMapping("/api/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public List<BoardEntity> board(@RequestBody BoardEntity grade) {
        BoardEntity gradeEntity = new BoardEntity();
        gradeEntity.setBoardGrade(grade.getBoardGrade());
        return boardService.findByAll(gradeEntity.getBoardGrade());
    }

    // 새로운 게시글 작성 엔드포인트
    @PostMapping("/api/posts/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BoardEntity> createPost(@RequestBody BoardEntity post) {
        BoardEntity newPost = new BoardEntity(post);
        newPost.setBoardGrade(newPost.getBoardGrade());
        boardService.createPost(newPost);

        return ResponseEntity.ok(newPost);
    }

    @GetMapping("/api/posts/{id}")
    public ResponseEntity<BoardEntity> getPostById(@PathVariable Long id) {
        Optional<BoardEntity> post = boardService.getPostById(id);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
