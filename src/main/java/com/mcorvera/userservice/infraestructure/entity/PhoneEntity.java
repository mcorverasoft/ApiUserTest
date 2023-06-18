package com.mcorvera.userservice.infraestructure.entity;

import com.mcorvera.userservice.infraestructure.entity.audit.Audit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Parent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Table(name="phone")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "please enter a valid phone")
    @Column(nullable = false, length = 10)
    private String number;
    @Column(nullable = true, length = 3)
    private String citycode;
    @NotBlank(message = "please enter a country code")
    @Column(nullable = false, length = 3)
    private String contrycode;

}
