package wang.yobbo.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by xiaoJ on 2017/10/25.
 */
@Component
public class APIApplicationProperties implements CommandLineRunner {
    @Autowired private  APIProperties apiProperties;
    private static String kgSearchUrl           = null;
    private static String qqSearchUrl           = null;
    private static String kgDetailUrl           = null;
    private static String qqDetailUrl           = null;

    @Override
    public void run(String... strings) throws Exception {
        Map kgMap = this.apiProperties.getKg();
        System.out.println("kgmap:" + kgMap);
        Map qqMap = this.apiProperties.getQmsq();
        kgSearchUrl = kgMap.get("listUrl") != null ? kgMap.get("listUrl").toString() : "";
        kgDetailUrl = kgMap.get("detailUrl") != null ? kgMap.get("detailUrl").toString() : "";
        qqSearchUrl = qqMap.get("listUrl") != null ? qqMap.get("listUrl").toString() : "";
        qqDetailUrl = qqMap.get("detailUrl") != null ? qqMap.get("detailUrl").toString() : "";
        this.apiProperties = null;
        System.out.println(strings);
    }

    public static String getKgSearchUrl() {
        return kgSearchUrl;
    }

    public static String getQqSearchUrl() {
        return qqSearchUrl;
    }

    public static String getKgDetailUrl() {
        return kgDetailUrl;
    }

    public static String getQqDetailUrl() {
        return qqDetailUrl;
    }
}
