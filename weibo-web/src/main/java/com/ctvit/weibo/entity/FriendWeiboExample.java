package com.ctvit.weibo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FriendWeiboExample extends FriendWeibo{
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FriendWeiboExample() {
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

        public Criteria andFriendWeiboIdIsNull() {
            addCriterion("friend_weibo_id is null");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboIdIsNotNull() {
            addCriterion("friend_weibo_id is not null");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboIdEqualTo(String value) {
            addCriterion("friend_weibo_id =", value, "friendWeiboId");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboIdNotEqualTo(String value) {
            addCriterion("friend_weibo_id <>", value, "friendWeiboId");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboIdGreaterThan(String value) {
            addCriterion("friend_weibo_id >", value, "friendWeiboId");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboIdGreaterThanOrEqualTo(String value) {
            addCriterion("friend_weibo_id >=", value, "friendWeiboId");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboIdLessThan(String value) {
            addCriterion("friend_weibo_id <", value, "friendWeiboId");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboIdLessThanOrEqualTo(String value) {
            addCriterion("friend_weibo_id <=", value, "friendWeiboId");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboIdLike(String value) {
            addCriterion("friend_weibo_id like", value, "friendWeiboId");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboIdNotLike(String value) {
            addCriterion("friend_weibo_id not like", value, "friendWeiboId");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboIdIn(List<String> values) {
            addCriterion("friend_weibo_id in", values, "friendWeiboId");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboIdNotIn(List<String> values) {
            addCriterion("friend_weibo_id not in", values, "friendWeiboId");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboIdBetween(String value1, String value2) {
            addCriterion("friend_weibo_id between", value1, value2, "friendWeiboId");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboIdNotBetween(String value1, String value2) {
            addCriterion("friend_weibo_id not between", value1, value2, "friendWeiboId");
            return (Criteria) this;
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

        public Criteria andFriendWeiboUidIsNull() {
            addCriterion("friend_weibo_uid is null");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboUidIsNotNull() {
            addCriterion("friend_weibo_uid is not null");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboUidEqualTo(String value) {
            addCriterion("friend_weibo_uid =", value, "friendWeiboUid");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboUidNotEqualTo(String value) {
            addCriterion("friend_weibo_uid <>", value, "friendWeiboUid");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboUidGreaterThan(String value) {
            addCriterion("friend_weibo_uid >", value, "friendWeiboUid");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboUidGreaterThanOrEqualTo(String value) {
            addCriterion("friend_weibo_uid >=", value, "friendWeiboUid");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboUidLessThan(String value) {
            addCriterion("friend_weibo_uid <", value, "friendWeiboUid");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboUidLessThanOrEqualTo(String value) {
            addCriterion("friend_weibo_uid <=", value, "friendWeiboUid");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboUidLike(String value) {
            addCriterion("friend_weibo_uid like", value, "friendWeiboUid");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboUidNotLike(String value) {
            addCriterion("friend_weibo_uid not like", value, "friendWeiboUid");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboUidIn(List<String> values) {
            addCriterion("friend_weibo_uid in", values, "friendWeiboUid");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboUidNotIn(List<String> values) {
            addCriterion("friend_weibo_uid not in", values, "friendWeiboUid");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboUidBetween(String value1, String value2) {
            addCriterion("friend_weibo_uid between", value1, value2, "friendWeiboUid");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboUidNotBetween(String value1, String value2) {
            addCriterion("friend_weibo_uid not between", value1, value2, "friendWeiboUid");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboNameIsNull() {
            addCriterion("friend_weibo_name is null");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboNameIsNotNull() {
            addCriterion("friend_weibo_name is not null");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboNameEqualTo(String value) {
            addCriterion("friend_weibo_name =", value, "friendWeiboName");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboNameNotEqualTo(String value) {
            addCriterion("friend_weibo_name <>", value, "friendWeiboName");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboNameGreaterThan(String value) {
            addCriterion("friend_weibo_name >", value, "friendWeiboName");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboNameGreaterThanOrEqualTo(String value) {
            addCriterion("friend_weibo_name >=", value, "friendWeiboName");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboNameLessThan(String value) {
            addCriterion("friend_weibo_name <", value, "friendWeiboName");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboNameLessThanOrEqualTo(String value) {
            addCriterion("friend_weibo_name <=", value, "friendWeiboName");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboNameLike(String value) {
            addCriterion("friend_weibo_name like", value, "friendWeiboName");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboNameNotLike(String value) {
            addCriterion("friend_weibo_name not like", value, "friendWeiboName");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboNameIn(List<String> values) {
            addCriterion("friend_weibo_name in", values, "friendWeiboName");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboNameNotIn(List<String> values) {
            addCriterion("friend_weibo_name not in", values, "friendWeiboName");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboNameBetween(String value1, String value2) {
            addCriterion("friend_weibo_name between", value1, value2, "friendWeiboName");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboNameNotBetween(String value1, String value2) {
            addCriterion("friend_weibo_name not between", value1, value2, "friendWeiboName");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboCreateTimeIsNull() {
            addCriterion("friend_weibo_create_time is null");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboCreateTimeIsNotNull() {
            addCriterion("friend_weibo_create_time is not null");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboCreateTimeEqualTo(Date value) {
            addCriterion("friend_weibo_create_time =", value, "friendWeiboCreateTime");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboCreateTimeNotEqualTo(Date value) {
            addCriterion("friend_weibo_create_time <>", value, "friendWeiboCreateTime");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboCreateTimeGreaterThan(Date value) {
            addCriterion("friend_weibo_create_time >", value, "friendWeiboCreateTime");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("friend_weibo_create_time >=", value, "friendWeiboCreateTime");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboCreateTimeLessThan(Date value) {
            addCriterion("friend_weibo_create_time <", value, "friendWeiboCreateTime");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("friend_weibo_create_time <=", value, "friendWeiboCreateTime");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboCreateTimeIn(List<Date> values) {
            addCriterion("friend_weibo_create_time in", values, "friendWeiboCreateTime");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboCreateTimeNotIn(List<Date> values) {
            addCriterion("friend_weibo_create_time not in", values, "friendWeiboCreateTime");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboCreateTimeBetween(Date value1, Date value2) {
            addCriterion("friend_weibo_create_time between", value1, value2, "friendWeiboCreateTime");
            return (Criteria) this;
        }

        public Criteria andFriendWeiboCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("friend_weibo_create_time not between", value1, value2, "friendWeiboCreateTime");
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