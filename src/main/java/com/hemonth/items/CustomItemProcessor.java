package com.hemonth.items;

import com.hemonth.model.Person;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor {

    @Override
    public Object process(Object o) throws Exception {
        return o.toString().concat("=>> Hemonth!");
    }
}
