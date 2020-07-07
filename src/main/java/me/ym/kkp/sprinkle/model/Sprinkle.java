package me.ym.kkp.sprinkle.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * Lombok 사용
 * @Data : 클래스안의 모든 private 필드에 대해 @Getter와 @Setter를 적용하여 세터/게터를 만들어주고
 *         클래스내에 @ToString 과 @EqualsAndHashCode를 적용시켜 메소드를 오버라이드 해주며 @RequiredArgsConstructor를 지정
 */
@Data
@Alias("sprinkle")
public class Sprinkle {

    private int seq;
    private String token;
    private String roomId;
    private int sprinklerId;
    private int sprinklerPrice;
    private int receiverCnt;
    private Date regDate;
    private Date validTime;

    private int receiverTotPrice;

    private int receiverId;
    private int receiverPrice;
    private String receiveDate;


}
