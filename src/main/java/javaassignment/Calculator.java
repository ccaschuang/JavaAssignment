package javaassignment;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javaassignment.error.DivZeroException;
/**
 * Calculator:
 *  support add/mult/sub/div 
 *      use like add(1,3) = 4
 *  support let (variable) 
 *   ex:
 *      let(a, 2, add(a,5)) = 7;
 *   
 *   Note: Only support integers:
 *         div(8, 3) will return 2
 * 
 *   Note: add/mult/sub/div/let are reserved words. which can be uppercase or lowercase.
 *        but variables are case sensitive.
 * 
 * Debug Level: 
 * using -DDebugLevel to add the debug level
 * Default is "INFO"
 * can use "DEBUG", "ERROR" if you want to debug
 * can use "NONE" if you don't want to see any the logs
 * 
 * ex: java -DDebugLevel=DEBUG "add(1,2)"
 * @author ccaschuang
 * 
 */

public class Calculator {


    Log log;
    
    public Calculator(){
        String dLevel= System.getProperty("DebugLevel");
        log = new Log(dLevel);
    }

    public static final void main(String[] args) throws Exception {
        Calculator calc = new Calculator();
        String dLevel= System.getProperty("DebugLevel");
        
        calc.log = new Log(dLevel);
        
        
        String formula = args[0];
        System.out.println(calc.calculate(formula));


    }

    private static final String ADD = "add";
    private static final String SUB = "sub";
    private static final String MULT = "mult";
    private static final String DIV = "div";
    private static final String LET = "let";

    
    Map<String, Integer> letMap = new HashMap<String, Integer>();
    int currentPos = 0;
    String formula;
    //Algorithm:
    // using stack to remeber the behavior we want to do
    // 1: 
    //   if it's "," or "(" separate and put it to stack
    // 2: 
    //   if it's ")", put the number and pop from stack to get all digits to do the calculate
    // 3: 
    //   let  need 3 parameters and if it's a variable--> going before to get the correct assign and calculate it.
    //   if get "let" method  --> use recursive to parse the left variables inside...
    //   and set it back ... continue to process
    public int calculate(String f) throws Exception {
        //int result = 0;
        Stack<String> stack = new Stack<String>();
        //stack.clear();
        currentPos = 0; //currentPos will be the local variable
        letMap.clear();
        formula = f;
        return parse(stack, false);
    }
    
    private int parse(Stack<String> stack, boolean isPartial) throws Exception{
        if(isPartial){
            log.info("start to get number for let");
        }else{
            log.info("start to parse formula: "+ formula );
        }

        String v= getNext();
        while( v!= null) {
            log.debug("currentPos: "+ currentPos+ "  getNext:" + v);
            if(LET.equals(v) || LET.equals(v.toLowerCase())){
                stack.push(getNext()); //push "(" inside
                getLetNumber();
            }else if (")".equals(v)){
                String num2 = stack.pop();
                String num1 = stack.pop();
                log.debug("num2 = " + num2 + " num1 = "+ num1);
                if("(".equals(num1)) {
                    stack.push(num2);
                    if(isPartial && stack.size() == 1){
                        return Integer.parseInt(stack.pop());
                    }
                }else{
                    stack.pop(); // remove "("
                    String arith = stack.pop();
                    stack.add(String.valueOf(cal(arith, num1, num2)));

                    if(isPartial && stack.size() == 1){
                        return Integer.parseInt(stack.pop());
                    }
                }

            }else if("(".equals(v)){
                stack.add(v);
            }else{
                stack.add(v);
                if(isPartial && stack.size()==1 && isNumber(v)){
                    //if it's let assignment, just return.
                    return Integer.parseInt(stack.pop());
                }
            }

            v=getNext();
        }

        if(stack.size() ==1 ){
            return Integer.parseInt(stack.pop());
        }
        int result = Integer.parseInt(stack.pop());
        log.debug("Debug.... after parsing... still has more than 1 elements inside: ");
        log.debug(""+result);
        
        boolean isParsingError = false;
        while(!stack.empty()){
            String element = stack.pop();
            log.debug(element);
            if(!"(".equals(element)){
                isParsingError = true;
            }
        }

        if(isParsingError) {
            log.error("invalid methods... can not parse them all");
            throw new Exception("invalid methods...");
        }
        return result;
    }

    /**
     * check that the input is a valid number or not.
     * @param s
     * @return boolean (s is a number or not)
     */
    private boolean isNumber(String s){
       try{
            Integer.parseInt(s);
            return true;
       }catch(Exception e){
           return false;
       }
    } 

    
    private int cal(String arith, String num1, String num2) throws Exception {
        log.debug("calc: " + num1+ " " + arith+" " + num2);
        int n1 = getNumber(num1);
        int n2 = getNumber(num2);
        
        arith = arith.toLowerCase();
        if(ADD.equals(arith)){
            log.debug("   " + num1+ " " + arith+" " + num2+ " =   " + (n1+n2)) ;
            return n1+n2;
        }
        if(SUB.equals(arith)){
            log.debug("   " + num1+ " " + arith+" " + num2+ " =   " + (n1 - n2)) ;
            return n1-n2;
        }
        if(MULT.equals(arith)){
            log.debug("   " + num1+ " " + arith+" " + num2+ " =   " + (n1 * n2)) ;
            return n1 * n2;
        }
        if(DIV.equals(arith)){
            if(n2 == 0){
                log.error("   " + num1+ " " + arith+" " + num2+ " DivZeroException") ;
                throw new DivZeroException();
            }
            log.debug("   " + num1+ " " + arith+" " + num2+ " =   " + (n1 / n2)) ;
            return n1 / n2;
        }
        
        log.error("unhandled arithmetic: "+arith);
        throw new Exception("unhandled arithmetic: " + arith);
        
    }

    /**
     * local method, will check the variable map to see if there's a valid variable to set or not.
     * @param num
     * @return
     */
    private int getNumber(String num){
        if(letMap.containsKey(num)){
            return letMap.get(num);
        }
        return Integer.parseInt(num);
    }
    
    /**
     * parse the string and get the next valid variable/number/arith
     * @return
     */
    private String getNext(){
        StringBuilder sb = new StringBuilder();
        for (; currentPos<formula.length(); currentPos++){
            char c = formula.charAt(currentPos);
            switch(c){
            case ' ': //ignore
                continue;
            case '(':
            case ')':
                if(sb.length() != 0){
                    return sb.toString();
                }
                currentPos++;
                return String.valueOf(c);

            case ',': //skip and return directly
                if(sb.length() >0){
                    currentPos++;
                    return sb.toString();
                }

                continue;
                //break;
            default:
                sb.append(c);
            }
        
        }

        currentPos++;
        return null;
    }

    private void getLetNumber() throws Exception{
        log.debug("getLetNumber");

        String v = getNext();
        Stack<String> stack = new Stack<String>();
        int value = parse(stack, true); //
        letMap.put(v, value);
        
        log.debug("put " + v + " = "+ value+ " to the let map");
    }
}
