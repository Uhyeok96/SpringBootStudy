package org.zerock.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("/hello")
    public String[] hello(){
        return new String[]{"Hello", "World", "Java", "Cloud", "Security"};
        // RestController 사용시 기본 출력은 json 이다.
    }
}
