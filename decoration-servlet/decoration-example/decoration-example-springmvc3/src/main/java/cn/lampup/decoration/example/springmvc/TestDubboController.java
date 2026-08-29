package cn.lampup.decoration.example.springmvc;

import org.apache.dubbo.config.annotation.DubboReference;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lamp.decoration.servlet.example.service.TestService;

@Controller
@RequestMapping("test/dubbo/")
public class TestDubboController {

    @DubboReference(url = "127.0.0.1:22131")
    private TestService testService;


    @PostMapping("test")
    public String test() {
        return testService.test();
    }

}
