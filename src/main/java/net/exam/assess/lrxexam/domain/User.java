package net.exam.assess.lrxexam.domain;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Aytor lrx
 * @Date 2021-09-05
 * user input model
 *
 */


@Data
@ToString
public class User {

    /**
     * first name
     */
    @NotNull
    @Size( max = 50, message = "1-50")
    private String first;

    /**
     * last name
     */
    @NotNull
    @Size( max = 50, message = "1-50")
    private String last;

    /**
     * email
     */
    @NotNull
    @Size( max = 50, message = "1-50")
    private String email;

    /**
     * password
     */
    @NotNull
    @Size( max = 50, message = "1-50")
    private String password;
}
