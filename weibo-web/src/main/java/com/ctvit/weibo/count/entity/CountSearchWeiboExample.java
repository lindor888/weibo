package com.ctvit.weibo.count.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CountSearchWeiboExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CountSearchWeiboExample() {
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

        public Criteria andCountIdIsNull() {
            addCriterion("count_id is null");
            return (Criteria) this;
        }

        public Criteria andCountIdIsNotNull() {
            addCriterion("count_id is not null");
            return (Criteria) this;
        }

        public Criteria andCountIdEqualTo(Integer value) {
            addCriterion("count_id =", value, "countId");
            return (Criteria) this;
        }

        public Criteria andCountIdNotEqualTo(Integer value) {
            addCriterion("count_id <>", value, "countId");
            return (Criteria) this;
        }

        public Criteria andCountIdGreaterThan(Integer value) {
            addCriterion("count_id >", value, "countId");
            return (Criteria) this;
        }

        public Criteria andCountIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("count_id >=", value, "countId");
            return (Criteria) this;
        }

        public Criteria andCountIdLessThan(Integer value) {
            addCriterion("count_id <", value, "countId");
            return (Criteria) this;
        }

        public Criteria andCountIdLessThanOrEqualTo(Integer value) {
            addCriterion("count_id <=", value, "countId");
            return (Criteria) this;
        }

        public Criteria andCountIdIn(List<Integer> values) {
            addCriterion("count_id in", values, "countId");
            return (Criteria) this;
        }

        public Criteria andCountIdNotIn(List<Integer> values) {
            addCriterion("count_id not in", values, "countId");
            return (Criteria) this;
        }

        public Criteria andCountIdBetween(Integer value1, Integer value2) {
            addCriterion("count_id between", value1, value2, "countId");
            return (Criteria) this;
        }

        public Criteria andCountIdNotBetween(Integer value1, Integer value2) {
            addCriterion("count_id not between", value1, value2, "countId");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNull() {
            addCriterion("task_id is null");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNotNull() {
            addCriterion("task_id is not null");
            return (Criteria) this;
        }

        public Criteria andTaskIdEqualTo(String value) {
            addCriterion("task_id =", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotEqualTo(String value) {
            addCriterion("task_id <>", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThan(String value) {
            addCriterion("task_id >", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThanOrEqualTo(String value) {
            addCriterion("task_id >=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThan(String value) {
            addCriterion("task_id <", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThanOrEqualTo(String value) {
            addCriterion("task_id <=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLike(String value) {
            addCriterion("task_id like", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotLike(String value) {
            addCriterion("task_id not like", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdIn(List<String> values) {
            addCriterion("task_id in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotIn(List<String> values) {
            addCriterion("task_id not in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdBetween(String value1, String value2) {
            addCriterion("task_id between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotBetween(String value1, String value2) {
            addCriterion("task_id not between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andQIsNull() {
            addCriterion("q is null");
            return (Criteria) this;
        }

        public Criteria andQIsNotNull() {
            addCriterion("q is not null");
            return (Criteria) this;
        }

        public Criteria andQEqualTo(String value) {
            addCriterion("q =", value, "q");
            return (Criteria) this;
        }

        public Criteria andQNotEqualTo(String value) {
            addCriterion("q <>", value, "q");
            return (Criteria) this;
        }

        public Criteria andQGreaterThan(String value) {
            addCriterion("q >", value, "q");
            return (Criteria) this;
        }

        public Criteria andQGreaterThanOrEqualTo(String value) {
            addCriterion("q >=", value, "q");
            return (Criteria) this;
        }

        public Criteria andQLessThan(String value) {
            addCriterion("q <", value, "q");
            return (Criteria) this;
        }

        public Criteria andQLessThanOrEqualTo(String value) {
            addCriterion("q <=", value, "q");
            return (Criteria) this;
        }

        public Criteria andQLike(String value) {
            addCriterion("q like", value, "q");
            return (Criteria) this;
        }

        public Criteria andQNotLike(String value) {
            addCriterion("q not like", value, "q");
            return (Criteria) this;
        }

        public Criteria andQIn(List<String> values) {
            addCriterion("q in", values, "q");
            return (Criteria) this;
        }

        public Criteria andQNotIn(List<String> values) {
            addCriterion("q not in", values, "q");
            return (Criteria) this;
        }

        public Criteria andQBetween(String value1, String value2) {
            addCriterion("q between", value1, value2, "q");
            return (Criteria) this;
        }

        public Criteria andQNotBetween(String value1, String value2) {
            addCriterion("q not between", value1, value2, "q");
            return (Criteria) this;
        }

        public Criteria andLanmuIsNull() {
            addCriterion("lanmu is null");
            return (Criteria) this;
        }

        public Criteria andLanmuIsNotNull() {
            addCriterion("lanmu is not null");
            return (Criteria) this;
        }

        public Criteria andLanmuEqualTo(String value) {
            addCriterion("lanmu =", value, "lanmu");
            return (Criteria) this;
        }

        public Criteria andLanmuNotEqualTo(String value) {
            addCriterion("lanmu <>", value, "lanmu");
            return (Criteria) this;
        }

        public Criteria andLanmuGreaterThan(String value) {
            addCriterion("lanmu >", value, "lanmu");
            return (Criteria) this;
        }

        public Criteria andLanmuGreaterThanOrEqualTo(String value) {
            addCriterion("lanmu >=", value, "lanmu");
            return (Criteria) this;
        }

        public Criteria andLanmuLessThan(String value) {
            addCriterion("lanmu <", value, "lanmu");
            return (Criteria) this;
        }

        public Criteria andLanmuLessThanOrEqualTo(String value) {
            addCriterion("lanmu <=", value, "lanmu");
            return (Criteria) this;
        }

        public Criteria andLanmuLike(String value) {
            addCriterion("lanmu like", value, "lanmu");
            return (Criteria) this;
        }

        public Criteria andLanmuNotLike(String value) {
            addCriterion("lanmu not like", value, "lanmu");
            return (Criteria) this;
        }

        public Criteria andLanmuIn(List<String> values) {
            addCriterion("lanmu in", values, "lanmu");
            return (Criteria) this;
        }

        public Criteria andLanmuNotIn(List<String> values) {
            addCriterion("lanmu not in", values, "lanmu");
            return (Criteria) this;
        }

        public Criteria andLanmuBetween(String value1, String value2) {
            addCriterion("lanmu between", value1, value2, "lanmu");
            return (Criteria) this;
        }

        public Criteria andLanmuNotBetween(String value1, String value2) {
            addCriterion("lanmu not between", value1, value2, "lanmu");
            return (Criteria) this;
        }

        public Criteria andCountTimeIsNull() {
            addCriterion("count_time is null");
            return (Criteria) this;
        }

        public Criteria andCountTimeIsNotNull() {
            addCriterion("count_time is not null");
            return (Criteria) this;
        }

        public Criteria andCountTimeEqualTo(Date value) {
            addCriterion("count_time =", value, "countTime");
            return (Criteria) this;
        }

        public Criteria andCountTimeNotEqualTo(Date value) {
            addCriterion("count_time <>", value, "countTime");
            return (Criteria) this;
        }

        public Criteria andCountTimeGreaterThan(Date value) {
            addCriterion("count_time >", value, "countTime");
            return (Criteria) this;
        }

        public Criteria andCountTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("count_time >=", value, "countTime");
            return (Criteria) this;
        }

        public Criteria andCountTimeLessThan(Date value) {
            addCriterion("count_time <", value, "countTime");
            return (Criteria) this;
        }

        public Criteria andCountTimeLessThanOrEqualTo(Date value) {
            addCriterion("count_time <=", value, "countTime");
            return (Criteria) this;
        }

        public Criteria andCountTimeIn(List<Date> values) {
            addCriterion("count_time in", values, "countTime");
            return (Criteria) this;
        }

        public Criteria andCountTimeNotIn(List<Date> values) {
            addCriterion("count_time not in", values, "countTime");
            return (Criteria) this;
        }

        public Criteria andCountTimeBetween(Date value1, Date value2) {
            addCriterion("count_time between", value1, value2, "countTime");
            return (Criteria) this;
        }

        public Criteria andCountTimeNotBetween(Date value1, Date value2) {
            addCriterion("count_time not between", value1, value2, "countTime");
            return (Criteria) this;
        }

        public Criteria andCountWeiboNumIsNull() {
            addCriterion("count_weibo_num is null");
            return (Criteria) this;
        }

        public Criteria andCountWeiboNumIsNotNull() {
            addCriterion("count_weibo_num is not null");
            return (Criteria) this;
        }

        public Criteria andCountWeiboNumEqualTo(Integer value) {
            addCriterion("count_weibo_num =", value, "countWeiboNum");
            return (Criteria) this;
        }

        public Criteria andCountWeiboNumNotEqualTo(Integer value) {
            addCriterion("count_weibo_num <>", value, "countWeiboNum");
            return (Criteria) this;
        }

        public Criteria andCountWeiboNumGreaterThan(Integer value) {
            addCriterion("count_weibo_num >", value, "countWeiboNum");
            return (Criteria) this;
        }

        public Criteria andCountWeiboNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("count_weibo_num >=", value, "countWeiboNum");
            return (Criteria) this;
        }

        public Criteria andCountWeiboNumLessThan(Integer value) {
            addCriterion("count_weibo_num <", value, "countWeiboNum");
            return (Criteria) this;
        }

        public Criteria andCountWeiboNumLessThanOrEqualTo(Integer value) {
            addCriterion("count_weibo_num <=", value, "countWeiboNum");
            return (Criteria) this;
        }

        public Criteria andCountWeiboNumIn(List<Integer> values) {
            addCriterion("count_weibo_num in", values, "countWeiboNum");
            return (Criteria) this;
        }

        public Criteria andCountWeiboNumNotIn(List<Integer> values) {
            addCriterion("count_weibo_num not in", values, "countWeiboNum");
            return (Criteria) this;
        }

        public Criteria andCountWeiboNumBetween(Integer value1, Integer value2) {
            addCriterion("count_weibo_num between", value1, value2, "countWeiboNum");
            return (Criteria) this;
        }

        public Criteria andCountWeiboNumNotBetween(Integer value1, Integer value2) {
            addCriterion("count_weibo_num not between", value1, value2, "countWeiboNum");
            return (Criteria) this;
        }

        public Criteria andCountUserNumIsNull() {
            addCriterion("count_user_num is null");
            return (Criteria) this;
        }

        public Criteria andCountUserNumIsNotNull() {
            addCriterion("count_user_num is not null");
            return (Criteria) this;
        }

        public Criteria andCountUserNumEqualTo(Integer value) {
            addCriterion("count_user_num =", value, "countUserNum");
            return (Criteria) this;
        }

        public Criteria andCountUserNumNotEqualTo(Integer value) {
            addCriterion("count_user_num <>", value, "countUserNum");
            return (Criteria) this;
        }

        public Criteria andCountUserNumGreaterThan(Integer value) {
            addCriterion("count_user_num >", value, "countUserNum");
            return (Criteria) this;
        }

        public Criteria andCountUserNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("count_user_num >=", value, "countUserNum");
            return (Criteria) this;
        }

        public Criteria andCountUserNumLessThan(Integer value) {
            addCriterion("count_user_num <", value, "countUserNum");
            return (Criteria) this;
        }

        public Criteria andCountUserNumLessThanOrEqualTo(Integer value) {
            addCriterion("count_user_num <=", value, "countUserNum");
            return (Criteria) this;
        }

        public Criteria andCountUserNumIn(List<Integer> values) {
            addCriterion("count_user_num in", values, "countUserNum");
            return (Criteria) this;
        }

        public Criteria andCountUserNumNotIn(List<Integer> values) {
            addCriterion("count_user_num not in", values, "countUserNum");
            return (Criteria) this;
        }

        public Criteria andCountUserNumBetween(Integer value1, Integer value2) {
            addCriterion("count_user_num between", value1, value2, "countUserNum");
            return (Criteria) this;
        }

        public Criteria andCountUserNumNotBetween(Integer value1, Integer value2) {
            addCriterion("count_user_num not between", value1, value2, "countUserNum");
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