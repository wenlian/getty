package cn.batchfile.getty.parser;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.batchfile.getty.binding.Request;
import cn.batchfile.getty.binding.Response;

public class GroovyParser extends Parser {

	@Override
	public void parse(File file, HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		Binding binding = new Binding();
		binding.setProperty("_request", new Request(request));
		binding.setProperty("_response", new Response(response));
		for (Object key : request.getParameterMap().keySet()) {
			binding.setVariable(key.toString(), request.getParameter(key.toString()));
		}
		
		GroovyShell shell = new GroovyShell(binding);
		shell.evaluate(file);
	}
}
