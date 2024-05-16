package com.my.movie.demo.user.component;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

//https://www.kobis.or.kr/kobisopenapi/homepg/apiservice/searchServiceInfo.do

@Component
public class OpenApiManager {

    private final String CHART_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
    private final String serviceKey = "?key=70c695e7121e388aa973be0701c2af4d";

    private final String SEARCH_URL="https://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
    private final String MSEARCH_URL="http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json";

    public String makeUrl1() throws UnsupportedEncodingException {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
        calendar.add(Calendar.DATE, -1);
        String chkDate = SDF.format(calendar.getTime());
        return CHART_URL + serviceKey +"&targetDt="+chkDate;
    }

    public JSONArray makeUrl2(String group, String input, int page) throws IOException, ParseException {
        String urll= SEARCH_URL + serviceKey+"&itemPerPage=100&"+group+"="+input+"&curPage="+page;
        String result="";
            String urlStr = urll;
            URL url = new URL(urlStr);
            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = br.readLine();

            JSONParser jsonParser=new JSONParser();
            JSONObject jsonObject=(JSONObject) jsonParser.parse(result);

            JSONObject movieListResult=(JSONObject) jsonObject.get("movieListResult");
            JSONArray movieArr=(JSONArray)movieListResult.get("movieList");
            return movieArr;
    }
    public JSONArray makeUrl3(String movieCd) throws IOException, ParseException {
        String urll= MSEARCH_URL + serviceKey+"&movieCd"+"="+movieCd;
        String result="";
        String urlStr = urll;
        URL url = new URL(urlStr);
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        result = br.readLine();

        JSONParser jsonParser=new JSONParser();
        JSONObject jsonObject=(JSONObject) jsonParser.parse(result);

        JSONObject movieListResult=(JSONObject) jsonObject.get("movieListResult");
        JSONArray movieArr=(JSONArray)movieListResult.get("movieList");
        return movieArr;
    }

}
