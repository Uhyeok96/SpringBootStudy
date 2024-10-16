package org.zerock.memberboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.memberboard.dto.BoardDTO;
import org.zerock.memberboard.dto.PageRequestDTO;
import org.zerock.memberboard.dto.PageResultDTO;
import org.zerock.memberboard.entity.Board;
import org.zerock.memberboard.entity.Member;
import org.zerock.memberboard.repository.BoardRepository;
import org.zerock.memberboard.repository.ReplyRepository;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

    private final BoardRepository repository;   // 자동 주입 final

    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto) {    // 게시글 등록

        log.info(dto);

        Board board  = dtoToEntity(dto);

        repository.save(board);

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {   // 게시글 목록 처리

        log.info(pageRequestDTO);

        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board)en[0],(Member)en[1],(Long)en[2]));

//        Page<Object[]> result = repository.getBoardWithReplyCount(
//                pageRequestDTO.getPageable(Sort.by("bno").descending())  );
        Page<Object[]> result = repository.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending())  );


        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bno) { // 게시글 조회 처리

        Object result = repository.getBoardByBno(bno);

        Object[] arr = (Object[])result;

        return entityToDTO((Board)arr[0], (Member)arr[1], (Long)arr[2]);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {   // 삭제 기능 구현, 트랜잭션 추가

        //댓글 부터 삭제
        replyRepository.deleteByBno(bno);

        repository.deleteById(bno);

    }

    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) { // 게시글 수정 처리

        Board board = repository.getOne(boardDTO.getBno());

        if(board != null) {

            board.changeTitle(boardDTO.getTitle());
            board.changeContent(boardDTO.getContent());

            repository.save(board);
        }
    }
}
