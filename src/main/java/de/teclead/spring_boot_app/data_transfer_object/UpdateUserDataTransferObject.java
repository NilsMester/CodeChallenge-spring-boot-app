package de.teclead.spring_boot_app.data_transfer_object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDataTransferObject {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}
