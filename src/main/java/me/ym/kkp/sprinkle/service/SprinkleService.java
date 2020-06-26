package me.ym.kkp.sprinkle.service;

import lombok.extern.slf4j.Slf4j;
import me.ym.kkp.sprinkle.model.Sprinkle;
import me.ym.kkp.sprinkle.repository.SprinkleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Transactional
@Service
public class SprinkleService {

    @Autowired
    SprinkleMapper sprinkleMapper;

    public Sprinkle selectMySprinkle(String token){
        return sprinkleMapper.selectMySprinkle(token);
    }

    public List<HashMap<String,Object>> selectMyReceiver(String token) {
        return sprinkleMapper.selectMyReceiver(token);
    }



}
