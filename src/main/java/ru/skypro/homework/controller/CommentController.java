package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.service.CommentService;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/ads/{adId}/comments")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Validated
@Tag(name = "Комментарии")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    @Operation(summary = "Получение комментариев объявления")
    public CommentsDto getComments(@PathVariable Long adId) {
        return commentService.getComments(adId);
    }

    @PostMapping
    @Operation(summary = "Добавление комментария")
    public CommentDto addComment(@PathVariable Long adId,
                                 @Valid @RequestBody CreateOrUpdateCommentDto dto,
                                 Authentication authentication) {
        return commentService.addComment(adId, authentication.getName(), dto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(OK)
    @Operation(summary = "Удаление комментария")
    public void deleteComment(@PathVariable Long adId,
                              @PathVariable Long commentId,
                              Authentication authentication) {
        commentService.deleteComment(adId, commentId, authentication.getName());
    }

    @PatchMapping("/{commentId}")
    @Operation(summary = "Обновление комментария")
    public CommentDto updateComment(@PathVariable Long adId,
                                    @PathVariable Long commentId,
                                    @Valid @RequestBody CreateOrUpdateCommentDto dto,
                                    Authentication authentication) {
        return commentService.updateComment(adId, commentId, authentication.getName(), dto);
    }
}

