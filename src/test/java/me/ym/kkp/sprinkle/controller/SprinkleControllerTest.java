package me.ym.kkp.sprinkle.controller;

import me.ym.kkp.sprinkle.repository.SprinkleMapper;
import me.ym.kkp.sprinkle.service.SprinkleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SprinkleControllerTest {

    @Value("${local.server.port}")
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SprinkleService sprinkleService;

    @Autowired
    private SprinkleMapper sprinkleMapper;

    /*
    * restTemplate : Spring 3부터 지원 되었고 REST API 호출이후 응답을 받을 때까지 기다리는 동기방식이다
    * */

    @Test
    public void sprinkle_ok(){
        /*
         <restTemplate을 이용한 POST 요청방법>
            restTemplate.postForObject()
            restTemplate.postForEntity()
            restTemplate.exchange()
            restTemplate.execute()
        */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-USER-ID","444");
        headers.add("X-ROOM-ID", "aaa");

        Map<String, Integer> reqBody = new HashMap<>();
        reqBody.put("sprinklerPrice", 1000);
        reqBody.put("receiverCnt", 3);

        HttpEntity<Map<String, Integer>> request = new HttpEntity<>(reqBody, headers);

        //1.postForEntity => hashmap으로 헤더 정보를 함께 넘김 [postForEntity는 HttpEntity를 받을 수 없다.]
        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/sprinkle", request, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED); //201 Created: 요청이 성공적이었으며 그 결과로 새로운 리소스가 생성되었습니다

        //2.exchange
        ResponseEntity<String> response2 = restTemplate.exchange("/api/v1/sprinkle", HttpMethod.POST, request, String.class);
        assertThat(response2).isNotNull();
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void recieve_ok() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-USER-ID","123");
        headers.add("X-ROOM-ID", "aaa");
        headers.add("token", "ABC");

        HttpEntity<Map<String, Integer>> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/recieve", request, String.class);

        assertThat(response).isNotNull();
    }

    @Test
    public void getMySprinkle() {
        /*
         <restTemplate을 이용한 GET 요청방법>
            restTemplate.getForObject() : 기본 Http Header를 사용하며 결과를 객체로 반환 받는다.
            restTemplate.getForEntity() : 기본 Http Header를 사용하며 결과를 Http ResponseEntity로 반환 받는다.
            restTemplate.exchange() : Http Header 를 수정할 수 있고 결과를 Http ResponseEntity로 반환 받는다.
            restTemplate.execute() : Request/Response 콜백을 수정할 수 있다.
        */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-USER-ID","111");
        headers.add("X-ROOM-ID","aaa");
        headers.add("token","ABC");

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<HashMap> response = restTemplate.exchange("/api/v1/information", HttpMethod.GET, request, HashMap.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).toString().contains("\"sprinklePrice\"=10000");

    }

    @Test
    public void cancle_ok() {
        /*
         <restTemplate을 이용한 patch 요청방법>
            restTemplate.patchForObject()
            restTemplate.exchange()
            restTemplate.execute()
        */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-USER-ID","111");
        headers.add("X-ROOM-ID", "aaa");
        headers.add("token", "ABC");

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<HashMap> response = restTemplate.exchange("/api/v1/cancle", HttpMethod.PATCH, request, HashMap.class);

        assertThat(response).isNotNull();
    }


    @Test
    public void cancle2_ok() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-USER-ID","111");
        headers.add("X-ROOM-ID", "aaa");
        headers.add("token", "ABC");

        HttpEntity<String> request = new HttpEntity<>(headers);
        Integer response = restTemplate.patchForObject("/api/v1/cancle2", request, Integer.class);

        assertThat(response).isNotNull();
    }

    @Test
    public void updateAllReceiver(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", "ABC");

        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("newToken", "DDD");
        reqBody.put("receiverId", 456);
        reqBody.put("receiverPrice", 35600);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(reqBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put("http://localhost:"+this.port+"/api/v1/updateAllReceiver", request);
    }

    @Test
    public void deleteSprinkle(){
       /*
        <restTemplate을 이용한 delete 요청방법>
           restTemplate.delete()
           restTemplate.exchange()
           restTemplate.execute()
       */
        Map<String, String> request = new HashMap<>();
        request.put("token","ABC");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://localhost:"+this.port+"/api/v1/deleteSprinkle/{token}", request);
    }


}
