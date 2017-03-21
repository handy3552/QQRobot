package qq.moni;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 使用小黄鸡的接口
 * http://www.niurenqushi.com/app/simsimi/ajax.aspx
 * @author Zhang JT
 *
 */
public class Robot {
     CloseableHttpClient httpclient = HttpClients.createDefault();
    public static String postsms(String sms)
    {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost("http://www.niurenqushi.com/app/simsimi/");//http://www.niurenqushi.com/app/simsimi/
            String html="";
             List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
             formparams.add(new BasicNameValuePair("txt",sms));
             UrlEncodedFormEntity uefEntity;
             try {
                uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
                 httppost.setEntity(uefEntity);  
                System.out.println("executing request " + httppost.getURI());  
                 HttpResponse response =  httpclient.execute(httppost);
                 HttpEntity entity = response.getEntity();  
                 html=EntityUtils.toString(entity, "utf-8");   
                 System.out.println(html);
             } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }  
             catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



         return html;
    }
    public static void main(String[] args)
    {
        Robot rb=new Robot();
        rb.postsms("你");
    }
}
