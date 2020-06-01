package com.board.demo.controller;

import com.board.demo.domain.account.Role;
import com.board.demo.dto.post.PostResponseDto;
import com.board.demo.dto.post.PostSaveRequestDto;
import com.board.demo.dto.post.PostUpdateRequestDto;
import com.board.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final PostService postService;

    @Secured(Role.RoleProperties.ROLE_USER)
    @PostMapping("/api/v1/post")
    public Long save(@RequestBody PostSaveRequestDto requestDto) {

        return postService.save(requestDto);
    }

    @Secured(Role.RoleProperties.ROLE_USER)
    @PutMapping("/api/v1/post/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto) {
        return postService.update(id, requestDto);
    }

    @GetMapping("/api/v1/post/{id}")
    public PostResponseDto findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @Secured(Role.RoleProperties.ROLE_USER)
    @DeleteMapping("/api/v1/post/{id}")
    public Long delete(@PathVariable Long id) {
        postService.delete(id);
        return id;
    }
}
