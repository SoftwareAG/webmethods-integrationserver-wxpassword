package com.softwareag.wx.is.password;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
import com.wm.app.b2b.server.User;
import com.wm.app.b2b.server.UserManager;
import com.wm.data.IData;
import com.wm.data.IDataFactory;
import com.wm.lang.ns.NSName;
import com.wm.util.Files;

/**
 * Set password for given user (executed only once)
 */
public class PasswordSetter {

	/**
	 * Prefix of environment variables for setting a specific password
	 */
	public static final String ENVVAR_PASSWORD_PREFIX = "SAG_WXPASSWORD_SET_";

	/**
	 * Set of characters from which the random password is generated 
	 */
	private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static SecureRandom rnd = new SecureRandom();

	File workDir = null;
	String userName = null;
	String password = null;
	boolean isPasswordDefinedByEnvVar = true;

	/**
	 * Initialize password setter
	 * 
	 * @param workDir  Work directory
	 * @param userName User name
	 */
	public PasswordSetter(File workDir, String userName) {
		this.workDir = workDir;
		this.userName = userName;
		password = getPassword();
	}

	/**
	 * Perform the password update, if needed
	 * 
	 * @throws IOException
	 * @throws ServiceException 
	 */
	public void execute() throws IOException, ServiceException {
		if (isActionNeeded()) {
			// Get password from envVar or random generator
			String password = getPassword();

			// New random password is written before it gets applied to avoid loosing it by
			// an IO issue
			if (!isPasswordDefinedByEnvVar) {
				Files.write(fileWithPlainTextPassword(), password.getBytes());
				System.out.println("WxPassword : New password for user '" + userName + "' can be found at '"
						+ fileWithPlainTextPassword().getCanonicalPath() + "'");
			}

			// Perform password update
			IData svcInput = IDataFactory.create(new Object[][] { { "username", userName }, { "password", password } });
			try {
				Service.doInvoke(NSName.create("wm.server.access:userUpdate"), svcInput);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}

		}
	}

	/**
	 * Indicate whether the password needs to be updated. This is done by checking
	 * the existence of a file with the name of the user in the workDir location
	 * 
	 * @param userName Name of user
	 * @return true
	 */
	private boolean isActionNeeded() {

		// Password not defined by envVar and no file with plain-text password exists
		if (!fileWithPlainTextPassword().exists() && !isPasswordDefinedByEnvVar) {
			return true;

			// Password defined by envVar
		} else if (isPasswordDefinedByEnvVar) {
			return true;
		}
		return false;
	}

	/**
	 * Attempt to retrieve password for user
	 * 
	 * @param userName
	 * @return
	 * @throws IOException
	 */
	private String getPassword() {
		String password = System.getenv(ENVVAR_PASSWORD_PREFIX + userName);
		if (password == null) {
			isPasswordDefinedByEnvVar = false;
			password = randomString(30);
		}
		return password;
	}

	/**
	 * File for holding the randomly generated new password in clear text
	 * 
	 * @return
	 */
	private File fileWithPlainTextPassword() {
		return new File(workDir, userName);
	}

	/**
	 * Generate random string (taken from
	 * https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string)
	 * 
	 * @param len
	 * @return
	 */
	private static String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}
}
