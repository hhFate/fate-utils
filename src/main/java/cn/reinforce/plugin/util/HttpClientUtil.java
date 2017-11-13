package cn.reinforce.plugin.util;

import cn.reinforce.plugin.util.entity.HttpResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 模拟http请求的工具类
 * 支持get，post，put，delete
 *
 * @author 幻幻Fate
 * @create 2016-09-06
 * @since 1.0.0
 */
public class HttpClientUtil {

    private static final Logger LOG = Logger.getLogger(HttpClientUtil.class);

    private static final String ENCODE = "UTF-8";

    private HttpClientUtil() {
        super();
    }

    public static HttpResult post(String url, List<NameValuePair> data, List<Cookie> cookies) {
        StringBuffer tmpcookies = new StringBuffer();
        if(cookies!=null){
            for (Cookie c : cookies) {
                tmpcookies.append(c.getName()+"="+c.getValue() + ";");
            }
        }
        return post(url, data, tmpcookies.toString());
    }

    public static HttpResult postByEntity(String url, StringEntity stringEntity) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);

        try {
            post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
            post.setEntity(stringEntity);
            return getResult(httpclient.execute(post));

        } catch (UnsupportedEncodingException e) {
            LOG.error(e.getMessage(), e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static HttpResult post(String url, List<NameValuePair> data, String cookie) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);

        try {
            post.setHeader("cookie", cookie);
            post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(data, ENCODE);
            post.setEntity(entity);
            return getResult(httpclient.execute(post));

        } catch (UnsupportedEncodingException e) {
            LOG.error(e.getMessage(), e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 模拟表单上传
     */
    public static String multipartRequest(String url, Map<String, String> parameters) {
        MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
        ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            multipartEntity.addTextBody(entry.getKey(), entry.getValue(), contentType);
        }
        // Now write the image
        String fullFilePath = parameters.get("file_path");
        if (!StringUtils.isEmpty(fullFilePath)) {
            multipartEntity.addPart("uploadFile", new FileBody(new File(fullFilePath)));
        }

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);


        post.setEntity(multipartEntity.build());
        HttpResponse response = null;
        try {
            response = client.execute(post);
            String submitResult = EntityUtils.toString(response.getEntity(), ENCODE);

            return submitResult;
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static HttpResult get(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            HttpGet get = new HttpGet(url);
            get.addHeader("Content-Type", "text/html;charset=UTF-8");
            get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
            return getResult(httpclient.execute(get));

        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }

        return null;
    }

    public static HttpResult put(String url, List<NameValuePair> data) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPut put = new HttpPut(url);

        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(data, ENCODE);
            put.setEntity(entity);
            return getResult(httpclient.execute(put));
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }

        return null;
    }

    public static HttpResult delete(String url) {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            HttpDelete delete = new HttpDelete(url);
            delete.addHeader("Content-Type", "text/html;charset=UTF-8");
            return getResult(httpclient.execute(delete));

        }catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 下载图片到本地
     *
     * @param url
     * @return
     */
    public static String downloadImg(String url, String folder, String downloadUrl) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String fileType = url.substring(url.lastIndexOf(".") + 1);
        if (StringUtils.isEmpty(fileType) || !ImageUtil.isImage(fileType)) {
            fileType = "jpg";
        }
        fileType = "." + fileType;
        long now = System.currentTimeMillis();
        String newUrl = downloadUrl + now + fileType;
        File dir = new File(folder);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File storeFile = new File(folder + now + fileType);
        FileOutputStream output = null;
        try {
            HttpGet get = new HttpGet(url);
            get.addHeader("Content-Type", "text/html;charset=UTF-8");
            get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
            CloseableHttpResponse response = httpclient.execute(get);
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                InputStream input = responseEntity.getContent();
                output = new FileOutputStream(storeFile);
                IOUtils.copy(input, output);
                output.flush();
            }

        } catch (ClientProtocolException e) {
            LOG.error(e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            LOG.error(e.getMessage(), e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }

        return newUrl;
    }

    private static HttpResult getResult(CloseableHttpResponse response) {
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity == null) {
            return null;
        }
        String submitResult = null;
        try {
            submitResult = EntityUtils.toString(responseEntity, ENCODE);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
        HttpResult result = new HttpResult();
        result.setStatusCode(response.getStatusLine().getStatusCode());
        result.setResult(submitResult);
        return result;
    }

}
