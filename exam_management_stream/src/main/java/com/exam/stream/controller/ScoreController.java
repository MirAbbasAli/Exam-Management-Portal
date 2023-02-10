package com.exam.stream.controller;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/score")
public class ScoreController {
    @Autowired
    private StreamsBuilderFactoryBean factoryBean;

    @GetMapping("/{input}")
    public Long getData(@PathVariable("input")String input){
        final KafkaStreams kafkaStreams=factoryBean.getKafkaStreams();
        final ReadOnlyKeyValueStore<String, Long> datareturn=kafkaStreams.store(StoreQueryParameters.fromNameAndType("totalScore", QueryableStoreTypes.keyValueStore()));
        return datareturn.get(input);
    }
}
