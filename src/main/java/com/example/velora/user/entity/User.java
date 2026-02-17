package com.example.velora.user.entity;

import com.example.velora.subscription.entity.Subscription;
import com.example.velora.transaction.entity.Transaction;
import com.example.velora.wallet.entity.Wallet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private boolean isActive;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "owner")
    @Column(nullable = false)
    private List<Wallet> wallets;

    @OneToMany(mappedBy = "owner")
    @Column(nullable = false)
    private List<Subscription> subscriptions;

    @OneToMany
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Transaction> transactions;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    @Column(nullable = false)
    private LocalDateTime updatedOn;

}
