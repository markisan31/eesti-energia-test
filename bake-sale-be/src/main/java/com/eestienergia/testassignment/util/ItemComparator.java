package com.eestienergia.testassignment.util;

import com.eestienergia.testassignment.domain.Item;

import java.util.Comparator;

public class ItemComparator implements Comparator<Item> {
    @Override
    public int compare(Item item1, Item item2) {
        int result = item1.getId().compareTo(item2.getId());
        if (result != 0) return result;

        result = item1.getName().compareTo(item2.getName());
        if (result != 0) return result;

        result = item1.getPrice().compareTo(item2.getPrice());
        if (result != 0) return result;

        result = item1.getQuantity().compareTo(item2.getQuantity());
        if (result != 0) return result;

        return item1.getImage().compareTo(item2.getImage());
    }
}
