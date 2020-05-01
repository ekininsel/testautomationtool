package CSseniorProject;

import org.checkerframework.checker.units.qual.min;

import java.util.Random;

public class SalariedEmployee extends Employee {
    public double monthlySalary;
    public String name,id,designation;


    public SalariedEmployee(){
        name = "Salaried";
        id = "567";
        designation = "Finance";
        monthlySalary = 10435.23;
    }

    @Override
    public double getSalary(){
        return monthlySalary;
    }

    @Override
    public void increaseSalary(double amt) throws Exception{
        if(amt<=0)
            throw new Exception("Increase amount should be a positive number.");
        monthlySalary += amt;
    }

    @Override
    public void display(){
        super.display();
        System.out.println("Salary: "+monthlySalary);
    }
}