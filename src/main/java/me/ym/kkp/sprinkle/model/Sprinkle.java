package me.ym.kkp.sprinkle.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;


@Data
@Alias("sprinkle")
public class Sprinkle {

    private Long seq;
    private String token;
    private String roomId;
    private int sprinklerId;
    private Long sprinklerPrice;
    private int receiverCnt;
    private Date regDate;
    private Date validTime;

    private Long receiverTotPrice;

}
