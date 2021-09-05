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
public class UserActivityEntity {

    /**
     * primary key
     */
    private long id;

    /**
     * user id
     */
    private long userId;

    /**
     *  token
     */
    private String token;

    private Timestamp created;
    private String createdBy;
    private Timestamp modified;
    private String modifiedBy;
}
