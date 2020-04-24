package com.community.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.community.dto.AccessTokenDTO;
import com.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author luoyelun
 * @date 2020/4/24 0:21
 */
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            return string.split("&")[0].split("=")[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
//.header("Authorization", "token " + access
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //json转为对象
            return JSON.parseObject(string, GithubUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
