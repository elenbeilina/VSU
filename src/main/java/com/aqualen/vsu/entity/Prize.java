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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_prize")
    @SequenceGenerator(name="seq_prize",
            sequenceName="vsu.prize_seq", allocationSize=1)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", nullable = false)
    private User user;

    @NotNull
    private String name;

    @NotNull
    private String description;

    private String picture;

}
