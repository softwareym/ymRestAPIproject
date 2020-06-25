package me.ym.kkp.sprinkle.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Data
@Alias("sprinkle")
public class Sprinkle {

    private Long seq;
    private String token;
    private String roomId;
    private int sprinklerId;
    private Long sprinklerPrice;
    private int receiverCnt;
    private Timestamp regDate;


    private Long dtlSeq;
    private int receiverOrder;
    private int receiverId;
    private Long receiverPrice;
    private Timestamp receiveDate;
    private Long receiverTotPrice;

}
