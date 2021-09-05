package net.exam.assess.lrxexam.entity;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * @Aytor lrx
 * @Date 2021-09-05
 * persist object entity,
 *
 */

@Data
@ToString
public class UserEntity {

    /**
     * pk
     */
    private Long id;

    /**
     *
     */
    private String first;

    /**
     *
     */
    private String last;

    /**
     *
     */
    private String email;

    /**
     * password, it`s the SHA result of the original password
     */
    private String password;
    private Timestamp created;
    private String createdBy;
    private Timestamp modified;
    private String modifiedBy;

}
