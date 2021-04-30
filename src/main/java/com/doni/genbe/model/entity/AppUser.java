package com.doni.genbe.model.entity;

import com.doni.genbe.helper.AppUserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "mst_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@JsonIgnoreProperties({"password", "pin"})
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 35)
    private String username;

    @Column(columnDefinition = "INTEGER DEFAULT 0 NULL")
    private Integer isVerified;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppUserType userType;

    @Column(columnDefinition = "INTEGER DEFAULT 1 NULL")
    private Integer isActive;

    @Column(length = 64)
    @JsonIgnore
    private String password;

    @Column(name = "pin", length = 64)
    @JsonIgnore
    private String pin;

    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createdAt;

    @JsonIgnore
    private Long createdBy;

    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updatedAt;

    @JsonIgnore
    private Integer updatedBy;

    @Column(columnDefinition = "boolean default false")
    private Boolean isDeleted;

    public AppUser(Long id) {
        this.id = id;
    }
}
