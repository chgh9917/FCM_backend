package fcm.fcm.Controller;

import fcm.fcm.Dto.BoardDTO;
import fcm.fcm.Entity.BoardEntity;
import fcm.fcm.Service.BoardService;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public List<BoardEntity> board(@RequestParam String boardGrade) {
        return boardService.findByBoardGrade(boardGrade);
    }
    //커뮤니티 게시글 불러오기
    @GetMapping("/api/posts/community")
    public List<BoardDTO> getCommunityPosts() {
        List<BoardEntity> list = boardService.getCommunityPosts();
        List<BoardDTO> DTO_list = new ArrayList<>();
        for (BoardEntity boardEntity : list) {
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setId(boardEntity.getId());
            boardDTO.setTitle(boardEntity.getTitle());
            boardDTO.setContent(boardEntity.getContent());
            if (boardEntity.getBoardPassword() != null) {
                boardDTO.setTitle("비밀글입니다.");
            }
            DTO_list.add(boardDTO);
        }
        return DTO_list;
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

    //비밀글 여부 확인
    @GetMapping("/api/posts/{id}")
    public ResponseEntity<BoardEntity> getPostById(@PathVariable Long id) {

        Optional<BoardEntity> post = boardService.getPostById(id);
        if(post.get().getBoardPassword()==null) {
            return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return post.map(p -> ResponseEntity.badRequest().body(p)).orElseGet(() -> ResponseEntity.badRequest().build());
        }
    }

    //비밀글 암호 일치 확인
    @PostMapping("/api/posts/{id}")
    public ResponseEntity<BoardEntity> getPostById(@PathVariable Long id,@RequestBody String boardPassword) {

        Optional<BoardEntity> post = boardService.getPostById(id);
        if(Objects.equals(post.get().getBoardPassword(), boardPassword)){
            return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }
        return post.map(p -> ResponseEntity.badRequest().body(p)).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        boardService.deletePost(id);

        return ResponseEntity.ok().build();
    }
}