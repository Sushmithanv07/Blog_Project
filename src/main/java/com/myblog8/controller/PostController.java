package com.myblog8.controller;

import com.myblog8.payloadDTO.PostDto;
import com.myblog8.payloadDTO.PostResponse;
import com.myblog8.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

// 2 ways we can inject dependency injection----> 1.. by Autowired 2.. through constructors

   private  PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    //   http://localhost:8080/api/posts
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    //@RequestBody ----> takes the JSON content and puts that into the java object
    public ResponseEntity<Object> createPost(   // ResponseEntity<Object> or ResponseEntity<?> can be used if u r not sure of return type
            @Valid @RequestBody PostDto postdto,
            BindingResult result
    ){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto =  postService.createPost(postdto);
         return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //   http://localhost:8080/api/{postId}

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{postId}")
    public ResponseEntity<String> deletePostById(@PathVariable long postId){
        postService.deletePostById(postId);
        return new ResponseEntity<>("Post is deleted with id: "+postId, HttpStatus.OK);
    }

    @GetMapping("{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long postId){
       PostDto dto = postService.getPostById(postId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //    http://localhost:8080/api/posts?pageNo=0
    //    http://localhost:8080/api/posts?pageNo=0&pageSize=3
    //    http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=asc
    //    http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=title
    //    http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=description&sortDir=desc
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value="pageNo", defaultValue="0", required=false) int pageNo,
            @RequestParam(value="pageSize", defaultValue="10", required=false) int pageSize,
            @RequestParam(value="sortBy", defaultValue="id", required=false) String sortBy,
            @RequestParam(value="sortDir", defaultValue="asc", required=false) String sortDir
    ){
        PostResponse postResponse = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return postResponse;
    }


}
