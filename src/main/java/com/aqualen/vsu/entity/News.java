package com.aqualen.vsu.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "news",schema="vsu")
@DynamicUpdate
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_news")
    @SequenceGenerator(name="seq_news",
            sequenceName="vsu.news_seq", allocationSize=1)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", nullable = false)
    private User user;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    @Column(name = "date_created")
    private Timestamp dateCreated;
}
