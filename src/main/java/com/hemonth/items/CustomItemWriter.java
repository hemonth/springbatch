package com.hemonth.items;

import org.springframework.batch.item.ItemWriter;
import com.hemonth.model.Person;

import java.util.List;

public class CustomItemWriter implements ItemWriter<String> {

    @Override
    public void write(List<? extends String> list) throws Exception {
        System.out.println("Output List: "+ list);
    }
}
