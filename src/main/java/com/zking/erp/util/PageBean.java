package com.zking.erp.util;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class PageBean implements Serializable {

	//页码
	private int page=1;
	//每页显示条数
	private int rows=10;
	//总记录数
	private int total=0;
	//是否分页标记
	private boolean pagination=true;
	//上一次请求的路径
	private String url;
	//请求参数Map集合
	private Map<String,String[]> map;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Map<String, String[]> getMap() {
		return map;
	}
	public void setMap(Map<String, String[]> map) {
		this.map = map;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public boolean isPagination() {
		return pagination;
	}
	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}
	
	//重载setPage/setRows/setPagination方法
	public void setPage(String page) {
		if(null!=page&&!"".equals(page))
			this.page=Integer.parseInt(page);
	}
	public void setRows(String rows) {
		if(null!=rows&&!"".equals(rows))
			this.rows=Integer.parseInt(rows);
	}
	public void setPagination(String pagination) {
		if(null!=pagination&&!"".equals(pagination))
			this.pagination=Boolean.parseBoolean(pagination);
	}
	
	public void setRequest(HttpServletRequest req) {
		//获取前端提交的page/rows/pagination参数
		String page=req.getParameter("page");
		String rows=req.getParameter("rows");
		String pagination=req.getParameter("pagination");
		
		//设置属性
		this.setPage(page);
		this.setPagination(pagination);
		this.setRows(rows);
		
		//设置上一次请求的路径
		//第一次请求：
		//http://localhost:8080/项目名/请求路径.action?page=1
		//第二次请求：下一页  page=2
		//项目名+请求路径.action
		//req.getContextPath()+req.getServletPath()==req.getRequestURI()
		this.url=req.getRequestURI();
		
		//获取请求参数集合
		// checkbox name='hobby'
		// Map<String,String[]> hobby==key  value==new String[]{"篮球","足球",..}
		this.map=req.getParameterMap();
	}
	
	/**
	 * 获取分页的起始位置
	 * @return
	 */
	public int getStartIndex() {
		//第1页：(1-1)*10  ==0    limit 0,10
		//第2页：(2-1)*10  ==10   limit 10,10
		//..
		return (this.page-1)*this.rows;
	}
	
	//获取末页、上一页、下一页
	/**
	 * 获取末页
	 * @return
	 */
	public int getMaxPager() {
		int maxPager=this.total/this.rows;
		if(this.total%this.rows!=0)
			maxPager++;
		return maxPager;
	}
	
	/**
	 * 获取上一页
	 * @return
	 */
	public int getPreviousPager() {
		int previousPager=this.page-1;
		if(previousPager<=1)
			previousPager=1;
		return previousPager;
	}
	
	/**
	 * 获取下一页
	 * @return
	 */
	public int getNextPager() {
		int nextPager=this.page+1;
		if(nextPager>getMaxPager())
			nextPager=getMaxPager();
		return nextPager;
	}
	@Override
	public String toString() {
		return "PageBean [page=" + page + ", rows=" + rows + ", total=" + total + ", pagination=" + pagination
				+ ", url=" + url + ", map=" + map + "]";
	}
	
}











