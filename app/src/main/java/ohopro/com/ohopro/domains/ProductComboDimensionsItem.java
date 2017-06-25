package ohopro.com.ohopro.domains;

public class ProductComboDimensionsItem{
	private int count;
	private String countType;
	private String dimentionType;

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setCountType(String countType){
		this.countType = countType;
	}

	public String getCountType(){
		return countType;
	}

	public void setDimentionType(String dimentionType){
		this.dimentionType = dimentionType;
	}

	public String getDimentionType(){
		return dimentionType;
	}
}
