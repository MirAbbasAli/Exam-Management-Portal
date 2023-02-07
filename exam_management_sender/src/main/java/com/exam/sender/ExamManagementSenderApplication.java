package com.exam.sender;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class ExamManagementSenderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExamManagementSenderApplication.class, args);
    }
    // Creating two topics overall_score, overall_score_less_than70
    @Bean
    public NewTopic topicOne(){
        return TopicBuilder.name("overall_score")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicTwo(){
        return TopicBuilder.name("overall_score_less_than70")
                .partitions(1)
                .replicas(1)
                .build();
    }
    // Sending message to kafka through kafkaTemplate
    @Bean
    public ApplicationRunner sendMessage(KafkaTemplate<String, String> template){
        return args -> {
            for(int i=1;i<501;i++){
                String candidateId, finalScore, objectiveScore, handsOnScore, numTotalTestCases, numTestCasesPassed;
                candidateId="UU"+String.valueOf(Math.round(Math.random()*100));
                finalScore=String.valueOf(Math.round(Math.random()*100));
                objectiveScore=String.valueOf(Math.round(Math.random()*100));
                handsOnScore=String.valueOf(Math.round(Math.random()*100));
                numTotalTestCases="10";
                numTestCasesPassed=String.valueOf(Math.round(Math.random()*10));
                template.send("overall_score", "candidateId", candidateId);
                template.send("overall_score","finalScore", finalScore);
                template.send("overall_score", "objectiveScore", objectiveScore);
                template.send("overall_score","handsOnScore", handsOnScore);
                template.send("overall_score", "numTotalTestCases", numTotalTestCases);
                template.send("overall_score", "numTestCasesPassed", numTestCasesPassed);

                System.out.println("Sending data = Candidate ID: "+candidateId+" Final Score: "+finalScore+
                        " objective score: "+objectiveScore+
                        " Hands On Score: "+handsOnScore+" #Test Cases Passed: "+numTestCasesPassed+
                        " #Total Case: "+numTotalTestCases);
                Thread.sleep(2000,0);
            }
        };
    }

}