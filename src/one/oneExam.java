package one;

import java.util.Stack;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class change{

    private String testString = null;
    private Stack<Character> stack;

    public void TestStack(String testString){
        this.testString = testString;
        this.stack = new Stack<Character>();
    }

    public void turn(){
        for(int i=0; i<testString.length(); i++)
        {
            char c = testString.charAt(i);

            if(c == '+' || c == '-')
            {
                if(stack.isEmpty() || stack.peek() == '(')
                {
                    stack.push(c);
                }
                else
                {
                    while (!stack.isEmpty() && (stack.peek() == '*' || stack.peek() == '/' ||
                                stack.peek() == '+' || stack.peek() == '-'))
                    {
                        System.out.print(stack.pop());
                    }

                    stack.push(c);
                }
            }
            else if(c == '*' || c == '/')
            {
                if(stack.isEmpty() || stack.peek() == '+' || stack.peek() == '-'
                            || stack.peek() == '(')
                {
                    stack.push(c);
                }
                else
                {
                    while (!stack.isEmpty() && (stack.peek() == '/' || stack.peek() == '*'))
                    {
                        System.out.print(stack.pop());
                    }

                    stack.push(c);
                }
            }
            else if(c == '(')
            {
                stack.push(c);
            }
            else if(c == ')')
            {
                char temp = ' ';
                while ((temp = stack.pop()) != '(')
                {
                    System.out.print(stack.pop());
                }
            }
            else
            {
                System.out.print(c);
            }
        }

        if(!stack.isEmpty())
        {
            while (!stack.isEmpty())
            {
                System.out.print(stack.pop());
            }
        }
    }

    public double result(String s){
        return 0;
    }
}
class readFile{
    public void readFileByChars(String filename){
        File file = new File(filename);
        BufferedReader reader = null;
        change c = new change();

        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;

            //一次读入一行，直到读入null为文件结束
            while((tempString = reader.readLine()) != null){
                c.TestStack(tempString);
                c.turn();
            }

            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public int[] tt(){
        int a[] = new int[3];
        a[0] = 1;
        a[1] = 2;
        a[2] = 3;

        return a;
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
}

public class oneExam {
    public static void main(String []args)
    {
        String str = "2.5*4+(2*5)";
        Pattern p;
        Matcher m;
        String a[]=new String[20];
        str = str.replace("(", "");
        str = str.replace(")", "");
        p = Pattern.compile("\\+|\\-|\\*|\\/|\\(|\\)|\\d+(\\.\\d+)*");
        m = p.matcher(str);
        while(m.find()){
            System.out.println(m.group());
        }
        p = Pattern.compile("\\d+(\\.\\d+)*");
        m = p.matcher(str);
        int i = 0, j = 0;
        while(m.find()){
           //System.out.println(m.group());
            a[i++] = m.group();
            j++;
        }

       for(i=0; i<a.length; i++){
           System.out.println(a[i]);
       }
       System.out.println(j);

//        String s = new String();
//        s = "3+4*5/9";
//        boolean isMatch = Pattern.matches(pattern, s);
//        System.out.println("字符串中是否包含了 'runoob' 子字符串? " + isMatch);
//        readFile r = new readFile();
//        int a[] = r.tt();
//        System.out.println(a[0] + a[1]);
    }
}
