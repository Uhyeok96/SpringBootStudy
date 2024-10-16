package org.zerock.memberboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.memberboard.dto.ReplyDTO;
import org.zerock.memberboard.entity.Board;
import org.zerock.memberboard.entity.Reply;
import org.zerock.memberboard.repository.ReplyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;

    @Override
    public Long register(ReplyDTO replyDTO) {   // 댓글 등록

        Reply reply = dtoToEntity(replyDTO);

        replyRepository.save(reply);

        return reply.getRno();
    }

    @Override
    public List<ReplyDTO> getList(Long bno) {   // 특정 게시글의 댓글 목록

        List<Reply> result = replyRepository
                .getRepliesByBoardOrderByRno(Board.builder().bno(bno).build());

        return result.stream().map(reply -> entityToDTO(reply)).collect(Collectors.toList());
    }

    @Override
    public void modify(ReplyDTO replyDTO) { // 댓글 수정

        Reply reply = dtoToEntity(replyDTO);

        replyRepository.save(reply);

    }

    @Override
    public void remove(Long rno) {  // 댓글 삭제

        replyRepository.deleteById(rno);
    }
}
