

package com.zhao.shortlink.project.service.impl;

import com.zhao.shortlink.project.service.UrlTitleService;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;


/**
 * URL 标题接口实现层
 * 小赵
 */
@Service
public class UrlTitleServiceImpl implements UrlTitleService {

    private static final String ACCESS_KEY = "inpOjytO0UtdXQNqOFnOSaKF";
    private static final String SECRET_KEY = "xEp298aQ1TxBEH6nK5vUTApkdbw7Oeg6";

    @SneakyThrows
    @Override
    public String getTitleByUrl(String url) {
        URL targetUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Document document = Jsoup.connect(url).get();
            return document.title();
        }
        return "";
    }

//    @SneakyThrows
//    @Override
//    public String getTitleByUrl(String url) {
//        Qianfan qianfan = new Qianfan(Auth.TYPE_OAUTH, ACCESS_KEY, SECRET_KEY);
//
//        ChatResponse response = qianfan.chatCompletion()
//                .model("ERNIE-4.0-8K")
//                .addMessage("user", "请总结一下下面页面的内容，不超过50字,要求内容准确" + url)
//                .temperature(0.7)
//                .execute();
//        return response.getResult();
//    }
}
