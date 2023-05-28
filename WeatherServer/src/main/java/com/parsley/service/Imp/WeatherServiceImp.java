package com.parsley.service.Imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.parsley.domain.DTO.PostDataDTO;
import com.parsley.domain.DTO.ProcessDTO;
import com.parsley.domain.DTO.ServerDTO;
import com.parsley.domain.entity.ResponseDTO;
import com.parsley.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class WeatherServiceImp implements WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseDTO<String> predictWeather(PostDataDTO postDataDTO) {
        ProcessDTO processDTO = postDataDTO.getProcessDTO();
        Integer processIndex = postDataDTO.getProcessIndex();
        Map<String,Object> dataMap = postDataDTO.getDataMap();
        ServerDTO thisServer = processDTO.getServers().get(processIndex);
        ServerDTO nextServer = null;
        if(processDTO.getServers().size()!=processIndex+1){
            nextServer = processDTO.getServers().get(processIndex+1);
        }
        thisServer.getInputFunctions().forEach(function -> {
            if(function.getType().equals("img")){
//                dataMap.get("")
            }
        });
        try {
            InetAddress ip = null;
            ip = InetAddress.getLocalHost();
            Socket socket = new Socket(ip.getHostAddress(),10086);
            OutputStream os = socket.getOutputStream();
            PrintStream out = new PrintStream(os);
            //begin 获取网络的气温
            dataMap.put("temperature",Float.valueOf(dataMap.get("temperature").toString()));
            if(dataMap.get("weather")==null){
                dataMap.put("weather",getWeatherFromWeb());
            }
            if(dataMap.get("windpower")==null){
                dataMap.put("windpower",getWindPowerFromWeb());
            }
            if(dataMap.get("winddirection")==null){
                dataMap.put("winddirection",getWindDirectionFromWeb());
            }
            if(dataMap.get("humidity")==null){
                dataMap.put("humidity",getHumidityFromWeb());
            }
            //end
            out.print(JSON.toJSONString(dataMap));
            out.print("over");
            InputStream inputStream = socket.getInputStream();
            BufferedReader br = new BufferedReader((new InputStreamReader(inputStream)));
            String tmp = null;
            StringBuilder sb = new StringBuilder();
            // 读取内容
            while((tmp=br.readLine())!=null)
                sb.append(tmp).append('\n');
            dataMap = JSON.parseObject(sb.toString(), HashMap.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (nextServer!=null){
            String serverPath=nextServer.getPath();
            postDataDTO.setDataMap(dataMap);
            postDataDTO.setProcessIndex(processIndex+1);
            serverPath="http://"+serverPath;
            return restTemplate.postForObject(serverPath,postDataDTO,ResponseDTO.class);
        }else {
            return ResponseDTO.succData(JSON.toJSONString(dataMap));
        }
    }

    public Integer getWindPowerFromWeb(){
        try {
            ResponseEntity<String> response = requestTianQi();
            JSONObject jsonObject = JSONObject.parseObject(response.getBody());
            Double windpower = Double.parseDouble(jsonObject.getJSONObject("data").getJSONObject("real").getJSONObject("wind").get("speed").toString());
            if(windpower<=3){
                windpower = 3.0;
            }
            return Integer.valueOf(windpower.intValue());
        } catch (Exception e) {
            System.out.println("请求天气出问题了");
        }
        return -1;
    }

    public Integer getWindDirectionFromWeb(){
        String[] winddirections = {"东北","东","南", "西南", "西", "东南", "西北", "北"};
        try {
            ResponseEntity<String> response = requestTianQi();
            JSONObject jsonObject = JSONObject.parseObject(response.getBody());
            String winddirection = jsonObject.getJSONObject("data").getJSONObject("real").getJSONObject("wind").get("direct").toString();
            Integer result = Arrays.asList(winddirections).indexOf(winddirection);
            if(result == -1){
                result = new Random().nextInt(winddirections.length);
            }
            return result;
        } catch (Exception e) {
            System.out.println("请求天气出问题了");
        }

        return -1;
    }

    public Integer getWeatherFromWeb(){
        String[] weathers = {"雨","晴", "阴", "多云", "浮尘", "雾", "大雨", "小雨", "中雨", "阵雨", "雷阵雨"};
        try {
            ResponseEntity<String> response = requestTianQi();
            JSONObject jsonObject = JSONObject.parseObject(response.getBody());
            String weather = jsonObject.getJSONObject("data").getJSONObject("real").getJSONObject("weather").get("info").toString();
            return Arrays.asList(weathers).indexOf(weather);
        } catch (Exception e) {
            System.out.println("请求天气出问题了");
        }
        return -1;
    }

    public float getHumidityFromWeb(){
        try {
            ResponseEntity<String> response = requestTianQi();
            JSONObject jsonObject = JSONObject.parseObject(response.getBody());
            Float humidity = Float.parseFloat(jsonObject.getJSONObject("data").getJSONObject("real").getJSONObject("weather").get("humidity").toString());
            return humidity;
        } catch (Exception e) {
            System.out.println("请求天气出问题了");
        }
        return -1;
    }

    private ResponseEntity<String> requestTianQi() throws Exception{
        RestTemplate restTemplate =  new RestTemplate();
        restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
        String url = "http://www.nmc.cn/rest/weather?stationid=57494";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
        HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
        return response;
    }


}
