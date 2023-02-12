package com.eestienergia.testassignment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.eestienergia.testassignment.domain.Item;
import com.eestienergia.testassignment.repository.ItemRepository;
import com.eestienergia.testassignment.service.impl.ItemServiceImpl;
import com.eestienergia.testassignment.util.ItemComparator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class ItemServiceImplTest {

    private ItemService itemService;
    private Item item1;
    private Item item2;
    private Item item3;

    @Mock
    private ItemRepository itemRepository;

    @BeforeEach
    public void setUp() {
        itemService = new ItemServiceImpl(itemRepository);
        item1 = new Item(UUID.fromString("078b5b3b-fd14-4b7d-93f9-a9ed368f62a1"), "Item 1", 10.0, 10, "image1");
        item2 = new Item(UUID.fromString("078b5b3b-fd14-4b7d-93f9-a9ed368f62a2"), "Item 2", 20.0, 20, "image2");
        item3 = new Item(UUID.fromString("078b5b3b-fd14-4b7d-93f9-a9ed368f62a3"), "Item 3", 30.0, 30, "image3");
    }

    @Test
    public void testUpdateItems() {
        List<Item> items = Arrays.asList(
                new Item(UUID.fromString("078b5b3b-fd14-4b7d-93f9-a9ed368f62a1"), "Item 1 updated", 11.0, 11, "image11"),
                new Item(UUID.fromString("078b5b3b-fd14-4b7d-93f9-a9ed368f62a2"), "Item 2 updated", 22.0, 21, "image22"),
                new Item(UUID.fromString("078b5b3b-fd14-4b7d-93f9-a9ed368f62a3"), "Item 3 updated", 33.0, 31, "image33")
        );

        when(itemRepository.findById(UUID.fromString("078b5b3b-fd14-4b7d-93f9-a9ed368f62a1"))).thenReturn(java.util.Optional.of(item1));
        when(itemRepository.findById(UUID.fromString("078b5b3b-fd14-4b7d-93f9-a9ed368f62a2"))).thenReturn(java.util.Optional.of(item2));
        when(itemRepository.findById(UUID.fromString("078b5b3b-fd14-4b7d-93f9-a9ed368f62a3"))).thenReturn(java.util.Optional.of(item3));

        itemService.updateItems(items);

        for (Item item : items) {
            Item updatedItem = itemRepository.findById(item.getId()).get();
            assertThat(updatedItem.getName()).isNotEqualTo(item.getName());
            assertThat(updatedItem.getPrice()).isNotEqualTo(item.getPrice());
            assertThat(updatedItem.getQuantity()).isEqualTo(item.getQuantity());
            assertThat(updatedItem.getImage()).isNotEqualTo(item.getImage());
        }
    }

    @Test
    public void testGetAll() {
        List<Item> expected = Arrays.asList(item1, item2, item3);

        when(itemRepository.findAllByOrderByName()).thenReturn(expected);

        List<Item> actual = itemService.getAll();

        assertThat(actual).usingElementComparator(new ItemComparator())
                .containsExactlyElementsOf(expected);
    }
}
