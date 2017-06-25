package ohopro.com.ohopro.domains;

public class WalletDomain {
	private double balance;
	private String phone;
	private int amountTobeUpdated;
	private String userName;
	private String walletDetailsId;

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setAmountTobeUpdated(int amountTobeUpdated) {
		this.amountTobeUpdated = amountTobeUpdated;
	}

	public int getAmountTobeUpdated() {
		return amountTobeUpdated;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setWalletDetailsId(String walletDetailsId) {
		this.walletDetailsId = walletDetailsId;
	}

	public String getWalletDetailsId() {
		return walletDetailsId;
	}
}
