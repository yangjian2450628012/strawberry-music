package wang.yobbo.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by xiaoJ on 2017/10/24.
 */
@Component
public class APIPropertiesApplication implements CommandLineRunner {
    @Autowired
    private APIProperties apiProperties;
    @Override
    public void run(String... strings) throws Exception {

    }
}
