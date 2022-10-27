package cn.xgl.mbg.model;

import java.util.ArrayList;
import java.util.List;

public class TumsCnameExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TumsCnameExample() {
        oredCriteria = new ArrayList<>();
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
            criteria = new ArrayList<>();
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

        public Criteria andCnameIsNull() {
            addCriterion("CNAME is null");
            return (Criteria) this;
        }

        public Criteria andCnameIsNotNull() {
            addCriterion("CNAME is not null");
            return (Criteria) this;
        }

        public Criteria andCnameEqualTo(String value) {
            addCriterion("CNAME =", value, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameNotEqualTo(String value) {
            addCriterion("CNAME <>", value, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameGreaterThan(String value) {
            addCriterion("CNAME >", value, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameGreaterThanOrEqualTo(String value) {
            addCriterion("CNAME >=", value, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameLessThan(String value) {
            addCriterion("CNAME <", value, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameLessThanOrEqualTo(String value) {
            addCriterion("CNAME <=", value, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameLike(String value) {
            addCriterion("CNAME like", value, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameNotLike(String value) {
            addCriterion("CNAME not like", value, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameIn(List<String> values) {
            addCriterion("CNAME in", values, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameNotIn(List<String> values) {
            addCriterion("CNAME not in", values, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameBetween(String value1, String value2) {
            addCriterion("CNAME between", value1, value2, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameNotBetween(String value1, String value2) {
            addCriterion("CNAME not between", value1, value2, "cname");
            return (Criteria) this;
        }

        public Criteria andDnameIsNull() {
            addCriterion("DNAME is null");
            return (Criteria) this;
        }

        public Criteria andDnameIsNotNull() {
            addCriterion("DNAME is not null");
            return (Criteria) this;
        }

        public Criteria andDnameEqualTo(String value) {
            addCriterion("DNAME =", value, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameNotEqualTo(String value) {
            addCriterion("DNAME <>", value, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameGreaterThan(String value) {
            addCriterion("DNAME >", value, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameGreaterThanOrEqualTo(String value) {
            addCriterion("DNAME >=", value, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameLessThan(String value) {
            addCriterion("DNAME <", value, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameLessThanOrEqualTo(String value) {
            addCriterion("DNAME <=", value, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameLike(String value) {
            addCriterion("DNAME like", value, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameNotLike(String value) {
            addCriterion("DNAME not like", value, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameIn(List<String> values) {
            addCriterion("DNAME in", values, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameNotIn(List<String> values) {
            addCriterion("DNAME not in", values, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameBetween(String value1, String value2) {
            addCriterion("DNAME between", value1, value2, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameNotBetween(String value1, String value2) {
            addCriterion("DNAME not between", value1, value2, "dname");
            return (Criteria) this;
        }

        public Criteria andCnamecpIsNull() {
            addCriterion("CNAMECP is null");
            return (Criteria) this;
        }

        public Criteria andCnamecpIsNotNull() {
            addCriterion("CNAMECP is not null");
            return (Criteria) this;
        }

        public Criteria andCnamecpEqualTo(String value) {
            addCriterion("CNAMECP =", value, "cnamecp");
            return (Criteria) this;
        }

        public Criteria andCnamecpNotEqualTo(String value) {
            addCriterion("CNAMECP <>", value, "cnamecp");
            return (Criteria) this;
        }

        public Criteria andCnamecpGreaterThan(String value) {
            addCriterion("CNAMECP >", value, "cnamecp");
            return (Criteria) this;
        }

        public Criteria andCnamecpGreaterThanOrEqualTo(String value) {
            addCriterion("CNAMECP >=", value, "cnamecp");
            return (Criteria) this;
        }

        public Criteria andCnamecpLessThan(String value) {
            addCriterion("CNAMECP <", value, "cnamecp");
            return (Criteria) this;
        }

        public Criteria andCnamecpLessThanOrEqualTo(String value) {
            addCriterion("CNAMECP <=", value, "cnamecp");
            return (Criteria) this;
        }

        public Criteria andCnamecpLike(String value) {
            addCriterion("CNAMECP like", value, "cnamecp");
            return (Criteria) this;
        }

        public Criteria andCnamecpNotLike(String value) {
            addCriterion("CNAMECP not like", value, "cnamecp");
            return (Criteria) this;
        }

        public Criteria andCnamecpIn(List<String> values) {
            addCriterion("CNAMECP in", values, "cnamecp");
            return (Criteria) this;
        }

        public Criteria andCnamecpNotIn(List<String> values) {
            addCriterion("CNAMECP not in", values, "cnamecp");
            return (Criteria) this;
        }

        public Criteria andCnamecpBetween(String value1, String value2) {
            addCriterion("CNAMECP between", value1, value2, "cnamecp");
            return (Criteria) this;
        }

        public Criteria andCnamecpNotBetween(String value1, String value2) {
            addCriterion("CNAMECP not between", value1, value2, "cnamecp");
            return (Criteria) this;
        }

        public Criteria andDnamecpIsNull() {
            addCriterion("DNAMECP is null");
            return (Criteria) this;
        }

        public Criteria andDnamecpIsNotNull() {
            addCriterion("DNAMECP is not null");
            return (Criteria) this;
        }

        public Criteria andDnamecpEqualTo(String value) {
            addCriterion("DNAMECP =", value, "dnamecp");
            return (Criteria) this;
        }

        public Criteria andDnamecpNotEqualTo(String value) {
            addCriterion("DNAMECP <>", value, "dnamecp");
            return (Criteria) this;
        }

        public Criteria andDnamecpGreaterThan(String value) {
            addCriterion("DNAMECP >", value, "dnamecp");
            return (Criteria) this;
        }

        public Criteria andDnamecpGreaterThanOrEqualTo(String value) {
            addCriterion("DNAMECP >=", value, "dnamecp");
            return (Criteria) this;
        }

        public Criteria andDnamecpLessThan(String value) {
            addCriterion("DNAMECP <", value, "dnamecp");
            return (Criteria) this;
        }

        public Criteria andDnamecpLessThanOrEqualTo(String value) {
            addCriterion("DNAMECP <=", value, "dnamecp");
            return (Criteria) this;
        }

        public Criteria andDnamecpLike(String value) {
            addCriterion("DNAMECP like", value, "dnamecp");
            return (Criteria) this;
        }

        public Criteria andDnamecpNotLike(String value) {
            addCriterion("DNAMECP not like", value, "dnamecp");
            return (Criteria) this;
        }

        public Criteria andDnamecpIn(List<String> values) {
            addCriterion("DNAMECP in", values, "dnamecp");
            return (Criteria) this;
        }

        public Criteria andDnamecpNotIn(List<String> values) {
            addCriterion("DNAMECP not in", values, "dnamecp");
            return (Criteria) this;
        }

        public Criteria andDnamecpBetween(String value1, String value2) {
            addCriterion("DNAMECP between", value1, value2, "dnamecp");
            return (Criteria) this;
        }

        public Criteria andDnamecpNotBetween(String value1, String value2) {
            addCriterion("DNAMECP not between", value1, value2, "dnamecp");
            return (Criteria) this;
        }

        public Criteria andRegionIsNull() {
            addCriterion("REGION is null");
            return (Criteria) this;
        }

        public Criteria andRegionIsNotNull() {
            addCriterion("REGION is not null");
            return (Criteria) this;
        }

        public Criteria andRegionEqualTo(String value) {
            addCriterion("REGION =", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotEqualTo(String value) {
            addCriterion("REGION <>", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionGreaterThan(String value) {
            addCriterion("REGION >", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionGreaterThanOrEqualTo(String value) {
            addCriterion("REGION >=", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionLessThan(String value) {
            addCriterion("REGION <", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionLessThanOrEqualTo(String value) {
            addCriterion("REGION <=", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionLike(String value) {
            addCriterion("REGION like", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotLike(String value) {
            addCriterion("REGION not like", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionIn(List<String> values) {
            addCriterion("REGION in", values, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotIn(List<String> values) {
            addCriterion("REGION not in", values, "region");
            return (Criteria) this;
        }

        public Criteria andRegionBetween(String value1, String value2) {
            addCriterion("REGION between", value1, value2, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotBetween(String value1, String value2) {
            addCriterion("REGION not between", value1, value2, "region");
            return (Criteria) this;
        }

        public Criteria andReqsIsNull() {
            addCriterion("REQS is null");
            return (Criteria) this;
        }

        public Criteria andReqsIsNotNull() {
            addCriterion("REQS is not null");
            return (Criteria) this;
        }

        public Criteria andReqsEqualTo(Integer value) {
            addCriterion("REQS =", value, "reqs");
            return (Criteria) this;
        }

        public Criteria andReqsNotEqualTo(Integer value) {
            addCriterion("REQS <>", value, "reqs");
            return (Criteria) this;
        }

        public Criteria andReqsGreaterThan(Integer value) {
            addCriterion("REQS >", value, "reqs");
            return (Criteria) this;
        }

        public Criteria andReqsGreaterThanOrEqualTo(Integer value) {
            addCriterion("REQS >=", value, "reqs");
            return (Criteria) this;
        }

        public Criteria andReqsLessThan(Integer value) {
            addCriterion("REQS <", value, "reqs");
            return (Criteria) this;
        }

        public Criteria andReqsLessThanOrEqualTo(Integer value) {
            addCriterion("REQS <=", value, "reqs");
            return (Criteria) this;
        }

        public Criteria andReqsIn(List<Integer> values) {
            addCriterion("REQS in", values, "reqs");
            return (Criteria) this;
        }

        public Criteria andReqsNotIn(List<Integer> values) {
            addCriterion("REQS not in", values, "reqs");
            return (Criteria) this;
        }

        public Criteria andReqsBetween(Integer value1, Integer value2) {
            addCriterion("REQS between", value1, value2, "reqs");
            return (Criteria) this;
        }

        public Criteria andReqsNotBetween(Integer value1, Integer value2) {
            addCriterion("REQS not between", value1, value2, "reqs");
            return (Criteria) this;
        }

        public Criteria andImportrateIsNull() {
            addCriterion("IMPORTRATE is null");
            return (Criteria) this;
        }

        public Criteria andImportrateIsNotNull() {
            addCriterion("IMPORTRATE is not null");
            return (Criteria) this;
        }

        public Criteria andImportrateEqualTo(Double value) {
            addCriterion("IMPORTRATE =", value, "importrate");
            return (Criteria) this;
        }

        public Criteria andImportrateNotEqualTo(Double value) {
            addCriterion("IMPORTRATE <>", value, "importrate");
            return (Criteria) this;
        }

        public Criteria andImportrateGreaterThan(Double value) {
            addCriterion("IMPORTRATE >", value, "importrate");
            return (Criteria) this;
        }

        public Criteria andImportrateGreaterThanOrEqualTo(Double value) {
            addCriterion("IMPORTRATE >=", value, "importrate");
            return (Criteria) this;
        }

        public Criteria andImportrateLessThan(Double value) {
            addCriterion("IMPORTRATE <", value, "importrate");
            return (Criteria) this;
        }

        public Criteria andImportrateLessThanOrEqualTo(Double value) {
            addCriterion("IMPORTRATE <=", value, "importrate");
            return (Criteria) this;
        }

        public Criteria andImportrateIn(List<Double> values) {
            addCriterion("IMPORTRATE in", values, "importrate");
            return (Criteria) this;
        }

        public Criteria andImportrateNotIn(List<Double> values) {
            addCriterion("IMPORTRATE not in", values, "importrate");
            return (Criteria) this;
        }

        public Criteria andImportrateBetween(Double value1, Double value2) {
            addCriterion("IMPORTRATE between", value1, value2, "importrate");
            return (Criteria) this;
        }

        public Criteria andImportrateNotBetween(Double value1, Double value2) {
            addCriterion("IMPORTRATE not between", value1, value2, "importrate");
            return (Criteria) this;
        }

        public Criteria andSelfrateIsNull() {
            addCriterion("SELFRATE is null");
            return (Criteria) this;
        }

        public Criteria andSelfrateIsNotNull() {
            addCriterion("SELFRATE is not null");
            return (Criteria) this;
        }

        public Criteria andSelfrateEqualTo(Double value) {
            addCriterion("SELFRATE =", value, "selfrate");
            return (Criteria) this;
        }

        public Criteria andSelfrateNotEqualTo(Double value) {
            addCriterion("SELFRATE <>", value, "selfrate");
            return (Criteria) this;
        }

        public Criteria andSelfrateGreaterThan(Double value) {
            addCriterion("SELFRATE >", value, "selfrate");
            return (Criteria) this;
        }

        public Criteria andSelfrateGreaterThanOrEqualTo(Double value) {
            addCriterion("SELFRATE >=", value, "selfrate");
            return (Criteria) this;
        }

        public Criteria andSelfrateLessThan(Double value) {
            addCriterion("SELFRATE <", value, "selfrate");
            return (Criteria) this;
        }

        public Criteria andSelfrateLessThanOrEqualTo(Double value) {
            addCriterion("SELFRATE <=", value, "selfrate");
            return (Criteria) this;
        }

        public Criteria andSelfrateIn(List<Double> values) {
            addCriterion("SELFRATE in", values, "selfrate");
            return (Criteria) this;
        }

        public Criteria andSelfrateNotIn(List<Double> values) {
            addCriterion("SELFRATE not in", values, "selfrate");
            return (Criteria) this;
        }

        public Criteria andSelfrateBetween(Double value1, Double value2) {
            addCriterion("SELFRATE between", value1, value2, "selfrate");
            return (Criteria) this;
        }

        public Criteria andSelfrateNotBetween(Double value1, Double value2) {
            addCriterion("SELFRATE not between", value1, value2, "selfrate");
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