package org.com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.naming.ldap.ManageReferralControl;

import org.com.model.Employee;

public class OrgAnalyzer {
	private Map<String, Employee> employeeMap = new HashMap<>();
	private Employee emp;
	
	  public OrgAnalyzer(List<Employee> employees) {
	        for (Employee e : employees) employeeMap.put(e.id, e);
	        for (Employee e : employees) {
	            if (e.managerId == null) emp = e;
	            else employeeMap.get(e.managerId).subordinates.add(e);
	        }
	    }
	
    public List<String> getUnderpaidManagers() {
        return employeeMap.values().stream()
                .filter(Employee::isManager)
                .map(manager -> {
                    double avg = manager.subordinates.stream().mapToDouble(e -> e.salary).average().orElse(0.0);
                    double min = avg * 1.2;
                    if (manager.salary < min) {
                        return String.format("- %s (ID: %s) earns %.2f, should earn at least %.2f (short by %.2f)",
                                manager.getFullName(), manager.id, manager.salary, min, min - manager.salary);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    
    public List<String> getOverpaidManagers() {
        return employeeMap.values().stream()
                .filter(Employee::isManager)
                .map(manager -> {
                    double avg = manager.subordinates.stream().mapToDouble(e -> e.salary).average().orElse(0.0);
                    double max = avg * 1.5;
                    if (manager.salary > max) {
                        return String.format("- %s (ID: %s) earns %.2f, should earn at most %.2f (over by %.2f)",
                                manager.getFullName(), manager.id, manager.salary, max, manager.salary - max);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    public List<String> getDeepEmployees() {
        return findDeepEmployees(emp, 0);
    }
    
    private List<String> findDeepEmployees(Employee emp, int depth) {
        List<String> result = new ArrayList<>();
        for (Employee sub : emp.subordinates) {
            int level = depth + 1;
            if (level > 4) {
                result.add(String.format("- %s (ID: %s) has %d levels between them and CEO (exceeds by %d)",
                        sub.getFullName(), sub.id, level, level - 4));
            }
            result.addAll(findDeepEmployees(sub, level));
        }
        return result;
    }
}


