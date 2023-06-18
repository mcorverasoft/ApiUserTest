package com.mcorvera.userservice.infraestructure.entity;

import com.mcorvera.userservice.infraestructure.entity.audit.Audit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.*;


@Table(name="users",uniqueConstraints= {
        @UniqueConstraint(columnNames={"email"},name = "email_unique"),
        @UniqueConstraint(columnNames={"username"}, name = "username_unique")})
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends Audit {
    private static final BCryptPasswordEncoder bCryptPassEncoder = new BCryptPasswordEncoder();
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36)
    private String id;
    @Column(unique=true, nullable = false, length = 55)
    private String username;
    @NotBlank(message = "Please enter name")
    @Column(nullable = false, length = 50)
    private String name;
    @Email(message = "Please, enter a valid email")
    @Column(unique=true, nullable = false, length = 100)
    private String email;
    @NotBlank(message = "Password must not empty")
    @Column(nullable = false, length = 100)
    private String password;
    private Instant last_login;
    private Boolean isActive;
    @Column(length = 255)
    private String token;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "userid")
    private Set<PhoneEntity> phones;

    @PrePersist
    public void prePersist(){
        this.password= bCryptPassEncoder.encode(this.password);
        this.last_login=Instant.now();
        this.isActive=true;
    }

    @PreUpdate
    public void preUpdate(){
        this.last_login=Instant.now();
    }}
