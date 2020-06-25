package me.ym.kkp.sprinkle.repository;
import me.ym.kkp.sprinkle.model.Sprinkle;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SprinkleMapper {

    List<Sprinkle> selectMySprinkle();
    List<Sprinkle> selectMyReceiver();

//    int insertMeeting(Meeting conference);
/*    List<Meeting> selectConTimeByConDate(ConferenceDto conferenceDto);*/

}
