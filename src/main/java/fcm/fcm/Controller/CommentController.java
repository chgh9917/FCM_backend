package fcm.fcm.Controller;

import fcm.fcm.Entity.CommentEntity;
import fcm.fcm.Service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/Comment/{boardId}")
    public List<CommentEntity> Comment(@PathVariable Long boardId) {
        List<CommentEntity> list = new ArrayList<>();

        return list;
    }

    @PostMapping("/comment/{boardId}")
    public ResponseEntity<CommentEntity> addComment(@PathVariable Long boardId, @RequestBody CommentEntity comment) {
        int level = comment.getLevel();
        CommentEntity commentEntity = new CommentEntity(comment.getContent(), boardId, comment.getWriter(), level + 1);
        commentService.save(commentEntity);

        return ResponseEntity.ok(commentEntity);
    }
}
