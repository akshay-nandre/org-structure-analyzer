package org.com;

import java.io.IOException;
import java.util.List;

import org.com.model.Employee;
import org.com.service.OrgAnalyzer;
import org.com.util.CsvUtil;

public class App {

	public static void main(String[] args) {
		 try {
			List<Employee> employees = CsvUtil.readEmployees("employess.csv");
			OrgAnalyzer analyzer = new OrgAnalyzer(employees);
			
			System.out.println("Underpaid Managers: ");
			analyzer.getUnderpaidManagers().forEach(System.out::println);
			
			System.out.println("Overpaid Managers: ");
			analyzer.getOverpaidManagers().forEach(System.out::println);
			
			System.out.println("Deep Reporting Chain: ");
			analyzer.getDeepEmployees().forEach(System.out::println);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		 
 
	}

}
