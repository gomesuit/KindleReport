package kindlereport.mapper;

import kindlereport.model.Comment;

import java.util.List;

public interface CommentMapper {
  int insertComment(Comment comment);
  List<Comment> selectComment(String asin);
  Comment selectCommentById(int id);
}
