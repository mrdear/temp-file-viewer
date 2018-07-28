package cn.ifreehub.viewer.util;

import io.github.mrdear.tinify.TinifyWebClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author quding
 */
public class HTTPUtils implements TinifyWebClient {

  private static Logger logger = LoggerFactory.getLogger(HTTPUtils.class);

  private static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder()
      //设置读取超时时间
      .readTimeout(60, TimeUnit.SECONDS)
      //设置写的超时时间
      .writeTimeout(0, TimeUnit.SECONDS)
      //设置连接超时时间
      .connectTimeout(0, TimeUnit.SECONDS)
      .build();


  public static String get(String url) {
    return get(Collections.emptyMap(), url, Collections.emptyMap());
  }

  public static String get(String url, Map<String, String> data) {
    return get(Collections.emptyMap(), url, data);
  }

  public static String get(Map<String, ?> headers, String url, Map<String, ?> data) {
    Request.Builder requestBuilder = new Request.Builder();

    // headers 处理
    if (null != headers && !headers.isEmpty()) {
      Headers.Builder headerBuilder = new Headers.Builder();
      headers.forEach((k, v) -> headerBuilder.add(k, String.valueOf(v)));
      requestBuilder.headers(headerBuilder.build());
    }
    // data 处理
    if (null != data && !data.isEmpty()) {
      HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
      for (Map.Entry<String, ?> entry : data.entrySet()) {
        urlBuilder.addQueryParameter(entry.getKey(), String.valueOf(entry.getValue()));
      }
      url = urlBuilder.toString();
    }
    // 构造
    Request request = requestBuilder
        .url(url)
        .get()
        .build();

    try (Response response = HTTP_CLIENT.newCall(request).execute()) {
      if (null != response.body()) {
        return response.body().string();
      }
    } catch (IOException e) {
      logger.error("http get fail, url is {}", url, e);
    }
    return null;
  }



  public static String postForm(Map<String, ?> headers, String url, Map<String, ?> data) {
    Request.Builder requestBuilder = new Request.Builder().url(url);

    // headers 处理
    if (null != headers && !headers.isEmpty()) {
      Headers.Builder headerBuilder = new Headers.Builder();
      headers.forEach((k, v) -> headerBuilder.add(k, String.valueOf(v)));
      requestBuilder.headers(headerBuilder.build());
    }

    // form data
    if (null != data && !data.isEmpty()) {
      FormBody.Builder builder = new FormBody.Builder();
      for (Map.Entry<String, ?> elem : data.entrySet()) {
        builder.add(elem.getKey(), String.valueOf(elem.getValue()));
      }
      requestBuilder.post(builder.build());
    }

    final Request request = requestBuilder.build();

    try (Response response = HTTP_CLIENT.newCall(request).execute()) {
      if (null != response.body()) {
        return response.body().string();
      }
    } catch (IOException e) {
      logger.error("http postForm fail, url is {}", url, e);
    }
    return null;
  }


  public static String postBody(Map<String, String> headers, String url, byte[] body) {
    if (null == body) {
      body = new byte[0];
    }
    Request.Builder requestBuilder = new Request.Builder();
    headers.forEach(requestBuilder::header);
    requestBuilder.url(url).post(RequestBody.create(null, body));

    try (Response response = HTTP_CLIENT.newCall(requestBuilder.build()).execute()) {
      if (null != response.body()) {
        return response.body().string();
      }
    } catch (IOException e) {
      logger.error("doPost fail, url is {}", url, e);
    }
    return null;
  }

  /**
   * 下载文件
   * @param url 下载地址
   * @return 下载结果
   */
  public static InputStream downFile(String url) {
    Request request = new Request.Builder().url(url).build();
    try {
      Response response = HTTP_CLIENT.newCall(request).execute();
      if (null != response.body()) {
        return response.body().byteStream();
      }
    } catch (IOException e) {
      logger.error("downFile fail, url is {}", url);
    }
    return null;
  }


  //  ---------- tinify client

  @Override
  public String doPost(String url, Map<String, String> headers, byte[] body) {
    return postBody(headers, url, body);
  }
}
