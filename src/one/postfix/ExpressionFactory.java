package one.postfix;

/**
 * Created by wangzhe on 16/7/16.
 */
public class ExpressionFactory {

    public static Expression createShuntingExp(){
        return new ShuntingExpression();
    }

    public static Expression createBinaryExp(){
        return new BinaryExpression();
    }
}
