package wang.yobbo.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import wang.yobbo.properties.APIProperties;

import java.util.Map;

/**
 * Created by xiaoJ on 2017/7/4.
 */
@Controller
public class IndexApplication {

    @GetMapping(value = "/")
    public String toIndex(){
        APIProperties apiProperties = new APIProperties();
        Map kg_detail_search_url = apiProperties.getKg();
        System.out.println(kg_detail_search_url);
        return "index";
    }

    @GetMapping("/manage")
    public  String toHome(){
        return "manage";
    }

}
