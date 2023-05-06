package vn.neo.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import vn.neo.model.Customer;
import vn.neo.model.CustomerFile;
import vn.neo.repository.CustomerRepository;
import vn.neo.service.CustomerProcessor;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class SpringBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CustomerRepository customerRepository;
    private final CustomerProcessor customerProcessor;
    private final HikariDataSource ds;

    private static final String PATH_FILE = "file/customer.csv";
    private static final String SQL_INSERT = "insert into customer (contact_name, country, dob, email, first_name, gender, last_name) values (?, ?, ?, ?, ?, ?, ?)";


    /**
     * cấu hình job bao gồm các step
     */
    @Bean
    public Job job(){
        return jobBuilderFactory.get("importCustomerFile")
                .flow(step())
                .end()
                .build();

    }

    /**
     * Step bao gồm các bước
     *  + chunk: chia các bản ghi trong 1 lần xử lý
     *  + reader : đọc và mapping từ file thàng các đối tượng
     *  + processer: xử lý logic convert dữ liệu
     *  + writer: thực hiện ghi vào DataBase
     */
    @Bean
    public Step step(){
        return stepBuilderFactory.get("csv-step")
                .<CustomerFile, Customer>chunk(3000)
                .reader(reader())
                .processor(customerProcessor)
                .writer(batchItemWriter())
                .build();
    }

    @Bean
    public FlatFileItemReader<CustomerFile> reader(){
        FlatFileItemReader<CustomerFile> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("file/customer.csv"));
        itemReader.setName("CSV-READER");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }


    private LineMapper<CustomerFile> lineMapper() {
        DefaultLineMapper<CustomerFile> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id","firstName", "lastName", "email", "gender", "contactNo", "country", "dob");

        BeanWrapperFieldSetMapper<CustomerFile> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(CustomerFile.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    RepositoryItemWriter<Customer> writer(){
        RepositoryItemWriter<Customer> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(customerRepository);
        itemWriter.setMethodName("save");
        return itemWriter;
    }

    @Bean
    JdbcBatchItemWriter<Customer> batchItemWriter (){
        JdbcBatchItemWriter<Customer> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(ds);
        itemWriter.setSql(SQL_INSERT);
        itemWriter.setItemPreparedStatementSetter(new ItemPreparedStatementSetter<Customer>() {
            @Override
            public void setValues(Customer customer, PreparedStatement preparedStatement) throws SQLException {
                //contact_name, country, dob, email, first_name, gender, last_name
                preparedStatement.setString(1,customer.getContactName());
                preparedStatement.setString(2,customer.getCountry());
                preparedStatement.setDate(3, new Date(customer.getDob().getTime()));
                preparedStatement.setString(4,customer.getEmail());
                preparedStatement.setString(5,customer.getFirstName());
                preparedStatement.setString(6,customer.getGender());
                preparedStatement.setString(7,customer.getLastName());
            }
        });
        return itemWriter;
    }

}
