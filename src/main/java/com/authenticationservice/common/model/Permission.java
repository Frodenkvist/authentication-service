package com.authenticationservice.common.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permission")
public class Permission {
    @Id
    @SequenceGenerator(name = "permission_permission_id_seq", sequenceName = "permission_permission_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_permission_id_seq")
    @Column(name = "permission_id", updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    @Type(type = "com.authenticationservice.common.model.PermissionName")
    private PermissionName name;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "personnummer")
    private Person person;

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Permission)) {
            return false;
        }

        return id.equals(((Permission)other).id);
    }
}
