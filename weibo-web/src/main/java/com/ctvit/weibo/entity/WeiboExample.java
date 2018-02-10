package com.ctvit.weibo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeiboExample extends Weibo{
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WeiboExample() {
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

        public Criteria andWeiboIdIsNull() {
            addCriterion("weibo_id is null");
            return (Criteria) this;
        }

        public Criteria andWeiboIdIsNotNull() {
            addCriterion("weibo_id is not null");
            return (Criteria) this;
        }

        public Criteria andWeiboIdEqualTo(String value) {
            addCriterion("weibo_id =", value, "weiboId");
            return (Criteria) this;
        }

        public Criteria andWeiboIdNotEqualTo(String value) {
            addCriterion("weibo_id <>", value, "weiboId");
            return (Criteria) this;
        }

        public Criteria andWeiboIdGreaterThan(String value) {
            addCriterion("weibo_id >", value, "weiboId");
            return (Criteria) this;
        }

        public Criteria andWeiboIdGreaterThanOrEqualTo(String value) {
            addCriterion("weibo_id >=", value, "weiboId");
            return (Criteria) this;
        }

        public Criteria andWeiboIdLessThan(String value) {
            addCriterion("weibo_id <", value, "weiboId");
            return (Criteria) this;
        }

        public Criteria andWeiboIdLessThanOrEqualTo(String value) {
            addCriterion("weibo_id <=", value, "weiboId");
            return (Criteria) this;
        }

        public Criteria andWeiboIdLike(String value) {
            addCriterion("weibo_id like", value, "weiboId");
            return (Criteria) this;
        }

        public Criteria andWeiboIdNotLike(String value) {
            addCriterion("weibo_id not like", value, "weiboId");
            return (Criteria) this;
        }

        public Criteria andWeiboIdIn(List<String> values) {
            addCriterion("weibo_id in", values, "weiboId");
            return (Criteria) this;
        }

        public Criteria andWeiboIdNotIn(List<String> values) {
            addCriterion("weibo_id not in", values, "weiboId");
            return (Criteria) this;
        }

        public Criteria andWeiboIdBetween(String value1, String value2) {
            addCriterion("weibo_id between", value1, value2, "weiboId");
            return (Criteria) this;
        }

        public Criteria andWeiboIdNotBetween(String value1, String value2) {
            addCriterion("weibo_id not between", value1, value2, "weiboId");
            return (Criteria) this;
        }

        public Criteria andWeiboUserNameIsNull() {
            addCriterion("weibo_user_name is null");
            return (Criteria) this;
        }

        public Criteria andWeiboUserNameIsNotNull() {
            addCriterion("weibo_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andWeiboUserNameEqualTo(String value) {
            addCriterion("weibo_user_name =", value, "weiboUserName");
            return (Criteria) this;
        }

        public Criteria andWeiboUserNameNotEqualTo(String value) {
            addCriterion("weibo_user_name <>", value, "weiboUserName");
            return (Criteria) this;
        }

        public Criteria andWeiboUserNameGreaterThan(String value) {
            addCriterion("weibo_user_name >", value, "weiboUserName");
            return (Criteria) this;
        }

        public Criteria andWeiboUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("weibo_user_name >=", value, "weiboUserName");
            return (Criteria) this;
        }

        public Criteria andWeiboUserNameLessThan(String value) {
            addCriterion("weibo_user_name <", value, "weiboUserName");
            return (Criteria) this;
        }

        public Criteria andWeiboUserNameLessThanOrEqualTo(String value) {
            addCriterion("weibo_user_name <=", value, "weiboUserName");
            return (Criteria) this;
        }

        public Criteria andWeiboUserNameLike(String value) {
            addCriterion("weibo_user_name like", value, "weiboUserName");
            return (Criteria) this;
        }

        public Criteria andWeiboUserNameNotLike(String value) {
            addCriterion("weibo_user_name not like", value, "weiboUserName");
            return (Criteria) this;
        }

        public Criteria andWeiboUserNameIn(List<String> values) {
            addCriterion("weibo_user_name in", values, "weiboUserName");
            return (Criteria) this;
        }

        public Criteria andWeiboUserNameNotIn(List<String> values) {
            addCriterion("weibo_user_name not in", values, "weiboUserName");
            return (Criteria) this;
        }

        public Criteria andWeiboUserNameBetween(String value1, String value2) {
            addCriterion("weibo_user_name between", value1, value2, "weiboUserName");
            return (Criteria) this;
        }

        public Criteria andWeiboUserNameNotBetween(String value1, String value2) {
            addCriterion("weibo_user_name not between", value1, value2, "weiboUserName");
            return (Criteria) this;
        }

        public Criteria andWeiboPasswordIsNull() {
            addCriterion("weibo_password is null");
            return (Criteria) this;
        }

        public Criteria andWeiboPasswordIsNotNull() {
            addCriterion("weibo_password is not null");
            return (Criteria) this;
        }

        public Criteria andWeiboPasswordEqualTo(String value) {
            addCriterion("weibo_password =", value, "weiboPassword");
            return (Criteria) this;
        }

        public Criteria andWeiboPasswordNotEqualTo(String value) {
            addCriterion("weibo_password <>", value, "weiboPassword");
            return (Criteria) this;
        }

        public Criteria andWeiboPasswordGreaterThan(String value) {
            addCriterion("weibo_password >", value, "weiboPassword");
            return (Criteria) this;
        }

        public Criteria andWeiboPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("weibo_password >=", value, "weiboPassword");
            return (Criteria) this;
        }

        public Criteria andWeiboPasswordLessThan(String value) {
            addCriterion("weibo_password <", value, "weiboPassword");
            return (Criteria) this;
        }

        public Criteria andWeiboPasswordLessThanOrEqualTo(String value) {
            addCriterion("weibo_password <=", value, "weiboPassword");
            return (Criteria) this;
        }

        public Criteria andWeiboPasswordLike(String value) {
            addCriterion("weibo_password like", value, "weiboPassword");
            return (Criteria) this;
        }

        public Criteria andWeiboPasswordNotLike(String value) {
            addCriterion("weibo_password not like", value, "weiboPassword");
            return (Criteria) this;
        }

        public Criteria andWeiboPasswordIn(List<String> values) {
            addCriterion("weibo_password in", values, "weiboPassword");
            return (Criteria) this;
        }

        public Criteria andWeiboPasswordNotIn(List<String> values) {
            addCriterion("weibo_password not in", values, "weiboPassword");
            return (Criteria) this;
        }

        public Criteria andWeiboPasswordBetween(String value1, String value2) {
            addCriterion("weibo_password between", value1, value2, "weiboPassword");
            return (Criteria) this;
        }

        public Criteria andWeiboPasswordNotBetween(String value1, String value2) {
            addCriterion("weibo_password not between", value1, value2, "weiboPassword");
            return (Criteria) this;
        }

        public Criteria andWeiboUidIsNull() {
            addCriterion("weibo_uid is null");
            return (Criteria) this;
        }

        public Criteria andWeiboUidIsNotNull() {
            addCriterion("weibo_uid is not null");
            return (Criteria) this;
        }

        public Criteria andWeiboUidEqualTo(String value) {
            addCriterion("weibo_uid =", value, "weiboUid");
            return (Criteria) this;
        }

        public Criteria andWeiboUidNotEqualTo(String value) {
            addCriterion("weibo_uid <>", value, "weiboUid");
            return (Criteria) this;
        }

        public Criteria andWeiboUidGreaterThan(String value) {
            addCriterion("weibo_uid >", value, "weiboUid");
            return (Criteria) this;
        }

        public Criteria andWeiboUidGreaterThanOrEqualTo(String value) {
            addCriterion("weibo_uid >=", value, "weiboUid");
            return (Criteria) this;
        }

        public Criteria andWeiboUidLessThan(String value) {
            addCriterion("weibo_uid <", value, "weiboUid");
            return (Criteria) this;
        }

        public Criteria andWeiboUidLessThanOrEqualTo(String value) {
            addCriterion("weibo_uid <=", value, "weiboUid");
            return (Criteria) this;
        }

        public Criteria andWeiboUidLike(String value) {
            addCriterion("weibo_uid like", value, "weiboUid");
            return (Criteria) this;
        }

        public Criteria andWeiboUidNotLike(String value) {
            addCriterion("weibo_uid not like", value, "weiboUid");
            return (Criteria) this;
        }

        public Criteria andWeiboUidIn(List<String> values) {
            addCriterion("weibo_uid in", values, "weiboUid");
            return (Criteria) this;
        }

        public Criteria andWeiboUidNotIn(List<String> values) {
            addCriterion("weibo_uid not in", values, "weiboUid");
            return (Criteria) this;
        }

        public Criteria andWeiboUidBetween(String value1, String value2) {
            addCriterion("weibo_uid between", value1, value2, "weiboUid");
            return (Criteria) this;
        }

        public Criteria andWeiboUidNotBetween(String value1, String value2) {
            addCriterion("weibo_uid not between", value1, value2, "weiboUid");
            return (Criteria) this;
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

        public Criteria andWeiboCreateTimeIsNull() {
            addCriterion("weibo_create_time is null");
            return (Criteria) this;
        }

        public Criteria andWeiboCreateTimeIsNotNull() {
            addCriterion("weibo_create_time is not null");
            return (Criteria) this;
        }

        public Criteria andWeiboCreateTimeEqualTo(Date value) {
            addCriterion("weibo_create_time =", value, "weiboCreateTime");
            return (Criteria) this;
        }

        public Criteria andWeiboCreateTimeNotEqualTo(Date value) {
            addCriterion("weibo_create_time <>", value, "weiboCreateTime");
            return (Criteria) this;
        }

        public Criteria andWeiboCreateTimeGreaterThan(Date value) {
            addCriterion("weibo_create_time >", value, "weiboCreateTime");
            return (Criteria) this;
        }

        public Criteria andWeiboCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("weibo_create_time >=", value, "weiboCreateTime");
            return (Criteria) this;
        }

        public Criteria andWeiboCreateTimeLessThan(Date value) {
            addCriterion("weibo_create_time <", value, "weiboCreateTime");
            return (Criteria) this;
        }

        public Criteria andWeiboCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("weibo_create_time <=", value, "weiboCreateTime");
            return (Criteria) this;
        }

        public Criteria andWeiboCreateTimeIn(List<Date> values) {
            addCriterion("weibo_create_time in", values, "weiboCreateTime");
            return (Criteria) this;
        }

        public Criteria andWeiboCreateTimeNotIn(List<Date> values) {
            addCriterion("weibo_create_time not in", values, "weiboCreateTime");
            return (Criteria) this;
        }

        public Criteria andWeiboCreateTimeBetween(Date value1, Date value2) {
            addCriterion("weibo_create_time between", value1, value2, "weiboCreateTime");
            return (Criteria) this;
        }

        public Criteria andWeiboCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("weibo_create_time not between", value1, value2, "weiboCreateTime");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIsNull() {
            addCriterion("delete_flag is null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIsNotNull() {
            addCriterion("delete_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagEqualTo(Integer value) {
            addCriterion("delete_flag =", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotEqualTo(Integer value) {
            addCriterion("delete_flag <>", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagGreaterThan(Integer value) {
            addCriterion("delete_flag >", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("delete_flag >=", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLessThan(Integer value) {
            addCriterion("delete_flag <", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLessThanOrEqualTo(Integer value) {
            addCriterion("delete_flag <=", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIn(List<Integer> values) {
            addCriterion("delete_flag in", values, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotIn(List<Integer> values) {
            addCriterion("delete_flag not in", values, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagBetween(Integer value1, Integer value2) {
            addCriterion("delete_flag between", value1, value2, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("delete_flag not between", value1, value2, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andWeiboTokenIsNull() {
            addCriterion("weibo_token is null");
            return (Criteria) this;
        }

        public Criteria andWeiboTokenIsNotNull() {
            addCriterion("weibo_token is not null");
            return (Criteria) this;
        }

        public Criteria andWeiboTokenEqualTo(String value) {
            addCriterion("weibo_token =", value, "weiboToken");
            return (Criteria) this;
        }

        public Criteria andWeiboTokenNotEqualTo(String value) {
            addCriterion("weibo_token <>", value, "weiboToken");
            return (Criteria) this;
        }

        public Criteria andWeiboTokenGreaterThan(String value) {
            addCriterion("weibo_token >", value, "weiboToken");
            return (Criteria) this;
        }

        public Criteria andWeiboTokenGreaterThanOrEqualTo(String value) {
            addCriterion("weibo_token >=", value, "weiboToken");
            return (Criteria) this;
        }

        public Criteria andWeiboTokenLessThan(String value) {
            addCriterion("weibo_token <", value, "weiboToken");
            return (Criteria) this;
        }

        public Criteria andWeiboTokenLessThanOrEqualTo(String value) {
            addCriterion("weibo_token <=", value, "weiboToken");
            return (Criteria) this;
        }

        public Criteria andWeiboTokenLike(String value) {
            addCriterion("weibo_token like", value, "weiboToken");
            return (Criteria) this;
        }

        public Criteria andWeiboTokenNotLike(String value) {
            addCriterion("weibo_token not like", value, "weiboToken");
            return (Criteria) this;
        }

        public Criteria andWeiboTokenIn(List<String> values) {
            addCriterion("weibo_token in", values, "weiboToken");
            return (Criteria) this;
        }

        public Criteria andWeiboTokenNotIn(List<String> values) {
            addCriterion("weibo_token not in", values, "weiboToken");
            return (Criteria) this;
        }

        public Criteria andWeiboTokenBetween(String value1, String value2) {
            addCriterion("weibo_token between", value1, value2, "weiboToken");
            return (Criteria) this;
        }

        public Criteria andWeiboTokenNotBetween(String value1, String value2) {
            addCriterion("weibo_token not between", value1, value2, "weiboToken");
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