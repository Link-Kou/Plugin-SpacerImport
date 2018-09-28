package com;


import com.plugin.spacerimport.SpacerImport;
import com.plugin.spacerimport.SpacerPipe;
import com.plugin.spacerimport.returns.RPCResponse;

import java.util.List;

@SpacerPipe
public interface ServiceDemo {

	@SpacerImport(ServiceClassMethods = "#{ServiceDemoBeCall.Demo}")
	RPCResponse<List<String>> Demo2(Integer d, short s, long l, demo demo);


}
