package com.ztesoft.zsmart.zcip.javacode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * java source 文件解析，内部类暂不单独解析
 * @author chm
 *
 */
public class ClassDto {
	
	private String className;
	
	private int startLineNumber = 1;
	
	private int endLineNumber;
	
	private List<MethodDto> methodList = new ArrayList<MethodDto>();
	
	private List<FieldDto> fieldList = new ArrayList<FieldDto>();

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getStartLineNumber() {
		return startLineNumber;
	}


	public int getEndLineNumber() {
		return endLineNumber;
	}


	public List<MethodDto> getMethodList() {
		return methodList;
	}

	public void setMethodList(List<MethodDto> methodList) {
		this.methodList = methodList;
	}

	public List<FieldDto> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<FieldDto> fieldList) {
		this.fieldList = fieldList;
	}
	
	
	public ClassDto endLineNumber(int endLineNumber) {
		this.endLineNumber = endLineNumber;
		return this;
	}
	
	public ClassDto className(String className) {
		this.setClassName(className);
		return this;
	} 
	
	public ClassDto addMethod(MethodDto methodDto) {
		this.methodList.add(methodDto);
		return this;
	} 
	
	public ClassDto addField(FieldDto fieldDto) {
		this.fieldList.add(fieldDto);
		return this;
	} 
	
	/**
	 * 根据行返回此行所在的方法
	 * @param lineNumber 被定位的行
	 * @return 方法实体
	 * @throws IOException 因为后续存在文件操作，此处暂且抛出一个IO异常吧
	 */
	public MethodDto locationMethodByLineNumber(int lineNumber) throws IOException {
		boolean isContineToMethod = false;
		for (MethodDto method : methodList) {
			if (lineNumber <= method.getMethodEndLineNumber() && lineNumber >= method.getMethodStartLineNumber()) {
				isContineToMethod = true;
				return method;
			}
		}
		if (!isContineToMethod) {
			throw new IOException("lineNumber: " + lineNumber + " not locatoin any method");
		}
		return null;
	}
	
	/**
	 * 根据传入的多行，返回一个map集合，如果行没有关联的方法，那么返回的集合中不包含此行
	 * @param lineNumberList
	 * @return
	 */
    public Map<Integer, MethodDto> locationMethodByLineNumberList(List<Integer> lineNumberList) {
    	
    	Map<Integer, MethodDto> lineListMap = new HashMap<Integer, MethodDto>();
    	for (int lineNumber: lineNumberList) {
    		
    		try {
    			MethodDto obj = locationMethodByLineNumber(lineNumber);
    			lineListMap.put(lineNumber, obj);
    		}
    		catch (IOException e) {
    			System.out.println(e);
			}
    		
    	}
    	return lineListMap;
    }
	
	
	@Override
	public String toString() {
		return new StringBuffer(256).append("className: ").append(className).append(" line from: ").append(this.startLineNumber)
		.append(" to: ").append(this.endLineNumber).append("\nmethodList: \n").append(this.methodList).append("\n")
		.append("fieldList: \n").append(this.fieldList).toString();
	}
	
}
