package com.github.fbascheper.axis2modules.soaplogger;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.ModuleConfiguration;
import org.apache.axis2.description.Parameter;
import org.apache.axis2.handlers.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An Axis2 handler implementationt to log SOAP messages.
 * <p>
 * The Logger used can be configured with 
 * <ul>
 * <li>The 'logger' parameter in axis2.xml</li>
 * <li>The 'logger' parameter in module.xml</li>
 * </ul>
 * </p>
 * 
 * @author Erik-Berndt Scheper
 * 
 */
public class SOAPLoggingHandler extends AbstractHandler {
	/**
	 * The root logger, when the parameterised logger cannot be found.
	 */
	private static final Logger ROOT_LOGGER = LoggerFactory.getLogger(SOAPLoggingHandler.class);

	/**
	 * Parameter name of the logger used.
	 * <p>
	 * This is configured in module.xml or axis2.xml of a webservice or ws-client.
	 * </p>
	 */
	private static final String PARAMETER_NAME_LOGGER = "logger";

	public InvocationResponse invoke(MessageContext msgContext) throws AxisFault {
		Logger logger = findLogger(msgContext);
		if (logger.isDebugEnabled()) {
			logger.debug("INVOKE: SOAP-envelope = " + msgContext.getEnvelope().toString());
		}

		return InvocationResponse.CONTINUE;
	}

	/**
	 * 
	 * @param msgContext
	 */
	public void revoke(MessageContext msgContext) {
		Logger logger = findLogger(msgContext);
		if (logger.isDebugEnabled()) {
			logger.debug("REVOKE: SOAP-envelope = " + msgContext.getEnvelope().toString());
		}

	}

	/**
	 * Find the logger used, based on the messageContext.
	 * <p>
	 * If the parameter 'logger' cannot be found in the module description, the ROOT_LOGGER is used.
	 * </p>
	 * 
	 * @param msgContext
	 *            the messagecontext
	 * @return the logger used.
	 */
	private Logger findLogger(MessageContext msgContext) {
		Logger result = ROOT_LOGGER;
		Parameter param = null;

		ModuleConfiguration moduleConfig = msgContext.getConfigurationContext()
				.getAxisConfiguration().getModuleConfig(SOAPLoggingModule.NAME);
		if (moduleConfig == null) {
			ROOT_LOGGER.error("Cannot find Axis2 moduleConfig for modulename = "
					+ SOAPLoggingModule.NAME);
		} else {
			param = moduleConfig.getParameter(PARAMETER_NAME_LOGGER);
		}

		if (param == null) {
			// not expected; fallback to value in module.xml
			ROOT_LOGGER.warn("Cannot find Axis2 module parameter; parameter name = "
					+ PARAMETER_NAME_LOGGER);
			param = getParameter(PARAMETER_NAME_LOGGER);
		}

		if (param == null) {
			// should not occur; is module.xml ok ??
			ROOT_LOGGER.error("Cannot find Axis2 module parameter; parameter name = "
					+ PARAMETER_NAME_LOGGER);
		} else {
			Object paramValue = param.getValue();
			if (!(paramValue instanceof String)) {
				ROOT_LOGGER.warn("Expected String as value of Axis2 module parameter "
						+ PARAMETER_NAME_LOGGER + "; was: " + paramValue);
			} else {
				result = LoggerFactory.getLogger((String) paramValue);
			}

		}

		return result;
	}

}
