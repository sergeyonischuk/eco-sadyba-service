import lombok.Data;

import java.time.LocalDate;

@Data
public class Client {
    private String cardCode;
    private String fullName;
    private LocalDate dateOfBirth;
    private String homePhone;
    private String mobilePhone;
    private String workPhone;
    private double discount;
}
