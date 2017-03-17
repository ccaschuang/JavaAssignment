# JavaAssignment

Test for the java assignment


Calculator:
support add/mult/sub/div 
      use like add(1,3) = 4
  support let (variable) 
   ex:
      let(a, 2, add(a,5)) = 7;
   
   Note: Only support integers:
         div(8, 3) will return 2
 
   Note: add/mult/sub/div/let are reserved words. all in lowercase
 
Can see more examples in TestCalculator.java 
 
 
 Debug Level: 
 using -DDebugLevel to add the debug level
 Default is "INFO"
 can use "DEBUG", "ERROR" if you want to debug
 can use "NONE" if you don't want to see any the logs
 
 ex:
 java -DDebugLevel=NONE javaassignment.Calculator "add(1,2)"
 output: 3
ex2:  
java -DDebugLevel=DEBUG javaassignment.Calculator "add(1,2)"
INFO  :start to parse formula: add(1,2)
DEBUG: currentPos: 3  getNext:add
DEBUG: currentPos: 4  getNext:(
DEBUG: currentPos: 6  getNext:1
DEBUG: currentPos: 7  getNext:2
DEBUG: currentPos: 8  getNext:)
DEBUG: num2 = 2 num1 = 1
DEBUG: calc: 1 add 2
DEBUG:    1 add 2 =   3
3

 
 

CI url: https://app.codeship.com/projects/208326


