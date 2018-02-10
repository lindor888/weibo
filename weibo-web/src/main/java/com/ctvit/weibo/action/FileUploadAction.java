package com.ctvit.weibo.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ctvit.weibo.util.KeyUtil;
import com.ctvit.weibo.util.ResourceLoader;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 文件上传
 * @author Administrator
 *
 */
public class FileUploadAction extends ActionSupport{
	private Logger log = Logger.getLogger(FileUploadAction.class);
	
	private static final long serialVersionUID = 1L;
	// 封装上传文件域的属性
    private File  image;
    // 封装上传文件类型的属性
    private String imageContentType;
    // 封装上传文件名的属性
    private String imageFileName;
    // 接受依赖注入的属性
    private String savePath;
    private Map<String,Object> queryJson;
    
    private static String IMAGE_FILE = ResourceLoader.getInstance().getConfig().getProperty("imageFile");
    private static String IMAGE_URL = ResourceLoader.getInstance().getConfig().getProperty("imageUrl");
    
    public String upload() {
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            // 建立文件输出流
            if(getImage()!=null){
            	String imageId = KeyUtil.getKey("WEIBO"+new Object());
            	String imageName = imageId + getImageFileName().substring(getImageFileName().lastIndexOf("."), getImageFileName().length());
            	String filePath = this.getFilePath();
                String path = IMAGE_FILE + filePath + imageName;
                String urlPath = IMAGE_URL + filePath + imageName;
                File file = new File(path);
                if(!file.getParentFile().exists())
    	 		file.getParentFile().mkdirs();
    			if(!file.exists()) 
    			file.createNewFile();
            	fos = new FileOutputStream(path);
                // 建立文件上传流
                fis = new FileInputStream(getImage());
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                queryJson = new HashMap<String,Object>();
                queryJson.put("imageUrl", urlPath);
                
                HttpServletResponse response =  ServletActionContext.getResponse();
                //设置response的ContentType解决中文乱码
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print("{\"imageUrl\":\""+urlPath+"\"}");
            }
        } catch (Exception e) {
            System.out.println("文件上传失败");
            e.printStackTrace();
            log.error("文件上传失败:"+ e.toString());
        } finally {
            close(fos, fis);
        }
        return null;
    }
    
    
    private String getFilePath(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String now = sdf.format(new Date());
		String retPath = now.substring(0,4) + "/";
		retPath += now.substring(4, 6) + "/";
		retPath += now.substring(6, 8) + "/";
    	return retPath;
    }
    
    /**
     * 返回上传文件的保存位置
     * 
     * @return
     */
    public String getSavePath() throws Exception{
        return ServletActionContext.getServletContext().getRealPath(savePath); 
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

	private void close(FileOutputStream fos, FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                System.out.println("FileInputStream关闭失败");
                e.printStackTrace();
            }
        }
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                System.out.println("FileOutputStream关闭失败");
                e.printStackTrace();
            }
        }
    }


	public File getImage() {
		return image;
	}


	public void setImage(File image) {
		this.image = image;
	}


	public String getImageContentType() {
		return imageContentType;
	}


	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}


	public String getImageFileName() {
		return imageFileName;
	}


	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public Map<String, Object> getQueryJson() {
		return queryJson;
	}

	public void setQueryJson(Map<String, Object> queryJson) {
		this.queryJson = queryJson;
	}

	
}
