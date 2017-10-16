package wang.yobbo.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by xiaoJ on 2017/7/7.
 */
@Controller
@RequestMapping("menu")
public class MenuApplication {

    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String toMenu(){
        return "menu/index";
    }
}
