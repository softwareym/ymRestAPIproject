package me.ym.kkp.sprinkle.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Data
@Alias("sprinkle")
public class Sprinkle {

    private Long seq;
    private String token;
    private String sprinklerId;
    private Long sprinklerPrice;
    private int receiverCnt;
    private Timestamp regDate;


    private Long dtlSeq;
    private int receiverOrder;
    private String receiverId;
    private Long receiverPrice;
    private Timestamp receiveDate;

}
