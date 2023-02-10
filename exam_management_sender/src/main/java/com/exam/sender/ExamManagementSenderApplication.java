package com.exam.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class ExamManagementSenderApplication {
    private static final String sendToTopic="overall_score";
    @Autowired
    private KafkaTemplate<String, String> template;
    public static void main(String[] args) {
        SpringApplication.run(ExamManagementSenderApplication.class, args);
    }


    // Sending message to kafka through kafkaTemplate
    @Bean
    public ApplicationRunner sendMessage(KafkaTemplate<String, String> template){
        return args -> {

            for(int i=1;i<501;i++){

                String candidateId;
                String finalScore, objectiveScore, handsOnScore, numTotalTestCases, numTestCasesPassed;
                candidateId="UU"+ Math.round(Math.random() * 100);
                objectiveScore= String.valueOf(Math.round(Math.random()*50));
                handsOnScore= String.valueOf(Math.round(Math.random()*50));
                finalScore= String.valueOf(Long.valueOf(objectiveScore)+Long.valueOf(handsOnScore));
                numTotalTestCases= String.valueOf(10L);
                numTestCasesPassed= String.valueOf(Math.round(Math.random()*10));

                //template.send(sendToTopic, candidateDetails);
                template.send(sendToTopic, candidateId);
                template.send(sendToTopic, finalScore);
                template.send(sendToTopic, objectiveScore);
                template.send(sendToTopic, handsOnScore);
                template.send(sendToTopic, numTestCasesPassed);
                template.send(sendToTopic, numTotalTestCases);
                System.out.println("Sending data = Candidate ID: "+candidateId+
                        " Final Score: "+finalScore+
                        " objective score: "+objectiveScore+
                        " Hands On Score: "+handsOnScore+
                        " #Test Cases Passed: "+numTestCasesPassed+
                        " #Total Case: "+numTotalTestCases);
                Thread.sleep(2000,0);
            }
        };
    }
/*
    @Override
    public void run(ApplicationArguments args) throws Exception {
        for(int i=1;i<501;i++){

            String candidateId;
            Long finalScore, objectiveScore, handsOnScore, numTotalTestCases, numTestCasesPassed;
            candidateId="UU"+ Math.round(Math.random() * 100);
            finalScore=Math.round(Math.random()*100);
            objectiveScore=Math.round(Math.random()*100);
            handsOnScore=Math.round(Math.random()*100);
            numTotalTestCases= 10L;
            numTestCasesPassed=Math.round(Math.random()*10);

            CandidateDetails candidateDetails= CandidateDetails.builder()
                    .candidateId(candidateId)
                    .finalScore(finalScore)
                    .objectiveScore(objectiveScore)
                    .handsOnScore(handsOnScore)
                    .numTotalTestCases(numTotalTestCases)
                    .numTestCasesPassed(numTestCasesPassed)
                    .build();
            template.send(sendToTopic, new CandidateDetails());

            System.out.println("Sending data = Candidate ID: "+candidateDetails.getCandidateId()+
                    " Final Score: "+candidateDetails.getFinalScore()+
                    " objective score: "+candidateDetails.getObjectiveScore()+
                    " Hands On Score: "+candidateDetails.getHandsOnScore()+
                    " #Test Cases Passed: "+candidateDetails.getNumTestCasesPassed()+
                    " #Total Case: "+candidateDetails.getNumTotalTestCases());
            Thread.sleep(2000,0);
        }
    }

 */
}