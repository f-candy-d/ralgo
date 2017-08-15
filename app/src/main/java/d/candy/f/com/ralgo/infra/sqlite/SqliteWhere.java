package d.candy.f.com.ralgo.infra.sqlite;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by daichi on 8/15/17.
 */

public class SqliteWhere {
    /**
     * The super class of expression classes
     */
    abstract static class Expr {
        protected static final String SPACE = " ";
        public Expr() {}
    }

    /**
     * Logical expression class
     */
    public static class LogicExpr extends Expr {

        public enum LogicOp {
            AND,
            OR
        }

        @NonNull private ArrayList<Pair<Expr, LogicOp>> mOperands;
        private boolean mIsInBrancket = false;

        public LogicExpr(@NonNull Expr mostLeftExpr) {
         mOperands = new ArrayList<>();
            mOperands.add(new Pair<Expr, LogicOp>(mostLeftExpr, null));
        }

        /**
         * Add '[logicOp] [expr]' expression
         */
        private LogicExpr join(@NonNull Expr expr, @NonNull LogicOp logicOp) {
            mOperands.add(new Pair<>(expr, logicOp));
            return this;
        }

        /**
         * Add '[outerLogicOp] ([mColumn] [innerLogicOp] [mCondition])' expression
         */
        private LogicExpr joinLogicExpr(@NonNull LogicOp outerLogicOp, @NonNull Expr left,
                                        @NonNull Expr right, @NonNull LogicOp innerLogicOp) {

            LogicExpr inner = new LogicExpr(left).join(right, innerLogicOp);
            inner.setInBrancket(true);
            return join(inner, outerLogicOp);
        }

        /**
         * add 'AND [expr]' expression
         */
        public LogicExpr and(@NonNull Expr expr) {
            return join(expr, LogicOp.AND);
        }

        /**
         * add 'OR [expr]' expression
         */
        public LogicExpr or(@NonNull Expr expr) {
            return join(expr, LogicOp.OR);
        }

        /**
         * add 'AND ([mColumn] AND [mCondition])' expression
         */
        public LogicExpr andAndExpr(@NonNull Expr left, @NonNull Expr right) {
            return joinLogicExpr(LogicOp.AND, left, right, LogicOp.AND);
        }

        /**
         * add 'AND ([mColumn] OR [mCondition])' expression
         */
        public LogicExpr andOrExpr(@NonNull Expr left, @NonNull Expr right) {
            return joinLogicExpr(LogicOp.AND, left, right, LogicOp.OR);
        }

        /**
         * add 'OR ([mColumn] AND [mCondition])' expression
         */
        public LogicExpr orAndExpr(@NonNull Expr left, @NonNull Expr right) {
            return joinLogicExpr(LogicOp.OR, left, right, LogicOp.AND);
        }

        /**
         * add 'OR ([mColumn] OR [mCondition])' expression
         */
        public LogicExpr orOrExpr(@NonNull Expr left, @NonNull Expr right) {
            return joinLogicExpr(LogicOp.OR, left, right, LogicOp.OR);
        }

        public boolean isInBrancket() {
            return mIsInBrancket;
        }

        public void setInBrancket(boolean inBrancket) {
            mIsInBrancket = inBrancket;
        }

        public void reset(@NonNull Expr mostLeftExpr) {
            mOperands.clear();
            mOperands.add(new Pair<Expr, LogicOp>(mostLeftExpr, null));
        }

        @Override
        public String toString() {
            if (mOperands.size() == 0) {
                throw new IllegalStateException();
            }

            // mOperands.get(0).first is a most mColumn operand,
            // and mOperands.get(0).second must be a null object.
            String expression = mOperands.get(0).first.toString();

            for (Pair<Expr, LogicOp> pair : mOperands) {
                expression = expression.concat(SPACE + pair.second.toString() + SPACE + pair.first.toString());
            }

            return (mIsInBrancket)
                    ? "(" + expression + ")"
                    : expression;
        }
    }

    /**
     * Conditional expression class
     */
    public static class CondExpr extends Expr {

        public enum CondOp {
            LT("<"),       // a <  b (a is Less Than b)
            LTE("<="),     // a <= b (a is Less Than or Equal to b)
            GT(">"),       // a >  b (a is Grater Than b)
            GTE(">="),     // a >= b (a is Grater Than or Equal to b)
            EQ("="),       // a == b (a is EQual to b)
            NEQ("!=");     // a != b (a is Not EQual to b)

            private String mString;

            CondOp(final String string) {
                mString = string;
            }

            @Override
            public String toString() {
                return mString;
            }
        }

        @NonNull private String mColumn;
        @Nullable private String mCondition = null;
        @Nullable private CondOp mOperator = null;

        public CondExpr(@NonNull String column) {
            mColumn = column;
        }

        private <T> CondExpr join(@NonNull T right, @NonNull CondOp operator) {
            mCondition = right.toString();
            mOperator = operator;
            return this;
        }

        public <T> CondExpr lessThan(@NonNull T right) {
            return join(right, CondOp.LT);
        }

        public <T> CondExpr lessThanOrEqualTo(@NonNull T right) {
            return join(right, CondOp.LTE);
        }

        public <T> CondExpr graterThan(@NonNull T right) {
            return join(right, CondOp.GT);
        }

        public <T> CondExpr graterThanOrEqualTo(@NonNull T right) {
            return join(right, CondOp.GTE);
        }

        public <T> CondExpr equalTo(@NonNull T right) {
            return join(right, CondOp.EQ);
        }

        public <T> CondExpr notEqualTo(@NonNull T right) {
            return join(right, CondOp.NEQ);
        }

        public CondExpr reset(@NonNull String left) {
            mColumn = left;
            mOperator = null;
            return this;
        }

        @Override
        public String toString() {
            if (this.mCondition != null && this.mOperator != null) {
                return this.mColumn + SPACE + this.mOperator.toString() + SPACE + this.mCondition;
            }
            throw new IllegalStateException("Syntax error");
        }
    }

    /**
     * Other special expression class
     */
    public static class SpecExpr extends Expr {

        public enum SpecOp {
            BETWEEN,
            NOT_BETWEEN,
            LIKE,
            IN,
            IS_NULL,
            IS_NOT_NULL;

            public String toString(final ArrayList<String> args) {
                switch (this) {
                    case BETWEEN: {
                        if (2 <= args.size()) {
                            return "BETWEEN " + args.get(0) + " AND " + args.get(1);
                        }
                        throw new IllegalArgumentException("Too few arguments");
                    }

                    case NOT_BETWEEN: {
                        if (2 <= args.size()) {
                            return "NOT BETWEEN " + args.get(0) + " AND " + args.get(1);
                        }
                        throw new IllegalArgumentException("Too few arguments");
                    }

                    case LIKE: {
                        if (1 <= args.size()) {
                            return "LIKE " + args.get(0);
                        }
                        throw new IllegalArgumentException("Too few arguments");
                    }

                    case IN: {
                        if (1 <= args.size()) {
                            return "IN(" + TextUtils.join(",", args) + ")";
                        }
                        throw new IllegalArgumentException("Too few arguments");
                    }

                    case IS_NULL: {
                        return "IS NULL";
                    }

                    case IS_NOT_NULL: {
                        return "IS NOT NULL";
                    }

                    default:
                        throw new IllegalArgumentException();
                }
            }
        }

        @Nullable private SpecOp operator = null;
        private ArrayList<String> args;
        public String operand = null;

        public SpecExpr() {
            // (NOT)BETWEEN requires 2 arguments, LIKE requires 1 argument,
            // IN requires some arguments more than 0, and IS(NOT)NULL requires no argument, so...
            this.args = new ArrayList<>(2);
        }

        public void between(final String operand, final long min, final long max) {
            this.operator = SpecOp.BETWEEN;
            this.operand = operand;
            this.args.clear();
            this.args.add(String.valueOf(min));
            this.args.add(String.valueOf(max));
        }

        public void notBetween(final String operand, final long min, final long max) {
            this.operator = SpecOp.NOT_BETWEEN;
            this.operand = operand;
            this.args.clear();
            this.args.add(String.valueOf(min));
            this.args.add(String.valueOf(max));
        }

        public void like(final String operand, final String regex) {
            this.operator = SpecOp.LIKE;
            this.operand = operand;
            this.args.clear();
            this.args.add("'" + regex + "'");
        }

        public void in(final String operand, final String[] vals) {
            this.operator = SpecOp.IN;
            this.operand = operand;
            this.args.clear();
            for (String val : vals) {
                this.args.add("'" + val + "'");
            }
        }

        public void in(final String operand, final int[] vals) {
            this.operator = SpecOp.IN;
            this.operand = operand;
            this.args.clear();
            for (int val : vals) {
                this.args.add(String.valueOf(val));
            }
        }

        public void in(final String operand, final long[] vals) {
            this.operator = SpecOp.IN;
            this.operand = operand;
            this.args.clear();
            for (long val : vals) {
                this.args.add(String.valueOf(val));
            }
        }

        public void isNull(final String operand) {
            this.operator = SpecOp.IS_NULL;
            this.operand = operand;
            this.args.clear();
        }

        public void isNotNull(final String operand) {
            this.operator = SpecOp.IS_NOT_NULL;
            this.operand = operand;
            this.args.clear();
        }

        @Override
        public String toString() {
            if (this.operand != null && this.operator != null) {
                return this.operand + SPACE + this.operator.toString(this.args);
            }
            return null;
        }
    }

    public static class BetweenExpr extends Expr {

        @NonNull private String mColumn;
        private String mMin;
        private String mMax;

        public BetweenExpr(@NonNull String column) {
            mColumn = column;
            mMax = null;
            mMin = null;
        }

        public <T> BetweenExpr setRange(@NonNull T min, @NonNull T max) {
            mMin = min.toString();
            mMax = max.toString();
            return this;
        }

        public BetweenExpr setColumn(@NonNull String column) {
            mColumn = column;
            return this;
        }

        @Override
        public String toString() {
            if (mMin != null && mMax != null) {
                return mColumn + SPACE + "BETWEEN " + mMin + " AND " + mMax;
            }
            throw new IllegalStateException("Syntax error");
        }
    }

    public static class LikeExprt extends Expr {

    }
}
