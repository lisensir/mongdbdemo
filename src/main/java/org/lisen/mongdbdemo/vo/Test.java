package org.lisen.mongdbdemo.vo;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

@Entity("user")
@Getter
@Setter
public class Test {

    @Id
    private ObjectId _id;

    private Integer userId;

    private String userName;

    private String password;

    private Date telephone;

}
