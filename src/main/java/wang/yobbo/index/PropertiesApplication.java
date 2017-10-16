package wang.yobbo.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.yobbo.properties.GirlProperties;

/**
 * Created by xiaoJ on 2017/7/8.
 */
@RestController
public class PropertiesApplication {

    /* @Value("${cupSize}") private String cupSize;
    @Value("${age}") private Integer age;
    @Value("${content}") private String content;*/

    @Autowired
    private GirlProperties girlProperties;

    @RequestMapping(value = "/properties")
    public String goIndex(){
        return girlProperties.getCupSize() + girlProperties.getAge();
    }
}
