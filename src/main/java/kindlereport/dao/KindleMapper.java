package kindlereport.dao;

import kindlereport.model.Kindle;
import kindlereport.model.KindleTile;

import java.util.List;
import java.util.Map;

public interface KindleMapper {
  List<Kindle> selectTodayKindleList(String date);
  Map<String,String> selectKindle(String asin);
  List<String> selectExclusion();
  List<KindleTile> selectKindleList(Map<String,Integer> map);
  List<String> selectRereaseDateList(String date);
  List<KindleTile> selectDayKindleList(String releaseDate);
  Kindle selectKindleByAsin(String asin);
}
