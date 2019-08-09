package com.example.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

/**
 * コメントのリポジトリー.
 * 
 * @author atsushi
 *
 */
@Repository
public class CommentRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) -> {
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		return comment;
	};
	
	public List<Comment> findByArticleId(int articleId){
		String findSql = "SELECT id,name,content,article_id FROM comments WHERE article_id = :articleId ORDER BY id DESC";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		List<Comment> commentList = template.query(findSql, param, COMMENT_ROW_MAPPER);
		return commentList;
	}
	
	/**
	 * コメント情報挿入.
	 * 
	 * @param comment　コメント情報　
	 */
	public void insert(Comment comment) {
		String insertSql = "INSERT INTO comments (id,name,content,article_id) VALUES (:id,:name,:content,:articleId)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		template.update(insertSql, param);
	}
	
	/**
	 * コメント削除.
	 * 
	 * @param articleId
	 */
	public void deleteByArticleId(int articleId) {
		String deleteSql="DELETE FROM comments WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		template.update(deleteSql, param);
	}
}
