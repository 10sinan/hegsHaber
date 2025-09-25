package com.sinan.hegsHaber.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_subscriptions")
public class User_subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne// ManyToOne iliskisi, bir kullanicinin birden fazla aboneligi olabilecegini belirtir.
    @JoinColumn(name = "package_id")// Foreign key kolonu
    private SubscriptionPackage subscriptionPackage;
}
