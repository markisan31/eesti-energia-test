package com.eestienergia.testassignment.service;

import com.eestienergia.testassignment.domain.Item;

import java.util.List;

public interface ItemService extends CrudService<Item> {
    void updateItems(List<Item> items);
    void updateItemsFromFile(List<String> items);
}
