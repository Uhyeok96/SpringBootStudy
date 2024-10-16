package org.zerock.memberboard.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")    // board테이블은 ToString 제외처리
public class Reply extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String text;
    
    private String replyer;
    
    @ManyToOne(fetch = FetchType.LAZY) // LAZY 로딩으로 설정
    private Board board;

//    Hibernate:
//    create table reply (
//            rno bigint not null auto_increment,
//            moddate datetime(6),
//    regdate datetime(6),
//    replyer varchar(255),
//    text varchar(255),
//    board_bno bigint,
//    primary key (rno)
//    ) engine=InnoDB

//    Hibernate:
//    alter table if exists reply
//    add constraint FKr1bmblqir7dalmh47ngwo7mcs
//    foreign key (board_bno)
//    references board (bno)
}
