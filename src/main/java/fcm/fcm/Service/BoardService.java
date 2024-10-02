package fcm.fcm.Service;

import fcm.fcm.Entity.BoardEntity;
import fcm.fcm.Entity.UserEntity;
import fcm.fcm.Entity.grade.BoardGrade;
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

    public List<BoardEntity> findByAll(BoardGrade grade) {
        return boardRepository.findByBoardGrade(grade);
    }

    public List<BoardEntity> getAllPosts() {
        return boardRepository.findAll();
    }

    public BoardEntity createPost(BoardEntity post) {
        return boardRepository.save(post);
    }

    // ID로 게시글 조회 메서드
    public Optional<BoardEntity> getPostById(Long id) {
        return boardRepository.findById(id);
    }

    public List<BoardEntity> getCommunityPosts() {
        return boardRepository.findByBoardGrade(BoardGrade.community);
    }

    public List<BoardEntity> findByBoardGrade(String boardGrade) {
        return boardRepository.findByBoardGrade(BoardGrade.valueOf(boardGrade));
    }

    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }
}

