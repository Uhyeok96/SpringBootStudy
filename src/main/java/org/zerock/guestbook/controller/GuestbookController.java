package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.service.GuestbookService;

@Controller
@RequestMapping("/guestbook")   // http://localhost:80/guestbook
@Log4j2
@RequiredArgsConstructor    // 생성자 자동 주입 final용
public class GuestbookController {
    
    private final GuestbookService service; // @RequiredArgsConstructor용 필드

    @GetMapping("/")
    public String index(){  // http://localhost:80/ 로 왔을 때 list로 보낼것임
        return "redirect:/guestbook/list";

    }

    @GetMapping({"/list"}) // http://localhost:80/guestbook/ or // http://localhost:80/guestbook/list
    public String list(PageRequestDTO pageRequestDTO, Model model){
        log.info("GuestbookController.list 메서드 실행...");
        
        model.addAttribute("result", service.getList(pageRequestDTO));
        // 페이징처리 + dto -> jpa -> 엔티티 -> 모든 결과 -> model에 담김

        return "/guestbook/list";   // resources/templates/guestbook/list.html
    }
}
