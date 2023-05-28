package com.parsley.service.Imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.parsley.domain.DTO.PostDataDTO;
import com.parsley.domain.DTO.ProcessDTO;
import com.parsley.domain.DTO.ServerDTO;
import com.parsley.domain.entity.ResponseDTO;
import com.parsley.service.GPT3Service;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GPT3ServiceImp implements GPT3Service {

    @Autowired
    private RestTemplate template;

    private static List<Map<String, Object>> messages = new ArrayList<>();


    @Override
    public ResponseDTO<String> chat(PostDataDTO postDataDTO) {
        ProcessDTO processDTO = postDataDTO.getProcessDTO();
        Integer processIndex = postDataDTO.getProcessIndex();
        Map<String,Object> dataMap = postDataDTO.getDataMap();
        ServerDTO thisServer = processDTO.getServers().get(processIndex);
        ServerDTO nextServer = null;
        if(processDTO.getServers().size()!=processIndex+1){
            nextServer = processDTO.getServers().get(processIndex+1);
        }
        String result = requestGPT3(dataMap.get("txt").toString());
        dataMap.clear();
        dataMap.put("txt",result);
        if (nextServer!=null){
            String serverPath=nextServer.getPath();
            postDataDTO.setDataMap(dataMap);
            postDataDTO.setProcessIndex(processIndex+1);
            serverPath="http://"+serverPath;
            return template.postForObject(serverPath,postDataDTO,ResponseDTO.class);
        }else {
            return ResponseDTO.succData(JSON.toJSONString(dataMap));
        }
    }

    public static String requestGPT3(String content){
        String proxyHost = "127.0.0.1"; // 代理所在的ip
        int proxyPort = 7890; // 代理所在的端口
        HttpHost proxy = new HttpHost(proxyHost,proxyPort); // 生成代理Host
        HttpClient httpClient = HttpClientBuilder.create().setProxy(proxy).build();
        // 设置RestTemplate的HTTP客户端
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory); // 使用代理的RestTemplate
        Map<String, Object> sentence = new HashMap(); // 本次发送的句子
        Map<String, Object> postData = new HashMap<>(); // 本次发送的完整参数
        sentence.put("role","user");
        sentence.put("content",content);
        messages.add(sentence); // 历史消息添加当前句子
        postData.put("model","gpt-3.5-turbo");
        postData.put("messages",messages);
        restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
        String url = "https://api.openai.com/v1/chat/completions";
        HttpHeaders httpHeaders = new HttpHeaders(); // 请求头
        httpHeaders.setContentType(MediaType.APPLICATION_JSON); // 使用application/json 新版本会自动识别使用charset=UTF-8
        httpHeaders.setBearerAuth("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"); //chatgpt api_key
        String body = JSON.toJSONString(postData); // 生成请求体参数
        HttpEntity<String> entity = new HttpEntity<String>(body,httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(url,entity,String.class); // 发送post请求
        JSONObject jsonObject = JSONObject.parseObject(response.getBody()).getJSONArray("choices")
                .getJSONObject(0).getJSONObject("message"); // 解析返回数据
        messages.add(jsonObject.getInnerMap()); // 把ai返回的数据添加进历史记录
        return jsonObject.get("content").toString();
    }

}
