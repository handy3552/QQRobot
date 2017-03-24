package qq.moni;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import qq.moni.entity.Params;
import qq.moni.entity.QQMsg;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Login {

			
	private boolean loginflag=false;
    private String ptuiCBurl="";
    private String ptwebqq="";
    private String psessionid="";
    private String aid="";
    private String iamgeImg="D:\\jj";
    private Header[] header = null;
    private String qrsig = "";
    static Params params;
    static QQMsg qqMsg;
    public Login() {
		// TODO Auto-generated constructor stub
    	if(null == qqMsg) {
    		qqMsg = new QQMsg();
    	}
    	if(null == params) {
    		params = new Params();
    	}
	}
    public CloseableHttpClient pagemain()
    {
         CloseableHttpClient httpclient = HttpClients.createDefault();
         HttpGet httpget = new HttpGet("http://w.qq.com/");
         httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");

         System.out.println("Executing request " + httpget.getURI());//开始
         String html="";
         try {
            CloseableHttpResponse response = httpclient.execute(httpget);
             System.out.println(response.getStatusLine());
             
             HttpEntity entity = response.getEntity();  
             html=EntityUtils.toString(entity, "utf-8");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         System.out.println(html);
         return httpclient;
    }
    //https://ssl.ptlogin2.qq.com/ptqrshow?appid=501004106&e=0&l=M&s=5&d=72&v=4&t=0.3770239355508238
    public CloseableHttpClient getErweima(CloseableHttpClient httpclient)
    {
         //HttpGet httpget = new HttpGet("https://ssl.ptlogin2.qq.com/ptqrshow?appid=501004106&e=0&l=M&s=5&d=72&v=4&t=0.3770239355508238");
         HttpGet httpget = new HttpGet("https://ssl.ptlogin2.qq.com/ptqrshow?appid=501004106&e=0&l=M&s=5&d=72&v=4&t=" + Math.random());
         System.out.println("获取二维码：Executing request " + httpget.getURI());//开始
         String html="";
         FileOutputStream fos;
         try {
            CloseableHttpResponse response = httpclient.execute(httpget);
             System.out.println(response.getStatusLine());
             header = response.getHeaders("Set-Cookie");
             this.qrsig = HttpTool.getCookie("qrsig", response);
             InputStream inputStream = response.getEntity().getContent();
             File file = new File(this.iamgeImg);
             if (!file.exists()) {
                 file.mkdirs();
             } else {
            	 File file2 = new File(this.iamgeImg+"\\test.jpg");
            	 if(file2.exists()) {
            		 file.delete();
            	 }
             }
             fos = new FileOutputStream("D:\\jj\\test.jpg");
             byte[] data = new byte[1024];
             int len = 0;
             while ((len = inputStream.read(data)) != -1) {
                 fos.write(data, 0, len);
             }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         System.out.println(html);
         return httpclient;
    }
   
    /**
     * 检查是否登录成功
     * @param httpclient
     * @return
     */
    public  CloseableHttpClient checkLogin(CloseableHttpClient httpclient)
    {
		
        // HttpGet httpget = new HttpGet("https://ssl.ptlogin2.qq.com/ptqrlogin?webqq_type=10&remember_uin=1&login2qq=1&aid=501004106&u1=http%3A%2F%2Fw.qq.com%2Fproxy.html%3Flogin2qq%3D1%26webqq_type%3D10&ptredirect=0&ptlang=2052&daid=164&from_ui=1&pttype=1&dumy=&fp=loginerroralert&action=0-0-157510&mibao_css=m_webqq&t=1&g=1&js_type=0&js_ver=10143&login_sig=&pt_randsalt=0");
         HttpGet httpget = new HttpGet("https://ssl.ptlogin2.qq.com/ptqrlogin?ptqrtoken="+HttpTool.ptqrtoken(this.qrsig)+"&webqq_type=10&remember_uin=1&login2qq=1&aid=501004106&u1=http%3A%2F%2Fw.qq.com%2Fproxy.html%3Flogin2qq%3D1%26webqq_type%3D10&ptredirect=0&ptlang=2052&daid=164&from_ui=1&pttype=1&dumy=&fp=loginerroralert&action=0-0-123332&mibao_css=m_webqq&t=undefined&g=1&js_type=0&js_ver=10141&login_sig=&pt_randsalt=0'");
         /*
          Referer:https://ui.ptlogin2.qq.com/cgi-bin/login?daid=164&target=self&style=16&mibao_css=m_webqq&appid=501004106&enable_qlogin=0&no_verifyimg=1&s_url=http%3A%2F%2Fw.qq.com%2Fproxy.html&f_url=loginerroralert&strong_login=1&login_state=10&t=20131024001
          */
         System.out.println("检查是否登录成：Executing request " + httpget.getURI());//开始
         String html="";
         try {	
        	 httpget.addHeader("Referer", "https://ui.ptlogin2.qq.com/cgi-bin/login?daid=164&target=self&style=16&mibao_css=m_webqq&appid=501004106&enable_qlogin=0&no_verifyimg=1&s_url=http%3A%2F%2Fw.qq.com%2Fproxy.html&f_url=loginerroralert&strong_login=1&login_state=10&t=20131024001");
             CloseableHttpResponse response = httpclient.execute(httpget);
             System.out.println(response.getStatusLine());
             HttpEntity entity = response.getEntity();  
             html=EntityUtils.toString(entity, "utf-8");
             //ptuiCB('0','0','http://ptlogin4.web2.qq.com/check_sig?pttype=1&uin=1069478446&service=ptqrlogin&nodirect=0&ptsigx=afde2a7fe5f26485b976c9f0f0d87c1ebf27706be0d7b9a0fbab5df1d9e5ec9fb1df62d5cdef526fa7e2df2b3ce2dd84fad270fbdfc90bdd4da0308f73a337fd&s_url=http%3A%2F%2Fw.qq.com%2Fproxy.html%3Flogin2qq%3D1%26webqq_type%3D10&f_url=&ptlang=2052&ptredirect=100&aid=501004106&daid=164&j_later=0&low_login_hour=0&regmaster=0&pt_login_type=3&pt_aid=0&pt_aaid=16&pt_light=0&pt_3rd_aid=0','0','登录成功！', 'lonter');
             if(html.indexOf("登录成功")!=-1)
             {
                 this.aid=HttpTool.getaid(html);
                 this.loginflag=true;
                 int start=html.indexOf("http:");
                 int end=html.indexOf("pt_3rd_aid");
                 this.ptuiCBurl=html.substring(start, end+12);
                 System.out.println(this.ptuiCBurl);
                 HttpTool hp=new HttpTool();
                 //this.ptwebqq=hp.getCookie("ptwebqq", response);
                 params.setPtwebqq(hp.getCookie("ptwebqq", response));
                 System.out.println("this.ptwebqq:"+params.getPtwebqq());
             }

             System.out.println(html);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

         return httpclient;
    }
    /**
     * 获得登录参数， 第一次登录
     * @param httpclient
     * @return
     */
    public CloseableHttpClient getPara(CloseableHttpClient httpclient)
    {
         HttpGet httpget = new HttpGet(this.ptuiCBurl);
         httpget.addHeader("Referer", "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1");
         System.out.println("获得登录参数： Executing request " + httpget.getURI());//开始
         String html="";
         try {
            CloseableHttpResponse response = httpclient.execute(httpget);
             System.out.println(response.getStatusLine());
             HttpEntity entity = response.getEntity();  
             html=EntityUtils.toString(entity, "utf-8");
             System.out.println(html);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         return httpclient;
    }
    /**
     * 获得 vfwebqq
     * @param httpclient
     */
    /*public void getVfwebqq(CloseableHttpClient httpclient) {
    	HttpGet httpGet = new HttpGet("http://s.web2.qq.com/api/getvfwebqq?clientid=53999199");
    	System.out.println("获取vfwebqq：Executing request " + httpGet.getURI());
        String html="";
        try {
        	CloseableHttpResponse response = httpclient.execute(httpGet);
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();  
            html=EntityUtils.toString(entity, "utf-8");
		} catch (ClientProtocolException e) {
			// TODO: handle exception
		} catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    
    /**
     * 第二次登录
     * @param httpclient
     * @return
     */
    public CloseableHttpClient getpsessionid(CloseableHttpClient httpclient)
    {
        HttpPost httppost = new HttpPost("http://d1.web2.qq.com/channel/login2");
        
        String html;
         List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
         formparams.add(new BasicNameValuePair("r","{\"ptwebqq\":\""+this.ptwebqq+"\",\"clientid\":53999199,\"psessionid\":\"\",\"status\":\"online\"}"));
         UrlEncodedFormEntity uefEntity;
         try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
             httppost.setEntity(uefEntity);  
             System.out.println("executing request " + httppost.getURI());
             
             httppost.addHeader("Referer","http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2");
             httppost.addHeader("Connection","Keep-Alive");
             httppost.addHeader("Origin","http://d1.web2.qq.com");
             HttpResponse response =  httpclient.execute(httppost);
             HttpEntity entity = response.getEntity();  
             html=EntityUtils.toString(entity, "utf-8");
             //{"result":{"cip":23600812,"f":0,"index":1075,"port":47450,"psessionid":"8368046764001d636f6e6e7365727665725f77656271714031302e3133332e34312e383400001ad00000066b026e040015808a206d0000000a406172314338344a69526d0000002859185d94e66218548d1ecb1a12513c86126b3afb97a3c2955b1070324790733ddb059ab166de6857","status":"online","uin":732419269,"user_state":0,"vfwebqq":"59185d94e66218548d1ecb1a12513c86126b3afb97a3c2955b1070324790733ddb059ab166de6857"},"retcode":0}
             System.out.println(html);//
             int start=html.indexOf("psessionid\":\"")+13;
             html=html.substring(start);
             int end=html.indexOf("\"");
             html=html.substring(0, end);
             //this.psessionid=html;
             params.setPsessionid(html);
             System.out.println(html);
         } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }  
         catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         return httpclient;
    }
    
    private void reLogin(CloseableHttpClient httpclient) {
    	
    }
    /**
     * 此函数运行，等待二维码生成后，扫描二维码即可登陆
     * bug：目前所有模拟登陆都存在的bug，需要先用浏览器打开smart qq，扫描登陆后再运行本程序
     *      即想本程序正常运行，需要先打开浏览器登录smart qq，然后再用本程序登陆，本程序登陆后
     *      就不用管用浏览器登录的那个了。市面上很多模拟登陆都存在这个问题哦，不止我的，这是cookie
     *      跨域了还是咋地，搞不懂
     *  现在好像没有这个问题了
     * @param args
     */
    public static void main(String[] args)
    {
        Login lg=new Login();
        CloseableHttpClient httpclient=lg.pagemain();
        httpclient=lg.getErweima(httpclient);
        for(int i=0;;i++)
        {
            try {
                Thread.sleep(3000); 
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            httpclient = lg.checkLogin(httpclient);
            if(lg.loginflag)
                break;
        }

        httpclient=lg.getPara(httpclient);
        //lg.getVfwebqq(httpclient);
        httpclient=lg.getpsessionid(httpclient);
        qqMsg.getmsg(httpclient, params);

    }
    
    
    
    
    
    
    
    
    
    
    
	public Params getParams() {
		return params;
	}
	public void setParams(Params params) {
		this.params = params;
	}
    
}

