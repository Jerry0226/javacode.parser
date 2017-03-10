package com.ztesoft.zsmart.zcip.javacode;

/**
 * 变量
 * @author chm
 *
 */
public class FieldDto {
	String field;
	int startLineNumber;
	int endLineNumber;
	
	public FieldDto field(String field) {
		this.field = field;
		return this;
	}
	public String getField() {
		return field;
	}
	
	public FieldDto startLineNumber(int startLineNumber) {
		this.startLineNumber = startLineNumber;
		return this;
	}
	
	public FieldDto endLineNumber(int endLineNumber) {
		this.endLineNumber = endLineNumber;
		return this;
	}
	
	
	@Override
	public String toString() {
		return new StringBuffer(64).append(field).append(" line from: ").append(this.startLineNumber).append(" to: ").append(this.endLineNumber).toString();
	}
	
}
