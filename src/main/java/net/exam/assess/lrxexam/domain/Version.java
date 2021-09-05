package net.exam.assess.lrxexam.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;


/**
 * @Aytor lrx
 * @Date 2021-09-05
 * version model, will be return to end user
 *
 */

@Data
public class Version {

    private String version;

    @JsonFormat( pattern = "YYYY-MM-dd'T'HH:mm:ss'Z'")
    private Date releaseAt;

}
