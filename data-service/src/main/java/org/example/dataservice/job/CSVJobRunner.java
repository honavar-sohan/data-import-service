package org.example.dataservice.job;


import org.example.dataservice.entity.write.Reviews;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.FileSystemResource;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;


@Configuration
@EnableBatchProcessing

public class CSVJobRunner {

    /**
    function where job stages are defined
     */
    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<Reviews> itemReader,
                   ItemWriter<Reviews> itemWriter
                   ) {


        Step step = stepBuilderFactory.get("ETL-FILE-LOAD")
                .<Reviews, Reviews>chunk(100)
                .reader(itemReader)
               .writer(itemWriter)
                .build();

        Job job = jobBuilderFactory.get("Import Job")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();

        return  job;
    }

    /**

    function to convert string into date formate before writing to db
     */
    @Bean
    public ConversionService createConversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();
        DefaultConversionService.addDefaultConverters(conversionService);
        conversionService.addConverter(new Converter<String, Date>() {
            @Override
            public Date convert(String text) {
                DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                try {
                    return format.parse(text);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return new Date();
                }
            }
        });
        return conversionService;
    }

    /**
    function that is used to read data from the file
     */
    @Bean
    public FlatFileItemReader<Reviews> itemReader() {
        FlatFileItemReader<Reviews> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/reviews.csv"));
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper(createConversionService()));
        return flatFileItemReader;
    }




    /**
    function that converts each line into entity that will be written to database
     */
    @Bean
    public LineMapper<Reviews> lineMapper(ConversionService conversionService) {
        DefaultLineMapper<Reviews> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("productId","reviewId","productName","reviewTitle","reviewBody","reviewRating","reviewerId","verifiedReviewer","vendor","reviewSource","createdAt","updatedAt");

        BeanWrapperFieldSetMapper<Reviews> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Reviews.class);
        fieldSetMapper.setConversionService(conversionService);
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;

    }
}
