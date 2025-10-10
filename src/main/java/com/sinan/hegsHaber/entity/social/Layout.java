package com.sinan.hegsHaber.entity.social;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Layout {
    private Long tileId;
    private Long plantId;
}

