package com.bookmyshow.bookmyshow.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {
    //This class will contain the common attriutes for all classes
    @Id//primary key column
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Auto increment
    private Long id;
    private Date createdAt;
    private Date lastModifiedAt;
}
