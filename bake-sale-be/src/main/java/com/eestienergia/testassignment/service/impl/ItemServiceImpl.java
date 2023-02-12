package com.eestienergia.testassignment.service.impl;

import com.eestienergia.testassignment.domain.Item;
import com.eestienergia.testassignment.exception.GeneralException;
import com.eestienergia.testassignment.repository.ItemRepository;
import com.eestienergia.testassignment.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    public void updateItems(List<Item> items) {
        items.forEach(i -> {
            Item itemToUpdate = itemRepository.findById(i.getId()).orElseThrow(() -> new GeneralException("Item is not found with the given id"));
            itemToUpdate.setName(itemToUpdate.getName());
            itemToUpdate.setPrice(itemToUpdate.getPrice());
            itemToUpdate.setQuantity(i.getQuantity());
            itemToUpdate.setImage(itemToUpdate.getImage());
            itemRepository.save(itemToUpdate);
        });
    }

//    Bonus task
    public void updateItemsFromFile(List<String> items) {
        items.forEach(i -> {
            String[] parts = i.split(",");
            double price;
            int quantity;
            String name = parts[0];
            try {
                price = Double.parseDouble(parts[1]);
                quantity = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                throw new GeneralException("Can't parse, wrong format");
            }
            Item itemToUpdate = itemRepository.findByName(name).orElseThrow(() -> new GeneralException("Item is not found with the given id"));
            if (itemToUpdate != null) {
                itemToUpdate.setName(itemToUpdate.getName());
                itemToUpdate.setPrice(price);
                itemToUpdate.setQuantity(quantity);
                itemToUpdate.setImage(itemToUpdate.getImage());
                itemRepository.save(itemToUpdate);
            }
        });
    }

    @Override
    public List<Item> getAll() {
        return itemRepository.findAllByOrderByName();
    }

    @Override
    public Item get(UUID id) {
        return itemRepository.getReferenceById(id);
    }

    @Override
    public Item insert(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item update(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void delete(UUID id) {
        itemRepository.deleteById(id);
    }
}
