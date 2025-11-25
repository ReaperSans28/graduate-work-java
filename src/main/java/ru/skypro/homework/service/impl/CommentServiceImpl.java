package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.exception.ForbiddenOperationException;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.Role;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final AdRepository adRepository;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public CommentsDto getComments(Long adId) {
        ensureAdExists(adId);
        List<Comment> comments = commentRepository.findAllByAdId(adId);
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setCount(comments.size());
        commentsDto.setResults(comments.stream().map(commentMapper::toDto).collect(Collectors.toList()));
        return commentsDto;
    }

    @Override
    public CommentDto addComment(Long adId, String email, CreateOrUpdateCommentDto dto) {
        Ad ad = getAd(adId);
        User user = userService.findByEmail(email);
        Comment comment = commentMapper.toEntity(dto);
        comment.setAd(ad);
        comment.setAuthor(user);
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long adId, Long commentId, String email) {
        Comment comment = findComment(commentId, adId);
        ensureRights(userService.findByEmail(email), comment);
        commentRepository.delete(comment);
    }

    @Override
    public CommentDto updateComment(Long adId, Long commentId, String email, CreateOrUpdateCommentDto dto) {
        Comment comment = findComment(commentId, adId);
        ensureRights(userService.findByEmail(email), comment);
        commentMapper.updateComment(dto, comment);
        return commentMapper.toDto(comment);
    }

    private Comment findComment(Long commentId, Long adId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий не найден"));
        if (!comment.getAd().getId().equals(adId)) {
            throw new NotFoundException("Комментарий не относится к объявлению");
        }
        return comment;
    }

    private Ad getAd(Long adId) {
        return adRepository.findById(adId)
                .orElseThrow(() -> new NotFoundException("Объявление не найдено"));
    }

    private void ensureAdExists(Long adId) {
        if (!adRepository.existsById(adId)) {
            throw new NotFoundException("Объявление не найдено");
        }
    }

    private void ensureRights(User user, Comment comment) {
        boolean isOwner = comment.getAuthor().getId().equals(user.getId());
        boolean isAdAuthor = comment.getAd().getAuthor().getId().equals(user.getId());
        boolean isAdmin = user.getRole() == Role.ADMIN;
        if (!(isOwner || isAdAuthor || isAdmin)) {
            throw new ForbiddenOperationException("Нет прав на изменение комментария");
        }
    }
}

