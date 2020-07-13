package me.ym.kkp.sprinkle.controller;

import lombok.extern.slf4j.Slf4j;

import me.ym.kkp.sprinkle.TokenGenerator;
import me.ym.kkp.sprinkle.model.Response;
import me.ym.kkp.sprinkle.model.Sprinkle;
import me.ym.kkp.sprinkle.repository.SprinkleMapper;
import me.ym.kkp.sprinkle.service.SprinkleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class SprinkleController {

    @Autowired
    private SprinkleService sprinkleService;

    @Autowired
    private SprinkleMapper sprinkleMapper;

    /**
     *  뿌리기 (POST / "application/json;charset=UTF-8" 요청만을 처리하도록 제한)
     * @param sprinklerId
     * @param roomId
     * @param reqBody
     * @return
     * @throws NoSuchAlgorithmException
     */
    @PostMapping(value="sprinkle", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Response> sprinkle(@RequestHeader("X-USER-ID") int sprinklerId,
                           @RequestHeader("X-ROOM-ID") String roomId,
                           @RequestBody HashMap<String, Object> reqBody
                           ) throws NoSuchAlgorithmException{

        //암호화된 정보 : @RequestHeader          jwt
        //@RequestBody GET 방식은 Request Packet에 Body 가 존재하지 않기 때문에 @RequestBody로 받으려면 반드시 POST 여야 한다.
        TokenGenerator tk = new TokenGenerator();
        String token = tk.generate();

        //유효기간
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        today.add(Calendar.MINUTE, 10); //+10분
        Date validTime = new Date(today.getTimeInMillis());     //Calendar 의 현재 시각을 long 로 취득합니다. (형변환)

        Sprinkle sp = new Sprinkle();
        sp.setToken(token);
        sp.setRoomId(roomId);
        sp.setSprinklerId(sprinklerId);
        sp.setSprinklerPrice((Integer) reqBody.get("sprinklerPrice"));
        sp.setReceiverCnt((Integer) reqBody.get("receiverCnt"));
        sp.setValidTime(validTime);

        sprinkleMapper.insertSprinkle(sp);

        Map<String, Object> resultData = new HashMap<>();
        resultData.put("token", token);                         //고유키
        resultData.put("X-USER-ID", sprinklerId);
        resultData.put("X-ROOM-ID", roomId);
        resultData.put("validTime", validTime);

        //HttpStatus.CREATED : 201 [자원생성이 정상적으로 이루어 졌다.]
        //ResponseEntity 는 status field를 가지기 때문에 상태코드는 필수적으로 리턴해줘야 한다.
        //ResponseEntity를 사용하여 반환되는 Response의 Header의 HTTP Status Code를 직접 제어할 수 있다.
        //body()에 담겨질 객체는 Json 또는 Xml Format으로 Return 됨
        return new ResponseEntity<>(new Response("sprinkle", resultData), HttpStatus.CREATED);
    }

    /**
     * 줍기 (POST / "application/json;charset=UTF-8" 요청만을 처리하도록 제한)
     * @param receiverId
     * @param roomId
     * @param token
     * @return
     */
    @PostMapping(value="recieve", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Response> recieve(@RequestHeader("X-USER-ID") int receiverId,
                                            @RequestHeader("X-ROOM-ID") String roomId,
                                            @RequestHeader("token") String token
                                            )throws Exception{
        Sprinkle sp = new Sprinkle();
        sp.setToken(token);
        sp.setReceiverId(receiverId);

        //랜덤 금액 만들기
        int receiverPrice = sprinkleService.makeRandomReceivePrice(sp);
        sp.setReceiverPrice(receiverPrice);
    //    sprinkleMapper.insertReceiver(sp);

        Map<String, Object> resultData = new HashMap<>();
        resultData.put("token", token);                         //고유키
        resultData.put("X-USER-ID", receiverId);
        resultData.put("X-ROOM-ID", roomId);
        resultData.put("receiverPrice", sp.getReceiverPrice());

        return new ResponseEntity<>(new Response("recieve", resultData), HttpStatus.CREATED);
    }

    /**
     * 조회 (GET)
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

        HashMap<String, Object> map = new HashMap<>(); //
        Sprinkle sprinkle = sprinkleService.selectMySprinkle(token);

        if(sprinkle != null){

            //todo 뿌린사람만 조회가능
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
                //todo 조회기간 지남
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
            //todo 유효한 토큰이 아님
            throw new Exception();
        }

        return map;
    }

    /**
     ***** PUT이 해당 자원의 전체를 교체하는 의미를 지니는 대신, PATCH는 일부를 변경한다는 의미를 지니기 때문에 최근 update 이벤트에서 PUT보다 더 의미적으로 적합하다고 평가받고 있다
     *
     * 뿌리기 취소 (PATCH)
     * @param sprinklerId
     * @param roomId
     * @param token
     * @return
     * @throws Exception
     **/
    @PatchMapping(value="cancle")
    public ResponseEntity<Response> cancle(@RequestHeader("X-USER-ID") int sprinklerId,
                                            @RequestHeader("X-ROOM-ID") String roomId,
                                            @RequestHeader("token") String token
                                             )throws Exception{
        Sprinkle sp = new Sprinkle();
        sp.setToken(token);
        sp.setSprinklerId(sprinklerId);

        int ret = sprinkleService.cancleSprinkle(sp);

        if(ret == 0){                   // insert, update, delete에는 resultType이 없고 row 개수 반환하는데 수정 할수 있는 조건이 없을 경우
            throw new Exception();      //todo 잘못된 sprinklerId 전송
        }

        Map<String, Object> resultData = new HashMap<>();
        resultData.put("token", token);                         //고유키
        resultData.put("X-USER-ID", sprinklerId);
        resultData.put("X-ROOM-ID", roomId);
        return new ResponseEntity<>(new Response("cancle", resultData), HttpStatus.OK);

    }

    @PatchMapping(value="cancle2")
    public int cancle2(@RequestHeader("X-USER-ID") int sprinklerId,
                       @RequestHeader("X-ROOM-ID") String roomId,
                       @RequestHeader("token") String token
                      )throws Exception{
        Sprinkle sp = new Sprinkle();
        sp.setToken(token);
        sp.setSprinklerId(sprinklerId);

        int ret = sprinkleService.cancleSprinkle(sp);
        return ret;
    }

    /**
     * put : 요청받은 새로운 토큰으로 tblReceivers 전체 업데이트
     * @param token
     * @return
     * @throws Exception
     */
    @PutMapping(value="updateAllReceiver", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int updateAllReceiver(@RequestHeader("token") String token,
                                 @RequestBody HashMap<String, Object> reqBody
                            )throws Exception {
        Sprinkle sp = new Sprinkle();
        sp.setToken(token);
        sp.setNewToken((String) reqBody.get("newToken"));
        sp.setReceiverId((int) reqBody.get("receiverId"));
        sp.setReceiverPrice((int) reqBody.get("receiverPrice"));

        int ret = sprinkleMapper.changeReceivers(sp);
        return ret;
    }

    /**
     * delete : 삭제
     * @param token
     * @return
     * @throws Exception
     */
    @DeleteMapping(value="deleteSprinkle/{token}")
    public int deleteSprinkle(@PathVariable(value="token") String token) throws Exception {
        int ret = sprinkleMapper.deleteSprinkle(token);
        return ret;
    }

}
