package me.ym.kkp.sprinkle.controller;

import lombok.extern.slf4j.Slf4j;

import me.ym.kkp.sprinkle.repository.SprinkleMapper;
import me.ym.kkp.sprinkle.service.SprinkleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/sprinkle")
public class SprinkleController {

    @Autowired
    private SprinkleService sprinkleService;

    @Autowired
    private SprinkleMapper sprinkleMapper;

    @GetMapping("/all")
    public Object selectMySprinkle(){
        return sprinkleMapper.selectMySprinkle();
    }
}
