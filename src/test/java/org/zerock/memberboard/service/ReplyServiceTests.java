package org.zerock.memberboard.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.memberboard.dto.ReplyDTO;

import java.util.List;

@SpringBootTest
public class ReplyServiceTests {

    @Autowired
    private ReplyService service;

    @Test
    public void testGetList() { // 특정 게시글의 댓글 목록 출력 테스트

        Long bno = 100L;//데이터베이스에 존재하는 번호

        List<ReplyDTO> replyDTOList = service.getList(bno);

        replyDTOList.forEach(replyDTO -> System.out.println(replyDTO));

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
//        ReplyDTO(rno=10, text=Reply.......10, replyer=guest, bno=null, regDate=2024-10-15T11:39:50.514570, modDate=2024-10-15T11:39:50.514570)
//        ReplyDTO(rno=88, text=Reply.......88, replyer=guest, bno=null, regDate=2024-10-15T11:39:50.843715, modDate=2024-10-15T11:39:50.843715)
//        ReplyDTO(rno=162, text=Reply.......162, replyer=guest, bno=null, regDate=2024-10-15T11:39:51.070636, modDate=2024-10-15T11:39:51.070636)
//        ReplyDTO(rno=204, text=Reply.......204, replyer=guest, bno=null, regDate=2024-10-15T11:39:51.199594, modDate=2024-10-15T11:39:51.199594)
//        ReplyDTO(rno=276, text=Reply.......276, replyer=guest, bno=null, regDate=2024-10-15T11:39:51.498609, modDate=2024-10-15T11:39:51.498609)
//        ReplyDTO(rno=293, text=Reply.......293, replyer=guest, bno=null, regDate=2024-10-15T11:39:51.598679, modDate=2024-10-15T11:39:51.598679)
    }
}
