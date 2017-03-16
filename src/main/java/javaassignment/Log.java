package javaassignment;

public class Log {

    private static final int DEBUG_LEVEL =3;
    private static final int ERROR_LEVEL =2;
    private static final int INFO_LEVEL =1;
    private int debugLevel = 1;// default: info level
    public Log(){}
    
    public Log(String level){
        if("DEBUG".equals(level)){
            debugLevel = DEBUG_LEVEL;
        }else if("ERROR".equals(level)){
            debugLevel = DEBUG_LEVEL;
        } else if ("NONE".equals(level)){
            debugLevel = 0;
        } 
    }
    
    public void info(String s){
        if(debugLevel >= INFO_LEVEL){
            System.out.println("INFO  :"+ s);
        }
    }

    public void error(String s){
        if(debugLevel >= ERROR_LEVEL){
            System.out.println("ERROR: "+s);
        }
    }
    
    public void debug(String s){
        if(debugLevel >= DEBUG_LEVEL){
            System.out.println("DEBUG: "+s);
        }
    }
}
