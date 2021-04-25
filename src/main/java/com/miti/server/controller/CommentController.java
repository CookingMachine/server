package com.miti.server.controller;

import com.miti.server.model.entity.Comment;
import com.miti.server.api.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/comment")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentController {

  private final CommentService commentService;

  @PostMapping("addComment")
  public Comment addComment(@RequestBody Comment comment) {
    return commentService.addComment(comment);
  }

  @PutMapping("editComment")
  public Comment editComment(@RequestParam(name = "commentId") Long commentId,
      @RequestBody Comment comment) {
    return commentService.editComment(commentId, comment);
  }

  @GetMapping("getCommentById")
  public Comment getCommentById(@RequestParam(name = "commentId") Long commentId) {
    return commentService.getCommentById(commentId);
  }

  @GetMapping("getCommentsByUserId")
  public List<Comment> getCommentsByUserId(@RequestParam(name = "userId") Long userId) {
    return commentService.getCommentsByUserId(userId);
  }

  @GetMapping("getCommentsByRecipeId")
  public List<Comment> getCommentsByRecipeId(@RequestParam(name = "recipeId") Long recipeId) {
    return commentService.getCommentsByRecipeId(recipeId);
  }

  @DeleteMapping("deleteCommentById")
  public String deleteCommentById(@RequestParam(name = "commentId") Long commentId) {
    commentService.deleteCommentById(commentId);
    return "Done!";
  }
}
