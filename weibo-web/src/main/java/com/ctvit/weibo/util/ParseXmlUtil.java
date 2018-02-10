package com.ctvit.weibo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ctvit.weibo.entity.SearchBean;

public class ParseXmlUtil {
	
	/**
	 * 解析微博xml
	 * @param xmlData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getXmlContent(String xmlData){
		
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			List<SearchBean> list = new ArrayList<SearchBean>();
			SearchBean bean = null;
			
			Document doc = DocumentHelper.parseText(xmlData);
			Element result = doc.getRootElement().element("result");
			Integer total = Integer.parseInt(result.attributeValue("numFound"));
			List<Element> docElements = result.elements("doc");
			if(docElements != null && docElements.size() > 0) {
				for(Element docElement : docElements) {
					bean = new SearchBean();
					List<Element> strElements = docElement.elements("str");
					if(strElements != null && strElements.size() > 0) {
						for(Element strElement : strElements){
							String name = strElement.attributeValue("name");
							String value = strElement.getText();
							if("channelName".equals(name)) {
								bean.setChannelName(value);
							} else if("logoPhoto".equals(name)) {
								bean.setLogoPhoto(value);
							} else if("channelId".equals(name)) {
								bean.setChannelId(value);
							} else if("tag".equals(name)) {
								bean.setTag(value);
							} else if("editorname".equals(name)) {
								bean.setEditorname(value);
							} else if("type".equals(name)) {
								bean.setTag(value);
							} else if("publishdate".equals(name)) {
								bean.setPublishdate(value);
							} else if("url".equals(name)) {
								bean.setUrl(value);
							} else if("id".equals(name)) {
								bean.setId(value);
							} else if("title".equals(name)) {
								bean.setTitle(value);
							} else if("currentdate".equals(name)) {
								bean.setCurrentdate(value);
							} else if("brief".equals(name)) {
								bean.setBrief(value);
							}
						}
						list.add(bean);
					}
				}
			}
			map.put("total", total);
			map.put("list", list);
			return map;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
}
