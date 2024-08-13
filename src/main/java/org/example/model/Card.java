package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String numberCard;
    private Date dateEnd;
    private int cvc;
    private BigDecimal balance;
    private Currency currency;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Card(int id, String numberCard, Currency currency) {
        this.id = id;
        this.numberCard = numberCard;
        this.currency = currency;
    }

    public Card() {

    }
}
