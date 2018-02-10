package com.ctvit.weibo.util;

/**
 * 分页父类
 * @author 邵亚东 2012-12-3
 */
public class Pagination {
	/**
	 * 计数用，用来标示rows和page都存在
	 */
	private int count = 0;
	/**
	 * 从第多少行开始，包含
	 */
	private Integer beginIndex; 
	/**
	 * 到第多少行结束，不包含
	 */
	private Integer endIndex;
	/**
	 * 一页显示多少行
	 */
	private Integer rows;
	/**
	 * 展示第几页的内容
	 */
	private Integer page;
	/**
	 * 一共有多少页
	 */
	private Integer pageSum;
	
	/**
	 * 设置分页
	 * @author 邵亚东 2012-12-3
	 */
	private void setPagination(){
		count++;
		if(count==2){
			beginIndex = (page - 1) * rows;
			endIndex = beginIndex + rows;
		}
	}
	
//+++++++++++++++++++++++++++++++++++++++++++++++++

	/**
	 * @return 从第多少行开始，包含
	 */
	public Integer getBeginIndex() {
		return beginIndex;
	}

	/**
	 * @param 从第多少行开始，包含
	 */
	public void setBeginIndex(Integer beginIndex) {
		this.beginIndex = beginIndex;
	}

	/**
	 * @return 到第多少行结束，不包含
	 */
	public Integer getEndIndex() {
		return endIndex;
	}

	/**
	 * @param 到第多少行结束，不包含
	 */
	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}

	/**
	 * @return 一页显示多少行
	 */
	public Integer getRows() {
		return rows;
	}

	/**
	 * @param 一页显示多少行
	 */
	public void setRows(Integer rows) {
		if(rows!=null){
			this.rows = rows;
			setPagination();
		}
	}

	/**
	 * @return 展示第几页的内容
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param 展示第几页的内容
	 */
	public void setPage(Integer page) {
		if(page!=null){
			this.page = page;
			setPagination();
		}
	}

	/**
	 * @return 一共有多少页
	 */
	public Integer getPageSum(Integer total) {
		if(pageSum==null&&this.rows!=null){
			pageSum = (total + (this.rows - 1)) / this.rows;
		}
		return pageSum;
	}

	/**
	 * @param pageSum 一共有多少页
	 */
	public void setPageSum(Integer pageSum) {
		this.pageSum = pageSum;
	}
}
