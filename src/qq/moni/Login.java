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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Login {

	/**
	 * ��ȡ����
	 */
	public static final String URL_GET_FRIEND_INFO = "http://s.web2.qq.com/api/get_user_friends2";
	/**
	 * İ����
	 */
	public static final String URL_GET_STRANGER_INFO = "http://s.web2.qq.com/api/get_stranger_info2";
	/**
	 * ��ȡȺ
	 */
	public static final String URL_GET_GROUP_NAME_LIST = "http://s.web2.qq.com/api/get_group_name_list_mask2";
	
	/**
	 * ������Ϣ������
	 */
	public static final String URL_SEND_FRIEND = "http://d1.web2.qq.com/channel/send_buddy_msg2";
	/**
	 * ������Ϣ��Ⱥ
	 */
	public static final String URL_SEND_GROUP = "http://d1.web2.qq.com/channel/send_qun_msg2";
	/**
	 * ������Ϣ��������
	 */
	public static final String URL_SEND_DISCU = "http://d1.web2.qq.com/channel/send_discu_msg2";
	/**
	 * ����Ϣ�����ѡ�Ⱥ����������ʱ�õ�referer
	 */
	public static final String URL_SEND_REFERER = "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2";
			
	private boolean loginflag=false;
    private String ptuiCBurl="";
    private String ptwebqq="";
    private String psessionid="";
    private String clientid="";
    private String aid="";
    private String iamgeImg="D:\\jj";
    private Header[] header = null;
    private Cookie[] cookie = null;
    private String qrsig = "";
    private String vfwebqq = "";
    public CloseableHttpClient pagemain()
    {
         CloseableHttpClient httpclient = HttpClients.createDefault();
         HttpGet httpget = new HttpGet("http://w.qq.com/");
         httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");

         System.out.println("Executing request " + httpget.getURI());//��ʼ
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
         System.out.println("��ȡ��ά�룺Executing request " + httpget.getURI());//��ʼ
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
     * ����Ƿ��¼�ɹ�
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
         System.out.println("����Ƿ��¼�ɣ�Executing request " + httpget.getURI());//��ʼ
         String html="";
         try {	
        	//httpget.setHeader("Set-Cookie", header.toString());
        	 httpget.addHeader("Referer", "https://ui.ptlogin2.qq.com/cgi-bin/login?daid=164&target=self&style=16&mibao_css=m_webqq&appid=501004106&enable_qlogin=0&no_verifyimg=1&s_url=http%3A%2F%2Fw.qq.com%2Fproxy.html&f_url=loginerroralert&strong_login=1&login_state=10&t=20131024001");
             CloseableHttpResponse response = httpclient.execute(httpget);
             System.out.println(response.getStatusLine());
             HttpEntity entity = response.getEntity();  
             html=EntityUtils.toString(entity, "utf-8");
             //ptuiCB('0','0','http://ptlogin4.web2.qq.com/check_sig?pttype=1&uin=1069478446&service=ptqrlogin&nodirect=0&ptsigx=afde2a7fe5f26485b976c9f0f0d87c1ebf27706be0d7b9a0fbab5df1d9e5ec9fb1df62d5cdef526fa7e2df2b3ce2dd84fad270fbdfc90bdd4da0308f73a337fd&s_url=http%3A%2F%2Fw.qq.com%2Fproxy.html%3Flogin2qq%3D1%26webqq_type%3D10&f_url=&ptlang=2052&ptredirect=100&aid=501004106&daid=164&j_later=0&low_login_hour=0&regmaster=0&pt_login_type=3&pt_aid=0&pt_aaid=16&pt_light=0&pt_3rd_aid=0','0','��¼�ɹ���', 'lonter');
             if(html.indexOf("��¼�ɹ�")!=-1)
             {
                 this.aid=HttpTool.getaid(html);
                 this.loginflag=true;
                 int start=html.indexOf("http:");
                 int end=html.indexOf("pt_3rd_aid");
                 this.ptuiCBurl=html.substring(start, end+12);
                 System.out.println(this.ptuiCBurl);
                 HttpTool hp=new HttpTool();
                 this.ptwebqq=hp.getCookie("ptwebqq", response);
                 System.out.println("this.ptwebqq:"+this.ptwebqq);
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
     * ��õ�¼������ ��һ�ε�¼
     * @param httpclient
     * @return
     */
    public CloseableHttpClient getPara(CloseableHttpClient httpclient)
    {
         HttpGet httpget = new HttpGet(this.ptuiCBurl);
         httpget.addHeader("Referer", "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1");
         System.out.println("��õ�¼������ Executing request " + httpget.getURI());//��ʼ
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
     * ��� vfwebqq
     * @param httpclient
     */
    /*public void getVfwebqq(CloseableHttpClient httpclient) {
    	HttpGet httpGet = new HttpGet("http://s.web2.qq.com/api/getvfwebqq?clientid=53999199");
    	System.out.println("��ȡvfwebqq��Executing request " + httpGet.getURI());
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
             this.psessionid=html;
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
    
    /**
     * �����Ϣ ��Ҫ��Header�������ã�
     * @param httpclient
     */
    public String getmsg(CloseableHttpClient httpclient)
    {

            HttpPost httppost = new HttpPost("http://d1.web2.qq.com/channel/poll2");
            //URI.create(URL_GET_STRANGER_INFO)
            String html="";
            httppost.setHeader("Content-Type","application/x-www-form-urlencoded");
            httppost.setHeader("charset","UTF-8");
            httppost.setHeader("Origin","http://d1.web2.qq.com");
            httppost.setHeader("Referer","http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2");
            //httppost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
            formparams.add(new BasicNameValuePair("r","{\"ptwebqq\":\""+this.ptwebqq+"\",\"clientid\":53999199,\"psessionid\":\""+this.psessionid+"\",\"key\":\"\"}"));
            UrlEncodedFormEntity uefEntity;
            try {
                uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
                httppost.setEntity(uefEntity);  
                System.out.println("executing request " + httppost.getURI());  
                HttpResponse response =  httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();  
                html=EntityUtils.toString(entity, "utf-8");   
                System.out.println("----->"+html+"\t"+html.length());
                
                JSONObject jsonObj = (JSONObject) JSONObject.parse(html);
                int retcode = jsonObj.getIntValue("retcode");
              
                if(html.length()>176)
                {
                	
                	//{"errmsg":"error!!!","retcode":103} ��Ҫ���µ�¼
                    if(0 == retcode) {
                    	//
                    	JSONArray resultArray = (JSONArray) jsonObj.get("result");
                    	JSONObject resultJson = (JSONObject) resultArray.get(0);
                    	
                    	String poll_type = resultJson.getString("poll_type");
                    	JSONObject valueObj = (JSONObject) resultJson.get("value");
                    	String from_uin = valueObj.getString("from_uin");
                    	String msg_type = valueObj.getString("msg_type");
                    	String msg_id = valueObj.getString("msg_id");
                    	JSONArray jsonArray = (JSONArray) valueObj.get("content");
                    	String msg = jsonArray.getString(1);
                    	sendmsg(httpclient, poll_type, msg_id, from_uin, msg);//С�Ƽ������������ɾ������
                    }
                    
                 /*   String group_code=HttpTool.getgroup_code(html);//�ж�Ⱥ
                    System.out.println("group_code:"+group_code);
                    String msg=HttpTool.getmsgtext(html);
                    String sendsms=Robot.postsms(msg);
                    sendmsg(httpclient,sendsms,group_code);//С�Ƽ������������ɾ������
                    */

                }
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
    //sendmsg(httpclient, msg_type, msg_id, from_uin, msg);//С�Ƽ������������ɾ������
    public void sendmsg(CloseableHttpClient httpclient, String msg_type, String msg_id, String from_uin, String sms)
    {
        HttpPost httppost = new HttpPost();//new HttpPost("http://d1.web2.qq.com/channel/send_buddy_msg2");//
        httppost.setHeader("Content-Type","application/x-www-form-urlencoded");
        httppost.setHeader("Origin","http://d1.web2.qq.com");
        httppost.setHeader("Referer","http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2");
    //  httppost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");
        String html;
         List<NameValuePair> formparams = new ArrayList<NameValuePair>();
         if("message".equals(msg_type)) {//������Ϣ
        	 //httppost = new HttpPost(URL_SEND_FRIEND);
        	 httppost.setURI(URI.create(URL_SEND_FRIEND));//URL_SEND_FRIEND
        	 formparams.add(new BasicNameValuePair("r","{\"to\":"+from_uin+",\"content\":\"[\\\""+sms+"\\\",[\\\"font\\\",{\\\"name\\\":\\\"����\\\",\\\"size\\\":10,\\\"style\\\":[0,0,0],\\\"color\\\":\\\"000000\\\"}]]\",\"face\":165,\"clientid\":53999199,\"msg_id\":"+msg_id+",\"psessionid\":\""+this.psessionid+"\"}"));
         } else if("group_message".equals(msg_type)) {//Ⱥ��Ϣ
        	 //httppost = new HttpPost(URL_SEND_GROUP);
        	 httppost.setURI(URI.create(URL_SEND_GROUP));
        	 formparams.add(new BasicNameValuePair("r","{\"group_uin\":"+from_uin+",\"content\":\"[\\\""+sms+"\\\",[\\\"font\\\",{\\\"name\\\":\\\"����\\\",\\\"size\\\":10,\\\"style\\\":[0,0,0],\\\"color\\\":\\\"000000\\\"}]]\",\"face\":165,\"clientid\":53999199,\"msg_id\":"+msg_id+",\"psessionid\":\""+this.psessionid+"\"}"));
         } else if("discu_message".equals(msg_type)) {//��������Ϣ
        	 //httppost = new HttpPost(URL_SEND_DISCU);
        	 httppost.setURI(URI.create(URL_SEND_DISCU));
        	 formparams.add(new BasicNameValuePair("r","{\"did\":"+from_uin+",\"content\":\"[\\\""+sms+"\\\",[\\\"font\\\",{\\\"name\\\":\\\"����\\\",\\\"size\\\":10,\\\"style\\\":[0,0,0],\\\"color\\\":\\\"000000\\\"}]]\",\"face\":165,\"clientid\":53999199,\"msg_id\":"+msg_id+",\"psessionid\":\""+this.psessionid+"\"}"));
         }
         
         System.out.println("���͵Ĳ���r��"+formparams.get(0).getValue());
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

    }
    private void reLogin(CloseableHttpClient httpclient) {
    	
    	
    	
    }
    /**
     * �˺������У��ȴ���ά�����ɺ�ɨ���ά�뼴�ɵ�½
     * bug��Ŀǰ����ģ���½�����ڵ�bug����Ҫ�����������smart qq��ɨ���½�������б�����
     *      ���뱾�����������У���Ҫ�ȴ��������¼smart qq��Ȼ�����ñ������½���������½��
     *      �Ͳ��ù����������¼���Ǹ��ˡ������Ϻܶ�ģ���½�������������Ŷ����ֹ�ҵģ�����cookie
     *      �����˻���զ�أ��㲻��
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
        for(int i=0;;i++)
        {
            lg.getmsg(httpclient);
        }

    }
}

