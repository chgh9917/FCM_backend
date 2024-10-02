package fcm.fcm.Service;

import fcm.fcm.Entity.CommentEntity;
import fcm.fcm.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void save(CommentEntity commentEntity) {
        commentRepository.save(commentEntity);
    }
}
