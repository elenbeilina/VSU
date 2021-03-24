package com.aqualen.vsu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "prizes",schema="vsu")
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class Prize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    private User user;

    @NotNull
    private String name;

    @NotNull
    private String description;

    private String picture;

}
