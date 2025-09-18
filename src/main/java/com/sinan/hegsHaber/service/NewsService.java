package com.sinan.hegsHaber.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.sinan.hegsHaber.dto.NewsDto;
import com.rometools.rome.io.XmlReader;

@Service
public class NewsService {

    public List<NewsDto> haberiCek(String rssUrl, int limit) {// haberı cekmek ıcın limit koymamın sebebbı, cunku bıraz
                                                              // fazla haber gelebılır
        List<NewsDto> newsList = new ArrayList<>();// haberler ıcın lıste
        try {
            URL url = new URL(rssUrl);// urlyı parclara böl (//protokol, domaın, path vs)
            SyndFeedInput input = new SyndFeedInput();// SyndFeedInput = rss okuyucu
            SyndFeed feed = input.build(new XmlReader(url));// SyndFeed = kitap gibi dusun
            int count = 0;
            for (SyndEntry entry : feed.getEntries()) {// SyndEntry = kitap syfası gibi dusun
                if (count >= limit)
                    break;
                NewsDto news = new NewsDto();// her haber ıcın dto olustur
                news.setBaslik(entry.getTitle());// haber basligi
                news.setLink(entry.getLink());// haber linki
                news.setYayimlanmaTarihi(entry.getPublishedDate());// yayimlanma tarihi
                news.setAciklama(entry.getDescription() != null ? entry.getDescription().getValue() : "");// haber
                                                                                                             // aciklamasi
                                                                                                             // varmı
                                                                                                             // kontrol
                                                                                                             // et ykosa
                                                                                                             // bos
                                                                                                             // string
                                                                                                             // ata
                news.setKaynak(feed.getTitle());// haber kaynagi
                newsList.add(news);// listeye ekle
                count++;
            }
        } catch (Exception e) {
            // hataları logla
        }
        return newsList;// haber listesini dondur
    }
}
