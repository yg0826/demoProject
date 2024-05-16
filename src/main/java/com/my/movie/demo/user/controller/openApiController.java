package com.my.movie.demo.user.controller;

import com.my.movie.demo.user.component.OpenApiManager;
import com.my.movie.demo.user.dto.MovieDBDTO;
import com.my.movie.demo.user.dto.MovieDTO;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class openApiController {

    private final OpenApiManager openApiManager;

    @GetMapping("/movie/list/search")
    public String search(@RequestParam("group") String group,
                         @RequestParam("input") String input,
                         @RequestParam(name="page", defaultValue = "1") int page,
                         Model model) throws IOException, ParseException {
            List<MovieDBDTO> movieList=new ArrayList<>();
           JSONArray movieArr=openApiManager.makeUrl2(group, input, page);

            for(int i=0;i<movieArr.size();i++){
                JSONObject jsonObject=(JSONObject) movieArr.get(i);
                String movieNm= jsonObject.get("movieNm").toString();
                String movieCd= jsonObject.get("movieCd").toString();
                String openDt=jsonObject.get("openDt").toString();
                String prdtYear=jsonObject.get("prdtYear").toString();
                JSONArray directors=(JSONArray)jsonObject.get("directors");
                MovieDBDTO dbDto=new MovieDBDTO();
                if(!directors.isEmpty()) {
                    JSONObject director = (JSONObject) directors.get(0);
                    dbDto.setDirectors(director.get("peopleNm").toString());
                }
                dbDto.setMovieNm(movieNm);
                dbDto.setMovieCd(movieCd);
                dbDto.setPrdtYear(prdtYear);
                dbDto.setOpenDt(openDt);
                movieList.add(dbDto);
            }
        model.addAttribute("result",movieList);
        return "list.html";
    }

    @GetMapping("/movie/chart")
    public String getChart(Model model) throws IOException {
        String result="";
        List<MovieDTO> movieList=new ArrayList<>();
        try {
            String urlStr = openApiManager.makeUrl1();
            URL url = new URL(urlStr);
            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = br.readLine();

            JSONParser jsonParser=new JSONParser();
            JSONObject jsonObject=(JSONObject) jsonParser.parse(result);

            JSONObject movieListResult=(JSONObject) jsonObject.get("boxOfficeResult");
            JSONArray movieArr=(JSONArray)movieListResult.get("dailyBoxOfficeList");
            for(int i=0;i<movieArr.size();i++){
                jsonObject=(JSONObject) movieArr.get(i);
                String rank=(String) jsonObject.get("rank");
                String title=(String) jsonObject.get("movieNm");
                String audiAcc=(String) jsonObject.get("audiAcc");
                MovieDTO movieDto=new MovieDTO();
                movieDto.setRank(rank);
                movieDto.setTitle(title);
                movieDto.setAudiAcc(audiAcc);
                movieList.add(movieDto);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        Date today=new Date();
        model.addAttribute("result",movieList);
        model.addAttribute("today",today.toString());
        return "chart.html";
    }
}
