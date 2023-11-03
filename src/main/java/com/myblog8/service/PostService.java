package com.myblog8.service;

import com.myblog8.payloadDTO.PostDto;
import com.myblog8.payloadDTO.PostResponse;

public interface PostService {

    PostDto createPost(PostDto postDto);

    void deletePostById(long postId);

    PostDto getPostById(long postId);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
}
