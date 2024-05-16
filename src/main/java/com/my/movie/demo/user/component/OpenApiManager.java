package com.my.movie.demo.user.component;

import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class OpenApiManager {

    private final String CHART_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
    private final String serviceKey = "?key=70c695e7121e388aa973be0701c2af4d";

    private final String SEARCH_URL="https://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";


    public String makeUrl1() throws UnsupportedEncodingException {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
        calendar.add(Calendar.DATE, -1);
        String chkDate = SDF.format(calendar.getTime());
        return CHART_URL + serviceKey +"&targetDt="+chkDate;
    }

    public String makeUrl2(String group, String input) throws UnsupportedEncodingException {
        return SEARCH_URL + serviceKey+"&itemPerPage=20&"+group+"="+input;
    }
}
