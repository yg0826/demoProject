package com.my.movie.demo.user.controller;

import com.my.movie.demo.user.component.OpenApiManager;
import com.my.movie.demo.user.dto.MovieDTO;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
//https://velog.io/@sujin1018/%EC%8A%A4%ED%94%84%EB%A7%81-json-%ED%8C%8C%EC%8B%B1%ED%95%98%EA%B8%B0

@Controller
@RequiredArgsConstructor
public class openApiController {

    private final OpenApiManager openApiManager;

    @GetMapping("/open-api")
    public String fetch(Model model) throws IOException {
        String result="";
        List<MovieDTO> movieList=new ArrayList<>();
        try {
            String urlStr = openApiManager.makeUrl();
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
        model.addAttribute("result",movieList);
        return "main.html";
    }
}
