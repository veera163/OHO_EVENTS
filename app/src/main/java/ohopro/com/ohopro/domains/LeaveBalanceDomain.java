package ohopro.com.ohopro.domains;

public class LeaveBalanceDomain{
	private String phone;
	private String empName;
	private int totalLeaves;
	private int leavesAvailed;
	private int leavesLeft;

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setEmpName(String empName){
		this.empName = empName;
	}

	public String getEmpName(){
		return empName;
	}

	public void setTotalLeaves(int totalLeaves){
		this.totalLeaves = totalLeaves;
	}

	public int getTotalLeaves(){
		return totalLeaves;
	}

	public void setLeavesAvailed(int leavesAvailed){
		this.leavesAvailed = leavesAvailed;
	}

	public int getLeavesAvailed(){
		return leavesAvailed;
	}

	public void setLeavesLeft(int leavesLeft){
		this.leavesLeft = leavesLeft;
	}

	public int getLeavesLeft(){
		return leavesLeft;
	}
}
