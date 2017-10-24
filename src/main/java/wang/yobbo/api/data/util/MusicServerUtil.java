package wang.yobbo.api.data.util;

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

    private static String kgSearchUrl = "http://mobilecdn.kugou.com/api/v3/search/song?format=jsonp&keyword=%s&page=%s&pagesize=%s&showtype=1";
    private static String qqSearchUrl = "http://c.y.qq.com/soso/fcgi-bin/search_for_qq_cp?format=json&ie=utf-8&zhidaqu=1&catZhida=1&t=0&flag=1&sem=1&aggr=1&w=%s&perpage=%s&n=%s&p=%s&remoteplace=txt.mqq.all";
    private static String[] searchArray = new String[]{"artist","song_name","album_name","hash","album_id","mvhash","data_source"};

    //{"code":0,"data":{"keyword":"毛不易","priority":0,"qc":[],"semantic":{"curnum":0,"curpage":1,"list":[],"totalnum":0},"song":{"curnum":3,"curpage":1,"list":[{"albumid":2188465,"albummid":"002xoonH2Bk7FR","albumname":"明日之子 第7期","albumname_hilight":"明日之子 第7期","alertid":100002,"chinesesinger":0,"docid":"1202701566236419897","grp":[],"interval":179,"isonly":1,"lyric":"","lyric_hilight":"","msgid":14,"nt":3135213856,"pay":{"payalbum":0,"payalbumprice":0,"paydownload":1,"payinfo":1,"payplay":0,"paytrackmouth":1,"paytrackprice":200},"preview":{"trybegin":52783,"tryend":86831,"trysize":545853},"pubtime":1501257600,"pure":0,"singer":[{"id":1507534,"mid":"001BHDR33FZVZ0","name":"毛不易","name_hilight":"<span class=\"c_tx_highlight\">毛不易</span>"}],"size128":2870745,"size320":7176561,"sizeape":18775297,"sizeflac":18896223,"sizeogg":4089425,"songid":203451421,"songmid":"003kLvu04bLGzi","songname":"消愁 (Live)","songname_hilight":"消愁 (Live)","stream":5,"switch":636675,"t":1,"tag":0,"type":0,"ver":0,"vid":""},{"albumid":2196371,"albummid":"0001n7a82gh6IY","albumname":"明日之子 第8期","albumname_hilight":"明日之子 第8期","alertid":100002,"chinesesinger":0,"docid":"4600594890044038094","grp":[],"interval":171,"isonly":1,"lyric":"","lyric_hilight":"","msgid":14,"nt":3063726294,"pay":{"payalbum":0,"payalbumprice":0,"paydownload":1,"payinfo":1,"payplay":0,"paytrackmouth":1,"paytrackprice":200},"preview":{"trybegin":0,"tryend":0,"trysize":0},"pubtime":1501862400,"pure":0,"singer":[{"id":1507534,"mid":"001BHDR33FZVZ0","name":"毛不易","name_hilight":"<span class=\"c_tx_highlight\">毛不易</span>"}],"size128":2748709,"size320":6871459,"sizeape":17082769,"sizeflac":17109328,"sizeogg":3946204,"songid":203514624,"songmid":"00375L600p9sxv","songname":"像我这样的人 (Live)","songname_hilight":"像我这样的人 (Live)","stream":1,"switch":636675,"t":1,"tag":10,"type":0,"ver":0,"vid":""},{"albumid":2215797,"albummid":"002iBxBy3bAmCx","albumname":"明日之子 第11期","albumname_hilight":"明日之子 第11期","alertid":100002,"chinesesinger":0,"docid":"18141767068084872733","grp":[],"interval":214,"isonly":1,"lyric":"","lyric_hilight":"","msgid":14,"nt":2807595429,"pay":{"payalbum":0,"payalbumprice":0,"paydownload":1,"payinfo":1,"payplay":0,"paytrackmouth":1,"paytrackprice":200},"preview":{"trybegin":0,"tryend":0,"trysize":0},"pubtime":1503676800,"pure":0,"singer":[{"id":1507534,"mid":"001BHDR33FZVZ0","name":"毛不易","name_hilight":"<span class=\"c_tx_highlight\">毛不易</span>"}],"size128":3435408,"size320":8588218,"sizeape":23490035,"sizeflac":23672530,"sizeogg":5033823,"songid":203705594,"songmid":"002WLDmw0vkHtC","songname":"借 (Live)","songname_hilight":"借 (Live)","stream":7,"switch":636675,"t":1,"tag":10,"type":0,"ver":0,"vid":""}],"totalnum":69},"totaltime":0,"zhida":{"albumnum":0,"singerid":1507534,"singermid":"001BHDR33FZVZ0","singername":"毛不易","songnum":20,"type":2}},"message":"","notice":"","subcode":0,"time":1508242435,"tips":""}
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
                    JSONArray songList = song.getJSONArray("list");
                    int total = song.getIntValue("totalnum");
                    JSONObject musicInfo = new JSONObject();
                    musicInfo.put("total", total);
                    musicInfo.put("code", "0");
                    if(songList.isEmpty()){
                        return "";
                    }
                    for(int i=0;i<songList.size();i++){
                        JSONObject detail = (JSONObject) songList.get(i);
                        JSONObject newDetail = new JSONObject();
                        newDetail.put(searchArray[0], detail.getJSONObject("singer").getString("name"));
                        newDetail.put(searchArray[1], detail.getString("songname"));
                        newDetail.put(searchArray[2], detail.getString("albumname"));
                        newDetail.put(searchArray[3], detail.getString("songmid"));
                        newDetail.put(searchArray[4], detail.getString("albummid"));
                        newDetail.put(searchArray[5], detail.getString(""));
                        newDetail.put(searchArray[6], "q");
                        musicInfo.put("data", newDetail);
                    }
                    JSONObject zhida = data.getJSONObject("zhida");
                    if(!zhida.isEmpty()){
                        JSONObject singer = new JSONObject();
                        singer.put("singerid", zhida.getString("singerid"));
                        singer.put("singermid", zhida.getString("singername"));
                        musicInfo.put("singer", singer);
                    }
                    return musicInfo.toJSONString();
                }else{
                    throw new Exception(body.getString("message"));
                }
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        return "";
    }

    //{"status":1,"error":"","data":{"aggregation":[{"key":"DJ","count":0},{"key":"现场","count":0},{"key":"广场舞","count":0},{"key":"伴奏","count":0},{"key":"铃声","count":0}],"tab":"全部","info":[{"pay_type_320":0,"m4afilesize":1046139,"price_sq":0,"filesize":4079903,"source":"","bitrate":128,"topic":"","price":0,"Accompany":0,"old_cpy":1,"fail_process_sq":0,"singername":"薛之谦、毛不易","pay_type":0,"sourceid":0,"topic_url":"","fail_process_320":0,"pkg_price":0,"feetype":0,"filename":"薛之谦、毛不易 - 一半 (Live)","price_320":0,"extname":"mp3","320hash":"","mvhash":"ad3cfc78b18be2e06b69e1e82a3229ca","privilege":0,"group":[{"pay_type_320":0,"m4afilesize":1046139,"price_sq":0,"filesize":4079903,"source":"","bitrate":128,"topic":"","price":0,"Accompany":0,"old_cpy":1,"fail_process_sq":0,"singername":"薛之谦、毛不易","pay_type":0,"sourceid":0,"topic_url":"","fail_process_320":0,"pkg_price":0,"feetype":0,"filename":"薛之谦、毛不易 - 一半 (Live)","price_320":0,"extname":"mp3","320hash":"","mvhash":"ad3cfc78b18be2e06b69e1e82a3229ca","privilege":0,"album_audio_id":78476247,"album_id":"3501369","ownercount":30787,"rp_publish":1,"rp_type":"audio","audio_id":28162194,"320filesize":0,"isnew":0,"duration":255,"pkg_price_sq":0,"pkg_price_320":0,"srctype":1,"songname":"一半 (Live)","fail_process":0,"sqhash":"","album_name":"明日之子 第十二期","hash":"f9bc758420ae566899186d5103227206","pay_type_sq":0,"320privilege":0,"sqprivilege":0,"sqfilesize":0,"othername":""}],"album_audio_id":88064569,"album_id":"3015376","ownercount":30787,"rp_publish":1,"rp_type":"audio","audio_id":28162194,"320filesize":0,"isnew":0,"duration":255,"pkg_price_sq":0,"pkg_price_320":0,"srctype":1,"songname":"一半 (Live)","fail_process":0,"sqhash":"","album_name":"巨星不易工作室","hash":"f9bc758420ae566899186d5103227206","pay_type_sq":0,"320privilege":0,"sqprivilege":0,"sqfilesize":0,"othername":""},{"pay_type_320":0,"m4afilesize":706962,"price_sq":0,"filesize":2738694,"source":"","bitrate":128,"topic":"","price":0,"Accompany":0,"old_cpy":1,"fail_process_sq":0,"singername":"毛不易","pay_type":0,"sourceid":0,"topic_url":"","fail_process_320":0,"pkg_price":0,"feetype":0,"filename":"毛不易 - 消愁 (2017酷狗星乐坊现场)","price_320":0,"extname":"mp3","320hash":"","mvhash":"57ff609aa15f7afcd2986a843194063d","privilege":0,"group":[],"album_audio_id":75420100,"album_id":"","ownercount":30765,"rp_publish":1,"rp_type":"audio","audio_id":28031454,"320filesize":0,"isnew":0,"duration":171,"pkg_price_sq":0,"pkg_price_320":0,"srctype":1,"songname":"消愁 (2017酷狗星乐坊现场)","fail_process":0,"sqhash":"","album_name":"","hash":"e1416464e3cfd0f57b0426cf68b7f425","pay_type_sq":0,"320privilege":0,"sqprivilege":0,"sqfilesize":0,"othername":""},{"pay_type_320":0,"m4afilesize":1560995,"price_sq":0,"filesize":6087682,"source":"","bitrate":128,"topic":"","price":0,"Accompany":0,"old_cpy":1,"fail_process_sq":0,"singername":"毛不易","pay_type":0,"sourceid":0,"topic_url":"","fail_process_320":0,"pkg_price":0,"feetype":0,"filename":"毛不易 - 消愁-DJ黑豹版","price_320":0,"extname":"mp3","320hash":"c2b94436210f567c53762ade4f300efa","mvhash":"","privilege":0,"group":[],"album_audio_id":80752376,"album_id":"","ownercount":9128,"rp_publish":1,"rp_type":"audio","audio_id":28230953,"320filesize":15216919,"isnew":0,"duration":380,"pkg_price_sq":0,"pkg_price_320":0,"srctype":1,"songname":"消愁-DJ黑豹版","fail_process":0,"sqhash":"","album_name":"","hash":"9cb71d9e6cdf80c39a2d2b6e8a52eaa2","pay_type_sq":0,"320privilege":0,"sqprivilege":0,"sqfilesize":0,"othername":""}],"correctiontype":0,"timestamp":1508243427,"allowerr":0,"total":23,"istag":0,"istagresult":0,"forcecorrection":0,"correctiontip":""},"errcode":0}
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
                    if(info.isEmpty()){
                        return "";
                    }
                    com.alibaba.fastjson.JSONObject musicInfo = new com.alibaba.fastjson.JSONObject();
                    JSONArray musicInfoList = new JSONArray();
                    musicInfo.put("total", total);
                    musicInfo.put("code", "0");
                    for (int i=0;i<info.size();i++)
                    {
                        JSONObject detail = (JSONObject)info.get(i);
                        JSONObject newDetail = new JSONObject();
                        newDetail.put(searchArray[0], detail.getString("singername")); //歌手
                        newDetail.put(searchArray[1], detail.getString("songname")); //歌曲
                        newDetail.put(searchArray[2], detail.getString("album_name")); //专辑
                        newDetail.put(searchArray[3], detail.getString("320hash")); //320音效地址id
                        newDetail.put(searchArray[4], detail.getString("album_id")); //专辑id
                        newDetail.put(searchArray[5], detail.getString("mvhash"));//mv地址id
                        newDetail.put(searchArray[6], "k"); //数据来源
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
