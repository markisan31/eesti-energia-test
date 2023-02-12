package com.eestienergia.testassignment.controller;

import com.eestienergia.testassignment.domain.Item;
import com.eestienergia.testassignment.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("")
    public List<Item> getAllItems() {
        return itemService.getAll();
    }

    @PutMapping("/bulk")
    public void updateItems(@RequestBody List<Item> items) {
        itemService.updateItems(items);
    }
    @PutMapping("/from-file")
    public void updateItemsFromFile(@RequestBody List<String> items) {
        itemService.updateItemsFromFile(items);
    }
}
