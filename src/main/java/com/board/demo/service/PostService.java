package com.board.demo.service;

import com.board.demo.common.AccountProvider;
import com.board.demo.domain.post.Post;
import com.board.demo.domain.post.PostRepository;
import com.board.demo.dto.post.PostListResponseDto;
import com.board.demo.dto.post.PostResponseDto;
import com.board.demo.dto.post.PostSaveRequestDto;
import com.board.demo.dto.post.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Long save(PostSaveRequestDto requestDto) {
        return postRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostUpdateRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        if(AccountProvider.getAccount().getEmail().equals(post.getCreatedBy())) {
            throw new IllegalArgumentException("해당 게시글 수정 권한이 없습니다.");
        }

        post.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id) {
        Post entity = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public Page<PostListResponseDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostListResponseDto::new);
    }

    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        if(AccountProvider.getAccount().getEmail().equals(post.getCreatedBy())) {
            throw new IllegalArgumentException("해당 게시글 삭제 권한이 없습니다.");
        }

        postRepository.delete(post);
    }
}
