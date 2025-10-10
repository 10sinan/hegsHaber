
package com.sinan.hegsHaber.entity.social;

import jakarta.persistence.*;
import lombok.Data;
import com.sinan.hegsHaber.entity.user.User;

@Entity
@Data
@Table(name = "garden_games")
public class Garden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid", nullable = false)
    private User user;

}
