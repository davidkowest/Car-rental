package com.epam.carrental.data_generator;

import com.google.common.base.Splitter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("PropertySplitter")
public class PropertySplitter {

    /**
     * Example: one.example.property = KEY1:VALUE1,KEY2:VALUE2
     */
    public Map<String, String> map(String property) {
        return this.map(property, ",");
    }

    private Map<String, String> map(String property, String splitter) {
        return Splitter.on(splitter).omitEmptyStrings().trimResults().withKeyValueSeparator(":").split(property);
    }
}
