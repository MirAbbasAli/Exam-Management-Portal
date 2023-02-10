package com.exam.stream.utility;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StreamBuilder {
    @Autowired
    public void doProcess(StreamsBuilder streamsBuilder){
        String inputTopic="overall_score", outputTopic="overall_score_less_than70";
        // Create a stream of the 'overall_score' event as a KStream with required serdes
        KStream<String, String> candidateData=streamsBuilder.stream(inputTopic,
                Consumed.with(Serdes.String(),Serdes.String()));
        // Create a KTable using this KStream and filter the candidate id and its corresponding total score,
        // if the score is < 70%
        KTable<String, Long> candidateDatum=candidateData
                .filter((key, value)-> String.valueOf(key).equalsIgnoreCase("candidateId"))
                .filter((key, value)-> String.valueOf(key).equalsIgnoreCase("finalScore") &&
                        Long.parseLong(value)<70)
                        .groupBy((key,value)->value, Grouped.with(Serdes.String(),Serdes.String()))
                .count(Materialized.as("totalScore"));
        candidateDatum.toStream().to("score_less_than70", Produced.with(Serdes.String(), Serdes.Long()));
    }
}
