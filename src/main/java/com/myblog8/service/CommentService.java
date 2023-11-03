package com.myblog8.service;

import com.myblog8.payloadDTO.CommentDto;

public interface CommentService {

    CommentDto saveComment(CommentDto dto, long postId);

    void deleteByCommentId(long id);


    CommentDto updateByCommentId(CommentDto commentDto, long id);
}
