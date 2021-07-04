package com.example.demobatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


//@EnableBatchProcessing anotação  que permite executar o batch
@EnableBatchProcessing
@SpringBootApplication
public class HelloWorldApplication {

    //jobBuilderFactory e stepBuilderFactory:
    // componentes para construir de forma fluente o job e seus steps.
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);
    }

    //step(): Injeta (@Bean) e configura os steps do job.
    // No nosso exemplo, é criada uma simples tasklet que imprime o Hello World
    @Bean
    public Step step(){
        return stepBuilderFactory.get("step1").tasklet(new Tasklet(){
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("Hello World!");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }


    //job(): Esse método é injetado com o @Bean para retornar o job
    // que será construído a partir dos steps configurados.
    @Bean
    public Job job(){
        return this.jobBuilderFactory.get("nomeDoJob").start(step()).build();
    }

}
