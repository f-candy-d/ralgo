package d.candy.f.com.ralgo.infra.sqlite;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by daichi on 8/15/17.
 */

public class SqliteWhere {

    /**
     * The super class of expression classes
     */
    abstract static class Expr {
        protected static final String SPACE = " ";
        private boolean mNegation = false;
        private boolean mIsInBrancket = false;

        public Expr() {}

        public boolean isNegation() {
            return mNegation;
        }

        public void setNegation(boolean negation) {
            mNegation = negation;
        }

        public boolean isInBrancket() {
            return mIsInBrancket;
        }

        public void setInBrancket(boolean inBrancket) {
            mIsInBrancket = inBrancket;
        }

        protected String toStringConsideringIsInBrancketAndNegation(@NonNull String baseExpression) {
            if (isNegation()) {
                baseExpression = "NOT " + baseExpression;
            }

            return (isInBrancket())
                    ? "(" + baseExpression + ")"
                    : baseExpression;
        }
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

            return toStringConsideringIsInBrancketAndNegation(expression);
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
                String expression = this.mColumn + SPACE + this.mOperator.toString() + SPACE + this.mCondition;
                return toStringConsideringIsInBrancketAndNegation(expression);
            }
            throw new IllegalStateException("Syntax error");
        }
    }

    /**
     * BETWEEN expression class
     */
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
                String expression = mColumn + " BETWEEN " + mMin + " AND " + mMax;
                return toStringConsideringIsInBrancketAndNegation(expression);
            }
            throw new IllegalStateException("Syntax error");
        }
    }

    /**
     * LIKE expression class
     */
    public static class LikeExpr extends Expr {

        @NonNull private String mColumn;
        private String mRegex;

        public LikeExpr(@NonNull String column) {
            this(column, null);
        }

        public LikeExpr(@NonNull String column, String regex) {
            mColumn = column;
            mRegex = regex;
        }

        @NonNull
        public String getRegex() {
            return mRegex;
        }

        public void setRegex(@NonNull String regex) {
            mRegex = regex;
        }

        @NonNull
        public String getColumn() {
            return mColumn;
        }

        public void setColumn(@NonNull String column) {
            mColumn = column;
        }

        @Override
        public String toString() {
            if (mRegex != null) {
                String expression = mColumn + " LIKE " + mRegex;
                return toStringConsideringIsInBrancketAndNegation(expression);
            }
            throw new IllegalStateException("Syntax error");
        }
    }

    /**
     * IN expression class
     */
    public static class InExpr extends Expr {

        @NonNull private String mColumn;
        private ArrayList<String> mArgs;

        public InExpr(@NonNull String column) {
            mColumn = column;
            mArgs = new ArrayList<>();
        }

        @SafeVarargs
        public <T> InExpr(@NonNull String column, @NonNull T... args) {
            this(column);
            addArgs(args);
        }

        public void resetArgs() {
            mArgs.clear();
        }

        @SafeVarargs
        final public <T> void resetArgs(@NonNull T... args) {
            resetArgs();
            addArgs(args);
        }

        @SafeVarargs
        final public <T> void addArgs(@NonNull T... args) {
            for (T arg : args) {
                mArgs.add(arg.toString());
            }
        }

        @NonNull
        public String getColumn() {
            return mColumn;
        }

        public void setColumn(@NonNull String column) {
            mColumn = column;
        }

        @Override
        public String toString() {
            if (1 <= mArgs.size()) {
                String expression = mColumn + " IN(" + TextUtils.join(",", mArgs) + ")";
                return toStringConsideringIsInBrancketAndNegation(expression);
            }
            throw new IllegalArgumentException("Syntax error");
        }
    }

    /**
     * IS NULL expression class
     */
    public static class IsNullExpr extends Expr {

        @NonNull String mColumn;

        public IsNullExpr(@NonNull String column) {
            mColumn = column;
        }

        @NonNull
        public String getColumn() {
            return mColumn;
        }

        public void setColumn(@NonNull String column) {
            mColumn = column;
        }

        @Override
        public String toString() {
            String expression;
            if (isNegation()) {
                expression = mColumn + " IS NOT NULL";
            } else {
                expression = mColumn + " IS NULL";
            }

            return (isInBrancket())
                    ? "(" + expression +")"
                    : expression;
        }
    }
}
