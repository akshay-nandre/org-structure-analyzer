package org.com.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.com.model.Employee;

public class CsvUtil {

	public static List<Employee> readEmployees(String filePath) throws IOException{
		List<Employee> list = new ArrayList<>();
		try(BufferedReader br  = new BufferedReader(new FileReader(filePath))){
			String line;
			br.readLine();
			
			while((line = br.readLine()) != null) {
				String[] p = line.split(",");
				list.add(new Employee(p[0], p[1], p[2], Double.parseDouble(p[3]), p.length>4 ? p[4] : null));
				
				
			}
		}
		
		return list;
	}

}
