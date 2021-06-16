package wx.password;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.app.b2b.server.User;
import com.wm.app.b2b.server.UserManager;
import com.wm.app.b2b.server.ServerAPI;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Iterator;
import com.softwareag.wx.is.password.DefaultAccountUpdater;
// --- <<IS-END-IMPORTS>> ---

public final class pub

{
	// ---( internal utility methods )---

	final static pub _instance = new pub();

	static pub _newInstance() { return new pub(); }

	static pub _cast(Object o) { return (pub)o; }

	// ---( server methods )---




	public static final void nonDefaultPasswordsForStandardAccounts (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(nonDefaultPasswordsForStandardAccounts)>> ---
		// @sigtype java 3.5
		DefaultAccountUpdater.execute();
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---

	
	// --- <<IS-END-SHARED>> ---
}

