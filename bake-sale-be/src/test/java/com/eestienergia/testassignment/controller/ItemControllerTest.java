package com.eestienergia.testassignment.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.eestienergia.testassignment.domain.Item;
import com.eestienergia.testassignment.service.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    public void testGetAllItems() throws Exception {
        List<Item> expected = Arrays.asList(
                new Item(UUID.fromString("078b5b3b-fd14-4b7d-93f9-a9ed368f62a1"), "Item 1", 10.0, 10, "image1"),
                new Item(UUID.fromString("078b5b3b-fd14-4b7d-93f9-a9ed368f62a2"), "Item 2", 20.0, 20, "image2"),
                new Item(UUID.fromString("078b5b3b-fd14-4b7d-93f9-a9ed368f62a3"), "Item 3", 30.0, 30, "image3")
        );

        when(itemService.getAll()).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders.get("/items"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateItems() throws Exception {
        List<Item> items = Arrays.asList(
                new Item(UUID.fromString("078b5b3b-fd14-4b7d-93f9-a9ed368f62a1"), "Item 1", 10.0, 10, "image1"),
                new Item(UUID.fromString("078b5b3b-fd14-4b7d-93f9-a9ed368f62a2"), "Item 2", 20.0, 20, "image2"),
                new Item(UUID.fromString("078b5b3b-fd14-4b7d-93f9-a9ed368f62a3"), "Item 3", 30.0, 30, "image3")
        );

        mockMvc.perform(MockMvcRequestBuilders.put("/items/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(items)))
                .andExpect(status().isOk());

        verify(itemService).updateItems(items);
    }
}
