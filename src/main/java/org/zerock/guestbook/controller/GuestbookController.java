package org.zerock.guestbook.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guestbook")   // http://localhost:80/guestbook
@Log4j2
public class GuestbookController {

    @GetMapping({"/", "/list"}) // http://localhost:80/guestbook/ or // http://localhost:80/guestbook/list
    public String list(){
        log.info("GuestbookController.list 메서드 실행...");
        return "/guestbook/list";   // resources/templates/guestbook/list.html
    }
}
