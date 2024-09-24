package fcm.fcm.Service;

import fcm.fcm.Entity.BoardEntity;
import fcm.fcm.Entity.UserEntity;
import fcm.fcm.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Optional<BoardEntity> findById(Long id) {
        return boardRepository.findById(id);
    }

    public void write(BoardEntity board) {
        boardRepository.save(board);
    }

    public List<BoardEntity> findByAll(String boardName) {
        return boardRepository.findAll();
    }
}

