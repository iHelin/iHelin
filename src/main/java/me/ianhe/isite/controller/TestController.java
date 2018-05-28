package me.ianhe.isite.controller;

import com.google.common.collect.Lists;
import me.ianhe.isite.utils.JsonUtil;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author iHelin
 * @since 2017/12/20 15:12
 */
@RestController
public class TestController extends BaseController {

    private static String userAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1 wechatdevtools/1.01.1711020 MicroMessenger/6.5.7 Language/zh_CN webview/15174550714036006 webdebugger port/9974";
    private static CloseableHttpClient client = HttpClients.createDefault();
    public static final String BUS_PREFIX = "shanghaibus:";

    @PostMapping("ws")
    public Map<String, Object> sendMessage(@RequestBody Map<String, String> data) {
        webSocket.sendMessage(data.get("data"));
        return success();
    }

    @GetMapping("bus")
    public Set<String> bus(String patten) {
        if (patten == null) {
            patten = "";
        }
        return commonRedisDao.keys(BUS_PREFIX + patten + "*");
    }

    @GetMapping("bus/time")
    public List<HashMap> busTime(String busname) throws IOException {
        String id = commonRedisDao.get(BUS_PREFIX + busname);
        String text = getText(id, "17", true);
        return JsonUtil.parseArrayMap(text);
    }

    private String getText(String busID, String stopID, Boolean direction) throws IOException {
        String url = "http://shanghaicity.openservice.kankanews.com/public/bus/Getstop";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("User-Agent", userAgent);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("Referer", "http://shanghaicity.openservice.kankanews.com/public/bus/mes/sid/3d47ccebe409583debe5b5cfdbca2879");
        httpPost.setHeader("Cookie", "Hm_1vt_6f69830ae7173059e935b61372431b35=eSgsNFpyhb61aiYkFiD/Ag==; _ga=GA1.2.1569252670.1517454783; Hm_lvt_6f69830ae7173059e935b61372431b35=1517454783; Hm_lpvt_6f69830ae7173059e935b61372431b35=1517454783; HH=18cb12f866189aa71e07e264e1cb6e5afdeb7666; HK=251b81d92243111e20a17dc23774fd961f41dc75; HG=04d638ff665fb167b447d5c75da20321f4541ec9; HA=951e8147f2f134dc103915acc308d01708ca3642; HB=OTUxZTgxNDdmMmYxMzRkYzEwMzkxNWFjYzMwOGQwMTcwOGNhMzY0Mg==; HC=35ec138c3dbbc400ab405820c99da652cb444078; HD=MjAxODAyMDE=; HY=MjAxODAyMDE=251b81d92243111e20a17dc23774fd961f41dc7504d638ff665fb167b447d5c75da20321f4541ec90c7f8d6ce3a23dc1fe2f41a54deb8051c9b5ba4f; HO=TWpBeE9EQXlNREU9MTFNVGd3TVRjejEzVFc5NmFXeHNZUzgxTGpBZ0tFMWhZMmx1ZEc5emFEc2dTVzUwWld3Z1RXRmpJRTlUSUZnZ01UQmZNVE5mTXlrZ1FYQndiR1ZYWldKTGFYUXZOVE0zTGpNMklDaExTRlJOVEN3Z2JHbHJaU0JIWldOcmJ5a2dRMmh5YjIxbEx6WTBMakF1TXpJNE1pNHhNVGtnVTJGbVlYSnBMelV6Tnk0ek5nPT0wYzdmOGQ2Y2UzYTIzZGMxZmUyZjQxYTU0ZGViODA1MWM5YjViYTRm; Hm_p1vt_6f69830ae7173059e935b61372431b35=eSgsNFpyhcC1aiYkFiEDAg==");

        List<NameValuePair> nvps = Lists.newArrayList();
        nvps.add(new BasicNameValuePair("stoptype", direction ? "0" : "1"));
        nvps.add(new BasicNameValuePair("stopid", stopID));
        nvps.add(new BasicNameValuePair("sid", busID));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String text = "";
        if (entity != null) {
            text = EntityUtils.toString(entity, "utf-8");
        }
        return text;
    }

}
