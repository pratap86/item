package com.pratap.item.repository;

import com.pratap.item.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    ItemEntity findItemByItemId(String itemId);
}
