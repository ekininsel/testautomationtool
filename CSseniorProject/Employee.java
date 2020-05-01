package CSseniorProject;

public abstract class Employee {
    public String name,id,designation;

    public Employee(){
        name = "Employee";
        id = "1";
        designation = "12";
    }

    public void display(){
        System.out.print("Name: "+name+"; Id: "+id+"; Designation: "+designation+" ");
    }
    public void setName(String name){
        this.name = name;
    }
    public void setId(String id){
        this.id = id;
    }
    public void setDesignation(String designation){
        this.designation = designation;
    }
    public String getName(){
        return name;
    }
    public String getDesignation(){
        return designation;
    }
    public String getId(){
        return id;
    }
    public abstract double getSalary();
    public abstract void increaseSalary(double amt) throws Exception;
}