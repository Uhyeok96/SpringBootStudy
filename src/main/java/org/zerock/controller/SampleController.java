package org.zerock.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.controller.dto.SampleDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/sample")      // http://localhost:80/sample
@Log4j2
public class SampleController {

    @GetMapping("/hello")       // http://localhost:80/sample/hello
    public String[] hello(){
        return new String[]{"Hello", "World", "Java", "Cloud", "Security"};
        // RestController 사용시 기본 출력은 json 이다.
    }
    
    @GetMapping("/ex1")             // http://localhost:80/sample/ex1
    public void ex1(){                  // 리턴이 void -> resources/templates/sample/ex1.html
        log.info("ex1 메서드 실행"); // 콘솔 로그 출력
    }
    
    @GetMapping({"/ex2", "/exLink"}) // ex2 or exLink
    public void exModel(Model model){   // 스프링의 Model 영역을 통해서 데이터 전달
        List<SampleDTO> list = IntStream.rangeClosed(1,20).asLongStream().
                mapToObj(i-> {
                    SampleDTO dto = SampleDTO.builder()
                            .sno(i)
                            .first("First.." + i)
                            .last("Last.." + i)
                            .regTime(LocalDateTime.now())
                            .build();
                    return dto;
                }).collect(Collectors.toList());
        model.addAttribute("list", list);
    }

    @GetMapping("/exInline")
    public String exInline(RedirectAttributes redirectAttributes){
        // RedirectAttributes addFlashAttribute를 이용해서 1회용 객체를 처리함
        // http://localhost:80/exInline을 호출 하면 1회용 값 2개를 ex3.html에 전달함.
        log.info("exInline 메서드 실행중...");

        SampleDTO dto = SampleDTO.builder()
                .sno(100L)
                .first("first...100")
                .last("last...100")
                .regTime(LocalDateTime.now())
                .build();
        redirectAttributes.addFlashAttribute("result", "success");
        redirectAttributes.addFlashAttribute("dto",dto);
        return "redirect:/sample/ex3";
    }

    @GetMapping("/ex3")
    public void ex3(){  // void -> resources/templates/sample/ex3.html 리턴
        log.info("ex3 메서드 실행...");
    }

    @GetMapping({"/exLayout1", "/exLayout2", "/exTemplate", "/exSidebar"})
    public void exLayout1(){
        log.info("exLayout 메서드 실행...");
    }


}
