package ohopro.com.ohopro.domains;

public class WalletRespDOmain{
	private String requestedBy;
	private Object grantedAmount;
	private String requestorPhone;
	private String requestedAmount;
	private Object requestProcessedOn;
	private String cashRequestId;
	private Object processedBy;
	private String status;

	public void setRequestedBy(String requestedBy){
		this.requestedBy = requestedBy;
	}

	public String getRequestedBy(){
		return requestedBy;
	}

	public void setGrantedAmount(Object grantedAmount){
		this.grantedAmount = grantedAmount;
	}

	public Object getGrantedAmount(){
		return grantedAmount;
	}

	public void setRequestorPhone(String requestorPhone){
		this.requestorPhone = requestorPhone;
	}

	public String getRequestorPhone(){
		return requestorPhone;
	}

	public void setRequestedAmount(String requestedAmount){
		this.requestedAmount = requestedAmount;
	}

	public String getRequestedAmount(){
		return requestedAmount;
	}

	public void setRequestProcessedOn(Object requestProcessedOn){
		this.requestProcessedOn = requestProcessedOn;
	}

	public Object getRequestProcessedOn(){
		return requestProcessedOn;
	}

	public void setCashRequestId(String cashRequestId){
		this.cashRequestId = cashRequestId;
	}

	public String getCashRequestId(){
		return cashRequestId;
	}

	public void setProcessedBy(Object processedBy){
		this.processedBy = processedBy;
	}

	public Object getProcessedBy(){
		return processedBy;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}
