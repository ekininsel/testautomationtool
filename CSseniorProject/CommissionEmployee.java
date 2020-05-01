package CSseniorProject;

public class CommissionEmployee extends Employee{
    public double commission,sale;
    public String name,id,designation;

    public CommissionEmployee(){
        name = "Commission";
        id = "123";
        designation = "Tech";
        commission = 12.5;
        sale = 30.0;
    }

    public double getSalaryNew(double _sale){
        return commission*_sale;
    }

    @Override
    public void increaseSalary(double amt) throws Exception {
        if(commission+amt>.30)
            throw new Exception("Commission cannot be more than 30%");
        commission += amt;
    }

    @Override
    public double getSalary(){
        return commission*sale;
    }

    @Override
    public void display(){
        super.display();
        System.out.println("Commission: "+commission);
    }
}