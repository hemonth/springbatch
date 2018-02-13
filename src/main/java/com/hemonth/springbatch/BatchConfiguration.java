package com.hemonth.springbatch;


import com.hemonth.items.CustomItemProcessor;
import com.hemonth.items.CustomItemReader;
import com.hemonth.items.CustomItemWriter;
import com.hemonth.listener.JobCompleteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackages = "com.hemonth")
public class BatchConfiguration extends DefaultBatchConfigurer {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder.build();
    }

    @Bean
    public CustomItemReader reader(){
        return new CustomItemReader();
    }

    @Bean
    public CustomItemWriter writer(){
        return new CustomItemWriter();
    }

    @Bean
    public CustomItemProcessor processor(){
        return new CustomItemProcessor();
    }

    //One Job can have multiple number of Steps
    @Bean
    public Job testJob(JobCompleteListener jobCompleteListener){
        return jobBuilderFactory.get("testJob")
                .incrementer(new RunIdIncrementer())
                .listener(jobCompleteListener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1")
                .<Object,Object>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

}
