package com.authenticationservice.common.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "personnummer")
    private String personnummer;

    @JsonManagedReference
    @OneToMany(mappedBy = "person")
    private List<Permission> permissions;
}
