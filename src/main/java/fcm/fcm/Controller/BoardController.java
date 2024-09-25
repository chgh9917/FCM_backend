package fcm.fcm.Controller;

import fcm.fcm.Entity.BoardEntity;
import fcm.fcm.Service.BoardService;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
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

    @GetMapping("/api/posts")
    public List<BoardEntity> getAllPosts() {
        return boardService.getAllPosts();
    }

    @GetMapping("/api/posts/community")
    public List<BoardEntity> getCommunityPosts() {
        return boardService.getCommunityPosts();
    }

    // 새로운 게시글 작성 엔드포인트
    @PostMapping("/api/posts/create")
    public ResponseEntity<BoardEntity> createPost(@RequestBody BoardEntity post) {
        BoardEntity newPost = boardService.createPost(post);
        newPost.setCreateAt(LocalDate.now().atStartOfDay());
        newPost.setUpdateAt(LocalDate.now().atStartOfDay());
        return ResponseEntity.ok(newPost);
    }

    @GetMapping("/api/posts/{id}")
    public ResponseEntity<BoardEntity> getPostById(@PathVariable Long id) {
        Optional<BoardEntity> post = boardService.getPostById(id);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
