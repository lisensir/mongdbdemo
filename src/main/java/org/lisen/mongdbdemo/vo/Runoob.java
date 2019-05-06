package org.lisen.mongdbdemo.vo;

import lombok.Getter;
import lombok.Setter;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Getter
@Setter
@Entity(value="runoob")
public class Runoob {

    @Id
    private String rId;

    private String title;

    private String description;

    private String by_user;

    private String url;

}
