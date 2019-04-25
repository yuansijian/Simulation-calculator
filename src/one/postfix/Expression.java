package one.postfix;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.lang.Math;
import java.lang.Exception;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 中缀表达式转换为后缀表达式（逆波兰表达式），即调度场算法(shunting yard algorithm)
 * 1.建立运算符栈用于运算符的存储，此运算符遵循越往栈顶优先级越高的原则。
 * 2.预处理表达式，正、负号前加0(如果一个加号（减号）出现在最前面或左括号后面，则该加号（减号） 为正负号)。
 * 3.顺序扫描表达式，如果当前字符是数字（优先级为0的符号），则直接输出该数字；如果当前字符为运算符或者括号（优先级不为0的符号），则判断第四点。
 * 4.1 若当前运算符为'('，直接入栈；
 * 4.2 若为')'，出栈并顺序输出运算符直到遇到第一个'(',遇到的第一个'('出栈但不输出；
 * 4.3 若为其它，比较运算符栈栈顶元素与当前元素的优先级：
 * 4.3.1 如果栈顶元素是'('，当前元素直接入栈；
 * 4.3.2 如果栈顶元素优先级>=当前元素优先级，出栈并顺序输出运算符直到栈顶元素优先级<当前元素优先级，然后当前元素入栈；
 * 4.3.3 如果栈顶元素优先级<当前元素优先级，当前元素直接入栈。
 * 5.重复第三点直到表达式扫描完毕。
 * 6.顺序出栈并输出运算符直到栈元素为空。
 * <p/>
 * <p/>
 */
public abstract class Expression {

    public abstract double infix2Result(String infix);

    protected abstract String convertPostfix(String infix);

    /**
     * 预处理，负数补全0或者补全括号
     *
     * @param infix
     * @return
     */
    public abstract String preprocess(String infix);


    protected double postfixArithmetic(String postfix) {
        Stack<Double> stack = new Stack<>();
        String[] array = postfix.split(" ");
        for(int i = 0; i < array.length; i++) {
            array[i].trim();
            if (Character.isDigit(array[i].charAt(0))) {
                stack.push(Double.valueOf(array[i]));
            } else {
                double y = stack.pop();
                double x = stack.pop();
                stack.push(calculate(x, y, array[i]));
            }

        }
        return stack.pop();
    }

    protected double calculate(double x, double y, String operator) {
        double ret = 0.0;
        switch (operator) {
            case ".":
//                int len = Integer.toString((int)x).length();
//                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                ret = ((Double.parseDouble(Integer.toString((int)x) + "." + Integer.toString((int)y))));
                //ret = x + y / Math.pow(10, len);
                break;
            case "+":
                ret = x + y;
                break;
            case "-":
                ret = x - y;
                break;
            case "*":
                ret = x * y;
                break;
            case "/":
                if (y == 0) throw new IllegalArgumentException("Divisor cannot be zero");
                ret = x / y;
                break;
        }
        return ret;
    }

    //括号匹配并不匹配返回下标
    public int[] checkBrackets(String str){

        Stack<Character> stack = new Stack<Character>();
        Stack<Integer> postion = new Stack<Integer>();
        char c;
        int i = 0, count = 0;
        //boolean flag = false;

        for(i=0; i<str.length(); i++){
            c = str.charAt(i);
            if (c == '('){
                stack.push(c);
                postion.push(i);
                count++;
            }
            if (c == ')'){
                if(stack.isEmpty()){
                    postion.push(i);
                    count++;
                    break;
                    //return i;
                }
                stack.pop();
                postion.pop();
                count--;
            }
        }

        int a[] = new int[count];
        i = 0;
        while (!postion.isEmpty()){
            a[i++] = postion.pop();
        }

        return a;
    }

    //验证运算表达式
    protected String[] solve(String str){
        Pattern p;
        Matcher m;
        String a[]=new String[str.length()];
//        p = Pattern.compile("\\+|\\-|\\*|\\/|\\(|\\)");
//        m = p.matcher(str);
//        while(m.find()){
//            System.out.println(m.group());
//        }
        p = Pattern.compile("\\+|\\-|\\*|\\/|\\(|\\)|\\d+(\\.\\d+)*");
        m = p.matcher(str);
        int i = 0, j = 0;
        while(m.find()){
            //System.out.println(m.group());
//            a[i++] = m.group();
            a[i++] = m.group();
        }
//        for(i=0; i<a.length; i++){
//            System.out.println(a[i]);
//        }
//        System.out.println(j);

        return a;
    }
    protected boolean strNum(String str){
        boolean flag = true;

        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        flag = pattern.matcher(str).matches();
        return pattern.matcher(str).matches();
//        for(int i=0; i<str.length(); i++){
//            if(!Character.isDigit(str.charAt(i)))
//            {
//                flag = false;
//                break;
//            }
//        }
//
//        return flag;
    }
    protected boolean strNu(String str){
        boolean flag = true;
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        flag = pattern.matcher(str).matches();
        return pattern.matcher(str).matches();
    }
    protected boolean strN(String str){
        boolean flag = false;

        if(str.equals("+") || str.equals("-") || str.equals("/") || str.equals("*")){
            flag = true;
        }

        return flag;
    }
    protected int[] operator(String str){
        String s = preprocess(str);
        System.out.println(s);
        s = s.replace("(", "");
        s = s.replace(")", "");
        String b[] = solve(s);
        int len = b.length;
        int a[] = new int[len];
        int count = 0;
        int i=1, j=0;
        while(b[i] != null){
            if(strN(b[i])){
                if(b[i-1] != null && (strN(b[i-1]))){
                    if(j>0 && (i-1!=a[j-1])){
                        a[j++] = i-1;
                        i++;
                    }
                    else{
                        a[j++] = i-1;
                        i++;
                    }
                }
                else if(b[i+1] != null && strN(b[i+1])){
                    if(j>0 && (i-1!=a[j-1])){
                        a[j++] = i+1;
                        i++;
                    }
                    else{
                        a[j++] = i+1;
                        i++;
                    }
                }
            }
            i++;
        }

        if(strN(b[i-1])){
            j++;
        }
        int p[] = new int[j];
        for(int u=0; u<j; u++){
            p[u] = a[u];
        }

//        for(int i=0,j=0; i<len-1; i++){
//            c = str.charAt(i);
//            if(!Character.isDigit(c) && Character.isDigit(str.charAt(i-1)) && Character.isDigit(str.charAt(i+1))){
//
//            }
//            else if(Character.isDigit(c) && !Character.isDigit(str.charAt(i-1)) && !Character.isDigit(str.charAt(i+1))){
//
//            }
//            if(!strNum(b[i])){
//                if(!strNum(b[i-1])){
//                    if(j>0 && (i-1!=a[j-1])){
//                        a[j++] = i-1;
//                        i++;
//                    }
//                    else{
//                        a[j++] = i-1;
//                        i++;
//                    }
//                }
//                else if(!strNum(b[i+1])){
//                    if(j>0 && (i-1!=a[j-1])){
//                        a[j++] = i+1;
//                        i++;
//                    }
//                    else{
//                        a[j++] = i+1;
//                        i++;
//                    }
//                }
//            }
//        }

        return p;
    }

    //此函数用正则表达式匹配效果更好
    protected boolean validInfix(String infix) {
        Character[] validChar = {'+', '-', '*', '/', '(', ')', '.'};
        List<Character> validChars = Arrays.asList(validChar);
        int leftBracket = 0;
        int rightBracket = 0;
        boolean invalid = false;
        char c;
        int ap[] = operator(infix);
        int ax[] = checkBrackets(infix);

        try{
            if(ax.length!=0){
                throw new Exception("0括号异常");
            }
            if(ap.length!=0){
                throw new Exception("1操作数异常");
            }

        } catch (Exception e){
            System.out.println(e);

            int i=0;
            for(i=0; i<ax.length; i++){
                System.out.println("errorLocation: " + ax[i]);
            }

            int j=0;
            for(j=0; j<ap.length; j++){
                System.out.println("errorLocation: " + ap[j]);
            }

            System.exit(0);
        }

        return true;
//        for (int i = 0; i < infix.length(); i++) {
//            c = infix.charAt(i);
//            if (c == '('){
//                leftBracket++;
//            }
//            if (c == ')') rightBracket++;
//            if (!(validChars.contains(c) || Character.isDigit(c))) invalid = true;
//        }
//
//        return  leftBracket == rightBracket && !invalid;
//        String pattern = "(((?<o>\\()[-+]?(\\d+(?:\\.\\d+)?[-+*/])*)+\\d+(?:\\.\\d+)?((?<-o>\\))([-+*/]\\d+(?:\\.\\d+)?)*)+($|[-+*/]))*(?(o)(?!))";
//            for (int i = 0; i < infix.length(); i++) {
//                c = infix.charAt(i);
//                if (c == '('){
//                    leftBracket++;
//                }
//                if (c == ')') rightBracket++;
//                if (!(validChars.contains(c) || Character.isDigit(c))) invalid = true;
//            }
//
//        return  leftBracket == rightBracket && !invalid;
    }

    /**
     * 比较优先级高低
     *
     * @param a
     * @param b
     * @return
     */
    protected boolean higherPriority(char a, char b) {
        return priority(a) >= priority(b);
    }

    private int priority(char c) {
        int p = 0;
        switch (c) {
            case '(':
            //小数
            case '.':
                p = 4;
                break;
            case '*':
            case '/':
                p = 3;
                break;
            case '+':
            case '-':
                p = 2;
            default:
                p = 0;
                break;
        }
        return p;
    }
}
