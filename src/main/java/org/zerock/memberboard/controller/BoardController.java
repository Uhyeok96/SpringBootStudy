package org.zerock.memberboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.memberboard.dto.BoardDTO;
import org.zerock.memberboard.dto.PageRequestDTO;
import org.zerock.memberboard.service.BoardService;

@Controller
@RequestMapping("/board/")  // http://localhost:80/board/
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")    // http://localhost:80/board/list
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("list............." + pageRequestDTO);

        model.addAttribute("result", boardService.getList(pageRequestDTO));

    }

    @GetMapping("/register")    // http://localhost:80/board/register
    public void register(){ // get방식으로 폼을 불러와서 post방식으로 등록함
        log.info("regiser get...");
    }

    @PostMapping("/register")   // http://localhost:80/board/register
    public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes){

        log.info("dto..." + dto);
        //새로 추가된 엔티티의 번호
        Long bno = boardService.register(dto);

        log.info("BNO: " + bno);

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/board/list";  //  등록 후 목록 페이지로 이동
    }

    @GetMapping({"/read", "/modify" })   // http://localhost:80/board/read, http://localhost:80/board/modify
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long bno, Model model){
        // GET방식으로 들어오는 수정 작업을 위한 화면은 조회와 동일함
        log.info("bno: " + bno);

        BoardDTO boardDTO = boardService.get(bno);  // bno로 해당 객체 불러옴

        log.info(boardDTO);

        model.addAttribute("dto", boardDTO);    // 데이터(객체) 전송

    }

    @PostMapping("/remove") // http://localhost:80/board/remove
    public String remove(long bno, RedirectAttributes redirectAttributes){


        log.info("bno: " + bno);

        boardService.removeWithReplies(bno); // 해당 bno의 게시글(데이터) 삭제

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/board/list";  // 삭제 후 목록페이지로 이동

    }

    @PostMapping("/modify") // http://localhost:80/board/modify
    public String modify(BoardDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes){


        log.info("post modify.........................................");
        log.info("dto: " + dto);

        boardService.modify(dto);   // 객체 수정 처리

        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());

        redirectAttributes.addAttribute("bno",dto.getBno());

        return "redirect:/board/read";  // 수정 후 수정된 조회페이지로 이동

    }
}
