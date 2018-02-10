package com.ctvit.weibo.entity;

import com.ctvit.weibo.util.Pagination;

public class SearchBean extends Pagination{

	private String coreName;
	private String q;//查询关键词（需要encode）
	private String wt;//输入格式 默认为xml ，可以有xml,json
	//private Integer start;//用于分页定义结果起始记录数，默认为0
	//private Integer rows;//用于分页定义结果每页返回记录数，默认为10
	private String fq;//在q查询符合结果中同时是fq查询符合的，
						//例如：q=mm&fq=date_time:[20081001 TO  20091031]，
						//找关键字mm，并且date_time是20081001到20091031之间的
	private String sort;//排序，格式：sort=<field name>+<desc|asc>[,<field name>+<desc|asc>]… 。
						//示例：（inStock desc, price asc）表示先 “inStock” 降序, 再 “price” 升序，默认是相关性降序
	private String shards;//多核心联合检索参数，例如同时返回正文和视频的数据
						//(shards=XXXXX/search_main/article,XXXXX/search_main/video)
	
	
	private String channelName;//频道名称
	private String logoPhoto;//图片
	private String channelId;//频道ID
	private String tag;//标签
	private String editorname;//系统管理员
	private String type;//类型
	private String publishdate;//发布时间
	private String url;//静态页面url
	private String id;//主键
	private String title;//标题
	private String currentdate;//创建时间
	private String brief;//简介
	

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getWt() {
		return wt;
	}

	public void setWt(String wt) {
		this.wt = wt;
	}

	public String getFq() {
		return fq;
	}

	public void setFq(String fq) {
		this.fq = fq;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getShards() {
		return shards;
	}

	public void setShards(String shards) {
		this.shards = shards;
	}

	public String getCoreName() {
		return coreName;
	}

	public void setCoreName(String coreName) {
		this.coreName = coreName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getLogoPhoto() {
		return logoPhoto;
	}

	public void setLogoPhoto(String logoPhoto) {
		this.logoPhoto = logoPhoto;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPublishdate() {
		return publishdate;
	}

	public void setPublishdate(String publishdate) {
		this.publishdate = publishdate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCurrentdate() {
		return currentdate;
	}

	public void setCurrentdate(String currentdate) {
		this.currentdate = currentdate;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getEditorname() {
		return editorname;
	}

	public void setEditorname(String editorname) {
		this.editorname = editorname;
	}

}
