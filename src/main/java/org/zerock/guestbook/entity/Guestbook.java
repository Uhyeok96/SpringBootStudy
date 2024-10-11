package org.zerock.guestbook.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // jpa가 테이블을 관여한다.
@Getter // 가져오는 용
@Builder    // setter 대신
@AllArgsConstructor // 모든 필드값으로 생성자
@NoArgsConstructor  // 파라미터 없는 생성자
@ToString   // 문자로 변환
public class Guestbook extends BaseEntity{  // extends BaseEntity 날짜에 대한 감시용 코드

    @Id // pk용
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk번호용 기본숫자 처리
    private Long gno;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    // 메서드 추가 (방명록에 제목과 내용만 수정하도록)
    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }
}
//Hibernate:
//        create table guestbook (
//        gno bigint not null auto_increment,
//        moddate datetime(6),
//        regdate datetime(6),
//        content varchar(1000) not null,
//        title varchar(100) not null,
//        writer varchar(50) not null,
//        primary key (gno)
//        ) engine=InnoDB
