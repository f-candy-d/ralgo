package d.candy.f.com.ralgo.infra.sqlite;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by daichi on 8/15/17.
 */

public class SqliteWhere {

    /**
     * The super class of expression classes
     */
    abstract public static class Expr {
        static final String SPACE = " ";
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

        @NonNull
        String formalizeConsideringIsInBrancketAndNegation(@NonNull String baseExpression) {
            if (isNegation()) {
                baseExpression = "NOT " + baseExpression;
            }

            return (isInBrancket())
                    ? "(" + baseExpression + ")"
                    : baseExpression;
        }

        @NonNull abstract public String formalize();
    }

    /**
     * Logical expression class
     */
    public static class LogicExpr extends Expr {

        public enum LogicOp {
            AND,
            OR
        }

        @NonNull private String mExpression;

        public LogicExpr(@NonNull Expr mostLeftExpr) {
            mExpression = mostLeftExpr.formalize();
        }

        /**
         * Add '[logicOp] [expr]' expression
         */
        private LogicExpr join(@NonNull Expr expr, @NonNull LogicOp logicOp) {
            mExpression = mExpression.concat(SPACE + logicOp.toString() + SPACE + expr.formalize());
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
            mExpression = mostLeftExpr.formalize();
        }

        @NonNull
        @Override
        public String formalize() {
            return formalizeConsideringIsInBrancketAndNegation(mExpression);
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

        public CondExpr(@NonNull String column) {
            mColumn = column;
        }

        private <T> CondExpr join(@NonNull T right, @NonNull CondOp operator) {
            mCondition = operator.toString() + SPACE + right.toString();
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

        public void setColumn(@NonNull String column) {
            mColumn = column;
        }

        @NonNull
        @Override
        public String formalize() {
            if (this.mCondition != null) {
                String expression = this.mColumn + SPACE + this.mCondition;
                return formalizeConsideringIsInBrancketAndNegation(expression);
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
        private boolean mExcludeBoundaryOfMin = false;
        private boolean mExcludeBoundaryOfMax = false;

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

        public BetweenExpr setRangeBoundaries(boolean excludeMin, boolean excludeMax) {
            mExcludeBoundaryOfMin = excludeMin;
            mExcludeBoundaryOfMax = excludeMax;
            return this;
        }

        @NonNull
        @Override
        public String formalize() {
            if (mMin != null && mMax != null) {
                String expression;
                if (mExcludeBoundaryOfMin || mExcludeBoundaryOfMax) {
                    CondExpr condMin = (mExcludeBoundaryOfMin)
                            ? new CondExpr(mColumn).graterThan(mMin)
                            : new CondExpr(mColumn).graterThanOrEqualTo(mMin);

                    CondExpr condMan = (mExcludeBoundaryOfMax)
                            ? new CondExpr(mColumn).lessThan(mMax)
                            : new CondExpr(mColumn).lessThanOrEqualTo(mMax);

                    expression = new LogicExpr(condMin).and(condMan).formalize();

                } else {
                    expression = mColumn + " BETWEEN " + mMin + " AND " + mMax;
                }
                return formalizeConsideringIsInBrancketAndNegation(expression);
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

        public LikeExpr setRegex(@NonNull String regex) {
            mRegex = regex;
            return this;
        }

        public LikeExpr setColumn(@NonNull String column) {
            mColumn = column;
            return this;
        }

        @NonNull
        @Override
        public String formalize() {
            if (mRegex != null) {
                String expression = mColumn + " LIKE " + mRegex;
                return formalizeConsideringIsInBrancketAndNegation(expression);
            }
            throw new IllegalStateException("Syntax error");
        }
    }

    /**
     * IN expression class
     */
    public static class InExpr extends Expr {

        @NonNull private String mColumn;
        @Nullable private String mArgs;

        public InExpr(@NonNull String column) {
            mColumn = column;
            mArgs = null;
        }

        @SafeVarargs
        public <T> InExpr(@NonNull String column, @NonNull T... args) {
            this(column);
            addArgs(args);
        }

        public void resetArgs() {
            mArgs = null;
        }

        @SafeVarargs
        final public <T> void resetArgs(@NonNull T... args) {
            resetArgs();
            addArgs(args);
        }

        @SafeVarargs
        final public <T> InExpr addArgs(@NonNull T... args) {
            if (args.length != 0) {
                String commaSep = ",";
                String newArgs = TextUtils.join(commaSep, args);
                if (mArgs != null) {
                    mArgs = mArgs.concat(commaSep + newArgs);
                } else {
                    mArgs = newArgs;
                }
            }

            return this;
        }

        public InExpr setColumn(@NonNull String column) {
            mColumn = column;
            return this;
        }

        @NonNull
        @Override
        public String formalize() {
            if (mArgs != null) {
                String expression = mColumn + " IN(" + mArgs + ")";
                return formalizeConsideringIsInBrancketAndNegation(expression);
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

        public IsNullExpr setColumn(@NonNull String column) {
            mColumn = column;
            return this;
        }

        @NonNull
        @Override
        public String formalize() {
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
