package com.pratap.item.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pratap.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    List<ItemDto> getItems();
    ItemDto createItem(ItemDto itemDto) throws Exception;
    ItemDto getItemById(String itemId) throws Exception;
}
