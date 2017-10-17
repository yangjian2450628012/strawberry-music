package wang.yobbo.api.data.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by xiaoJ on 2017/10/16.
 * 提供数据接口公共类
 */
public class MusicServerUtil {
    private static String kgSearchUrl = "http://mobilecdn.kugou.com/api/v3/search/song?format=jsonp&keyword=%s&page=%s&pageSize=%s&showtype=1";
    private static String qqSearchUrl = "http://c.y.qq.com/soso/fcgi-bin/search_for_qq_cp?format=json&ie=utf-8&zhidaqu=1&catZhida=1&t=0&flag=1&sem=1&aggr=1&w=%s&perpage=%s&n=%s&p=%s&remoteplace=txt.mqq.all";

    public String getQQSearchList(String key, int page, int pageSize, String userAgent) throws Exception {
        try {
            if(StringUtils.isEmpty(key)){
                throw new Exception("值不能为空!");
            }
            String url = String.format(qqSearchUrl, key, pageSize, pageSize, page);
            HttpHeaders headers = new HttpHeaders();
            headers.set("user-agent", userAgent);
            RestTemplate restTemplate = new RestTemplate();
            headers.set("user-agent", userAgent);
            headers.setAccept(Arrays.asList(new MediaType[]{new MediaType("application", "json", Charset.forName("UTF-8"))}));
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            HttpEntity<Map<String,Object>> requestEntity = new HttpEntity<>(null, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            if(response.getStatusCode().is2xxSuccessful())
            {
                JSONObject body = JSONObject.parseObject(response.getBody());
                if(0 == body.getIntValue("code")){
                    JSONObject data = body.getJSONObject("data");
                    JSONObject song = data.getJSONObject("song");
                    JSONObject zhida = data.getJSONObject("zhida");
                }else{
                    throw new Exception(body.getString("message"));
                }
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        return "";
    }

    //获取kg音乐搜索结果
    public String getKgSearchList(String key, int page, int pageSize, String userAgent) throws Exception {
        try {
            if(StringUtils.isEmpty(key)){
                throw new Exception("值不能为空!");
            }
            RestTemplate restTemplate = new RestTemplate();
            String url = String.format(kgSearchUrl, key, page, pageSize); //格式化url
            HttpHeaders headers = new HttpHeaders();
            headers.set("user-agent", userAgent);
            headers.setAccept(Arrays.asList(new MediaType[]{new MediaType("application", "json", Charset.forName("UTF-8"))}));
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            HttpEntity<Map<String,Object>> requestEntity = new HttpEntity<>(null, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            System.out.println(response.getBody());
            if(response.getStatusCode().is2xxSuccessful()){
                String body = response.getBody();
                if(body.indexOf("(") == 0 && body.lastIndexOf(")") == body.length()-1)
                {
                    body = body.substring(1, body.lastIndexOf(")"));
                }
                com.alibaba.fastjson.JSONObject data = com.alibaba.fastjson.JSONObject.parseObject(body);
                if("1".equals(data.getString("status"))){
                    com.alibaba.fastjson.JSONObject dataBody = data.getJSONObject("data");
                    int total = dataBody.getIntValue("total");
                    JSONArray info = dataBody.getJSONArray("info");
                    com.alibaba.fastjson.JSONObject musicInfo = new com.alibaba.fastjson.JSONObject();
                    JSONArray musicInfoList = new JSONArray();
                    musicInfo.put("total", total);
                    for (int i=0;i<info.size();i++)
                    {
                        JSONObject detail = (JSONObject)info.get(i);
                        JSONObject newDetail = new JSONObject();
                        newDetail.put("artist",detail.getString("singername")); //歌手
                        newDetail.put("song_name",detail.getString("songname")); //歌曲
                        newDetail.put("album_name",detail.getString("album_name")); //专辑
                        newDetail.put("320hash",detail.getString("320hash")); //320音效地址id
                        newDetail.put("album_id",detail.getString("album_id")); //专辑id
                        newDetail.put("mvhash",detail.getString("mvhash"));//mv地址id
                        musicInfoList.add(newDetail);
                    }
                    musicInfo.put("data", musicInfoList);
                    return musicInfo.toJSONString();
                }else{
                    throw new Exception(data.getString("error"));
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return "";
    }

    public static void main(String[] arg0){
        String s = "http://127.0.0.1:8080?name=%s&age=%s";
        s = String.format(s, "yangyang", 12);
        System.out.println(s);

        try {
//            String list = new MusicServerUtil().getKgSearchList("毛不易", 1, 10,null);
//            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
