package com.softwareag.wx.is.password;

import java.io.File;

import com.wm.app.b2b.server.ServerAPI;
import com.wm.app.b2b.server.ServiceException;

/**
 * Represents the working directory of the WxPassword package
 * <p>
 * If the environment variable {@value #ENVVAR_DIR} is defined, its value will
 * be used. Otherwise a sub-directory called {@value #NAME} underneath the
 * server's configuration directory will be used. Either directory will be
 * created, if it does not exist.
 */
public class WorkDir {

	/**
	 * Environment variable to define custom directory for holding WxPassword files
	 */
	private static final String ENVVAR_DIR = "SAG_WXPASSWORD_DIR";

	/**
	 * Sub-directory underneath server's configuration directory that will be used,
	 * if no environment variable {@value #ENVVAR_DIR }is defined
	 */
	private static final String NAME = "WxPassword";

	private File file = null;

	/**
	 * Determine directory and create it, if needed
	 * 
	 * @throws ServiceException if directory creation fails
	 */
	public WorkDir() throws ServiceException {
		determineDir();
		createIfNeeded();
	}

	/**
	 * Determine directory
	 */
	private void determineDir() {
		String dirFromEnvVar = System.getenv(ENVVAR_DIR);
		if (dirFromEnvVar != null) {
			file = new File(dirFromEnvVar);
		} else {
			file = new File(ServerAPI.getServerConfigDir(), NAME);
		}
	}

	/**
	 * If the working directory does not exist, it will be created
	 * 
	 * @throws ServiceException if directory cannot be created
	 */
	private void createIfNeeded() throws ServiceException {
		System.out.println("Checking if directory '" + file.getAbsolutePath() + "' exists");
		if (!file.exists()) {
			System.out.println("Directory '" + file.getAbsolutePath() + "' not found, will be created");
			boolean successLogDirCreate = file.mkdir();
			if (!successLogDirCreate) {
				throw new ServiceException("Unable to create directory '" + file.getAbsolutePath() + "'");
			}
		}
	}

	/**
	 * Get effective working directory
	 * 
	 * @return
	 */
	public File get() {
		return file;
	}
}
