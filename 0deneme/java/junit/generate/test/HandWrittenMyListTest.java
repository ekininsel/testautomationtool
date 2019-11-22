
// Test class for MyList Class
package junit.generate.test;

import static org.junit.Assert.assertEquals;
 
import java.util.NoSuchElementException;
 
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cs401caseClasses.MyList;
 
public class HandWrittenMyListTest {
 
    private MyList lstTest = new MyList();
 
    @Before
    public void init() {
        lstTest.add("Apple");
        lstTest.add("Orange");
        lstTest.add("Grapes");
    }
 
    @Test
    public void testSize() {
        assertEquals("Checking size of List", 3, lstTest.size());
    }
 
    @Test
    public void testAdd() {
        lstTest.add("Banana");
        assertEquals("Adding 1 more fruit to list", 4, lstTest.size());
    }
 
    @Test
    public void testRemove() {
        lstTest.remove("Orange");
        assertEquals("Removing 1 fruit from list", 2, lstTest.size());
    }
 
    @Test(expected = NoSuchElementException.class)
    public void testRemoveException() {
        lstTest.remove("Kiwi");
        assertEquals("Removing 1 fruit from list", 2, lstTest.size());
    }
 
    @After
    public void destroy() {
        lstTest.removeAll();
    }
}