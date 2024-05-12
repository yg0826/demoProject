package com.my.movie.demo.user.component;

import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class OpenApiManager {

    private final String BASE_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
    private final String serviceKey = "?key=70c695e7121e388aa973be0701c2af4d";

    public String makeUrl() throws UnsupportedEncodingException {
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
        return BASE_URL + serviceKey +"&targetDt="+dtFormat.format(new Date());
    }
}
