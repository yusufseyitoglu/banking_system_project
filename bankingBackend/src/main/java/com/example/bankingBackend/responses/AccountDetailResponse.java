package com.example.bankingBackend.responses;


public class AccountDetailResponse {
	public class AccountsResponse {
	    private boolean success;
	    private Object data;
	    
		public Object getData() {
			return data;
		}
		public void setData(Object data) {
			this.data = data;
		}
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
	}
	

}
