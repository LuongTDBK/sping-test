package vn.neo.service;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;
import vn.neo.model.Customer;
import vn.neo.model.CustomerFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

@Service
public class CustomerProcessor implements ItemProcessor<CustomerFile, Customer> {
    private static final String DATE_FORMAT = "DD-MM-YYYY";
    @Override
    public Customer process(CustomerFile cf) throws Exception {
       return Customer
               .builder()
               .firstName(cf.getFirstName())
               .lastName(cf.getLastName())
               .email(cf.getEmail())
               .gender(cf.getGender())
               .contactName(cf.getContactName())
               .country(cf.getCountry())
               .dob(convertDob(cf.getDob()))
               .build();
    }

    private Date convertDob (String str) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.parse(str);
    }
}
