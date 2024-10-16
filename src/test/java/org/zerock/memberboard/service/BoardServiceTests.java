package org.zerock.memberboard.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.memberboard.dto.BoardDTO;
import org.zerock.memberboard.dto.PageRequestDTO;
import org.zerock.memberboard.dto.PageResultDTO;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() {    // 게시글 등록 테스트

        BoardDTO dto = BoardDTO.builder()
                .title("Test.")
                .content("Test...")
                .writerEmail("user55@aaa.com")  //현재 데이터베이스에 존재하는 회원 이메일
                .build();

        Long bno = boardService.register(dto);
//        Hibernate:
//        select
//        null,
//                m1_0.moddate,
//                m1_0.name,
//                m1_0.password,
//                m1_0.regdate
//        from
//        member m1_0
//        where
//        m1_0.email=?
//        Hibernate:
//        insert
//                into
//        board
//                (content, moddate, regdate, title, writer_email)
//        values
//                (?, ?, ?, ?, ?)
    }

    @Test
    public void testList() {    // 목록 처리 테스트 (1페이지에 해당하는 10개의 게시글, 회원, 댓글의 수 처리)

        //1페이지 10개
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for (BoardDTO boardDTO : result.getDtoList()) {
            System.out.println(boardDTO);
        }
//        Hibernate:
//        select
//        b1_0.bno,
//                b1_0.content,
//                b1_0.moddate,
//                b1_0.regdate,
//                b1_0.title,
//                b1_0.writer_email,
//                w1_0.email,
//                w1_0.moddate,
//                w1_0.name,
//                w1_0.password,
//                w1_0.regdate,
//                count(r1_0.rno)
//        from
//        board b1_0
//        left join
//        member w1_0
//        on w1_0.email=b1_0.writer_email
//        left join
//        reply r1_0
//        on r1_0.board_bno=b1_0.bno
//        group by
//        b1_0.bno
//        order by
//        b1_0.bno desc
//        limit
//                ?
//                Hibernate:
//                select
//        count(b1_0.bno)
//        from
//        board b1_0
//        BoardDTO(bno=101, title=Test., content=Test..., writerEmail=user55@aaa.com, writerName=USER55, regDate=2024-10-15T12:36:54.660074, modDate=2024-10-15T12:36:54.660074, replyCount=0)
//        BoardDTO(bno=100, title=Title...100, content=Content....100, writerEmail=user100@aaa.com, writerName=USER100, regDate=2024-10-15T11:35:33.960842, modDate=2024-10-15T11:35:33.960842, replyCount=6)
//        BoardDTO(bno=99, title=Title...99, content=Content....99, writerEmail=user99@aaa.com, writerName=USER99, regDate=2024-10-15T11:35:33.942545, modDate=2024-10-15T11:35:33.942545, replyCount=3)
//        BoardDTO(bno=98, title=Title...98, content=Content....98, writerEmail=user98@aaa.com, writerName=USER98, regDate=2024-10-15T11:35:33.909635, modDate=2024-10-15T11:35:33.909635, replyCount=2)
//        BoardDTO(bno=97, title=Title...97, content=Content....97, writerEmail=user97@aaa.com, writerName=USER97, regDate=2024-10-15T11:35:33.857631, modDate=2024-10-15T11:35:33.857631, replyCount=2)
//        BoardDTO(bno=96, title=Title...96, content=Content....96, writerEmail=user96@aaa.com, writerName=USER96, regDate=2024-10-15T11:35:33.850665, modDate=2024-10-15T11:35:33.850665, replyCount=3)
//        BoardDTO(bno=95, title=Title...95, content=Content....95, writerEmail=user95@aaa.com, writerName=USER95, regDate=2024-10-15T11:35:33.838574, modDate=2024-10-15T11:35:33.838574, replyCount=1)
//        BoardDTO(bno=94, title=Title...94, content=Content....94, writerEmail=user94@aaa.com, writerName=USER94, regDate=2024-10-15T11:35:33.827821, modDate=2024-10-15T11:35:33.827821, replyCount=3)
//        BoardDTO(bno=93, title=Title...93, content=Content....93, writerEmail=user93@aaa.com, writerName=USER93, regDate=2024-10-15T11:35:33.817373, modDate=2024-10-15T11:35:33.817373, replyCount=6)
//        BoardDTO(bno=92, title=Title...92, content=Content....92, writerEmail=user92@aaa.com, writerName=USER92, regDate=2024-10-15T11:35:33.810949, modDate=2024-10-15T11:35:33.810949, replyCount=2)
    }

    @Test
    public void testGet() { // 게시글 조회 처리 테스트

        Long bno = 100L;

        BoardDTO boardDTO = boardService.get(bno);

        System.out.println(boardDTO);

//        Hibernate:
//        select
//        b1_0.bno,
//                b1_0.content,
//                b1_0.moddate,
//                b1_0.regdate,
//                b1_0.title,
//                b1_0.writer_email,
//                w1_0.email,
//                w1_0.moddate,
//                w1_0.name,
//                w1_0.password,
//                w1_0.regdate,
//                count(r1_0.rno)
//        from
//        board b1_0
//        left join
//        member w1_0
//        on w1_0.email=b1_0.writer_email
//        left join
//        reply r1_0
//        on r1_0.board_bno=b1_0.bno
//        where
//        b1_0.bno=?
//        BoardDTO(bno=100, title=Title...100, content=Content....100, writerEmail=user100@aaa.com, writerName=USER100, regDate=2024-10-15T11:35:33.960842, modDate=2024-10-15T11:35:33.960842, replyCount=6)
    }
    
    @Test
    public void testRemove() {  // 게시글 삭제 테스트(1번 게시글과 댓글 포함 삭제)

        Long bno = 1L;

        boardService.removeWithReplies(bno);
//        Hibernate:
//        delete
//                from
//        reply
//                where
//        board_bno=?
//        Hibernate:
//        select
//        b1_0.bno,
//                b1_0.content,
//                b1_0.moddate,
//                b1_0.regdate,
//                b1_0.title,
//                b1_0.writer_email
//        from
//        board b1_0
//        where
//        b1_0.bno=?
//        Hibernate:
//        delete
//                from
//        board
//                where
//        bno=?
    }

    @Test
    public void testModify() {  // 게시글 수정 처리 테스트

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(2L)
                .title("제목 변경합니다.")
                .content("내용 변경합니다.")
                .build();

        boardService.modify(boardDTO);
//        Hibernate:
//        select
//        b1_0.bno,
//                b1_0.content,
//                b1_0.moddate,
//                b1_0.regdate,
//                b1_0.title,
//                b1_0.writer_email
//        from
//        board b1_0
//        where
//        b1_0.bno=?
//        Hibernate:
//        update
//                board
//        set
//        content=?,
//        moddate=?,
//        title=?,
//        writer_email=?
//        where
//        bno=?
    }
}
