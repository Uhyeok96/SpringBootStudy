package org.zerock.memberboard.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.memberboard.entity.Board;
import org.zerock.memberboard.entity.Reply;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply() { // board 데이터를 이용해서 300개의 댓글을 랜덤 생성

        IntStream.rangeClosed(1, 300).forEach(i -> {
            //1부터 100까지의 임의의 번호
            long bno  = (long)(Math.random() * 100) + 1;

            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .text("Reply......." +i)
                    .board(board)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);

        });
    }

    @Test
    public void readReply1() {  // 특정 댓글 조회 테스트

        Optional<Reply> result = replyRepository.findById(1L);

        Reply reply = result.get();

        System.out.println(reply);
        System.out.println(reply.getBoard());

//        Hibernate:
//        select
//        r1_0.rno,
//                b1_0.bno,
//                b1_0.content,
//                b1_0.moddate,
//                b1_0.regdate,
//                b1_0.title,
//                w1_0.email,
//                w1_0.moddate,
//                w1_0.name,
//                w1_0.password,
//                w1_0.regdate,
//                r1_0.moddate,
//                r1_0.regdate,
//                r1_0.replyer,
//                r1_0.text
//        from
//        reply r1_0
//        left join
//        board b1_0
//        on b1_0.bno=r1_0.board_bno
//        left join
//        member w1_0
//        on w1_0.email=b1_0.writer_email
//        where
//        r1_0.rno=?
//        Reply(rno=1, text=Reply.......1, replyer=guest)
//        Board(bno=49, title=Title...49, content=Content....49)
    }

    @Test
    public void testListByBoard() { // 게시글에 해당하는 댓글 리스트 출력 테스트

        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(
                Board.builder().bno(97L).build());

        replyList.forEach(reply -> System.out.println(reply));

//        Hibernate:
//        select
//        r1_0.rno,
//                r1_0.board_bno,
//                r1_0.moddate,
//                r1_0.regdate,
//                r1_0.replyer,
//                r1_0.text
//        from
//        reply r1_0
//        where
//        r1_0.board_bno=?
//        order by
//        r1_0.rno
//        Reply(rno=59, text=Reply.......59, replyer=guest)
//        Reply(rno=197, text=Reply.......197, replyer=guest)
    }
}
