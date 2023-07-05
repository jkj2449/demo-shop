package com.shop.demo.service;

import com.shop.demo.domain.item.Item;
import com.shop.demo.domain.item.ItemRepository;
import com.shop.demo.dto.item.ItemListResponseDto;
import com.shop.demo.dto.item.ItemResponseDto;
import com.shop.demo.dto.item.ItemSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemResponseDto findById(final Long id) {
        Item entity = itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 아이템이 없습니다. id=" + id));
        return new ItemResponseDto(entity);
    }

    @Transactional
    public Long save(final ItemSaveRequestDto requestDto) {
        return itemRepository.save(requestDto.toEntity()).getId();
    }

    public List<ItemListResponseDto> findAll() {
        return itemRepository.findAll()
                .stream()
                .map(item -> ItemListResponseDto.builder().entity(item).build())
                .collect(Collectors.toList());
    }

    public Page<ItemListResponseDto> findAll(final Pageable pageable) {
        return itemRepository.findAll(pageable).map(ItemListResponseDto::new);
    }

//    public Page<ItemListResponseDto> findAllByMemberId(final Long memberId, final Pageable pageable) {
//        if (!SecurityContextProvider.getMember().getId().equals(memberId)) {
//            throw new IllegalArgumentException("권한이 없습니다.");
//        }
//
//        return itemRepository.findAllByMemberId(memberId, pageable).map(ItemListResponseDto::new);
//    }
}
