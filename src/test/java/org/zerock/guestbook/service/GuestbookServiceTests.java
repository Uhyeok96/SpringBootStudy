package org.zerock.guestbook.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;

@SpringBootTest
@Log4j2
public class GuestbookServiceTests {

    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister(){
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("서비스 테스트용 제목")
                .content("서비스 테스트용 내용")
                .writer("서비스 사용자")
                .build();
        log.info("서비스 테스트중 : 엔티티 출력 -> " + service.register(guestbookDTO));
    }

    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(31)
                .size(10)
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        for(GuestbookDTO guestbookDTO : resultDTO.getDtoList()){
            System.out.println("방명록리스트 : " + guestbookDTO);
        }
        System.out.println("페이징처리 : " + pageRequestDTO);
        System.out.println("이전 : " + resultDTO.isPrev());
        System.out.println("다음 : " + resultDTO.isNext());
        System.out.println("총 페이지 : " + resultDTO.getTotalPage());
        System.out.println("------------------------------------");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }
}
