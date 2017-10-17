package wang.yobbo.api.data.kdc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wang.yobbo.api.data.util.MusicServerUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by xiaoJ on 2017/10/16.
 */
@Controller
@RequestMapping(value = "/ms/g")
public class DataKdcController {
    
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public String search(@RequestParam Map<String,Object> parama, HttpServletRequest request){
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String key = headerNames.nextElement();
            System.out.println("header==>  "+ key + "=" +request.getHeader(key));
        }
        System.out.println(parama);
        if(!parama.containsKey("key") || null == parama.get("key") || "".equals(parama.get("key"))){
            return "{\"ret\":1,\"msg\":\"key不能为空!\"}";
        }
        int page = Integer.valueOf(parama.get("page").toString());
        int pageSize = Integer.valueOf(parama.get("pageSize").toString());
        MusicServerUtil musicServerUtil = new MusicServerUtil();
        try {
            String r = musicServerUtil.getKgSearchList(parama.get("key").toString(), page, pageSize, request.getHeader("user-agent"));
            //TODO做逻辑处理
            return r;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    
}
