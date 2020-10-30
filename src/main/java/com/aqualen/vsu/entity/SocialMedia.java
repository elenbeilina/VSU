package com.aqualen.vsu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Entity
@Table(name = "social_media",schema="vsu")
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class SocialMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_social_media")
    @SequenceGenerator(name="seq_social_media",
            sequenceName="vsu.social_media_seq", allocationSize=1)
    private long id;

    private String vk;

    private String facebook;

    private String instagram;

    private String twitter;

}
