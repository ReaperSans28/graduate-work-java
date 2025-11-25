package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;

public interface CommentService {
    CommentsDto getComments(Long adId);
    CommentDto addComment(Long adId, String email, CreateOrUpdateCommentDto dto);
    void deleteComment(Long adId, Long commentId, String email);
    CommentDto updateComment(Long adId, Long commentId, String email, CreateOrUpdateCommentDto dto);
}

