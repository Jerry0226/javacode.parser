package com.ztesoft.zsmart.zcip.javacode;


/**
 * 方法的详细信息
 * @author chm
 *
 */
public class MethodDto {
	
	/**
	 * 方法注释
	 */
	private String javaDoc;
	
	/**
	 * 方法注释的开始行
	 */
	private int javaDocStartLineNumber;
	
	/**
	 * 方法注释的结束行
	 */
	private int javaDocEndLineNumber;
	
	
	/**
	 * 方法名
	 */
	private String methodName;
	
	
	/**
	 * 方法返回值
	 */
	private String methodReturnType;
	
	

	/**
	 * 参数集合
	 */
	private String methodParameters;
	
	/***
	 * 包含javaDoc的行开始符
	 */
	private int methodStartLineNumber;
	
	/**
	 * 方法结束行
	 */
	private int methodEndLineNumber;
	

	public String getJavaDoc() {
		return javaDoc;
	}


	public int getJavaDocStartLineNumber() {
		return javaDocStartLineNumber;
	}


	public int getJavaDocEndLineNumber() {
		return javaDocEndLineNumber;
	}


	public String getMethodName() {
		return methodName;
	}


	public String getMethodParameters() {
		return methodParameters;
	}


	public int getMethodStartLineNumber() {
		return methodStartLineNumber;
	}


	public int getMethodEndLineNumber() {
		return methodEndLineNumber;
	}

	public void setMethodEndLineNumber(int methodEndLineNumber) {
		this.methodEndLineNumber = methodEndLineNumber;
	}
	
	
	public String getMethodReturnType() {
		return methodReturnType;
	}

	public void setMethodReturnType(String methodReturnType) {
		this.methodReturnType = methodReturnType;
	}
	
	public MethodDto javaDoc(String javaDoc) {
		this.javaDoc = javaDoc;
		return this;
	}
	
	
	public MethodDto javaDocStartLineNumber(int javaDocStartLineNumber) {
		this.javaDocStartLineNumber = javaDocStartLineNumber;
		return this;
	}
	
	public MethodDto javaDocEndLineNumber(int javaDocEndLineNumber) {
		this.javaDocEndLineNumber = javaDocEndLineNumber;
		return this;
	}
	
	public MethodDto methodName(String methodName) {
		this.methodName = methodName;
		return this;
	}
	
	public MethodDto methodParameters(String methodParameters) {
		this.methodParameters = methodParameters;
		return this;
	}
	
	public MethodDto methodStartLineNumber(int methodStartLineNumber) {
		this.methodStartLineNumber = methodStartLineNumber;
		return this;
	}
	
	public MethodDto methodEndLineNumber(int methodEndLineNumber) {
		this.methodEndLineNumber = methodEndLineNumber;
		return this;
	}
	
	public MethodDto methodReturnType(String methodReturnType) {
		this.methodReturnType = methodReturnType;
		return this;
	}
	
	
	@Override
	public String toString() {
		StringBuffer content = new StringBuffer(128);
		content.append("methodName: ").append(methodName)
		.append(" methodParameters: ").append(methodParameters)
		.append(" methodReturnType: ").append(methodReturnType)
		.append(" line from: ").append(this.methodStartLineNumber).append(" to: ").append(this.methodEndLineNumber);
		if (javaDoc != null) {
			content.append(" methodDoc: ").append(javaDoc).append(" line from : ").append(this.javaDocStartLineNumber).append(" to: ").append(this.javaDocEndLineNumber);
		}
		return content.toString();
		
		
	}
	
	
}
