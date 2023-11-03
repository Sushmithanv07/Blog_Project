package com.myblog8.service.impl;

import com.myblog8.entity.Post;
import com.myblog8.exception.ResourceNotFound;
import com.myblog8.payloadDTO.PostDto;
import com.myblog8.payloadDTO.PostResponse;
import com.myblog8.repository.PostRepository;
import com.myblog8.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private ModelMapper modelMapper;

   //     use @Autowired or do dependency injection using constructor

//    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
//        this.postRepository = postRepository;
//        this.modelMapper = modelMapper;
//    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);

        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());

        return dto;
    }

    @Override
    public void deletePostById(long postId) {
        postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFound("Post not found with id: "+postId)
        );
        postRepository.deleteById(postId);
    }

    @Override
    public PostDto getPostById(long postId) {
      Post post =  postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFound("Post not found with id: "+postId)
        );
       return mapToDto(post);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        //  pagination
         Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));  // Sort.by(sortBy) ----> converts sortBy string to object
        Page<Post> all = postRepository.findAll(pageable);
        List<Post> posts = all.getContent();
        //

        //List<Post> posts = postRepository.findAll();  // use this when you are not using pagination concept
        
        List<PostDto> dtos = posts.stream().map(post->mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(dtos);
        postResponse.setPageNo(all.getNumber());
        postResponse.setTotalPages(all.getTotalPages());
        postResponse.setTotalElements((int)all.getTotalElements());
        postResponse.setPageSize(all.getSize());
        postResponse.setLast(all.isLast());

        return postResponse;
    }

    PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
        return dto;
        

//        or
        
//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
//        return dto;
    }

}
