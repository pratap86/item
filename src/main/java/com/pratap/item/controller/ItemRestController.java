package com.pratap.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratap.item.dto.ItemDto;
import com.pratap.item.model.request.ItemRequestModel;
import com.pratap.item.model.response.CouponResponseModel;
import com.pratap.item.model.response.ItemResponseModel;
import com.pratap.item.restclients.CouponServiceProxy;
import com.pratap.item.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
@Slf4j
public class ItemRestController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ObjectMapper jsonMapper;

    @Autowired
    private CouponServiceProxy couponServiceProxy;

    CouponResponseModel couponResponseModel;

    @GetMapping
    public ResponseEntity<List<ItemResponseModel>> getItems(){

        log.info("Executing getItems(), Request_Type={}", "GET");
        List<ItemDto> itemDtos = itemService.getItems();

        List<ItemResponseModel> itemResponseModels = itemDtos.stream().map(itemDto -> new ModelMapper().map(itemDto, ItemResponseModel.class)).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(itemResponseModels);
    }

    @PostMapping
    public ResponseEntity<ItemResponseModel> createItem(@Valid @RequestBody ItemRequestModel itemRequestModel) throws Exception {

        log.info("Executing createItem() with payload={}", jsonMapper.writeValueAsString(itemRequestModel));
        ItemDto itemDto = new ModelMapper().map(itemRequestModel, ItemDto.class);
        try {
            couponResponseModel = couponServiceProxy.getCouponByCode(itemRequestModel.getCouponCode()).getBody();
        } catch (Exception exception){
            log.error("Feign Exception caught", exception);
            throw new Exception(exception);
        }
        itemDto.setPrice(itemDto.getPrice().subtract(couponResponseModel.getDiscount()));
        ItemDto savedItem = itemService.createItem(itemDto);
        ItemResponseModel itemResponseModel = new ModelMapper().map(savedItem, ItemResponseModel.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{itemId}")
                .buildAndExpand(itemResponseModel.getItemId()).toUri();
        return ResponseEntity.created(location).body(itemResponseModel);
    }

    @GetMapping("/{itemId}")
    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8081"})
    public ResponseEntity<ItemResponseModel> getItemById(@PathVariable String itemId) throws Exception {

        log.info("Executing getItemById() with itemId={}", itemId);
        ItemDto itemDto = itemService.getItemById(itemId);
        ItemResponseModel itemResponseModel = new ModelMapper().map(itemDto, ItemResponseModel.class);
        return ResponseEntity.status(HttpStatus.OK).body(itemResponseModel);
    }
}
