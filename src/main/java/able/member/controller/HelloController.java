package able.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // 작동되느지 확인
    @GetMapping("hello")
    public String HelloPage() {
        return "Hello World";
    }
}
