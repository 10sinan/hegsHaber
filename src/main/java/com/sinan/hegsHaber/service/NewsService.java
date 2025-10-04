package com.sinan.hegsHaber.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilderFactory;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.sinan.hegsHaber.dto.NewsDto;
import com.rometools.rome.io.XmlReader;

@Service
public class NewsService {

    public List<NewsDto> haberiCek(String rssUrl, int limit) {
        List<NewsDto> newsList = new ArrayList<>();
        try {
            URL url = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));
            int count = 0;
            for (SyndEntry entry : feed.getEntries()) {
                if (count >= limit)
                    break;
                NewsDto news = new NewsDto();
                news.setBaslik(entry.getTitle());
                news.setLink(entry.getLink());
                news.setYayimlanmaTarihi(entry.getPublishedDate());
                news.setAciklama(entry.getDescription() != null ? entry.getDescription().getValue() : "");
                news.setKaynak(feed.getTitle());

                String imageUrl = null;
                if (entry.getEnclosures() != null && !entry.getEnclosures().isEmpty()) {
                    imageUrl = entry.getEnclosures().get(0).getUrl();
                } else {
                    // enclosure yoksa manuel olarak <image> tag'ını ara
                    imageUrl = extractImageUrlManually(rssUrl, entry.getLink());
                }
                news.setImageUrl(imageUrl);

                String id = extractGuidManually(rssUrl, entry.getLink());
                news.setId(id);

                newsList.add(news);
                count++;
            }
        } catch (Exception e) {
            // hataları logla
        }
        return newsList;
    }

    // Manuel olarak <image> tag'ından görsel URL'si çekme
    private String extractImageUrlManually(String rssUrl, String entryLink) {
        try {
            var doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(rssUrl);
            NodeList items = doc.getElementsByTagName("item");
            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);
                NodeList linkNodes = item.getElementsByTagName("link");
                if (linkNodes.getLength() > 0 && linkNodes.item(0).getTextContent().equals(entryLink)) {
                    NodeList imageNodes = item.getElementsByTagName("image");
                    if (imageNodes.getLength() > 0) {
                        return imageNodes.item(0).getTextContent();
                    }
                }
            }
        } catch (Exception e) {
            // hata yönetimi
        }
        return null;
    }

    // Manuel olarak <guid> tag'ından benzersiz kimlik çekme
    private String extractGuidManually(String rssUrl, String entryLink) {
        try {
            var doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(rssUrl);
            NodeList items = doc.getElementsByTagName("item");
            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);
                NodeList linkNodes = item.getElementsByTagName("link");
                if (linkNodes.getLength() > 0 && linkNodes.item(0).getTextContent().equals(entryLink)) {
                    NodeList guidNodes = item.getElementsByTagName("guid");
                    if (guidNodes.getLength() > 0) {
                        return guidNodes.item(0).getTextContent();
                    }
                }
            }
        } catch (Exception e) {
            // hata yönetimi
        }
        return null;
    }
}
