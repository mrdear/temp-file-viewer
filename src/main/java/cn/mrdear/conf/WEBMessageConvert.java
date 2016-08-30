package cn.mrdear.conf;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用fastjson作为消息转换器
 *
 * @author Niu Li
 * @date 2016/8/9
 */
@Configuration
public class WEBMessageConvert {
    /**
     * 使用bean注入,才能使其有效果,验证的话就在Entity字段中使用fastjson的
     * 注解@JSONField(serialize = false),转换出来的信息不含该字段,则成功
     */
    @Bean
    public HttpMessageConverters customConverters() {

        //fastjson处理消息转换
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
        /*
          List<MediaType> fastMediaTypes = new ArrayList<>();
          fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
          fastConverter.setSupportedMediaTypes(fastMediaTypes);
        */
        fastConverter.setFastJsonConfig(fastJsonConfig);

        //文件下载使用ByteArrayHttpMessageConverter处理
        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        byteArrayHttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
        /*
         //ByteArrayHttpMessageConverter默认处理请求类型就是APPLICATION_OCTET_STREAM
         List<MediaType> byteMediaTypes = new ArrayList<>();
         byteMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
         byteArrayHttpMessageConverter.setSupportedMediaTypes(byteMediaTypes);
         */
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(fastConverter);
        converters.add(byteArrayHttpMessageConverter);

        return new HttpMessageConverters(converters);
    }
}
