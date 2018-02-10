package com.ctvit.weibo.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ctvit.weibo.entity.App;
import com.ctvit.weibo.entity.AppExample;
import com.ctvit.weibo.entity.FriendWeibo;
import com.ctvit.weibo.entity.FriendWeiboExample;
import com.ctvit.weibo.entity.User;
import com.ctvit.weibo.entity.Weibo;
import com.ctvit.weibo.entity.WeiboExample;
import com.ctvit.weibo.entity.Words;
import com.ctvit.weibo.service.AppService;
import com.ctvit.weibo.service.WeiboService;
import com.ctvit.weibo.service.WordsService;
import com.ctvit.weibo.studio.entity.BtvComment;
import com.ctvit.weibo.studio.entity.BtvUser;
import com.ctvit.weibo.util.ConstantParam;
import com.ctvit.weibo.util.SensitiveWordFilter;
import com.ctvit.weibo.util.SinaWeiboUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

/**
 * 
 * @author Administrator
 * 
 */
public class WeiboAction extends ActionSupport {

	private static final long serialVersionUID = 3172461683182708318L;
	/**
	 * 日志对象
	 */
	private Logger log = Logger.getLogger(WeiboAction.class);

	private AppService appService;// 我的应用服务类
	private WeiboService weiboService;// 微博服务类

	private List<App> apps;// 我的应用集合
	private List<Weibo> weibos;// 我的微博集合
	private Map<String, Object> queryJson;

	private SinaWeiboUtil sinaWeiboUtil;// 新浪微博工具类

	private Weibo bean;// 我的微博实体类
	private App app;// 应用
	private FriendWeibo friendWeibo;// 我的关注微博实体类
	private WeiboExample example;// 我的微博分页类
	private FriendWeiboExample friendExample;// 我的关注微博分页类

	private File fileupload; // 和JSP中input标记name同名
	private String fileuploadFileName; // 上传来的文件的名字

	private WordsService wordsService;

	private BtvComment btvComment;

	private BtvUser btvUser;

	private String weiboId;

	private String taskWeiboId;

	private String commentId;

	/**
	 * 添加或者修改微博的评论
	 * 
	 * @return
	 */
	public String addOrUpdateBtvComment() {
		queryJson = new HashMap<String, Object>();
		try {
			weiboService.addOrUpdateBtvComment(btvComment, weiboId, taskWeiboId);
			queryJson.put("msg", "success");
		} catch (Exception e) {
			queryJson.put("msg", "error");
			log.error("", e);
		}
		return "addOrUpdateBtvComment";
	}

	/**
	 * 查询编辑维护的用户
	 * 
	 * @return
	 */
	public String selectBtvUser() {
		queryJson = new HashMap<String, Object>();
		try {
			List<BtvUser> list = weiboService.selectBtvUser();
			queryJson.put("rows", list);
		} catch (Exception e) {
			queryJson.put("msg", "error");
			log.error("", e);
		}
		return "selectBtvUser";
	}

	/**
	 * 按主键查询用户
	 * 
	 * @return
	 */
	public String selectBtvUserByKey() {
		queryJson = new HashMap<String, Object>();
		try {
			BtvUser b = weiboService.selectBtvUserByKey(btvUser);
			queryJson.put("rows", b);
		} catch (Exception e) {
			queryJson.put("msg", "error");
			log.error("", e);
		}
		return "selectBtvUserByKey";

	}

	public String indexPre() throws Exception {
		try {
			User user = getSessionUser();
			AppExample appExample = new AppExample();
			if (user != null) {
				appExample.setUserId(user.getUserId());
			}
			apps = appService.getByPaging(appExample);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "indexPre";
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getByPaging() {
		try {
			User user = getSessionUser();
			if (user != null) {
				example.setUserId(user.getUserId());
			}
			weibos = weiboService.getByPaging(example);
			int total = weiboService.getCount(example);
			queryJson = new HashMap<String, Object>();
			queryJson.put("total", total);
			queryJson.put("pageSum", example.getPageSum(total));
			queryJson.put("rows", weibos);
		} catch (Exception e) {
			log.error("", e);
		}
		return "dataList";
	}

	/**
	 * 准备添加微博，同时查出我的应用
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addPre() {
		try {
			User user = getSessionUser();
			AppExample appExample = new AppExample();
			if (user != null) {
				appExample.setUserId(user.getUserId());
			}
			apps = appService.getByPaging(appExample);
		} catch (Exception e) {
			log.error("", e);
		}
		return "addPre";
	}

	/**
	 * 我的应用-->查看绑定微博--->添加微博时调用方法
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addPreApp() {
		try {
			app = appService.searchById(bean.getAppId());
			app.setAppName(new String(bean.getAppName().getBytes("iso8859-1"), "UTF-8"));
		} catch (Exception e) {
			log.error("", e);
		}
		return "addPreApp";
	}

	/**
	 * 添加微博
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() {
		try {
			weiboService.add(bean);
			return refreshTableDataOfParentWindow();
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}

	/**
	 * 检查微博是否已经添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchByUserName() {
		try {
			Weibo weibo = null;
			// 更新微博时，当没有修改微博登录名时，不检查微博是否已添加，直接进行token验证
			if (!bean.getWeiboUserName().equals(bean.getWeiboUserNameOld())) {
				weibo = weiboService.searchByWeiboUserName(bean);
			}
			queryJson = new HashMap<String, Object>();
			if (weibo != null) {
				queryJson.put("msg", 1);// 该微博已添加
			} else {
				String token = sinaWeiboUtil.getToken(bean.getAppKey(), bean.getAppSecret(), bean.getAppRedirectUri(), bean.getWeiboUserName(), bean.getWeiboPassword());
				if (token == null) {
					queryJson.put("msg", 2);// 该微博验证失败
				} else {
					queryJson.put("msg", token);// 验证成功并且没有添加
				}
			}
		} catch (Exception e) {
			queryJson.put("msg", 2);// 该微博已添加
			log.error("", e);
		}
		return "dataList";
	}

	/**
	 * 更新前先查出微博信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updatePre() {
		try {
			apps = appService.getByPaging(null);
			bean = weiboService.searchById(bean.getWeiboId());
		} catch (Exception e) {
			log.error("", e);
		}
		return "updatePre";
	}

	/**
	 * 更新微博信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() {
		try {
			weiboService.update(bean);
		} catch (Exception e) {
			log.error("", e);
		}
		return refreshTableDataOfParentWindow();
	}

	/**
	 * 删除微博
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() {
		try {
			bean.setDeleteFlag(ConstantParam.STATUS_INVALID);
			weiboService.update(bean);
			queryJson = new HashMap<String, Object>();
			queryJson.put("result", "success");
		} catch (Exception e) {
			log.error("", e);
		}
		return "dataList";
	}

	public String contentIndexPre() {
		try {
			Weibo weibo = new Weibo();
			User user = (User) ActionContext.getContext().getSession().get("user");
			weibo.setUserId(user.getUserId());
			weibos = weiboService.selectByUserId(weibo);
		} catch (Exception e) {
			log.error("", e);
		}
		return "contentIndexPre";
	}

	/**
	 * 通过微博用户ID查询已发微博信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String selectContent() {
		try {

			List<Weibo> weibos = weiboService.selectContentByWeiboId(example);
			int count = weiboService.selectContentCount(example);
			queryJson = new HashMap<String, Object>();

			queryJson = new HashMap<String, Object>();
			queryJson.put("total", weibos.size());
			queryJson.put("pageSum", example.getPageSum(count));

			queryJson.put("rows", weibos);
		} catch (Exception e) {
			log.error("", e);
		}
		return "dataList";
	}

	/**
	 * 获取微博内容列表xml ross
	 * 
	 * @return
	 */
	public void getRoss() {
		queryJson = new HashMap<String, Object>();
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			List<Weibo> weibos = weiboService.getRossList();
			// queryJson.put("total", list.size());
			XStream xstream = new XStream();
			xstream.alias("WeiboData", Weibo.class);
			// xstream.alias("interacts", List.class);
			// xstream.alias("interacts", Interacts.class);
			String xml = "<?xml version='1.0' encoding='UTF-8' ?>";
			xml += xstream.toXML(weibos);
			// System.out.println(xml);
			// 解决历史数据问题，将图片的外网地址替换为内网地址
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			// new PrintWriter(new OutputStreamWriter(new (json, "UTF-8"));
			// out.print(xml);
			out.write(xml);
			out.flush();
			out.close();
			queryJson.put("rows", weibos);
		} catch (Exception e) {
			log.error("", e);
		}

	}

	/**
	 * 根据commentId获取评论信息
	 * 
	 * @return
	 */
	public String selectCommentByKey() {
		queryJson = new HashMap<String, Object>();
		try {
			BtvComment btvComment = weiboService.selectCommentByKey(commentId);
			queryJson.put("rows", btvComment);

		} catch (Exception e) {
			log.error("", e);
		}
		return "selectCommentByKey";
	}

	/**
	 * 通过微博ID查询微博评论
	 * 
	 * @return
	 * @throws Exception
	 */
	public String selectComment() {
		try {
			List<Weibo> weibos = weiboService.selectComment(example);
			int count = weiboService.selectCommentCount(example);

			// 这里需要过滤敏感词
			List<Words> wordsList = wordsService.findListByPaging(null);
			List<String> contentList = new ArrayList<String>();
			for (Words w : wordsList) {
				contentList.add(w.getContent());
			}

			SensitiveWordFilter filter = new SensitiveWordFilter();
			filter.addKeywords(contentList);

			for (Weibo weibo : weibos) {
				String content = weibo.getContent();
				if (content != null && !"".equals(content)) {
					Set<String> set = filter.getTxtKeyWords(content);
					for (String t : set) {
						content = content.replaceAll(t, "<font color=blue>" + t + "</font>");
					}
					weibo.setContent(content);
				}
			}

			queryJson = new HashMap<String, Object>();
			queryJson.put("rows", weibos);
			queryJson.put("total", weibos.size());
			queryJson.put("pageSum", example.getPageSum(count));
		} catch (Exception e) {
			log.error("", e);
		}
		return "dataList";
	}

	/**
	 * 发送微博页
	 * 
	 * @return
	 * @throws Exception
	 */
	public String send() {
		try {
			int status = weiboService.send(bean);
			queryJson = new HashMap<String, Object>();
			if (status == 0) {
				log.error("error cause image");
				queryJson.put("msg", "error");
				return "error";
			} else {
				queryJson.put("msg", "ok");
				log.info(status);
			}
		} catch (Exception e) {
			queryJson.put("msg", "error");
			log.error("", e);
		}
		return "dataList";
	}

	/**
	 * 审核微博
	 * 
	 * @return
	 */
	public String shenhew() {
		queryJson = new HashMap<String, Object>();
		try {
			weiboService.shenheWeibo(example.getUserId(), example.getWeiboId(), example.getFlag());
			queryJson.put("msg", "success");
		} catch (Exception e) {
			queryJson.put("msg", "error");
			log.error("", e);
		}
		return "shenhew";
	}

	/**
	 * 审核评论
	 * 
	 * @return
	 */
	public String shenhec() {
		queryJson = new HashMap<String, Object>();
		try {
			weiboService.shenheComment(example.getUserId(), example.getWeiboId(), example.getFlag());
			queryJson.put("msg", "success");
		} catch (Exception e) {
			queryJson.put("msg", "error");
			log.error("", e);
		}
		return "shenhec";
	}

	public String uploadlogo() {

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码

		String extName = ""; // 保存文件拓展名
		String newFileName = ""; // 保存新的文件名
		String photoId = "";// 保存新的文件id
		String nowTimeStr = "";
		String rootPath = "D:\\Apache2.2";
		String photoCachePath = "\\image\\";
		try {
			weiboService.createDir(rootPath + photoCachePath);
			// 获取拓展名
			if (fileuploadFileName.lastIndexOf(".") >= 0) {
				extName = fileuploadFileName.substring(fileuploadFileName.lastIndexOf("."));
			}
			newFileName = System.currentTimeMillis() + extName; // 文件重命名后的名字
			boolean re = copyFile(fileupload.getPath(), rootPath + photoCachePath + newFileName);
			List<Weibo> photoList = new ArrayList<Weibo>();
			bean = new Weibo();
			bean.setImageUrl(rootPath + photoCachePath + newFileName);
			photoList.add(bean);
			queryJson = new Hashtable<String, Object>();
			queryJson.put("root", photoList);
			queryJson.put("message", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			return "uploadlogo";
		}
		return "dataList";
	}

	/**
	 * 刷新父窗口表格里的数据
	 * 
	 * @return
	 * @throws Exception
	 */
	protected String refreshTableDataOfParentWindow() {
		try {
			String html = "<script language='javascript'>parent.window.refreshTableData();</script>";
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			out.println(html);
			out.flush();
			out.close();
		} catch (IOException e) {
			log.error("", e);
		}
		return null;
	}

	/**
	 * 刷新父窗口表格里的数据
	 * 
	 * @return
	 * @throws Exception
	 */
	protected String refreshTableDataOfParentWindow(String msg) {
		try {
			String html = "<script language='javascript'>parent.window.refreshTableData('" + msg + "');</script>";
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			out.println(html);
			out.flush();
			out.close();
		} catch (IOException e) {
			log.error("", e);
		}
		return null;
	}

	public boolean copyFile(String srcName, String destName) {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(srcName));
			out = new BufferedOutputStream(new FileOutputStream(destName));
			int i = 0;
			byte[] buffer = new byte[2048];
			while ((i = in.read(buffer)) != -1) {
				out.write(buffer, 0, i);
			}
			out.close();
			in.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {

				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {

				}
			}
		}
	}

	private User getSessionUser() {
		User user = (User) ActionContext.getContext().getSession().get("user");
		return user;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	public List<App> getApps() {
		return apps;
	}

	public Weibo getBean() {
		return bean;
	}

	public void setBean(Weibo bean) {
		this.bean = bean;
	}

	public void setQueryJson(Map<String, Object> queryJson) {
		this.queryJson = queryJson;
	}

	public void setWeiboService(WeiboService weiboService) {
		this.weiboService = weiboService;
	}

	public Map<String, Object> getQueryJson() {
		return queryJson;
	}

	public void setSinaWeiboUtil(SinaWeiboUtil sinaWeiboUtil) {
		this.sinaWeiboUtil = sinaWeiboUtil;
	}

	public void setExample(WeiboExample example) {
		this.example = example;
	}

	public WeiboExample getExample() {
		return example;
	}

	public FriendWeiboExample getFriendExample() {
		return friendExample;
	}

	public void setFriendExample(FriendWeiboExample friendExample) {
		this.friendExample = friendExample;
	}

	public FriendWeibo getFriendWeibo() {
		return friendWeibo;
	}

	public void setFriendWeibo(FriendWeibo friendWeibo) {
		this.friendWeibo = friendWeibo;
	}

	public List<Weibo> getWeibos() {
		return weibos;
	}

	public void setFileupload(File fileupload) {
		this.fileupload = fileupload;
	}

	public void setFileuploadFileName(String fileuploadFileName) {
		this.fileuploadFileName = fileuploadFileName;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public WordsService getWordsService() {
		return wordsService;
	}

	public void setWordsService(WordsService wordsService) {
		this.wordsService = wordsService;
	}

	public BtvComment getBtvComment() {
		return btvComment;
	}

	public void setBtvComment(BtvComment btvComment) {
		this.btvComment = btvComment;
	}

	public String getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(String weiboId) {
		this.weiboId = weiboId;
	}

	public BtvUser getBtvUser() {
		return btvUser;
	}

	public void setBtvUser(BtvUser btvUser) {
		this.btvUser = btvUser;
	}

	public String getTaskWeiboId() {
		return taskWeiboId;
	}

	public void setTaskWeiboId(String taskWeiboId) {
		this.taskWeiboId = taskWeiboId;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

}
