package com.github.fbascheper.axis2modules.soaplogger;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.description.AxisDescription;
import org.apache.axis2.description.AxisModule;
import org.apache.axis2.modules.Module;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;

/**
 * Axis2 module for logging of SOAP messages.
 * <p>
 * The Logger used can be configured with 
 * <ul>
 * <li>The 'logger' parameter in axis2.xml</li>
 * <li>The 'logger' parameter in module.xml</li>
 * </ul>
 * </p>
 * 
 * @see SOAPLoggingHandler
 * @author Erik-Berndt Scheper
 */
public class SOAPLoggingModule implements Module {

	/**
	 * Name of this module in module.xml
	 */
	public static final String NAME = "SOAPLoggingModule";

	// initialize the module
	public void init(ConfigurationContext configContext, AxisModule module) throws AxisFault {
	}

	public void engageNotify(AxisDescription axisDescription) throws AxisFault {
	}

	// shutdown the module
	public void shutdown(ConfigurationContext configContext) throws AxisFault {
	}

	/**
	 * Neethi policy namespaces.
	 * 
	 * @return an array of string
	 */
	public String[] getPolicyNamespaces() {
		return null;
	}

	public void applyPolicy(Policy policy, AxisDescription axisDescription) throws AxisFault {
	}

	public boolean canSupportAssertion(Assertion assertion) {
		return true;
	}

}
