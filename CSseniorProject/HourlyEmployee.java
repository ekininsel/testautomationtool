package CSseniorProject;

public class HourlyEmployee extends Employee{
    public double hourlyRate;
    public int hourlyWorked;
    public String name,id,designation;

    public HourlyEmployee(){
        name = "Hourly";
        id = "1234";
        designation = "Marketing";
        hourlyRate = 45.6;
        hourlyWorked = 12;
    }

    public double getSalaryNew(int hourlyWorked){
        return hourlyRate*hourlyWorked;
    }
    @Override
    public void increaseSalary(double amt) throws Exception{
        if(hourlyRate+amt>500)
            throw new Exception("Hourly rate can’t be more than 500");
        hourlyRate += amt;
    }
    @Override
    public double getSalary(){
        return hourlyRate*hourlyWorked;
    }
    @Override
    public void display(){
        super.display();
        System.out.println("Rate: "+hourlyRate);
    }

}
