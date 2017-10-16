package wang.yobbo.api.data.proper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoJ on 2017/10/16.
 * 获取接口相关配置文件
 */
@Component
@ConfigurationProperties(prefix="music")
public class ApiProperties {
    private Map<String,Object> kg = new HashMap<>();
    private Map<String,Object> qmsq = new HashMap<>();

    public Map<String, Object> getKg() {
        return kg;
    }

    public Map<String, Object> getQmsq() {
        return qmsq;
    }

    public void setKg(Map<String, Object> kg) {
        this.kg = kg;
    }

    public void setQmsq(Map<String, Object> qmsq) {
        this.qmsq = qmsq;
    }
   
}
