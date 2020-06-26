package me.ym.kkp.sprinkle.controller;

import lombok.extern.slf4j.Slf4j;

import me.ym.kkp.sprinkle.TokenGenerator;
import me.ym.kkp.sprinkle.model.Sprinkle;
import me.ym.kkp.sprinkle.repository.SprinkleMapper;
import me.ym.kkp.sprinkle.service.SprinkleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class SprinkleController {

    @Autowired
    private SprinkleService sprinkleService;

    @Autowired
    private SprinkleMapper sprinkleMapper;

    /**
     * @param sprinklerId
     * @param roomId
     * @param request
     * @return
     */
    @PostMapping("sprinkle")
    public String sprinkle(@RequestHeader("X-USER-ID") int sprinklerId,
                           @RequestHeader("X-ROOM-ID") String roomId,
                           @RequestBody HashMap<String, Object> reqBody
                           ) throws NoSuchAlgorithmException{

        TokenGenerator tk = new TokenGenerator();
        String token = tk.generate();

        //유효기간
        Calendar validTime = Calendar.getInstance();
        validTime.setTime(new Date());
        validTime.add(Calendar.MINUTE, 10); //+10분


        return token;

    }

    /**
     * 조회
     * @param sprinklerId
     * @param roomId
     * @param token
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/information")
    public HashMap<String, Object> selectMySprinkle(@RequestHeader("X-USER-ID") int sprinklerId,
                                   @RequestHeader("X-ROOM-ID") String roomId,
                                   @RequestHeader("token") String token ) throws Exception{

        HashMap<String, Object> map = new HashMap<>();
        Sprinkle sprinkle = sprinkleService.selectMySprinkle(token);

        if(sprinkle != null){

            //뿌린사람만 조회가능
            if(sprinklerId != sprinkle.getSprinklerId()){
                throw new Exception();
            }

            //7일 동안만 조회 가능 / 7보다 크면 exception
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentDate = new Date();
            long calDate = sdf.parse(sdf.format(currentDate)).getTime() - sdf.parse(sdf.format(sprinkle.getRegDate())).getTime();
            long calDateDays = calDate / (24*60*60*1000);
            calDateDays = Math.abs(calDateDays);

            if (calDateDays > 7) {
                throw new Exception();
            }

            //뿌린 시각, 뿌린 금액, 받기 완료된 금액
            map.put("sprinkleDate",sprinkle.getRegDate());
            map.put("sprinklePrice",sprinkle.getSprinklerPrice());
            map.put("receiverTotPrice",sprinkle.getReceiverTotPrice());

            List<HashMap<String,Object>> receiverList = sprinkleService.selectMyReceiver(token);

            //받기 완료된 정보 ([받은 금액, 받은 사용자 아이디] 리스트)
            map.put("receiverTotList", receiverList);

        }else{
            //유효한 토큰이 아님
            throw new Exception();
        }


        return map;
    }


}
