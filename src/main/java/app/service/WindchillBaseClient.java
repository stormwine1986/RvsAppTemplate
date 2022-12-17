package app.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.AppLogger;

/**
 * Windchill 客户端基础组件
 * 
 */
public abstract class WindchillBaseClient {

    private static Logger logger = AppLogger.getLogger(WindchillBaseClient.class);

    private String pdmConfig;

    public WindchillBaseClient(){
        if(pdmConfig!=null) return;
        try{
            // 从 RVS 服务器获知 PDM 连接数据
            String url = String.format("http://%s:%s/services/pdm.jsp", System.getenv("MKSSI_HOST"), System.getenv("MKSSI_PORT"));
            byte[] resp = _get(null, url);
            pdmConfig = new String(Base64.getDecoder().decode(resp));
        }catch(Exception e){
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    protected byte[] _get(Map<String, String> headers, String url) throws IOException {
        logger.info("requesting url = " + url);
        URL urlInstance = new URL(url);
        URLConnection conn = urlInstance.openConnection();
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(3000);
        // 设置请求头
        if(headers!=null){
            headers.entrySet().stream().forEach(it->{
                conn.setRequestProperty(it.getKey(), it.getKey());
            });
        }
        // 读取请求数据
        try(InputStream is = conn.getInputStream()){
            int b = -1;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            while((b = is.read())!=-1){
                os.write(b);
            }
            os.flush();
            os.close();
            logger.info("request url = " + url + " ,response size = " + os.size());
            return os.toByteArray();
        }
    }

    protected byte[] _post(Map<String, String> headers, String url, byte[] body) throws IOException {
        logger.info("requesting url = " + url);
        URL urlInstance = new URL(url);
        URLConnection conn = urlInstance.openConnection();
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(3000);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        // 设置请求头
        if(headers!=null){
            headers.entrySet().stream().forEach(it->{
                conn.setRequestProperty(it.getKey(), it.getKey());
            });
        }
        // 发送请求数据
        try(OutputStream outputStream = conn.getOutputStream()){
            outputStream.write(body);
            outputStream.flush();
        }
        // 读取响应数据
        try(InputStream is = conn.getInputStream()){
            int b = -1;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            while((b = is.read())!=-1){
                os.write(b);
            }
            os.flush();
            os.close();
            logger.info("request url = " + url + " ,request body size = " + body.length + " ,response size = " + os.size());
            return os.toByteArray();
        }
    }
    
}
