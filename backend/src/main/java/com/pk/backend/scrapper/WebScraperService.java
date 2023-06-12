package com.pk.backend.scrapper;

import com.pk.backend.nosql.model.CurrencyModel;
import com.pk.backend.nosql.model.NosqlData;
import com.pk.backend.nosql.repo.es.EsRepo;
import com.pk.backend.nosql.repo.mongo.MongoRepo;
import com.pk.backend.sql.model.SqlData;
import com.pk.backend.sql.repo.CurrencyRepo;
import com.pk.backend.sql.repo.DataRepo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class WebScraperService {

    private final EsRepo esRepo;
    private final MongoRepo mongoRepo;
    private final DataRepo dataRepo;
    private final CurrencyRepo currencyRepo;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH);

    public WebScraperService(EsRepo esRepo, MongoRepo mongoRepo, DataRepo dataRepo, CurrencyRepo currencyRepo) {
        this.esRepo = esRepo;
        this.mongoRepo = mongoRepo;
        this.dataRepo = dataRepo;
        this.currencyRepo = currencyRepo;
        //getHtml();
    }

    public void getHtml() {
        try {
            var document = Jsoup.connect("https://finance.yahoo.com/currencies")
                    .get();

            for (Element element : document.body().getElementsByClass("simpTblRow")) {
                saveToEs(element);
                //saveToMongo(element);
                //saveToPostgres(element);
            }

        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }

    private void saveToPostgres(Element element) throws IOException {
        var item = new SqlData();
        var id = UUID.randomUUID();
        item.setId(id);
        item.setSymbol(element.child(0).text());
        item.setName(element.child(1).text());
        item.setLastPrice(element.child(2).text());
        item.setChange(element.child(3).text());
        item.setChangePercent(element.child(4).text());
        item = dataRepo.save(item);
        getCurrencyModelSQL(element.child(0).text().trim().split("=")[0], item);
    }

    private void getCurrencyModelSQL(String cur, SqlData data) throws IOException {
        var doc = Jsoup.connect("https://finance.yahoo.com/quote/" + cur + "%3DX/history?period1=1070236800&period2=1685836800&interval=1d&filter=history&frequency=1d&includeAdjustedClose=true")
                .get();
        doc.getElementsByClass("BdT").stream()
                .filter(element -> !element.child(0).text().startsWith("*"))
                .forEach(element -> {
                    try {
                        getCurrencyModelSQL(element, data);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private void getCurrencyModelSQL(Element element, SqlData data) throws IOException {
        var item = new com.pk.backend.sql.model.CurrencyModel();
        item.setId(UUID.randomUUID());
        item.setDate(LocalDate.parse(element.child(0).text(), formatter));
        item.setOpen(element.child(1).text());
        item.setHigh(element.child(2).text());
        item.setLow(element.child(3).text());
        item.setClose(element.child(4).text());
        item.setAdjClose(element.child(5).text());
        item.setSqlData(data);
        currencyRepo.save(item);
    }


    private void saveToMongo(Element element) throws IOException {
        var item = new NosqlData();
        item.setId(UUID.randomUUID());
        item.setSymbol(element.child(0).text());
        item.setName(element.child(1).text());
        item.setLastPrice(Double.valueOf(element.child(2).text().replace(",", "")));
        item.setChange(element.child(3).text());
        item.setChangePercent(element.child(4).text());
        item.setCurrencyModel(getCurrencyModel(element.child(0).text().trim().split("=")[0]));
        mongoRepo.save(item);
    }

    private void saveToEs(Element element) throws IOException {
        var item = new NosqlData();
        item.setId(UUID.randomUUID());
        item.setSymbol(element.child(0).text());
        item.setName(element.child(1).text());
        item.setLastPrice(Double.valueOf(element.child(2).text().replace(",", "")));
        item.setChange(element.child(3).text());
        item.setChangePercent(element.child(4).text());
        item.setCurrencyModel(getCurrencyModel(element.child(0).text().trim().split("=")[0]));
        esRepo.save(item);
    }

    private List<CurrencyModel> getCurrencyModel(String cur) throws IOException {
        var doc = Jsoup.connect("https://finance.yahoo.com/quote/" + cur + "%3DX/history?period1=1070236800&period2=1685836800&interval=1d&filter=history&frequency=1d&includeAdjustedClose=true")
                .get();
        return doc.getElementsByClass("BdT").stream()
                .filter(element -> !element.child(0).text().startsWith("*"))
                .map(element -> {
                    try {
                        return getCurrencyModel(element);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
    }

    private CurrencyModel getCurrencyModel(Element element) throws IOException {
        var item = new CurrencyModel();
        item.setDate(LocalDate.parse(element.child(0).text(), formatter));
        item.setOpen(Double.valueOf(element.child(1).text().replace(",", "")));
        item.setHigh(Double.valueOf(element.child(2).text().replace(",", "")));
        item.setLow(Double.valueOf(element.child(3).text().replace(",", "")));
        item.setClose(Double.valueOf(element.child(4).text().replace(",", "")));
        item.setAdjClose(Double.valueOf(element.child(5).text().replace(",", "")));
        return item;
    }
}
