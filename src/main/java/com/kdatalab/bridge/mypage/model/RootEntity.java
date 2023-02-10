package com.kdatalab.bridge.mypage.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class RootEntity {
    @Id
    private int id;
}
