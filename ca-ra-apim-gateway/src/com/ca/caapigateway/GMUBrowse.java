/*******************************************************************************************************
*	Copyright (c) 2014 CA. All rights reserved.
*
*	This software and all information contained therein is confidential and proprietary and shall
*	not be duplicated, used, disclosed or disseminated in any way except as authorized by the
*	applicable license agreement, without the express written permission of CA. All authorized
*	reproductions must be marked with this language.
*
*	EXCEPT AS SET FORTH IN THE APPLICABLE LICENSE AGREEMENT, TO THE EXTENT PERMITTED BY APPLICABLE
*	LAW, CA PROVIDES THIS SOFTWARE WITHOUT WARRANTY OF ANY KIND, INCLUDING WITHOUT LIMITATION, ANY
*	IMPLIED WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. IN NO EVENT WILL CA
*	BE LIABLE TO THE END USER OR ANY THIRD PARTY FOR ANY LOSS OR DAMAGE, DIRECT OR INDIRECT, FROM
*	THE USE OF THIS SOFTWARE, INCLUDING WITHOUT LIMITATION, LOST PROFITS, BUSINESS INTERRUPTION,
*	GOODWILL, OR LOST DATA, EVEN IF CA IS EXPRESSLY ADVISED OF SUCH LOSS OR DAMAGE.
********************************************************************************************************/

package com.ca.caapigateway;

import com.ca.nolio.rdk.dto.*;
import com.ca.nolio.rdk.dto.OS;
import com.ca.nolio.rdk.dto.exception.*;
import com.ca.nolio.rdk.template.helper.*;
import com.nolio.platform.shared.api.ActionDescriptor;
import com.nolio.platform.shared.api.ActionResult;
import com.nolio.platform.shared.api.Password;
import com.nolio.platform.shared.api.ParameterDescriptor;
import com.nolio.platform.shared.datamodel.Action;
import org.apache.commons.exec.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;
import com.jayway.jsonpath.JsonPath;
import java.io.StringReader;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import javax.xml.xpath.XPathConstants;

/**
 *
 * @author Cyrille Riviere
 */
@ActionDescriptor(
        name = "GMU browse",
		description = "Displays the folders, services, policies, aliases on the Gateway with their related Ids.",
        category="CA.APIM.GatewayMigrationUtility" )
    public class GMUBrowse extends Action {
    private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(GMUBrowse.class);
	private static final String SCAPE_CHAR = "\\";
	private List<InputParam> inputParameters = null;	
	private List<OutputParam> outputParameters = null;
    




	@ParameterDescriptor(
		name = "GMU bin path",
		description = "Location of the binary of the GMU executable",
		out = false,
		in = true,
		nullable = false, // parameter is required and no default value set
		order = 10,
        defaultValueAsString = ""
    	)

    


            private String gmubinpath1 = "$inputDefaultValue";
    

	@ParameterDescriptor(
		name = "Gateway hostname",
		description = "Host name of the Gateway",
		out = false,
		in = true,
		nullable = false, // parameter is required and no default value set
		order = 20,
        defaultValueAsString = ""
    	)

    




            private String gatewayhostname2;
            

	@ParameterDescriptor(
		name = "Gateway username",
		description = "User name of the Gateway",
		out = false,
		in = true,
		nullable = false, // parameter is required and no default value set
		order = 30,
        defaultValueAsString = ""
    	)

    




            private String gatewayusername3;
            

	@ParameterDescriptor(
		name = "Gateway user password",
		description = "Encoded password of the Gateway user",
		out = false,
		in = true,
		nullable = false, // parameter is required and no default value set
		order = 40,
        defaultValueAsString = ""
    	)

    




            private String gatewayuserpassword4;
            




	@ParameterDescriptor(
		name = "Execution Output",
		description = "This output parameter holds the standard output of the command execution.",
		out = true,
		in = false
	)
	
	
	private String executionoutput1;

	@ParameterDescriptor(
		name = "Error Output",
		description = "This output parameter holds the standard error output of the command execution.",
		out = true,
		in = false
	)
	
	
	private String erroroutput2;

	@ParameterDescriptor(
		name = "Exit Code",
		description = "This output parameter holds the returned exit code of the command execution.",
		out = true,
		in = false
	)
	
	
	private Integer exitcode3;




    // Getters and Setters
        public String getgmubinpath1() {
        return gmubinpath1;
    }
    
    public void setgmubinpath1(String gmubinpath1) {
    	this.gmubinpath1 = gmubinpath1;
    }
	        public String getgatewayhostname2() {
        return gatewayhostname2;
    }
    
    public void setgatewayhostname2(String gatewayhostname2) {
    	this.gatewayhostname2 = gatewayhostname2;
    }
	        public String getgatewayusername3() {
        return gatewayusername3;
    }
    
    public void setgatewayusername3(String gatewayusername3) {
    	this.gatewayusername3 = gatewayusername3;
    }
	        public String getgatewayuserpassword4() {
        return gatewayuserpassword4;
    }
    
    public void setgatewayuserpassword4(String gatewayuserpassword4) {
    	this.gatewayuserpassword4 = gatewayuserpassword4;
    }
	    // Getters and Setters
        public String getexecutionoutput1() {
        return executionoutput1;
    }
    
    public void setexecutionoutput1(String executionoutput1) {
    	this.executionoutput1 = executionoutput1;
    }
	        public String geterroroutput2() {
        return erroroutput2;
    }
    
    public void seterroroutput2(String erroroutput2) {
    	this.erroroutput2 = erroroutput2;
    }
	        public Integer getexitcode3() {
        return exitcode3;
    }
    
    public void setexitcode3(Integer exitcode3) {
    	this.exitcode3 = exitcode3;
    }
	
    
	@Override
	public ActionResult execute() {
		ExecutionResult execResult = null;
		com.ca.nolio.rdk.dto.CLIAction action;
		try {
		    action = getCLIAction();
			final CliCommand cliCommand = CommandLineExecutorHelper.getExecutionCommand(action);
			log.debug("Command to execute  : " + cliCommand.getCommand());
			log.debug("Execution directory : " + cliCommand.getExecDir());

            execResult = CommandLineExecutorHelper.executeCommand(cliCommand);
			
			log.debug("Execution result: " + execResult.getStandardOutput());
		} catch (Exception e) {
            log.error("Action execution failed.", e);
			return new ActionResult(false, e.toString());
		}

        log.info("Populating Output Params");
		populateOutputParameters(execResult);

		return getActionResult(action);
	}	


    private void populateOutputParameters(ExecutionResult execResult) {
        if (execResult == null) {
            return;
        }
        if (execResult.isEmpty()) {
            log.debug("Execution result is empty: skip populate parameters phasis!");
            return;
        }

        String output = null;
                        log.info("Standard Input " + execResult.getStandardOutput());
        log.info("Standard Error " + execResult.getStandardError());
        if (StringUtils.isNotEmpty(execResult.getStandardOutput())) {
            output = execResult.getStandardOutput();
                            try {
                Pattern pattern = Pattern.compile("[\\s\\S]*");
                Matcher matcher = pattern.matcher(output);
                String stringMatch = null;
                if (matcher.find()) {
                    stringMatch = matcher.group();
                } else{
                    log.info("Cound not find a match for " + output + " with [\\s\\S]*");
                }

                executionoutput1 = String.valueOf(stringMatch);
                log.info("Got output " + executionoutput1);
            } catch (PatternSyntaxException e) {
                log.error("Caught exception during populating output parameter: " + "Execution Output" + ". Bad pattern", e);
            } catch (Exception e) {
                log.error("Caught exception during populating output parameter: " + "Execution Output", e);
            }
                }
                                    if (StringUtils.isNotEmpty(execResult.getStandardError())) {
            output = execResult.getStandardError();
                            try {
                Pattern pattern = Pattern.compile("[\\s\\S]*");
                Matcher matcher = pattern.matcher(output);
                String stringMatch = null;
                if (matcher.find()) {
                    stringMatch = matcher.group();
                } else{
                    log.info("Cound not find a match for " + output + " with [\\s\\S]*");
                }

                erroroutput2 = String.valueOf(stringMatch);
                log.info("Got output " + erroroutput2);
            } catch (PatternSyntaxException e) {
                log.error("Caught exception during populating output parameter: " + "Error Output" + ". Bad pattern", e);
            } catch (Exception e) {
                log.error("Caught exception during populating output parameter: " + "Error Output", e);
            }
                }
                        if (execResult.getExitCode() != null) {
                    exitcode3 = execResult.getExitCode();
                }
        
    }
	
	private ActionResult getActionResult(com.ca.nolio.rdk.dto.CLIAction action) {
		if(action == null){
			return new ActionResult(false, "Action is null");
		}
		ActionResult actionResult = new ActionResult(true, action.getSuccessMessage());
		
		StringBuilder errors = new StringBuilder();
			            	                	                    	if( ErrorHelper.testErrorCondition(exitcode3, Operations.valueOf("NOT_EQUALS"), "0")) {
    		                        errors.append( ReplacementsHelper.replaceOutputParameters( ReplacementsHelper.replaceInputParameters( "The GMU command line has failed!" , getInput() ), getOutput() ) ).append("<br/>");
						}
            	    			String errorString = errors.toString();
		if (StringUtils.isNotEmpty(errorString)) {
			actionResult = new ActionResult(false, errorString);
		}

		
		return actionResult;
	}
	

 	private List<InputParam> getInput() {
				inputParameters = new ArrayList<InputParam>();
			InputParam p = null;
					p = new InputParam();
			p.setName("GMU bin path");
			 p.setDescription("Location of the binary of the GMU executable");
             p.setDefaultValue("2147483647");
             p.setPrefix("");
			//p.setValue("/opt/CA/GMU");
			p.setVariableName("gmubinpath");
			p.setType(VarType.String);

									    			 				p.setValue( gmubinpath1 );
			 								
			inputParameters.add(p);		
					p = new InputParam();
			p.setName("Gateway hostname");
			 p.setDescription("Host name of the Gateway");
             p.setDefaultValue("");
             p.setPrefix("");
			//p.setValue("labapigateway.ca.com");
			p.setVariableName("gatewayhostname");
			p.setType(VarType.String);

									    			 				p.setValue( gatewayhostname2 );
			 								
			inputParameters.add(p);		
					p = new InputParam();
			p.setName("Gateway username");
			 p.setDescription("User name of the Gateway");
             p.setDefaultValue("");
             p.setPrefix("");
			//p.setValue("admin");
			p.setVariableName("gatewayusername");
			p.setType(VarType.String);

									    			 				p.setValue( gatewayusername3 );
			 								
			inputParameters.add(p);		
					p = new InputParam();
			p.setName("Gateway user password");
			 p.setDescription("Encoded password of the Gateway user");
             p.setDefaultValue("");
             p.setPrefix("");
			//p.setValue("haeP4TlxV0s.LK4KBraZcpCxSAyNRv6lVQ");
			p.setVariableName("gatewayuserpassword");
			p.setType(VarType.String);

									    			 				p.setValue( gatewayuserpassword4 );
			 								
			inputParameters.add(p);		
				
	 		return inputParameters;
	}	
	
	
	private List<OutputParam> getOutput() {
				outputParameters = new ArrayList<OutputParam>();
			OutputParam p = null;
					p = new OutputParam();
			p.setName("Execution Output");
			            			p.setVariableName("executionoutput");
			p.setType(VarType.String);
			
							p.setFilterType(FilterType.REGEX);
						
									    			 				p.setFilterValue( executionoutput1 );
			 						
			outputParameters.add(p);					
					p = new OutputParam();
			p.setName("Error Output");
			            			p.setVariableName("erroroutput");
			p.setType(VarType.String);
			
							p.setFilterType(FilterType.REGEX);
						
									    			 				p.setFilterValue( erroroutput2 );
			 						
			outputParameters.add(p);					
					p = new OutputParam();
			p.setName("Exit Code");
			            			p.setVariableName("exitcode");
			p.setType(VarType.Integer);
			
							p.setFilterType(FilterType.REGEX);
						
									    			 								if(exitcode3 != null) {
					p.setFilterValue( exitcode3.toString() );
				}
			 						
			outputParameters.add(p);					
					return outputParameters;
	}
		
		


	private List<ErrorCondition> getErrorConditions(){
		List<ErrorCondition> errorCondition = null;
		
		errorCondition = new ArrayList<ErrorCondition>();
		ErrorCondition ec = null;
					ec = new ErrorCondition();
			ec.setMessage("The GMU command line has failed!");		
			ec.setParameter("Exit Code");
			ec.setValue("0");
							ec.setOperation(Operations.NOT_EQUALS);
							
		return errorCondition;
	}


	private List<CliCommand> getCliCommands(){
		List<CliCommand> cliCommands = new ArrayList<CliCommand>();
		
		CliCommand cliCmd = null;
		List<EnvVariable> envList = null;
		EnvVariable env = null;
		Script script = null;
								
					cliCmd = new CliCommand();
											cliCmd.setOsType(OS.WINDOWS);
										
					cliCmd.setCommand("");											
					cliCmd.setExecDir("%TEMP%");									
					
										
					cliCmd.setWaitForProcessToFinish(false);
					cliCmd.setOverwriteOutput(false);
															
											cliCmd.setTimeout(30);
										
					
										
					
					cliCommands.add(cliCmd);
																				
						
					cliCmd = new CliCommand();
											cliCmd.setOsType(OS.LINUX);
										
					cliCmd.setCommand("${GMU bin path}/GatewayMigrationUtility.sh browse -h ${Gateway hostname} -u ${Gateway username} -x ${Gateway user password} --showIds --trustCertificate");											
					cliCmd.setExecDir("/tmp/");									
					
										
					cliCmd.setWaitForProcessToFinish(false);
					cliCmd.setOverwriteOutput(false);
															
											cliCmd.setTimeout(30);
										
					
										
					
					cliCommands.add(cliCmd);
																				
								
		return cliCommands;
	}




	

private com.ca.nolio.rdk.dto.CLIAction getCLIAction() {
	com.ca.nolio.rdk.dto.CLIAction action = new com.ca.nolio.rdk.dto.CLIAction();

	action.setShellType(Shell.DEFAULT_OS);
					
	action.setCliCommands(getCliCommands());			
	action.setInputParamList(getInput());
	action.setName("GMU browse");
	action.setDescription("Displays the folders, services, policies, aliases on the Gateway with their related Ids.");
	action.setCategory("GatewayMigrationUtility");				
	action.setSuccessMessage("Execution succeeded");	
	return action;
}
}
