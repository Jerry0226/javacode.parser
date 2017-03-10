package com.ztesoft.zsmart.zcip.javacode.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import com.google.common.base.Optional;
import com.ztesoft.zsmart.zcip.javacode.ClassDto;
import com.ztesoft.zsmart.zcip.javacode.MethodDto;

/**
 * java 源文件解析器
 * @author chm
 *
 */
public class CodeParser {
	
	private ClassDto classDto;
	
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	public ClassDto parser4Class(String filePath) throws IOException {
		return this.parser4Class(new File(filePath));
	}
	
	public ClassDto parser4Class(File file) throws IOException {
		classDto = new ClassDto();
		String content = readJavaCode(file); //java源文件
	    //创建解析器  
	    ASTParser parsert = ASTParser.newParser(AST.JLS3); 
	    //设定解析器的源代码字符  
	    parsert.setSource(content.toCharArray()); 
	    //使用解析器进行解析并返回AST上下文结果(CompilationUnit为根节点)  
	    CompilationUnit result = (CompilationUnit) parsert.createAST(null); 
		CodeVisitor visitor = new CodeVisitor(classDto);
		result.accept(visitor);
		return classDto;
	}
	
	/**
	 * 重新解析java source 文件
	 * @param file javasource文件地址
	 * @param lineNumber 行数
	 * @return 关联的方法信息
	 * @throws IOException 
	 */
	public MethodDto parser4Method(File file, int lineNumber) throws IOException {
		parser4Class(file);
		return classDto.locationMethodByLineNumber(lineNumber);
		
	}
	
	
	/**
	 * 重新解析java source 文件， 根据传入的行集合返回每行关联的方法信息，无效行不返回
	 * @param file javasource文件地址
	 * @param lineNumber 行数
	 * @return 关联的方法信息
	 * @throws IOException 
	 */
	public Map<Integer, MethodDto> parser4MethodList(File file, List<Integer> lineNumberList) throws IOException {
		parser4Class(file);
		return classDto.locationMethodByLineNumberList(lineNumberList);
		
	}
	
	
	/**
	 * 重新解析java source 文件， 根据传入的行集合返回每行关联的方法信息，无效行不返回
	 * @param file javasource文件地址
	 * @param lineNumber 行数
	 * @return 关联的方法信息
	 * @throws IOException 
	 */
	public Map<Integer, MethodDto> parser4MethodList(String filePath, List<Integer> lineNumberList) throws IOException {
		return parser4MethodList(new File(filePath), lineNumberList);
	}
	
	
	/**
	 * 根据已经解析的java source 文件， 同时根据传入的行集合返回每行关联的方法信息，无效行不返回
	 * @param file javasource文件地址
	 * @param lineNumber 行数
	 * @return 关联的方法信息
	 * @throws IOException 
	 */
	public Map<Integer, MethodDto> parser4MethodList(List<Integer> lineNumberList) throws IOException {
		return this.classDto.locationMethodByLineNumberList(lineNumberList);
	}
	
	
	/**
	 * 根据 已经解析的java source 文件获取传入的行对应的方法，方法不存在抛出异常
	 * @param lineNumber 行数
	 * @return 关联的方法信息
	 * @throws IOException 
	 */
	public MethodDto parser4Method(int lineNumber) throws IOException {
		Optional.of(classDto);
		return classDto.locationMethodByLineNumber(lineNumber);
		
	}
	
	
	
	/**
	 * 重新解析java source 文件
	 * @param file javasource文件地址
	 * @param lineNumber 行数
	 * @return 关联的方法信息
	 * @throws IOException 
	 */
	public MethodDto parser4Method(String filePath, int lineNumber) throws IOException {
		return parser4Method(new File(filePath), lineNumber);
	}
	
	
	private String readJavaCode(File file) throws IOException {
	      byte[] b = new byte[(int) file.length()]; 
	      FileInputStream fis = new FileInputStream(file); 
	      fis.read(b);
	      fis.close();
	      return new String(b, DEFAULT_CHARSET);
	}
	
}
