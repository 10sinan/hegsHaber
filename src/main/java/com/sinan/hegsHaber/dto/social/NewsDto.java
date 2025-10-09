package com.sinan.hegsHaber.dto.social;

import java.util.Date;
import lombok.Data;

@Data
public class NewsDto {// haber dto su
    private String id;// haber id
    private String baslik;
    private String link;
    private Date yayimlanmaTarihi;// yayimlanma tarihi
    private String aciklama;
    private String kaynak;// haber kaynagi
    private String imageUrl;

}
