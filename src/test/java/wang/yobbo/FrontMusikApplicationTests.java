package wang.yobbo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.yobbo.properties.APIProperties;
import wang.yobbo.properties.GirlProperties;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FrontMusikApplicationTests {
	@Autowired GirlProperties girlProperties;
	@Autowired
    APIProperties apiProperties;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testProperties(){
//        Integer age = this.girlProperties.getAge();
//        System.out.println(age);
        Map<String, Object> kg = this.apiProperties.getKg();
        System.out.println(kg);

    }

}
