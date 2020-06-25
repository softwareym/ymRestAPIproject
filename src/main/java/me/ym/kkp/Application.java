package me.ym.kkp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "me.ym.kkp.*")
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
