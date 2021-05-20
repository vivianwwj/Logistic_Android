package com.android.buildinstorageform.utilies;


import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {
    /**
     * Builds the URL used to query GitHub.
     *
     * @param urlPath The keyword that will be queried for.
     * @return The URL to use to query the GitHub server.
     */
    public static URL buildUrl(String urlPath){

        URL url = null;
        try {
            url = new URL(urlPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
    /**
     * This method returns the entire result from the HTTP response (requestMethod:GET).
     *
     * @param urlPath The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl_GET (URL urlPath) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) urlPath.openConnection();
        try{
            urlConnection.setConnectTimeout(3000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            int code = urlConnection.getResponseCode();
            if (code == 200) {
                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");//从字符串开头进行匹配

                boolean hasInput = scanner.hasNext();
                if(hasInput){
                    return scanner.next();
                }else{
                    return null;
                }
            }else{
                Log.i("InStorageFormActivity","回应状态码非200！");
            }
        }catch (IOException e){
            Log.e("InStorageFormActivity",Log.getStackTraceString(e));
            e.printStackTrace();
        }finally {
            urlConnection.disconnect();
        }
        return null;
    }

    /**
     * This method returns the entire result from the HTTP response (requestMethod:POST).
     * @param post_url 请求地址
     * @param post_body 请求体
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl_POST (String post_url,String post_body) throws IOException{
        HttpURLConnection urlConnection = null;
        //拿数据
        InputStream is = null;
        StringBuilder sbd = new StringBuilder();
        String strings;
        try {
            //将url转变为URL类对象
            URL url = new URL(post_url);
            //打开URL连接
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3 * 1000);
            urlConnection.setReadTimeout(3 * 1000);
            // 设定请求的方法为"POST"，默认是GET
            urlConnection.setRequestMethod("POST");
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            urlConnection.setDoInput(true);
            // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
            // http正文内，因此需要设为true, 默认情况下是false;
            urlConnection.setDoOutput(true);
            // Post 请求不能使用缓存
            urlConnection.setUseCaches(false);
            //转码 防止乱码
            urlConnection.setRequestProperty("charset", "UTF-8");
            //  窗体数据被编码为 名称/值对 标准编码格式
            urlConnection.setRequestProperty("Content-type",
                    "application/x-www-form-urlencoded");
            //往后台写参数   第一个传进来的是地址
            //strings应该是这样的样式=>option=getUser&uName=jerehedu
            strings = post_body;
            //设置请求体的长度
//            urlConnection.setRequestProperty("Content-Length", String.valueOf(strings.length()));
            OutputStream os = urlConnection.getOutputStream();
            // 向对象输出流写出数据，这些数据将存到内存缓冲区中
            os.write(strings.getBytes(StandardCharsets.UTF_8));
            // 刷新对象输出流，将任何字节都写入潜在的流中（些处为ObjectOutputStream）
            os.flush();
            // 关闭流对象。此时，不能再向对象输出流写入任何数据，先前写入的数据存在于内存缓冲区中,
            // 在调用下边的getInputStream()函数时才把准备好的http请求正式发送到服务器
            os.close();

            if (urlConnection.getResponseCode() == 200) {
                is = urlConnection.getInputStream();
                int next = 0;
                byte[] b = new byte[1024];
                while ((next = is.read(b)) > 0) {
                    sbd.append(new String(b, 0, next));
                }
            }

            Log.i("Login", "POST_getResponseCode:"+urlConnection.getResponseCode());

        } catch (IOException e) {
            Log.e("GetAccount",Log.getStackTraceString(e));
//            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return sbd.toString();
    }

}
