package myProject;

/**
 * This class represents a New Password. A New Password is created by ensuring
 * that the old password matches, so that the new password can rightfully be
 * used.
 * 
 * @author Christian Lisle
 *
 */
public class NewPassword {
	private String oldPassword;
	private String newPassword;

	public NewPassword() {
	}

	/** 
	 * This method is used in the even a user wants to update their password.
	 * @param oldPassword Old password is stored so that it can be used to check accuracy in login methods
	 * @param newPassword
	 */
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
