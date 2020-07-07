package me.ym.kkp.sprinkle.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import java.util.Map;

/**
 * <API 응답 response>
 *
 * @NoArgsConstructor : (파라미터 없는) 기본 생성자 만들어주는 어노테이션
 * @AllArgsConstructor : 모든 필드값을 받는 생성자
 *
 * 생성자는 객체를 만들자마자 초기화할 수 있게 만들어주며, 어떤 생성자를 만들었냐에 따라 다양하게 초기화 할 수 있다.
 * 모든 클래스는 생성자를 갖고 있으며, (파라미터 없는) 기본 생성자는 만들지 않아도 생성이된다.
 * 단, 기본 생성자 이외의 생성자를 만들게 된다면 기본 생성자는 자동으로 만들어지지 않으므로 기본생성자 또한 만들어줘야 한다
 */
@Data
@Alias("rep")
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private String reqType;
    private Map<String, Object> data;

    /*
    //@NoArgsConstructor, @AllArgsConstructor 어노테이션을 사용하지 않을 경우 아래와 같이 생성자 필요
    //기본 생성자
    public Response() {
    }

    //모든 필드 생성자
    public Response(String reqType, Map<String, Object> data) {
        this.reqType = reqType;
        this.data = data;
    }
    */

}
