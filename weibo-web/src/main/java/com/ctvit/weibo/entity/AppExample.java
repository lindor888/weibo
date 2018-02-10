package com.ctvit.weibo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *@author Administrator
 *应用分页类 
 */
public class AppExample extends App{
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AppExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andAppIdIsNull() {
            addCriterion("app_id is null");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNotNull() {
            addCriterion("app_id is not null");
            return (Criteria) this;
        }

        public Criteria andAppIdEqualTo(String value) {
            addCriterion("app_id =", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotEqualTo(String value) {
            addCriterion("app_id <>", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThan(String value) {
            addCriterion("app_id >", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThanOrEqualTo(String value) {
            addCriterion("app_id >=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThan(String value) {
            addCriterion("app_id <", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThanOrEqualTo(String value) {
            addCriterion("app_id <=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLike(String value) {
            addCriterion("app_id like", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotLike(String value) {
            addCriterion("app_id not like", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdIn(List<String> values) {
            addCriterion("app_id in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotIn(List<String> values) {
            addCriterion("app_id not in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdBetween(String value1, String value2) {
            addCriterion("app_id between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotBetween(String value1, String value2) {
            addCriterion("app_id not between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andAppNameIsNull() {
            addCriterion("app_name is null");
            return (Criteria) this;
        }

        public Criteria andAppNameIsNotNull() {
            addCriterion("app_name is not null");
            return (Criteria) this;
        }

        public Criteria andAppNameEqualTo(String value) {
            addCriterion("app_name =", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotEqualTo(String value) {
            addCriterion("app_name <>", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameGreaterThan(String value) {
            addCriterion("app_name >", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameGreaterThanOrEqualTo(String value) {
            addCriterion("app_name >=", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLessThan(String value) {
            addCriterion("app_name <", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLessThanOrEqualTo(String value) {
            addCriterion("app_name <=", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLike(String value) {
            addCriterion("app_name like", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotLike(String value) {
            addCriterion("app_name not like", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameIn(List<String> values) {
            addCriterion("app_name in", values, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotIn(List<String> values) {
            addCriterion("app_name not in", values, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameBetween(String value1, String value2) {
            addCriterion("app_name between", value1, value2, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotBetween(String value1, String value2) {
            addCriterion("app_name not between", value1, value2, "appName");
            return (Criteria) this;
        }

        public Criteria andAppKeyIsNull() {
            addCriterion("app_key is null");
            return (Criteria) this;
        }

        public Criteria andAppKeyIsNotNull() {
            addCriterion("app_key is not null");
            return (Criteria) this;
        }

        public Criteria andAppKeyEqualTo(String value) {
            addCriterion("app_key =", value, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyNotEqualTo(String value) {
            addCriterion("app_key <>", value, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyGreaterThan(String value) {
            addCriterion("app_key >", value, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyGreaterThanOrEqualTo(String value) {
            addCriterion("app_key >=", value, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyLessThan(String value) {
            addCriterion("app_key <", value, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyLessThanOrEqualTo(String value) {
            addCriterion("app_key <=", value, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyLike(String value) {
            addCriterion("app_key like", value, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyNotLike(String value) {
            addCriterion("app_key not like", value, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyIn(List<String> values) {
            addCriterion("app_key in", values, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyNotIn(List<String> values) {
            addCriterion("app_key not in", values, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyBetween(String value1, String value2) {
            addCriterion("app_key between", value1, value2, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyNotBetween(String value1, String value2) {
            addCriterion("app_key not between", value1, value2, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppSecretIsNull() {
            addCriterion("app_secret is null");
            return (Criteria) this;
        }

        public Criteria andAppSecretIsNotNull() {
            addCriterion("app_secret is not null");
            return (Criteria) this;
        }

        public Criteria andAppSecretEqualTo(String value) {
            addCriterion("app_secret =", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretNotEqualTo(String value) {
            addCriterion("app_secret <>", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretGreaterThan(String value) {
            addCriterion("app_secret >", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretGreaterThanOrEqualTo(String value) {
            addCriterion("app_secret >=", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretLessThan(String value) {
            addCriterion("app_secret <", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretLessThanOrEqualTo(String value) {
            addCriterion("app_secret <=", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretLike(String value) {
            addCriterion("app_secret like", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretNotLike(String value) {
            addCriterion("app_secret not like", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretIn(List<String> values) {
            addCriterion("app_secret in", values, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretNotIn(List<String> values) {
            addCriterion("app_secret not in", values, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretBetween(String value1, String value2) {
            addCriterion("app_secret between", value1, value2, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretNotBetween(String value1, String value2) {
            addCriterion("app_secret not between", value1, value2, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppRedirectUriIsNull() {
            addCriterion("app_redirect_uri is null");
            return (Criteria) this;
        }

        public Criteria andAppRedirectUriIsNotNull() {
            addCriterion("app_redirect_uri is not null");
            return (Criteria) this;
        }

        public Criteria andAppRedirectUriEqualTo(String value) {
            addCriterion("app_redirect_uri =", value, "appRedirectUri");
            return (Criteria) this;
        }

        public Criteria andAppRedirectUriNotEqualTo(String value) {
            addCriterion("app_redirect_uri <>", value, "appRedirectUri");
            return (Criteria) this;
        }

        public Criteria andAppRedirectUriGreaterThan(String value) {
            addCriterion("app_redirect_uri >", value, "appRedirectUri");
            return (Criteria) this;
        }

        public Criteria andAppRedirectUriGreaterThanOrEqualTo(String value) {
            addCriterion("app_redirect_uri >=", value, "appRedirectUri");
            return (Criteria) this;
        }

        public Criteria andAppRedirectUriLessThan(String value) {
            addCriterion("app_redirect_uri <", value, "appRedirectUri");
            return (Criteria) this;
        }

        public Criteria andAppRedirectUriLessThanOrEqualTo(String value) {
            addCriterion("app_redirect_uri <=", value, "appRedirectUri");
            return (Criteria) this;
        }

        public Criteria andAppRedirectUriLike(String value) {
            addCriterion("app_redirect_uri like", value, "appRedirectUri");
            return (Criteria) this;
        }

        public Criteria andAppRedirectUriNotLike(String value) {
            addCriterion("app_redirect_uri not like", value, "appRedirectUri");
            return (Criteria) this;
        }

        public Criteria andAppRedirectUriIn(List<String> values) {
            addCriterion("app_redirect_uri in", values, "appRedirectUri");
            return (Criteria) this;
        }

        public Criteria andAppRedirectUriNotIn(List<String> values) {
            addCriterion("app_redirect_uri not in", values, "appRedirectUri");
            return (Criteria) this;
        }

        public Criteria andAppRedirectUriBetween(String value1, String value2) {
            addCriterion("app_redirect_uri between", value1, value2, "appRedirectUri");
            return (Criteria) this;
        }

        public Criteria andAppRedirectUriNotBetween(String value1, String value2) {
            addCriterion("app_redirect_uri not between", value1, value2, "appRedirectUri");
            return (Criteria) this;
        }

        public Criteria andAppLevelIsNull() {
            addCriterion("app_level is null");
            return (Criteria) this;
        }

        public Criteria andAppLevelIsNotNull() {
            addCriterion("app_level is not null");
            return (Criteria) this;
        }

        public Criteria andAppLevelEqualTo(Integer value) {
            addCriterion("app_level =", value, "appLevel");
            return (Criteria) this;
        }

        public Criteria andAppLevelNotEqualTo(Integer value) {
            addCriterion("app_level <>", value, "appLevel");
            return (Criteria) this;
        }

        public Criteria andAppLevelGreaterThan(Integer value) {
            addCriterion("app_level >", value, "appLevel");
            return (Criteria) this;
        }

        public Criteria andAppLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("app_level >=", value, "appLevel");
            return (Criteria) this;
        }

        public Criteria andAppLevelLessThan(Integer value) {
            addCriterion("app_level <", value, "appLevel");
            return (Criteria) this;
        }

        public Criteria andAppLevelLessThanOrEqualTo(Integer value) {
            addCriterion("app_level <=", value, "appLevel");
            return (Criteria) this;
        }

        public Criteria andAppLevelIn(List<Integer> values) {
            addCriterion("app_level in", values, "appLevel");
            return (Criteria) this;
        }

        public Criteria andAppLevelNotIn(List<Integer> values) {
            addCriterion("app_level not in", values, "appLevel");
            return (Criteria) this;
        }

        public Criteria andAppLevelBetween(Integer value1, Integer value2) {
            addCriterion("app_level between", value1, value2, "appLevel");
            return (Criteria) this;
        }

        public Criteria andAppLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("app_level not between", value1, value2, "appLevel");
            return (Criteria) this;
        }

        public Criteria andAppCreateTimeIsNull() {
            addCriterion("app_create_time is null");
            return (Criteria) this;
        }

        public Criteria andAppCreateTimeIsNotNull() {
            addCriterion("app_create_time is not null");
            return (Criteria) this;
        }

        public Criteria andAppCreateTimeEqualTo(Date value) {
            addCriterion("app_create_time =", value, "appCreateTime");
            return (Criteria) this;
        }

        public Criteria andAppCreateTimeNotEqualTo(Date value) {
            addCriterion("app_create_time <>", value, "appCreateTime");
            return (Criteria) this;
        }

        public Criteria andAppCreateTimeGreaterThan(Date value) {
            addCriterion("app_create_time >", value, "appCreateTime");
            return (Criteria) this;
        }

        public Criteria andAppCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("app_create_time >=", value, "appCreateTime");
            return (Criteria) this;
        }

        public Criteria andAppCreateTimeLessThan(Date value) {
            addCriterion("app_create_time <", value, "appCreateTime");
            return (Criteria) this;
        }

        public Criteria andAppCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("app_create_time <=", value, "appCreateTime");
            return (Criteria) this;
        }

        public Criteria andAppCreateTimeIn(List<Date> values) {
            addCriterion("app_create_time in", values, "appCreateTime");
            return (Criteria) this;
        }

        public Criteria andAppCreateTimeNotIn(List<Date> values) {
            addCriterion("app_create_time not in", values, "appCreateTime");
            return (Criteria) this;
        }

        public Criteria andAppCreateTimeBetween(Date value1, Date value2) {
            addCriterion("app_create_time between", value1, value2, "appCreateTime");
            return (Criteria) this;
        }

        public Criteria andAppCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("app_create_time not between", value1, value2, "appCreateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}