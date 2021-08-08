package String;

import org.junit.Test;

import static org.junit.Assert.*;

public class BasicCalculatorTest {

    @Test
    public void testCalculate1(){
        String input = "(7)-(0)+(4)";
        BasicCalculator obj = new BasicCalculator();
        assertEquals(obj.calculate(input), 11);
    }

    @Test
    public void testCalculate2(){
        String input = "-7 - 0 + 4";
        BasicCalculator obj = new BasicCalculator();
        assertEquals(obj.calculate(input), -3);
    }
}