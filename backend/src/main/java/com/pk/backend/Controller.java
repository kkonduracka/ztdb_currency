package com.pk.backend;

import com.pk.backend.nosql.model.NosqlData;
import com.pk.backend.nosql.repo.es.EsRepo;
import com.pk.backend.nosql.repo.mongo.MongoRepo;
import com.pk.backend.sql.dto.CurrencyDTO;
import com.pk.backend.sql.dto.DataDTO;
import com.pk.backend.sql.model.CurrencyModel;
import com.pk.backend.sql.model.SqlData;
import com.pk.backend.sql.repo.CurrencyRepo;
import com.pk.backend.sql.repo.DataRepo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
@CrossOrigin
public class Controller {

    private final EsRepo esRepo;
    private final MongoRepo mongoRepo;

    private final CurrencyRepo currencyRepo;
    private final DataRepo dataRepo;

    @GetMapping("/currencies")
    public List<String> getCurrencies() {
        return dataRepo.findAll().stream().map(SqlData::getName).toList();
    }

    @GetMapping("/getChartData")
    public Object getChartData(@RequestParam LocalDate startDate,
                               @RequestParam LocalDate endDate,
                               @RequestParam String currency,
                               @RequestParam String database) {
        switch (database) {
            case "Elastic":
                var start = System.currentTimeMillis();
                var data = getChartDataES(startDate, endDate, currency);
                var end = System.currentTimeMillis();
                data.setTime(String.valueOf(end - start));
                return data;
            case "MongoDB":
                start = System.currentTimeMillis();
                var mdata = getChartDataMongo(startDate, endDate, currency);
                end = System.currentTimeMillis();
                mdata.setTime(String.valueOf(end - start));
                return mdata;
            default:
                start = System.currentTimeMillis();
                var newData = getChartDataPg(startDate, endDate, currency);
                end = System.currentTimeMillis();
                newData.setTime(String.valueOf(end - start));
                return newData;
        }
    }


    @GetMapping("/testCase1")
    public HashMap<String, String> getMaxHighCurrencyEURUSDValueIn2023ForEveryDB(){
        var result = new HashMap<String, String>();
        var start = System.currentTimeMillis();
        getMaxHighCurrencyEURUSDValueIn2023ForPg();
        var end = System.currentTimeMillis();
        result.put("Postgres", String.valueOf(end - start));
        start = System.currentTimeMillis();
        getMaxHighCurrencyEURUSDValueIn2023ForMongo();
        end = System.currentTimeMillis();
        result.put("MongoDB", String.valueOf(end - start));
        start = System.currentTimeMillis();
        getMaxHighCurrencyEURUSDValueIn2023ForEs();
        end = System.currentTimeMillis();
        result.put("Elastic", String.valueOf(end - start));
        return result;
    }

    @GetMapping("/testCase2")
    public HashMap<String, String> getNumberOfDaysWhereOpenWas70To100PercentOfHigh(){
        var result = new HashMap<String, String>();
        var start = System.currentTimeMillis();
        getNumberOfDaysWhereOpenWas70To100PercentOfHighForPg();
        var end = System.currentTimeMillis();
        result.put("Postgres", String.valueOf(end - start));
        start = System.currentTimeMillis();
        getNumberOfDaysWhereOpenWas70To100PercentOfHighForMongo();
        end = System.currentTimeMillis();
        result.put("MongoDB", String.valueOf(end - start));
        start = System.currentTimeMillis();
        getNumberOfDaysWhereOpenWas70To100PercentOfHighForEs();
        end = System.currentTimeMillis();
        result.put("Elastic", String.valueOf(end - start));
        return result;
    }

    @GetMapping("/testCase3")
    public HashMap<String, String> getDaysWhereOpenIsHigherThanClose(){
        var result = new HashMap<String, String>();
        var start = System.currentTimeMillis();
        getDaysWhereOpenIsHigherThanCloseForPg();
        var end = System.currentTimeMillis();
        result.put("Postgres", String.valueOf(end - start));
        start = System.currentTimeMillis();
        getDaysWhereOpenIsHigherThanCloseForMongo();
        end = System.currentTimeMillis();
        result.put("MongoDB", String.valueOf(end - start));
        start = System.currentTimeMillis();
        getDaysWhereOpenIsHigherThanCloseForEs();
        end = System.currentTimeMillis();
        result.put("Elastic", String.valueOf(end - start));
        return result;
    }

    private void getDaysWhereOpenIsHigherThanCloseForEs() {
        var data = esRepo.findByName("EUR/USD");
        data.getCurrencyModel().stream().filter(currencyModel -> Double.parseDouble(currencyModel.getOpen()) > Double.parseDouble(currencyModel.getClose())).count();
    }

    private void getDaysWhereOpenIsHigherThanCloseForMongo() {
        var data = mongoRepo.findByName("EUR/USD");
        data.getCurrencyModel().stream().filter(currencyModel -> Double.parseDouble(currencyModel.getOpen()) > Double.parseDouble(currencyModel.getClose())).count();
    }

    private void getDaysWhereOpenIsHigherThanCloseForPg() {
        var data = currencyRepo.findAllBySqlDataId(dataRepo.findByName("EUR/USD").getId());
        data.stream().filter(currencyModel -> Double.parseDouble(currencyModel.getOpen()) > Double.parseDouble(currencyModel.getClose())).count();
    }


    private void getNumberOfDaysWhereOpenWas70To100PercentOfHighForEs() {
        var data = esRepo.findByName("EUR/USD");
        data.getCurrencyModel().stream().filter(currencyModel -> Double.parseDouble(currencyModel.getOpen()) >= Double.parseDouble(currencyModel.getHigh()) * 0.7 && Double.parseDouble(currencyModel.getOpen()) <= Double.parseDouble(currencyModel.getHigh())).count();
    }

    private void getNumberOfDaysWhereOpenWas70To100PercentOfHighForMongo() {
        var data = mongoRepo.findByName("EUR/USD");
        data.getCurrencyModel().stream().filter(currencyModel -> Double.parseDouble(currencyModel.getOpen()) >= Double.parseDouble(currencyModel.getHigh()) * 0.7 && Double.parseDouble(currencyModel.getOpen()) <= Double.parseDouble(currencyModel.getHigh())).count();
    }

    private void getNumberOfDaysWhereOpenWas70To100PercentOfHighForPg() {
        var data = currencyRepo.findAllBySqlDataId(dataRepo.findByName("EUR/USD").getId());
        data.stream().filter(currencyModel -> Double.parseDouble(currencyModel.getOpen()) >= Double.parseDouble(currencyModel.getHigh()) * 0.7 && Double.parseDouble(currencyModel.getOpen()) <= Double.parseDouble(currencyModel.getHigh())).count();
    }

    private void getMaxHighCurrencyEURUSDValueIn2023ForEs() {
        var data = esRepo.findByName("EUR/USD");
        data.getCurrencyModel().stream().filter(currencyModel -> currencyModel.getDate().getYear() == 2023).mapToDouble(x -> Double.parseDouble(x.getHigh())).max();
    }

    private void getMaxHighCurrencyEURUSDValueIn2023ForMongo() {
        var data = mongoRepo.findByName("EUR/USD");
        data.getCurrencyModel().stream().filter(currencyModel -> currencyModel.getDate().getYear() == 2023).mapToDouble(x -> Double.parseDouble(x.getHigh())).max();
    }

    private void getMaxHighCurrencyEURUSDValueIn2023ForPg() {
        var data = currencyRepo.findAllBySqlDataId(dataRepo.findByName("EUR/USD").getId());
        data.stream().filter(currencyModel -> currencyModel.getDate().getYear() == 2023).mapToDouble(x -> Double.parseDouble(x.getHigh())).max();
    }

    private NosqlData getChartDataMongo(LocalDate startDate,
                                     LocalDate endDate,
                                     String currency) {
        var data = mongoRepo.findByName(currency);
        if (Objects.isNull(startDate) && Objects.isNull(endDate)) {
            return data;
        } else if (Objects.isNull(startDate)) {
            data.getCurrencyModel().removeIf(currencyModel -> currencyModel.getDate().isAfter(endDate));
            return data;
        } else if (Objects.isNull(endDate)) {
            data.getCurrencyModel().removeIf(currencyModel -> currencyModel.getDate().isBefore(startDate));
            return data;
        } else {
            data.getCurrencyModel().removeIf(currencyModel -> currencyModel.getDate().isBefore(startDate) || currencyModel.getDate().isAfter(endDate));
            return data;
        }
    }
    private NosqlData getChartDataES(LocalDate startDate,
                                     LocalDate endDate,
                                     String currency) {
        var data = esRepo.findByName(currency);
        if (Objects.isNull(startDate) && Objects.isNull(endDate)) {
            return data;
        } else if (Objects.isNull(startDate)) {
            data.getCurrencyModel().removeIf(currencyModel -> currencyModel.getDate().isAfter(endDate));
            return data;
        } else if (Objects.isNull(endDate)) {
            data.getCurrencyModel().removeIf(currencyModel -> currencyModel.getDate().isBefore(startDate));
            return data;
        } else {
            data.getCurrencyModel().removeIf(currencyModel -> currencyModel.getDate().isBefore(startDate) || currencyModel.getDate().isAfter(endDate));
            return data;
        }
    }

    private DataDTO getChartDataPg(LocalDate startDate,
                                   LocalDate endDate,
                                   String currency) {
        if (Objects.isNull(startDate) && Objects.isNull(endDate)) {
            var data = map(dataRepo.findByName(currency));
            data.setCurrencyModel(currencyRepo.findAllBySqlDataId(data.getId()).stream().map(this::map).toList());
            return data;
        } else if (Objects.isNull(startDate)) {
            var data = map(dataRepo.findByName(currency));
            data.setCurrencyModel(currencyRepo.findAllBySqlDataIdAndDateBefore(data.getId(),endDate).stream().map(this::map).toList());
            return data;
        } else if (Objects.isNull(endDate)) {
            var data = map(dataRepo.findByName(currency));
            data.setCurrencyModel(currencyRepo.findAllBySqlDataIdAndDateAfter(data.getId(),startDate).stream().map(this::map).toList());
            return data;
        } else {
            var data = map(dataRepo.findByName(currency));
            data.setCurrencyModel(currencyRepo.findAllBySqlDataIdAndDateBetween(data.getId(),startDate,endDate).stream().map(this::map).toList());
            return data;
        }
    }

    private DataDTO map(SqlData data){
        var chartData = new DataDTO();
        chartData.setId(data.getId());
        chartData.setName(data.getName());
        chartData.setSymbol(data.getSymbol());
        chartData.setLastPrice(data.getLastPrice());
        chartData.setChange(data.getChange());
        chartData.setChangePercent(data.getChangePercent());
        return chartData;
    }

    private CurrencyDTO map (CurrencyModel model){
        var currency = new CurrencyDTO();
        currency.setId(model.getId());
        currency.setDate(model.getDate());
        currency.setOpen(model.getOpen());
        currency.setHigh(model.getHigh());
        currency.setLow(model.getLow());
        currency.setClose(model.getClose());
        currency.setAdjClose(model.getAdjClose());
        return currency;
    }

}