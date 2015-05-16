package kindlereport.dao;

import kindlereport.model.Kindle;

import java.util.List;
import java.util.Map;

public interface KindleMapper {
  List<Kindle> selectTodayKindleList(String date);
  Map<String,String> selectKindle(String asin);
  List<String> selectExclusion();
  List<Kindle> selectKindleList(Map<String,Integer> map);
  List<String> selectRereaseDateList(String date);
  List<Kindle> selectDayKindleList(String releaseDate);
  Kindle selectKindleByAsin(String asin);
}
