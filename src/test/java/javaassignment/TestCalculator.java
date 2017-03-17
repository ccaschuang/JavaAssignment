package javaassignment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class TestCalculator {

    Calculator calc;
    @Before
    public void init(){
        calc= new Calculator();

    }

    @Test
    public void testAdd() {
        try{
            assertEquals(100000,calc.calculate("add(99999,1)"));
            assertEquals(-999,  calc.calculate("add(-995, -4)"));
            assertEquals(-1994, calc.calculate("add(-995, -999)"));
        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSub() {
        try{
            assertEquals(99998,calc.calculate("sub(99999,1)"));
            assertEquals(-991,  calc.calculate("sub(-995, -4)"));
            assertEquals(4, calc.calculate("sub(-995, -999)"));
        }catch(Exception e){
            fail();
        }
    }
    @Test
    public void testMult() {
        try{
            assertEquals(99999,calc.calculate("mult(99999,1)"));
            assertEquals(3980,  calc.calculate("mult(-995, -4)"));
            assertEquals(-994005, calc.calculate("mult(995, -999)"));
        }catch(Exception e){
            fail();
        }
    }

    @Test
    public void testDiv() {
        try{
            assertEquals(99999,calc.calculate("div(99999,1)"));
            assertEquals(199,  calc.calculate("div(995, 5)"));
            assertEquals(2, calc.calculate("div(8, 3)")); // should be 2  not 2.66
        }catch(Exception e){
            fail();
        }
    }

    @Test
    public void testLet() {
        try{
            assertEquals(99999,calc.calculate("let(a,1,div(99999,a))"));
            assertEquals(199,  calc.calculate("let(Z,995, div(Z, 5))"));
        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test 
    public void testQuestion(){
        try {
            assertEquals(3,calc.calculate("add(1, 2)"));
            assertEquals(7,calc.calculate("add(1, mult(2, 3))"));
            assertEquals(12,calc.calculate("mult(add(2, 2), div(9, 3))"));

            assertEquals(10,calc.calculate("let(a, 5, add(a, a))"));
            assertEquals(55,calc.calculate("let(a, 5, let(b, mult(a, 10), add(b, a)))"));
            assertEquals(1,calc.calculate("sub(sub(10,5), mult(2,2))"));
            assertEquals(40,calc.calculate("let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))"));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testComplicateTestCase(){
        try{
            assertEquals(24,calc.calculate("add(let(a,4,add(a,4)),let(a, 8,add(a,8)))"));
            assertEquals(21,  calc.calculate("sub(add(let(a,4,add(a,4)),let(a, 8,add(a,8))), let(b,1,mult(3,b)))"));
        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }
    //testing that wrong syntax may cause exception
    @Test 
    public void testAssignError(){
        try {
            assertEquals(3,calc.calculate("add(c, 2)"));
            fail();

        } catch (Exception e) {}

        try {
            assertEquals(3,calc.calculate("let(let,,4,add(1, 2)),let(3))"));
            fail();

        } catch (Exception e) {}

        try {
            assertEquals(0,calc.calculate("div(div(3,3), mult(3,2), mult)"));
            fail();

        } catch (Exception e) {}
    }
    
//    @Test
//    public void testCodeShipError(){
//        fail();
//    }


}
