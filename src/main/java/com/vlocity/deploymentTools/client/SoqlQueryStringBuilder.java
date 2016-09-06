package client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Derek on 25/08/2016.
 */
public class SoqlQueryStringBuilder {

    private HashSet<String> Fields = new HashSet<String>() {{add("id");}};
    private WhereClauseGroup WhereClause = new WhereClauseGroup();
    private HashMap<String, SoqlQueryStringBuilder> SubQueries = new HashMap<>();
    private String ObjectName = null;

    public SoqlQueryStringBuilder(String objectName) {
        this.ObjectName = objectName;
    }

    public SoqlQueryStringBuilder(ArrayList<String> fields, String objectName) {
        this.Fields.addAll(fields);
        this.ObjectName = objectName;
    }

    public void AddField(String field) {
        if (field == null || field.isEmpty()) return;

        this.Fields.add(field.toLowerCase());
    }

    public Boolean RemoveField(String field) {
        if (field == null || field.isEmpty() || "id".equals(field.toLowerCase())) return false;

        return this.Fields.remove(field.toLowerCase());
    }

    public void AddSubQuery(String relationshipName, SoqlQueryStringBuilder query) {
        if (query == null || relationshipName == null || relationshipName.isEmpty()) {
            return;
        }

        query.ObjectName = relationshipName;

        this.SubQueries.put(relationshipName.toLowerCase(), query);
    }

    public void RemoveSubQuery(SoqlQueryStringBuilder query) {
        if (query == null || query.ObjectName == null || query.ObjectName.isEmpty()) {
            return;
        }

        this.SubQueries.remove(query.ObjectName.toLowerCase());
    }

    public Boolean getAtleastOneMatchForWhereClause() {
        return this.WhereClause.AtleastOneMatch;
    }

    public void setAtleastOneMatchForWhereClause(Boolean value) {
        this.WhereClause.AtleastOneMatch = value;
    }

    public void AddCondition(Condition condition) {
        this.WhereClause.Conditions.add(condition);
    }

    public void AddCondition(String fieldName, ComparisonOperatorEnum comparisonOpertor, Object comparisonValue) {
        Condition condition = new Condition();
        condition.Field = fieldName;
        condition.ComparisonOperator = comparisonOpertor;
        condition.ComparisonValue = comparisonValue;
        this.AddCondition(condition);
    }

    public String toString() {
        String template = "SELECT %1s FROM %2s";
        String whereTemplate = " WHERE %1s";

        String fieldsAndSubQueries = String.join(", ", this.Fields);

        for (SoqlQueryStringBuilder subQuery : this.SubQueries.values()) {
            fieldsAndSubQueries += ", (" + subQuery.toString() + ")";
        }

        String result = String.format(template, fieldsAndSubQueries, this.ObjectName);

        if (this.WhereClause.Conditions.size() > 0) {
            result += String.format(whereTemplate, this.WhereClause.toString());
        }

        return result;
    }


    public class WhereClauseGroup extends Condition {
        public Boolean AtleastOneMatch = false;
        public ArrayList<Condition> Conditions = new ArrayList<>();

        public String toString() {
            if (Conditions.size() == 0) return "";

            String logicalOperator = " AND ";

            if (AtleastOneMatch) {
                logicalOperator = " OR ";
            }

            return "(" + String.join(logicalOperator, ClausesAsStrings()) + ")";
        }

        private ArrayList<String> ClausesAsStrings() {
            ArrayList<String> results = new ArrayList<>();

            for (Condition clause : Conditions) {
                results.add(clause.toString());
            }

            return  results;
        }

    }

    public class Condition {
        public String Field = null;
        public Object ComparisonValue = null;
        public ComparisonOperatorEnum ComparisonOperator = ComparisonOperatorEnum.Equals;

        public String ComparisonValueToString() {
            if (ComparisonValue instanceof LocalDate) {
                LocalDate dateValue = (LocalDate)ComparisonValue;
                return dateValue.format(DateTimeFormatter.ISO_LOCAL_DATE);
            }
            else if (ComparisonValue instanceof LocalDateTime) {
                LocalDateTime dateTimeValue = (LocalDateTime)ComparisonValue;
                return dateTimeValue.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            }
            else if (ComparisonValue instanceof String) {
                return "'" + ComparisonValue + "'";
            }
            else if (ComparisonValue instanceof Iterable) {
                Iterable genericList = (Iterable)ComparisonValue;

                ArrayList<String> items = new ArrayList<>();

                for (Object item : genericList) {
                    items.add(String.valueOf(item));
                }

                String listAsText = "('" + String.join("', '", items) + "')";

                return listAsText;
            }
            else {
                return String.valueOf(ComparisonValue);
            }
        }

        public String toString() {
            String clauseText = Field + " " + ComparisonOperator.toString() + " " + ComparisonValueToString();
            if (ComparisonOperator.PrefixWithNot()) {
                clauseText = "(NOT " + clauseText + ")";
            }
            return clauseText;
        }
    }

    public enum ComparisonOperatorEnum {
        Equals,
        NotEquals,
        Like,
        NotLike,
        GreaterThan,
        GreaterThanOrEqualTo,
        LessThan,
        LessThanOrEqualTo,
        In,
        NotIn,
        Includes,
        Excludes;

        public String toString() {
            switch (this) {
                case Equals: return "=";
                case NotEquals: return "!=";
                case Like: return "LIKE";
                case NotLike: return "LIKE";
                case GreaterThan: return ">";
                case GreaterThanOrEqualTo: return ">=";
                case LessThan: return "<";
                case LessThanOrEqualTo: return "<=";
                case In: return "IN";
                case NotIn: return "NOT IN";
                case Includes: return "INCLUDES";
                case Excludes: return "EXCLUDES";
                default: return "";
            }
        }

        public Boolean PrefixWithNot() {
            return (this == NotLike);
        }
    }


}
