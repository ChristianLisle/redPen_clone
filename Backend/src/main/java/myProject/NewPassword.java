package myProject;

public class NewPassword {
	private String oldPassword;
	private String newPassword;

	public NewPassword()	{}
	
	// This method is used in the even a user wants to update their password.
	public NewPassword(String oldPassword, String newPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}
}
