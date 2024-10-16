package org.zerock.memberboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.memberboard.dto.ReplyDTO;
import org.zerock.memberboard.service.ReplyService;

import java.util.List;

@RestController  // Ajax 용(비동기 방식)
@RequestMapping("/replies/")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;    // 자동주입을 위해 final

    // 댓글 리스트 출력
    @GetMapping(value = "/board/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno ){
        // ResponseEntity : HTTP의 상태 코드 등을 같이 전달 가능
        // PathVariable : URL의 {bno}를 이용해서 변수로 사용 가능

        log.info("bno: " + bno);

        return new ResponseEntity<>( replyService.getList(bno), HttpStatus.OK);
    }

    // 댓글 등록
    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO){
        // RequestBody : JSON으로 들어오는 데이터를 자동으로 해당 타입의 객체로 매핑해주는 역할

        log.info(replyDTO);

        Long rno = replyService.register(replyDTO);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }
    
    // 댓글 삭제
    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {

        log.info("RNO:" + rno );

        replyService.remove(rno);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    
    // 댓글 수정
    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyDTO replyDTO) {

        log.info(replyDTO);

        replyService.modify(replyDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);

    }
}
