package com.tencent.weibo.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.util.EntityUtils;


import com.tencent.weibo.api.StatusesAPI;
import com.tencent.weibo.api.UserAPI;
import com.tencent.weibo.constants.OAuthConstants;
import com.tencent.weibo.oauthv2.OAuthV2;
import com.tencent.weibo.oauthv2.OAuthV2Client;

public class WeiboUtil {
	public static final String HEXSTRING = "0123456789ABCDEF";  
    public static OAuthV2 oAuth = new OAuthV2();  
    public static HttpClient client = wrapClient(new DefaultHttpClient(new ThreadSafeClientConnManager())); 
    
    // 初始oAuth应用信息  
    public static void init(String clientId,String clientSercret,String redirectUri){
    	oAuth.setClientId(clientId);  
        oAuth.setClientSecret(clientSercret);  
        oAuth.setRedirectUri(redirectUri);  
    }
    
    /** 
     *  
     * @param qq 
     *            http://check.ptlogin2.qq.com/check?uin={0}&appid=15000101&r={1 } 
     *            返回的第三个值 
     * @param password 
     *            QQ密码 
     * @param verifycode 
     *            验证码 
     * @return 加密后的密码 
     * @throws UnsupportedEncodingException 
     * @throws Exception 
     *  
     */  
    public static String GetPassword(String qq, String password,  
            String verifycode) throws Exception  
    {  
        String P = hexchar2bin(md5(password));  
        String U = md5(P + hexchar2bin(qq.replace("\\x", "").toUpperCase()));  
        String V = md5(U + verifycode.toUpperCase());  
        return V;  
    }  
    
    @SuppressWarnings("deprecation")
	public static HttpClient wrapClient(HttpClient base) 
    {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
				
				public X509Certificate[] getAcceptedIssuers() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public void checkServerTrusted(X509Certificate[] chain, String authType)
						throws CertificateException {
					// TODO Auto-generated method stub
					
				}
				
				public void checkClientTrusted(X509Certificate[] chain, String authType)
						throws CertificateException {
					// TODO Auto-generated method stub
					
				}
			};
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = base.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            //设置要使用的端口，默认是443
            sr.register(new Scheme("https", ssf, 443));
            return new DefaultHttpClient(ccm, base.getParams());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
      
    public static String md5(String originalText) throws Exception  
    {  
        byte buf[] = originalText.getBytes("ISO-8859-1");  
        StringBuffer hexString = new StringBuffer();  
        String result = "";  
        String digit = "";  
        try  
        {  
            MessageDigest algorithm = MessageDigest.getInstance("MD5");  
            algorithm.reset();  
            algorithm.update(buf);  
            byte[] digest = algorithm.digest();  
            for (int i = 0; i < digest.length; i++)  
            {  
                digit = Integer.toHexString(0xFF & digest[i]);  
                if (digit.length() == 1)  
                {  
                    digit = "0" + digit;  
                }  
                hexString.append(digit);  
            }  
            result = hexString.toString();  
        }  
        catch (Exception ex)  
        {  
            result = "";  
        }  
        return result.toUpperCase();  
    }  
  
    public static String hexchar2bin(String md5str) throws UnsupportedEncodingException  
    {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream(md5str.length() / 2);  
        for (int i = 0; i < md5str.length(); i = i + 2)  
        {  
            baos.write((HEXSTRING.indexOf(md5str.charAt(i)) << 4 | HEXSTRING  
                    .indexOf(md5str.charAt(i + 1))));  
        }  
        return new String(baos.toByteArray(), "ISO-8859-1");  
    }  
    
    /** 
     *  
     * 请求微博开放平台应用 返回登录授权页面，但是如果没有sessionKey的话永远登录不成功 sessionKey 
     * 发现在返回的页面中一个input标签里放的url中有，所以要取到这个sessionKey 其实直接访问标签中的url就可以跳转 
     *  
     */  
    public static String getUrl() throws ClientProtocolException, IOException  {  
        HttpGet getcode = new HttpGet("https://open.t.qq.com/cgi-bin/oauth2/authorize?client_id="+ oAuth.getClientId()+ "&response_type=code&redirect_uri="  
                                        + oAuth.getRedirectUri()+ "&checkStatus=yes&appfrom=&g_tk&checkType=showAuth&state=");  
        HttpResponse response3 = client.execute(getcode);  
        HttpEntity entityqqq = response3.getEntity();  
        String entityxcc = EntityUtils.toString(entityqqq);
        String noscript = entityxcc.substring(entityxcc.indexOf("<noscript"), entityxcc  
                .indexOf("</noscript>"));
        String url = noscript.substring(noscript.indexOf("data-redirect=\""),noscript.indexOf("\" data-proxy"));
        url = url.replace("data-redirect=\"", "");
        return url;
    }  
    /** 
     * 解析并设置Token 
     * @param get 
     * @throws Exception  
     */  
    public static void setToken(HttpGet get) throws Exception  {  
        HttpResponse response4 = client.execute(get);
        HttpEntity entityqqq1 = response4.getEntity();
        String getUrlcode = EntityUtils.toString(entityqqq1);
        // 返回了最终跳转的页面URL，也就是回调页redirect_uri,页面地址上包含code openid openkey  
        // 需要将这三个值单独取出来再拼接成 code=xxxxx&openid=xxxxx&openkey=xxxxxx的形式  
        String entity = getUrlcode.substring(getUrlcode.indexOf("url="),getUrlcode.indexOf("\">"));  
        StringBuffer sb = new StringBuffer();
        String[] arr = entity.split("\\?")[1].split("&");  
        for (int x = 0; x < arr.length; x++)  
        {  
            if (arr[x].indexOf("code") >= 0 || arr[x].indexOf("openid") >= 0  
                    || arr[x].indexOf("openkey") >= 0)  
            {  
                sb.append(arr[x] + "&");  
            }  
            ;  
        }  
        // 利用code获取accessToken  
        OAuthV2Client.parseAuthorization(sb.substring(0, sb.length() - 1), oAuth);  
        oAuth.setGrantType("authorize_code");  
        OAuthV2Client.accessToken(oAuth);  
    }  
    
   
    /*** 
     * 调用(腾迅开放平台账户接口)获取一个人的信息 
     * @throws Exception  
     */  
    public static void getInfo() throws Exception  {  
        //输出Token，如果拿到了Token就代表登录成功，并可以进行下一步操作。  
        System.out.println("Token="+oAuth.getAccessToken());  
        UserAPI getuser = new UserAPI(oAuth.getOauthVersion());  
        String userJson = getuser.otherInfo(oAuth, "json", "", oAuth.getOpenid());  
        JSONObject userJsonObject = JSONObject.fromObject(userJson);  
        Integer errcode = (Integer) userJsonObject.get("errcode");  
        if (errcode == 0)  
        {  
            JSONObject userdataJsonObject = (JSONObject) userJsonObject.get("data");  
            System.out.println(userdataJsonObject.toString());  
        }  
    }  
    
    
    public static String getRecentNews() throws Exception{
    	System.out.println("Token="+oAuth.getAccessToken());
    	StatusesAPI statusesAPI = new StatusesAPI(oAuth.getOauthVersion());
    	String json = statusesAPI.homeTimeline(oAuth, "json", "0", "0", "10", "0","0");
    	return json;
    }


    @SuppressWarnings("restriction")
	public static String login(String uin, String p) {
		  String cookies = "";
		  try {
		   HttpGet preget = new HttpGet("http://ui.ptlogin2.qq.com/cgi-bin/login?appid=46000101&style=13&lang=&low_login=1&hide_title_bar=1&hide_close_icon=1&self_regurl=http%3A//reg.t.qq.com/index.php&s_url=http%3A%2F%2Ft.qq.com&daid=6");
		   preget.setHeader("Host", "ui.ptlogin2.qq.com");
		   preget.setHeader("Referer", "http://t.qq.com/?from=11");
		   HttpResponse responsea = client.execute(preget);
		   String entitya = EntityUtils.toString(responsea.getEntity());
		   String t=entitya.substring(entitya.indexOf("login_sig:"), entitya.indexOf("clientip:"));
		   String login_sig=t.substring(t.indexOf("\"")+1, t.lastIndexOf("\""));
		
		   /********************* 获取验证码 ***********************/
		   String codeUrl="http://check.ptlogin2.qq.com/check?uin="+ uin + "&appid=46000101&ptlang=2052&r=" + Math.random();
		   HttpGet get = new HttpGet(codeUrl);
		   get.setHeader("Host", "check.ptlogin2.qq.com");
		   get.setHeader("Referer", "http://t.qq.com/?from=11");
		   get.setHeader("User-Agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
		   HttpResponse response = client.execute(get);
		
		   String entity = EntityUtils.toString(response.getEntity());
		   String[] checkNum = entity.substring(entity.indexOf("(") + 1,entity.lastIndexOf(")")).replace("'", "").split(",");
		   String checkCode = checkNum[1];
		
		   List<Cookie> ckt = ((AbstractHttpClient) client).getCookieStore().getCookies();
		   String ck="";
		   for (int i = 0; i < ckt.size(); i++) {
		    ck = ck +ckt.get(i).getName()+"="+ckt.get(i).getValue()+";";
		
		   } 
		
		   if(checkNum[0].equals("1")){
		    /*System.out.println(uin+"需要输入验证码");
		    String str12 = "http://captcha.qq.com/getimage?uin="+uin+"&aid=46000101&0.11248807175794862";
		    HttpGet httpget = new HttpGet(str12);
		    httpget.setHeader("Host", "captcha.qq.com");
		    httpget.setHeader("Referer", "http://ui.ptlogin2.qq.com/cgi-bin/login?appid=46000101&style=13&lang=&low_login=1&hide_title_bar=1&hide_close_icon=1&self_regurl=http%3A//reg.t.qq.com/index.php&s_url=http%3A%2F%2Ft.qq.com&daid=6");
		    httpget.setHeader("User-Agent", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");
		    HttpResponse responsex = client.execute(httpget);
		
		    File storeFile = new File("D:/fuck.jpg");
		    FileOutputStream output = new FileOutputStream(storeFile);
		
		    // 得到网络资源的字节数组,并写入文件
		    HttpEntity he = responsex.getEntity();
		    if (he != null) {
		     InputStream instream = he.getContent();
		     byte b[] = new byte[1024];
		     int j = 0;
		     while( (j = instream.read(b))!=-1){
		      output.write(b,0,j);
		     }
		     output.flush();
		     output.close();
		    }
		    Scanner input = new Scanner(System.in);
		    checkCode = input.next();
		    System.out.println("手动输入验证码："+checkCode);*/
		   }
		   
		   String pass = "";
		   /******************** *加密密码 ***************************/
		   ScriptEngineManager manager = new ScriptEngineManager();
		   ScriptEngine engine = manager.getEngineByName("javascript");
		   engine.eval("var hexcase = 1;var b64pad = \"\";var chrsz = 8;var mode = 32;function md5(A) {return hex_md5(A)}function hex_md5(A) {return binl2hex(core_md5(str2binl(A), A.length * chrsz))}"
		     + "function str_md5(A) {return binl2str(core_md5(str2binl(A), A.length * chrsz))}function hex_hmac_md5(A, B) {return binl2hex(core_hmac_md5(A, B))}function b64_hmac_md5(A, B) {return binl2b64(core_hmac_md5(A, B))}"
		     + "function str_hmac_md5(A, B) {return binl2str(core_hmac_md5(A, B))}"
		     + "function core_md5(K, F) {K[F >> 5] |= 128 << ((F) % 32);K[(((F + 64) >>> 9) << 4) + 14] = F;var J = 1732584193;var I = -271733879;var H = -1732584194;var G = 271733878;"
		     + "for (var C = 0; C < K.length; C += 16) {var E = J;var D = I;var B = H;var A = G;J = md5_ff(J, I, H, G, K[C + 0], 7, -680876936);G = md5_ff(G, J, I, H, K[C + 1], 12, -389564586);"
		     + "H = md5_ff(H, G, J, I, K[C + 2], 17, 606105819);I = md5_ff(I, H, G, J, K[C + 3], 22, -1044525330);J = md5_ff(J, I, H, G, K[C + 4], 7, -176418897);G = md5_ff(G, J, I, H, K[C + 5], 12, 1200080426);H = md5_ff(H, G, J, I, K[C + 6], 17, -1473231341);"
		     + "I = md5_ff(I, H, G, J, K[C + 7], 22, -45705983);J = md5_ff(J, I, H, G, K[C + 8], 7, 1770035416);G = md5_ff(G, J, I, H, K[C + 9], 12, -1958414417);H = md5_ff(H, G, J, I, K[C + 10], 17, -42063);"
		     + "I = md5_ff(I, H, G, J, K[C + 11], 22, -1990404162);J = md5_ff(J, I, H, G, K[C + 12], 7, 1804603682);G = md5_ff(G, J, I, H, K[C + 13], 12, -40341101);H = md5_ff(H, G, J, I, K[C + 14], 17, -1502002290);I = md5_ff(I, H, G, J, K[C + 15], 22, 1236535329);"
		     + "J = md5_gg(J, I, H, G, K[C + 1], 5, -165796510);G = md5_gg(G, J, I, H, K[C + 6], 9, -1069501632);H = md5_gg(H, G, J, I, K[C + 11], 14, 643717713);I = md5_gg(I, H, G, J, K[C + 0], 20, -373897302);"
		     + "J = md5_gg(J, I, H, G, K[C + 5], 5, -701558691);G = md5_gg(G, J, I, H, K[C + 10], 9, 38016083);H = md5_gg(H, G, J, I, K[C + 15], 14, -660478335);I = md5_gg(I, H, G, J, K[C + 4], 20, -405537848);"
		     + "J = md5_gg(J, I, H, G, K[C + 9], 5, 568446438);G = md5_gg(G, J, I, H, K[C + 14], 9, -1019803690);H = md5_gg(H, G, J, I, K[C + 3], 14, -187363961);I = md5_gg(I, H, G, J, K[C + 8], 20, 1163531501);"
		     + "J = md5_gg(J, I, H, G, K[C + 13], 5, -1444681467);G = md5_gg(G, J, I, H, K[C + 2], 9, -51403784);H = md5_gg(H, G, J, I, K[C + 7], 14, 1735328473);I = md5_gg(I, H, G, J, K[C + 12], 20, -1926607734);"
		     + "J = md5_hh(J, I, H, G, K[C + 5], 4, -378558);G = md5_hh(G, J, I, H, K[C + 8], 11, -2022574463);H = md5_hh(H, G, J, I, K[C + 11], 16, 1839030562);I = md5_hh(I, H, G, J, K[C + 14], 23, -35309556);"
		     + "J = md5_hh(J, I, H, G, K[C + 1], 4, -1530992060);G = md5_hh(G, J, I, H, K[C + 4], 11, 1272893353);H = md5_hh(H, G, J, I, K[C + 7], 16, -155497632);I = md5_hh(I, H, G, J, K[C + 10], 23, -1094730640);"
		     + "J = md5_hh(J, I, H, G, K[C + 13], 4, 681279174);G = md5_hh(G, J, I, H, K[C + 0], 11, -358537222);H = md5_hh(H, G, J, I, K[C + 3], 16, -722521979);I = md5_hh(I, H, G, J, K[C + 6], 23, 76029189);J = md5_hh(J, I, H, G, K[C + 9], 4, -640364487);G = md5_hh(G, J, I, H, K[C + 12], 11, -421815835);H = md5_hh(H, G, J, I, K[C + 15], 16, 530742520);"
		     + "I = md5_hh(I, H, G, J, K[C + 2], 23, -995338651);J = md5_ii(J, I, H, G, K[C + 0], 6, -198630844);G = md5_ii(G, J, I, H, K[C + 7], 10, 1126891415);H = md5_ii(H, G, J, I, K[C + 14], 15, -1416354905);"
		     + "I = md5_ii(I, H, G, J, K[C + 5], 21, -57434055);J = md5_ii(J, I, H, G, K[C + 12], 6, 1700485571);G = md5_ii(G, J, I, H, K[C + 3], 10, -1894986606);H = md5_ii(H, G, J, I, K[C + 10], 15, -1051523);I = md5_ii(I, H, G, J, K[C + 1], 21, -2054922799);"
		     + "J = md5_ii(J, I, H, G, K[C + 8], 6, 1873313359);G = md5_ii(G, J, I, H, K[C + 15], 10, -30611744);H = md5_ii(H, G, J, I, K[C + 6], 15, -1560198380);I = md5_ii(I, H, G, J, K[C + 13], 21, 1309151649);"
		     + "J = md5_ii(J, I, H, G, K[C + 4], 6, -145523070);G = md5_ii(G, J, I, H, K[C + 11], 10, -1120210379);H = md5_ii(H, G, J, I, K[C + 2], 15, 718787259);I = md5_ii(I, H, G, J, K[C + 9], 21, -343485551);"
		     + "J = safe_add(J, E);I = safe_add(I, D);H = safe_add(H, B);G = safe_add(G, A)}if (mode == 16) {return Array(I, H)} else {return Array(J, I, H, G)}}function md5_cmn(F, C, B, A, E, D) {"
		     + "return safe_add(bit_rol(safe_add(safe_add(C, F), safe_add(A, D)), E), B)}function md5_ff(C, B, G, F, A, E, D) {return md5_cmn((B & G) | ((~B) & F), C, B, A, E, D)}"
		     + "function md5_gg(C, B, G, F, A, E, D) {return md5_cmn((B & F) | (G & (~F)), C, B, A, E, D)}function md5_hh(C, B, G, F, A, E, D) {return md5_cmn(B ^ G ^ F, C, B, A, E, D)}"
		     + "function md5_ii(C, B, G, F, A, E, D) {return md5_cmn(G ^ (B | (~F)), C, B, A, E, D)}function core_hmac_md5(C, F) {var E = str2binl(C);if (E.length > 16) {E = core_md5(E, C.length * chrsz)}"
		     + "var A = Array(16), D = Array(16);for (var B = 0; B < 16; B++) {A[B] = E[B] ^ 909522486;D[B] = E[B] ^ 1549556828}var G = core_md5(A.concat(str2binl(F)), 512 + F.length * chrsz);"
		     + "return core_md5(D.concat(G), 512 + 128)}function safe_add(A, D) {var C = (A & 65535) + (D & 65535);var B = (A >> 16) + (D >> 16) + (C >> 16);return (B << 16) | (C & 65535)}"
		     + "function bit_rol(A, B) {return (A << B) | (A >>> (32 - B))}function str2binl(D) {var C = Array();var A = (1 << chrsz) - 1;for (var B = 0; B < D.length * chrsz; B += chrsz) {"
		     + "C[B >> 5] |= (D.charCodeAt(B / chrsz) & A) << (B % 32)}return C}function binl2str(C) {var D = \"\";var A = (1 << chrsz) - 1;for (var B = 0; B < C.length * 32; B += chrsz) {"
		     + "D += String.fromCharCode((C[B >> 5] >>> (B % 32)) & A)}return D}function binl2hex(C) {var B = hexcase ? \"0123456789ABCDEF\" : \"0123456789abcdef\";var D = \"\";"
		     + "for (var A = 0; A < C.length * 4; A++) {D += B.charAt((C[A >> 2] >> ((A % 4) * 8 + 4)) & 15)+ B.charAt((C[A >> 2] >> ((A % 4) * 8)) & 15)}return D}"
		     + "function binl2b64(D) {var C = \"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/\";var F = \"\";for (var B = 0; B < D.length * 4; B += 3) {var E = (((D[B >> 2] >> 8 * (B % 4)) & 255) << 16)"
		     + "| (((D[B + 1 >> 2] >> 8 * ((B + 1) % 4)) & 255) << 8)| ((D[B + 2 >> 2] >> 8 * ((B + 2) % 4)) & 255);for (var A = 0; A < 4; A++) {if (B * 8 + A * 6 > D.length * 32) {F += b64pad"
		     + "} else {F += C.charAt((E >> 6 * (3 - A)) & 63)}}}return F}function hexchar2bin(str) {var arr = [];for (var i = 0; i < str.length; i = i + 2) {arr.push(\"\\\\x\" + str.substr(i, 2))"
		     + "}arr = arr.join(\"\");eval(\"var temp = '\" + arr + \"'\");return temp}function QXWEncodePwd(pt_uin, p, vc) {var I = hexchar2bin(md5(p));var H = md5(I + TTescapechar2bin(pt_uin));"
		     + "var G = md5(H + vc.toUpperCase());return G}function TTescapechar2bin(str) {eval(\"var temp = '\" + str + \"'\");return temp}");
		   if (engine instanceof Invocable) {
		    Invocable invoke = (Invocable) engine;
		    pass = invoke.invokeFunction("QXWEncodePwd",
		      checkNum[2].trim(), p, checkCode).toString();
		   }
		
		   /************************* 登录 ****************************/
		   String strs="";
		    strs="http://ptlogin2.qq.com/login?u="+uin+"&p="+pass+"&verifycode="+ checkCode.trim()+"&aid=46000101&u1=http%3A%2F%2Ft.qq.com&h=1&ptredirect=1&ptlang=2052&daid=6&from_ui=1&dumy=&low_login_enable=1&low_login_hour=720&regmaster=&fp=loginerroralert&action=3-27-1387937865955&mibao_css=&t=1&g=1&js_ver=10061&js_type=1&login_sig="+login_sig+"&pt_rsa=0";
		   
		   get = new HttpGet(strs);
		   get.setHeader("Accept","application/javascript, */*;q=0.8");
		   get.setHeader("Accept-Language", "zh-CN");
		   get.setHeader("Accept-Encoding", "gzip,deflate");
		   get.setHeader("Connection", "keep-alive");
		   get.setHeader("Host", "ptlogin2.qq.com");
		   get.setHeader("Referer", "http://ui.ptlogin2.qq.com/cgi-bin/login?appid=46000101&style=13&lang=&low_login=1&hide_title_bar=1&hide_close_icon=1&self_regurl=http%3A//reg.t.qq.com/index.php&s_url=http%3A%2F%2Ft.qq.com&daid=6");
		   get.setHeader("Cookie",ck);
		   response = client.execute(get);
		   entity = EntityUtils.toString(response.getEntity());
		   System.out.println(entity);
		
		   String impUrl="";
		   if(entity.indexOf("登录成功")!=-1){
		    impUrl = entity.substring(16,entity.indexOf("登录成功")-7);
		    get = new HttpGet(impUrl);
		    HttpResponse response2 = client.execute(get);
		
		    String temp = "";
		    String cookieP = "";
		    int count = 1;
		    Header[] h = response2.getAllHeaders();
		    for (int i = 0; i < h.length; i++) {
		     if (h[i].getName().contains("Set-Cookie")) {
		      temp = h[i].getValue();
		      // cookie是键值对，此处的";"一定要加上
		      if (count == 1) {
		       cookieP = cookieP + temp;
		      } else {
		       cookieP = cookieP + ";" + temp;
		      }
		      count++;
		     }
		    }
		    List<Cookie> cookiesList = ((AbstractHttpClient) client).getCookieStore().getCookies(); 
		    if (cookiesList.isEmpty()) { 
		     System.out.println("None"); 
		    } else { 
		     for (int i = 0; i < cookiesList.size(); i++) {
		      cookies = cookies +cookiesList.get(i).getName()+"="+cookiesList.get(i).getValue()+";";
		     } 
		    }
		    System.out.println("成功："+uin);
		   }else {
		    System.out.println("失败："+uin);
		   }
		
		   /*String pa = "D:/cookies.txt";
		   //   String pa = "/data/account/qq/cookies.txt";
		   File file = new File(pa);
		   if (!file.exists()) {
		    file.createNewFile();
		   }
		   List<String> list = new ArrayList<String>();
		   BufferedReader br = new BufferedReader(new FileReader(file));  
		   String line = null;  
		   //因为不知道有几行数据，所以先存入list集合中  
		   while((line = br.readLine()) != null){   
		    list.add(line);
		   }  
		   br.close(); 
		
		
		   FileWriter fileWriter = new FileWriter(pa);
		   BufferedWriter bw = new BufferedWriter(fileWriter);
		   for (int i = 0; i < list.size(); i++) {
		    bw.write(list.get(i).toString()+"\n");
		   }
		   bw.write(cookies);
		   bw.close();  
		   fileWriter.close();
		   System.out.println("本次完成");*/
		  } catch (Exception e) {
		   return null;
		  }
		  return cookies;
 }

      
    public static void main(String[] args) throws Exception  
    {  
        login("26817476", "tqc45684516");
        HttpGet get = new HttpGet(getUrl());
        setToken(get);  
        System.out.println(getRecentNews());
    }  

}
