package data;

import java.util.Arrays;
import java.util.List;

public class EmployeeDatabase {
    public static List<Employee> getAllEmployees () {
        Project p1 = new Project("001", "Alpha", "abcd");
        Project p2 = new Project("002", "Beta", "abce");
        Project p3 = new Project("003", "Gamma", "abcf");
        Project p4 = new Project("004", "Zeta", "abcg");
        Project p5 = new Project("005", "Terra", "abch");

        Employee e1 = new Employee("1", "e1", "abcd", Arrays.asList(p1, p2), 20000.00, "Male");
        Employee e2 = new Employee("2", "e2", "abcd", Arrays.asList(p3, p4), 20000.00, "Male");
        Employee e3 = new Employee("3", "e3", "abcd", Arrays.asList(p5, p4), 20000.00, "Male");
        Employee e4 = new Employee("4", "e4", "abcd", Arrays.asList(p1, p2, p5), 20000.00, "Male");
        Employee e5 = new Employee("5", "e5", "abcd", Arrays.asList(p1, p2, p3), 20000.00, "Male");
        Employee e6 = new Employee("6", "e6", "abcd", Arrays.asList(p2,p4), 20000.00, "Male");
        Employee e7 = new Employee("7", "e7", "abcd", Arrays.asList(p1, p5), 20000.00, "Male");

        return Arrays.asList(e1, e2, e3, e4, e5, e6, e7);
    }
}
