package ohopro.com.ohopro.domains;

import java.util.List;

public class DashBoardStatesDomain{
	private List<ProductComboDimensionsItem> productComboDimensions;
	private ProductComboChartData productComboChartData;
	private List<ProductDimensionsItem> productDimensions;
	private ProductChartData productChartData;

	public void setProductComboDimensions(List<ProductComboDimensionsItem> productComboDimensions){
		this.productComboDimensions = productComboDimensions;
	}

	public List<ProductComboDimensionsItem> getProductComboDimensions(){
		return productComboDimensions;
	}

	public void setProductComboChartData(ProductComboChartData productComboChartData){
		this.productComboChartData = productComboChartData;
	}

	public ProductComboChartData getProductComboChartData(){
		return productComboChartData;
	}

	public void setProductDimensions(List<ProductDimensionsItem> productDimensions){
		this.productDimensions = productDimensions;
	}

	public List<ProductDimensionsItem> getProductDimensions(){
		return productDimensions;
	}

	public void setProductChartData(ProductChartData productChartData){
		this.productChartData = productChartData;
	}

	public ProductChartData getProductChartData(){
		return productChartData;
	}
}