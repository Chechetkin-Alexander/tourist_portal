package org.portal.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private long id;

    @Column(nullable = false)
    @Getter @Setter String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organizer_id", nullable = false)
    @Getter @Setter private User organizer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "participant_id")
    @Getter @Setter private User participant;

    @Column(nullable = false)
    @Getter @Setter private int price;

    @Column(length = 10000)
    @Getter @Setter private String description;

    @Column
    @Getter @Setter private Date completeDate;
}
