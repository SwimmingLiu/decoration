package cn.lampup.decoration.example.springmvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Decoration3Application {

    public static void main(String[] args) {
        try{
            SpringApplication.run(Decoration3Application.class, args);
            log.info("{} 启动成功", Decoration3Application.class.getSimpleName());
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
