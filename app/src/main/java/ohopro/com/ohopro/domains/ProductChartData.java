package ohopro.com.ohopro.domains;

import java.util.List;

public class ProductChartData{
	private List<String> data;
	private List<String> labels;

	public void setData(List<String> data){
		this.data = data;
	}

	public List<String> getData(){
		return data;
	}

	public void setLabels(List<String> labels){
		this.labels = labels;
	}

	public List<String> getLabels(){
		return labels;
	}
}