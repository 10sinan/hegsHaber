package com.sinan.hegsHaber.entity;

import java.time.Instant;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;


//Arkadaslik    tablosu
@Data
@Entity
@Table(name = "friendships")
public class Friendship {
    public enum Status {
        PENDING, ACCEPTED, REJECTED, BLOCKED// istek beklemede, kabul edildi, reddedildi, engellendi
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Istegi gonderen kullanici
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    // Istegi alan kullanici
    @ManyToOne(fetch = FetchType.LAZY)// Lazy yukleme yani gerektiginde yukle
    @JoinColumn(name = "receiver_id", nullable = false)// Veritabanindaki kolon adi
    private User receiver;

    //@Enumerated ile enum degerlerini veritabaninda nasil saklayacagimizi belirtiyoruz.
    //EnumType.STRING kullanarak enum degerlerini string olarak sakliyoruz.
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)// Kolon uzunlugu 20, bos olamaz
    private Status status = Status.PENDING;// Varsayilan deger PENDING

    @Column(nullable = false)
    //Instant saniye ve nanosaniye duyarliliginda zaman damgasi tutar.
    private Instant createdAt;// istegin gonderildigi zaman

    private Instant respondedAt; // kabul/red zamani

    //@PrePersist ile entity veritabanina kaydedilmeden once calisan metodu belirtiyoruz.
    //Bu metod, createdAt alanini simdiki zamanla doldurur eger bu alan nullsa.
    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
