package kindlereport.dao;

import kindlereport.model.KindleDetail;
import kindlereport.model.KindleTile;

import java.util.List;
import java.util.Map;

public interface KindleMapper {
  Map<String,String> selectKindle(String asin);
  List<KindleTile> selectKindleList(Map<String,Integer> map);
  List<KindleTile> selectKindleListByTag(Map<String,Integer> map);
  List<String> selectRereaseDateList(String date);
  List<KindleTile> selectDayKindleList(String releaseDate);
  KindleDetail selectKindleByAsin(String asin);
}
