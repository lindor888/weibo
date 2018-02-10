package com.ctvit.weibo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings({ "rawtypes", "unchecked" })
/**
 * 敏感词查找工具类
 * @author tqc
 *
 */
public class SensitiveWordFilter {
	private HashMap keysMap = new HashMap();
	private int matchType = 1; // 1:最小长度匹配 2：最大长度匹配
	
	/**
	 * 加入敏感词
	 * @param keywords
	 */
	public void addKeywords(List<String> keywords) {
		for (int i = 0; i < keywords.size(); i++) {
			String key = keywords.get(i).trim();
			HashMap nowhash = null;
			nowhash = keysMap;
			for (int j = 0; j < key.length(); j++) {
				char word = key.charAt(j);
				Object wordMap = nowhash.get(word);
				if (wordMap != null) {
					nowhash = (HashMap) wordMap;
				} else {
					HashMap<String, String> newWordHash = new HashMap<String, String>();
					newWordHash.put("isEnd", "0");
					nowhash.put(word, newWordHash);
					nowhash = newWordHash;
				}
				if (j == key.length() - 1) {
					nowhash.put("isEnd", "1");
				}
			}
		}
	}

	/**
	 * 清空敏感词
	 */
	public void clearKeywords() {
		keysMap.clear();
	}

	/**
	 * 检查一个字符串从begin位置起开始是否有keyword符合， 如果有符合的keyword值，返回值为匹配keyword的长度，否则返回零
	 * flag 1:最小长度匹配 2：最大长度匹配
	 * @param txt
	 * @param begin
	 * @param flag
	 * @return
	 */
	private int checkKeyWords(String txt, int begin, int flag) {
		HashMap nowhash = null;
		nowhash = keysMap;
		int maxMatchRes = 0;
		int res = 0;
		int l = txt.length();
		char word = 0;
		for (int i = begin; i < l; i++) {
			word = txt.charAt(i);
			Object wordMap = nowhash.get(word);
			if (wordMap != null) {
				res++;
				nowhash = (HashMap) wordMap;
				if (((String) nowhash.get("isEnd")).equals("1")) {
					if (flag == 1) {
						wordMap = null;
						nowhash = null;
						txt = null;
						return res;
					} else {
						maxMatchRes = res;
					}
				}
			} else {
				txt = null;
				nowhash = null;
				return maxMatchRes;
			}
		}
		txt = null;
		nowhash = null;
		return maxMatchRes;
	}

	/**
	 * 返回txt中敏感词的列表
	 * @param txt
	 * @return
	 */
	public Set<String> getTxtKeyWords(String txt) {
		Set set = new HashSet();
		int l = txt.length();
		for (int i = 0; i < l;) {
			int len = checkKeyWords(txt, i, matchType);
			if (len > 0) {
				set.add(txt.substring(i, i + len));
				i += len;
			} else {
				i++;
			}
		}
		txt = null;
		return set;
	}

	/**
	 * 仅判断txt中是否有敏感词
	 * @param txt
	 * @return
	 */
	public boolean isContentKeyWords(String txt) {
		for (int i = 0; i < txt.length(); i++) {
			int len = checkKeyWords(txt, i, 1);
			if (len > 0) {
				return true;
			}
		}
		txt = null;
		return false;
	}

	public int getMatchType() {
		return matchType;
	}

	public void setMatchType(int matchType) {
		this.matchType = matchType;
	}

	public static void main(String[] args) {
		long now = System.currentTimeMillis();
		SensitiveWordFilter filter = new SensitiveWordFilter();
		List<String> keywords = new ArrayList<String>();
		for(int i=0;i<1000;i++){
			keywords.add("fuck"+i);
			keywords.add("色情111"+i);
			keywords.add("faf"+i);
			keywords.add("yellow"+i);
			keywords.add("ff"+i);
			keywords.add("好"+i);
			keywords.add("ss"+i);
		}

		filter.addKeywords(keywords);
		String txt1 = "色情1110fuck沙拉维iPhone4范德萨发生发生的发生发生地方发色情1111生地方哈哈哈哈哈哈疯掉了双方乐山大佛拉斯维加斯色情1110fuck沙拉维iPhone4范德萨发生发生的发生发生地方发生地方哈哈哈哈哈哈疯掉了双方乐山大佛拉斯维加斯可放大沙发上的了飞机飞机了了拉了拉了了拉了拉了水水水水撒fuck反对方水电费";
		String txt2 = "fsdfsd发大水发水电费水电费水电费好1飞电风扇发发好2费大幅度发咳咳咳咳咳咳咳咳咳咳咳";
		Set<String> set1 = filter.getTxtKeyWords(txt1);
		Set<String> set2 = filter.getTxtKeyWords(txt2);
		for(String t:set1){
			txt1 = txt1.replaceAll(t, "<font color=yellow>"+t+"</font>");
		}
		System.out.println(set1);
		System.out.println(set2);
		System.out.println(txt1);
		System.out.println(System.currentTimeMillis() - now);
	}
}