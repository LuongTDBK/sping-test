package vn.neo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFile {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String contactName;
    private String country;
    private String dob;
}
