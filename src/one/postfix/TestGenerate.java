package one.postfix;

import java.util.Set;
import java.util.TreeSet;
import java.util.Random;
import java.util.Vector;

public class TestGenerate {
    public static void main(String[] args) {
        //Generate(4, 2);
        
    }

    /** 
     * Generate returns cnt expressions, where ecnt is wrong
     * @param cnt int, the number of expressions need to generate
     * @param ecnt int, the number of wrong expressions
     * @return String[], max(cnt, ecnt) expressions
     */
    public static String[] Generate(int cnt, int ecnt) {
        cnt = Math.max(cnt, ecnt);
        String[] res = generate(cnt);
        genErr(res, ecnt);
        return res;
    }

    private static String[] generate(int cnt){
        String[] res = new String[cnt];
        String str_num1 = "", str_num2 = "", tem = "";
        Double num1 = 0d, num2 = 0d;
        Integer len = 0, bracket = 0;
        Character ch = ' ';
        Random rand = new Random();
        for (int i = 0; i < cnt; i++) {
            num1 = rand.nextDouble() * 2000 - 1000;
            num2 = rand.nextDouble() * 2000 - 1000;
            ch = createSymbol();
            len = rand.nextInt(10) + 2;//the number of operand
            res[i] = format(num1) + ch + format(num2);
            for(int j = 2; j < len; j++){
                ch = createSymbol();
                str_num1 = res[i];
                bracket = rand.nextInt(4);
                if(bracket == 0){
                    str_num1 = '(' + str_num1 + ')';
                }
                ch = createSymbol();
                num2 = rand.nextDouble() * 2000 -1000;
                str_num2 = format(num2);
                bracket = rand.nextInt(2);
                if(bracket == 0){
                    tem = str_num1;
                    str_num1 = str_num2;
                    str_num2 = tem;
                }
                res[i] = str_num1 + ch + str_num2;
            }
        }
        return res;
    }

    private static void genErr(String[] s, int cnt){
        int n = s.length;
        Set<Integer> set = new TreeSet<>();
        Random rand = new Random();
        int index = 0;
        while(cnt > 0){
            index = rand.nextInt(n);
            if(set.isEmpty() || !set.contains(index)){
                set.add(index);
                cnt--;
                s[index] = createErr(s[index]);
            }
        }
    }

    private static String createErr(String s){
        Random random = new Random();
        /** 
         * error type
         * x = 0, Missing operator
         * x = 1, Missing right operand
         * x = 2, Brackets do not match
        */
        int x = random.nextInt(3);
        StringBuilder sb = new StringBuilder(s);
        while(x == 2 && (-1 == sb.indexOf("("))){
            x = random.nextInt(3);
        }

        switch(x){
            case 0:{
                int pos = -1, cnt = random.nextInt(3) + 1;
                for(int i = 1; i < sb.length(); i++){
                    switch(sb.charAt(i)){
                        case '-':{
                            if(sb.charAt(i - 1) == '('){
                                continue;
                            }
                        }
                        case '+':
                        case '*':
                        case '/':{
                            pos = i;
                            cnt--;
                            if(cnt == 0){
                                sb.replace(pos, pos + 1, " ");
                                return sb.toString();
                            }
                            break;
                        }
                        default:continue;
                    }
                }
                sb.replace(pos, pos + 1, " ");
                return sb.toString();
            }
            case 1:{
                int start = -1, end = -1;
                for(int i = 1; i < sb.length(); i++){
                    switch(sb.charAt(i)){
                        case '+':
                        case '-':
                        case '*':
                        case '/':{
                            if(sb.charAt(i - 1) != '('){
                                start = i + 1;
                                break;
                            }
                        }
                        default:continue;
                    }
                    break;
                }
                for(int i = start + 1; i < sb.length(); i++){
                    switch(sb.charAt(i)){
                        case '+':
                        case '-':
                        case '*':
                        case '/':{
                            if(sb.charAt(i - 1) != '('){
                                end = i;
                                break;
                            }
                        }
                        default:continue;
                    }
                    break;
                }
                if(end == -1){
                    end = sb.length();
                }
                sb.replace(start, end, " ");
                return sb.toString();
            }
            case 2:{
                int pos = -1, cnt = random.nextInt(4) + 1;
                for(int i = 0; i < sb.length(); i++){
                    switch(sb.charAt(i)){
                        case '(':
                        case ')':{
                            pos = i;
                            cnt--;
                            if(cnt == 0){
                                sb.replace(pos, pos + 1, " ");
                                return sb.toString();
                            }
                            break;
                        }
                        default:continue;
                    }
                }
                sb.replace(pos, pos + 1, " ");
                return sb.toString();
            }
        }
        return sb.toString();
    }

    private static char createSymbol() {
        Random random = new Random();
        switch (random.nextInt(5)) {
        case 0:
            return '+';
        case 1:
            return '-';
        case 2:
            return '*';
        case 3:
            return '/';
        default:
            return '+';
        }
    }

    private static String format(double x){
        if(x < 0){
            return "(" + String.format("%.2f", x) + ")";
        }
        return String.format("%.2f", x);
    }
}