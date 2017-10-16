package wang.yobbo.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import wang.yobbo.api.data.proper.ApiProperties;
import wang.yobbo.properties.GirlProperties;

import java.util.Map;

/**
 * Created by xiaoJ on 2017/7/4.
 */
@Controller
public class IndexApplication {

    @GetMapping(value = "/")
    public String toIndex(){
        ApiProperties apiProperties = new ApiProperties();
        Map kg_detail_search_url = apiProperties.getKg();
        System.out.println(kg_detail_search_url);
        return "index";
    }

    @GetMapping("/manage")
    public  String toHome(){
        return "manage";
    }

}
