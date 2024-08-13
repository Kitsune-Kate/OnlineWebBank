package org.example.repository;

import org.example.model.Card;
import org.example.model.Currency;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Integer> {

    List<Card> findByUser(User user);

}
