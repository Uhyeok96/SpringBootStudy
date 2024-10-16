package org.zerock.memberboard.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.memberboard.entity.Board;
import org.zerock.memberboard.entity.Member;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard() { // member 데이터를 이용해서 board 데이터 100개 추가

        IntStream.rangeClosed(1,100).forEach(i -> {

            Member member = Member.builder().email("user"+i +"@aaa.com").build();

            Board board = Board.builder()
                    .title("Title..."+i)
                    .content("Content...." + i)
                    .writer(member)
                    .build();

            boardRepository.save(board);

        });

    }

    @Transactional
    @Test
    public void testRead1() {   // 특정 게시물 조회 테스트

        Optional<Board> result = boardRepository.findById(100L); //데이터베이스에 존재하는 번호

        Board board = result.get();

        System.out.println(board);
        System.out.println(board.getWriter());

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
//        Board(bno=100, title=Title...100, content=Content....100)
//        Hibernate:
//        select
//        m1_0.email,
//                m1_0.moddate,
//                m1_0.name,
//                m1_0.password,
//                m1_0.regdate
//        from
//        member m1_0
//        where
//        m1_0.email=?
//        Member(email=user100@aaa.com, password=1111, name=USER100)
    }

    @Test
    public void testReadWithWriter() {  // board 엔티티 내 연관관계인 writer 변수를 이용해 조인 처리 테스트

        // 100번 게시글의 board 테이블과 writer를 이용해 member 데이터 불러오기
        Object result = boardRepository.getBoardWithWriter(100L);

        Object[] arr = (Object[])result;

        System.out.println("-------------------------------");
        System.out.println(Arrays.toString(arr));

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
//                w1_0.regdate
//        from
//        board b1_0
//        left join
//        member w1_0
//        on w1_0.email=b1_0.writer_email
//        where
//        b1_0.bno=?
//        -------------------------------
//[Board(bno=100, title=Title...100, content=Content....100), Member(email=user100@aaa.com, password=1111, name=USER100)]
    }

    @Test
    public void testGetBoardWithReply() {   // 100번 게시글의 연관관계가 없는 상황에서 댓글 테이블의 데이터 불러오기 테스트

        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testWithReplyCount(){   // 목록 페이지 테스트
        // 1페이지의 데이터를 처리한다고 가정 -> 페이지 번호 0, 10개 조회
        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {

            Object[] arr = (Object[])row;

            System.out.println(Arrays.toString(arr));
        });
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
//[Board(bno=100, title=Title...100, content=Content....100), Member(email=user100@aaa.com, password=1111, name=USER100), 6]
//[Board(bno=99, title=Title...99, content=Content....99), Member(email=user99@aaa.com, password=1111, name=USER99), 3]
//[Board(bno=98, title=Title...98, content=Content....98), Member(email=user98@aaa.com, password=1111, name=USER98), 2]
//[Board(bno=97, title=Title...97, content=Content....97), Member(email=user97@aaa.com, password=1111, name=USER97), 2]
//[Board(bno=96, title=Title...96, content=Content....96), Member(email=user96@aaa.com, password=1111, name=USER96), 3]
//[Board(bno=95, title=Title...95, content=Content....95), Member(email=user95@aaa.com, password=1111, name=USER95), 1]
//[Board(bno=94, title=Title...94, content=Content....94), Member(email=user94@aaa.com, password=1111, name=USER94), 3]
//[Board(bno=93, title=Title...93, content=Content....93), Member(email=user93@aaa.com, password=1111, name=USER93), 6]
//[Board(bno=92, title=Title...92, content=Content....92), Member(email=user92@aaa.com, password=1111, name=USER92), 2]
//[Board(bno=91, title=Title...91, content=Content....91), Member(email=user91@aaa.com, password=1111, name=USER91), 2]
    }

    @Test
    public void testRead3() {   // 조회 테스트

        Object result = boardRepository.getBoardByBno(100L);

        Object[] arr = (Object[])result;

        System.out.println(Arrays.toString(arr));
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
//[Board(bno=100, title=Title...100, content=Content....100), Member(email=user100@aaa.com, password=1111, name=USER100), 6]
    }

    @Test
    public void testSearch1() {

        boardRepository.search1();

    }

    @Test
    public void testSearchPage() {

        Pageable pageable =
                PageRequest.of(0,10,
                        Sort.by("bno").descending()
                                .and(Sort.by("title").ascending()));

        Page<Object[]> result = boardRepository.searchPage("t", "1", pageable);
        // '제목(t)'에 '1'이라는 단어가 있는 데이터 검색

    }
}
