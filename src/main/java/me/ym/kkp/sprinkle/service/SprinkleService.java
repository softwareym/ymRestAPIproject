package me.ym.kkp.sprinkle.service;

import lombok.extern.slf4j.Slf4j;
import me.ym.kkp.sprinkle.model.Sprinkle;
import me.ym.kkp.sprinkle.repository.SprinkleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
/*
    public void insertSprinkle(Sprinkle sprinkle){
        sprinkleMapper.insertSprinkle(sprinkle);
    }
*/
    //랜덤금액 생성
    public int makeRandomReceivePrice(Sprinkle sprinkle) throws Exception{
        int returnPrice=0;

        HashMap<String,Object> retmap = sprinkleMapper.selectSprinkleTotPrice(sprinkle);

        if(retmap.get("sprinklerId").equals(sprinkle.getReceiverId())){
            throw new Exception();  //todo 자신이 뿌린 건은 받기 불가
        }

        if(!"0".equals(String.valueOf(retmap.get("myIdCnt")))){
            throw new Exception();  //todo 이미 받은 적이 있는 유저일 경우 받기 불가
        }

        String tempSprinklerPrice = String.valueOf(retmap.get("sprinklerPrice"));        //뿌린 금액
        String tempReceiverTotPrice = String.valueOf(retmap.get("receiverTotPrice"));   //실제 총 받은금액

        int sprinklerPrice = Integer.parseInt(tempSprinklerPrice);
        int receiverTotPrice = Integer.parseInt(tempReceiverTotPrice);
        int price = sprinklerPrice - receiverTotPrice;

        String tempReceiverCnt =   String.valueOf(retmap.get("receiverCnt"));           //받을수있는 총 인원
        String tempReceiverRealCnt =  String.valueOf(retmap.get("receiverRealCnt"));    //실제 받은 인원
        int receiverCnt = Integer.parseInt(tempReceiverCnt);
        int receiverRealCnt = Integer.parseInt(tempReceiverRealCnt);

        if(sprinklerPrice <= receiverTotPrice){
            throw new Exception();      //todo 받을 수 있는 금액과 인원이 이미 초과
        }else{
            if(receiverCnt == receiverRealCnt+1) {       //마지막으로 받기 가능한 자일 경우 남은 금액
                returnPrice = price;
            }else{
                returnPrice = (int) Math.floor(Math.random()*price+1); //1~price포함까지의 랜덤금액
            }
        }

        return returnPrice;
    }

}
