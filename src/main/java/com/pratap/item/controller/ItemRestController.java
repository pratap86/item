package com.pratap.item.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratap.item.dto.ItemDto;
import com.pratap.item.model.request.ItemRequestModel;
import com.pratap.item.model.response.ItemResponseModel;
import com.pratap.item.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
@Slf4j
public class ItemRestController {

    @Autowired
    private ItemService itemService;

    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper jsonMapper;

    @GetMapping
    public ResponseEntity<List<ItemResponseModel>> getItems(){

        log.info("Executing getItems(), Request_Type={}", "GET");
        List<ItemDto> itemDtos = itemService.getItems();

        modelMapper = new ModelMapper();
        List<ItemResponseModel> itemResponseModels = itemDtos.stream().map(itemDto -> modelMapper.map(itemDto, ItemResponseModel.class)).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(itemResponseModels);
    }

    @PostMapping
    public ResponseEntity<ItemResponseModel> createItem(@Valid @RequestBody ItemRequestModel itemRequestModel) throws Exception {

        log.info("Executing createItem() with payload={}", jsonMapper.writeValueAsString(itemRequestModel));

        ItemDto itemDto = new ModelMapper().map(itemRequestModel, ItemDto.class);

        ItemDto savedItem = itemService.createItem(itemDto);

        ItemResponseModel itemResponseModel = new ModelMapper().map(savedItem, ItemResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(itemResponseModel);

    }
}
