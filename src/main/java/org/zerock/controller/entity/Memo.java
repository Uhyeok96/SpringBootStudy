package org.zerock.controller.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // jpa가 엔티티를 담당
@Table(name="tbl_memo")  // 테이블을 담당한다 (테이블명)
@ToString   // 객체가 문자열 처리
@Getter     // 게터메서드
@Builder    // 빌더 패턴 (세터대신 활용 .으로 값 추가)
            // Memo.builder().memoText("값").memoWriter("값").build();
@AllArgsConstructor // 모든 필드값으로 생성자 만듬
@NoArgsConstructor  // 빈 메서드로 생성자 만듬 Memo memo = new Memo();
public class Memo {

    // 필드
    @Id // 기본키 역할
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동번호 생성 (마리아DB용)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE) // 오라클 시퀀스 객체용
    //@GeneratedValue(strategy = GenerationType.AUTO)     // 알아서 자동으로 번호 생성
    //@GeneratedValue(strategy = GenerationType.UUID)     // 16진수화 번호 생성
    private Long mno;

    @Column(length = 200, nullable = false) // 메모글 (길이 200, null허용 안함)
    private  String memoText;

    @Column(length = 20, nullable = false)  // 작성자 (길이 20, null허용 안함)
    private String writer;

    // 생성자


    // 메서드
}
//Hibernate:
//        create table tbl_memo (
//        mno bigint not null auto_increment,
//        memo_text varchar(200) not null,
//        writer varchar(20) not null,
//        primary key (mno)
//        ) engine=InnoDB