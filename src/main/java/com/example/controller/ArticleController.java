package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

@Controller
@RequestMapping("/")
public class ArticleController {

	@ModelAttribute
	private ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	@ModelAttribute
	private CommentForm setUpCommentForm() {
		return new CommentForm();
	}
	
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private CommentRepository commentRepository;
	
	
	
	/**
	 * 記事、コメント一覧を表示する処理.
	 * 
	 * @param articleId 記事ID
	 * @param model リクエストスコープ
	 * @return 記事、コメント一覧
	 */
	@RequestMapping("")
	public String index(String articleId,Model model) {
		List<Article> articleList = articleRepository.findAll();
		for (Article article : articleList) {
			List<Comment> commentList = commentRepository.findByArticleId(article.getId());
			article.setCommentList(commentList);
		}
		model.addAttribute("articleList", articleList);
		return "article-form";
	}
	
	/**
	 * 記事を投稿する処理.
	 * 
	 * @param form 記事情報
	 * @return 最初の画面にもどる
	 */
	@RequestMapping("/insert-article")
	public String insertArticle(ArticleForm form) {
		 Article article = new Article();
		 article.setName(form.getName());
		 article.setContent(form.getContent());
		 articleRepository.insert(article);
		 return "redirect:/";
	} 
	
	/**
	 * コメントを投稿する処理.
	 * 
	 * @param comForm コメント情報
	 * @return 最初の画面に戻る
	 */
	public String insertComment(CommentForm comForm) {
		Comment comment = new Comment();
		comment.setName(comForm.getName());
		comment.setContent(comForm.getContent());
		comment.setArticleId(comForm.getIntArticleId());
		commentRepository.insert(comment);
		return "redirect:/";
	}
	
	
}
