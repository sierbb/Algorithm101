package OOD;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AllOoneDataStructureTest {

    public void runTest(String[] command, String[][] objects, String[] expected){
        AllOoneDataStructure obj = new AllOoneDataStructure();
        for (int i =1; i<command.length; i++){
            System.out.println("Validating command: " + command[i] + "; obj: "+ objects[i][0]+ "; expected: "+ expected[i]);
            if (command[i] == "inc"){
                obj.inc(objects[i][0]);
            }else if (command[i] == "dec"){
                obj.dec(objects[i][0]);
            }else if (command[i] == "getMinKey"){
//                System.out.println(obj.getMinKey().equals(expected[i]));
                assertEquals(obj.getMinKey(), expected[i]);
            }else if (command[i] == "getMaxKey"){
                assertEquals(obj.getMaxKey(), expected[i]);
            }else {
                throw new IllegalArgumentException("Invaid command");
            }
        }
    }

    @Test
    public void testCall1(){
        AllOoneDataStructure obj = new AllOoneDataStructure();
        String[] command = new String[] { "AllOne","inc","inc","inc","inc","inc","inc","dec","dec","getMinKey", "dec", "getMaxKey","getMinKey"};
        String[][] objects = new String[][] { {}, {"a"},{"b"},{"b"},{"c"},{"c"},{"c"},{"b"},{"b"},  {""},       {"a"},  {""},          {""}};
        String[] expected = new String[] {null,null,null,null,null,null,null,null,null,"a",null,"c","c"};
        runTest(command, objects, expected);
        //The expected of minKey is a (freq >=1) but not b (freq == 0)
    }

    @Test
    public void testCall2(){
        AllOoneDataStructure obj = new AllOoneDataStructure();
        String[] command = new String[] { "AllOne","getMaxKey","getMinKey"};
        String[][] objects = new String[][] { {},  {""},          {""}};
        String[] expected = new String[] {null,"",""};
        runTest(command, objects, expected);
    }

    @Test
    public void testCall3(){
        AllOoneDataStructure obj = new AllOoneDataStructure();
        String[] command = new String[] { "AllOne","inc","inc","getMaxKey","getMinKey","inc","getMaxKey","getMinKey"};
        String[][] objects = new String[][] { {""},  {"hello"},{"hello"},{""},{""},{"leet"},{""},{""}};
        String[] expected = new String[] {null, null, null, "hello", "hello", null, "hello", "leet"};
        runTest(command, objects, expected);

    }

}