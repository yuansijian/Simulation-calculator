package one.postfix;


import java.io.File;

public class Entrance {

    public static void main(String[] args) {
        String test[] = TestGenerate.Generate(10, 5);
//        String infix = "25.5++100+10";
        try {
            Expression expression = ExpressionFactory.createShuntingExp();

            for(int i=0; i<test.length; i++){
                double result = expression.infix2Result(test[i]);
                System.out.printf("%.2f\n",result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
