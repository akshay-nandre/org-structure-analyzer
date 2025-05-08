package org.com.serviceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.com.model.Employee;
import org.com.service.OrgAnalyzer;
import org.junit.Test;

public class OrgAnalyzerTest {
 
	 private List<Employee> getTestEmployees() {
	        return Arrays.asList(
	                new Employee("123", "Joe", "Doe", 60000, null),
	                new Employee("124", "Martin", "Chekov", 45000, "123"),
	                new Employee("125", "Bob", "Ronstad", 47000, "123"),
	                new Employee("300", "Alice", "Hasacat", 50000, "124"),
	                new Employee("305", "Brett", "Hardleaf", 34000, "300")
	        );
	    }

	    @Test
	    public void testUnderpaidManagers() {
	        OrgAnalyzer analyzer = new OrgAnalyzer(getTestEmployees());
	        List<String> underpaid = analyzer.getUnderpaidManagers();
	        assertTrue(underpaid.stream().anyMatch(s -> s.contains("Martin Chekov")));
	    }

	    @Test
	    public void testOverpaidManagers() {
	        OrgAnalyzer analyzer = new OrgAnalyzer(getTestEmployees());
	        List<String> overpaid = analyzer.getOverpaidManagers();
	        assertEquals(0, overpaid.size());
	    }

	    @Test
	    public void testDeepEmployees() {
	        OrgAnalyzer analyzer = new OrgAnalyzer(getTestEmployees());
	        List<String> deep = analyzer.getDeepEmployees();
	        assertTrue(deep.isEmpty());
	    }
}
