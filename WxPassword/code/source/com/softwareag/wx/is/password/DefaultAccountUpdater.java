package com.softwareag.wx.is.password;

import java.io.File;
import java.io.IOException;

import com.wm.app.b2b.server.ServiceException;

/**
 * Controls automated password update for default accounts
 */
public class DefaultAccountUpdater {

	/**
	 * List of default accounts to be updated
	 */
	private enum DefaultUser {
		Administrator, Replicator, Developer
	}

	/**
	 * Name of environment variable that enables (if set to "true") the automated
	 * password update for default accounts
	 */
	public static final String ENVVAR_UPDATE_DEFAULT_ACCOUNTS = "SAG_WXPASSWORD_UPDATE_DEFAULT_ACCOUNTS";

	/**
	 * Perform the update, if enabled
	 * 
	 * @throws ServiceException
	 */
	public static void execute() throws ServiceException {
		
		if (isEnabled()) {
			WorkDir workDir = new WorkDir();
			File workDirFile = workDir.get();
	
			for (DefaultUser defaultUser : DefaultUser.values()) {
				String userName = defaultUser.toString();
				PasswordSetter pws = new PasswordSetter(workDirFile, userName);
				try {
					pws.execute();
				} catch (IOException e) {
					throw new ServiceException(e);
				}
			}
		} else {
			System.out.println("WxPassword : Automated update of passwords for default accounts is disabled");
		}
	}

	/**
	 * Check if automated update is enabled
	 * 
	 * @return true if enabled, false otherwise
	 */
	private static boolean isEnabled() {
		String envVarIsEnabled = System.getenv(ENVVAR_UPDATE_DEFAULT_ACCOUNTS);
		return Boolean.valueOf(envVarIsEnabled);
	}
}
