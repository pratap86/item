package com.pratap.item.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratap.item.dto.ItemDto;
import com.pratap.item.entity.ItemEntity;
import com.pratap.item.exception.ItemNotFoundException;
import com.pratap.item.repository.ItemRepository;
import com.pratap.item.utils.ItemUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ObjectMapper jsonMapper;

    @Override
    public List<ItemDto> getItems() {

        log.info("Executing getItems()");
        List<ItemEntity> savedItems = itemRepository.findAll();

        return savedItems.stream()
                .map(itemEntity -> new ModelMapper().map(itemEntity, ItemDto.class)).collect(Collectors.toList());
    }

    @Override
    public ItemDto createItem(ItemDto itemDto) throws JsonProcessingException {

        itemDto.setItemId(ItemUtils.generateItemId(10));
        log.info("Executing createItem() with itemDto={}", jsonMapper.writeValueAsString(itemDto));

        ItemEntity itemEntity = new ModelMapper().map(itemDto, ItemEntity.class);
        ItemEntity savedItem = itemRepository.save(itemEntity);
        return new ModelMapper().map(savedItem, ItemDto.class);
    }

    @Override
    public ItemDto getItemById(String itemId) throws JsonProcessingException {
        
        log.info("Executing getItemById() with itemId={}", itemId);
        ItemEntity fetchedItemDetails = itemRepository.findItemByItemId(itemId);
        if (fetchedItemDetails == null){
            log.error("item not found by itemId={}", itemId);
            throw new ItemNotFoundException("item not found by itemId="+ itemId);
        }

        log.info("fetched item details fetchedItemDetails={}", jsonMapper.writeValueAsString(fetchedItemDetails));
        return new ModelMapper().map(fetchedItemDetails, ItemDto.class);
    }
}
