package javaassignment;

public class Log {

    public static final int DEBUG_LEVEL = 3;
    public static final int ERROR_LEVEL = 2;
    public static final int INFO_LEVEL  = 1;
    public static final int NONE_LEVEL  = 0;
    public int debugLevel = INFO_LEVEL;// default: info level
    
    public Log(){}

    public Log(int debugLevel){
        this.debugLevel = debugLevel;
    }

    public Log(String level){
        if("DEBUG".equals(level)){
            debugLevel = DEBUG_LEVEL;
        }else if("ERROR".equals(level)){
            debugLevel = DEBUG_LEVEL;
        } else if ("NONE".equals(level)){
            debugLevel = 0;
        } 
    }

    public void setDebugLevel(int debugLevel){
        this.debugLevel = debugLevel;
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
