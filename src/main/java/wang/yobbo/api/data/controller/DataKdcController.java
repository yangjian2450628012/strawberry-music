package wang.yobbo.api.data.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wang.yobbo.api.data.util.MusicServerUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xiaoJ on 2017/10/16.
 */
@Controller
@RequestMapping(value = "/ms/g")
public class DataKdcController {

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public String search(HttpServletRequest request){
        String key = request.getParameter("key");
        if(null == key || "".equals(key)){
            return "{\"ret\":1,\"msg\":\"key不能为空!\"}";
        }
        int page = Integer.valueOf(request.getParameter("page"));
        int pageSize = Integer.valueOf(request.getParameter("pageSize"));
        MusicServerUtil musicServerUtil = new MusicServerUtil();
        try {
            String rs = musicServerUtil.getKgSearchList(key, page, pageSize, request.getHeader("user-agent"));
            //TODO做逻辑处理
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
