package com.ztesoft.zsmart.zcip.javacode.parser;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.ztesoft.zsmart.zcip.javacode.ClassDto;
import com.ztesoft.zsmart.zcip.javacode.FieldDto;
import com.ztesoft.zsmart.zcip.javacode.MethodDto;

/**
 * 代码解析器
 * @author chm
 *
 */
public class CodeVisitor extends ASTVisitor {
	
	private boolean isMainClass = true;
	
	private ClassDto classDto;
	
	public CompilationUnit classNode;
	
	public CodeVisitor(ClassDto classDto) {
		this.classDto = classDto;
	}
	
	@Override
	public boolean visit(FieldDeclaration node) {
		
		FieldDto dto = new FieldDto();
		dto.field(node.toString())
		.startLineNumber(classNode.getLineNumber(node.getStartPosition()))
		.endLineNumber(classNode.getLineNumber(node.getStartPosition() + node.getLength()));
		classDto.addField(dto);
		
		return true;
	}
	

	@Override
	public boolean visit(MethodDeclaration node) {
		
		MethodDto method = new MethodDto();
		method.methodStartLineNumber(classNode.getLineNumber(node.getStartPosition()))
		.methodEndLineNumber(classNode.getLineNumber(node.getStartPosition() + node.getLength()))
		.methodName(node.getName().getFullyQualifiedName());
		
		
		if (node.getReturnType2() != null) {
			method.methodReturnType(node.getReturnType2().toString());
		}
		
		
		List<?> parameters = node.parameters();
		if (parameters != null) {
			method.methodParameters(parameters.toString());
		}
		
		if (node.getJavadoc() != null) {
			method.javaDoc(node.getJavadoc().toString())
			.javaDocStartLineNumber(classNode.getLineNumber(node.getJavadoc().getStartPosition()))
			.javaDocEndLineNumber(classNode.getLineNumber(node.getJavadoc().getStartPosition() + node.getJavadoc().getLength()));
		}
		
		this.classDto.addMethod(method);
		return true;
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		//该方法可访问类名
//		System.out.println("TypeDeclaration:\t" + node.toString());		
		//存在内部类时，此方法也会被调用，但是主方法会第一次被优先调用
		if (isMainClass) {
			this.classDto.className(node.getName().toString());
		}
		isMainClass = false;
		
		return true;
	}
	
	
	@Override
	public boolean visit(CompilationUnit node){
		
		//该方法可获得整个类文件
		this.classNode = node;
		//针对类求最后一行，如果不-1 会返回-1，估计是文件的最后位置算法是以0开始的计数的
		this.classDto.endLineNumber(node.getLineNumber(node.getStartPosition() + node.getLength() - 1));
		return true;
	}
	
	@Override
	public boolean visit(SingleVariableDeclaration node){
		//本方法在执行visit(MethodDeclaration node)后执行 Method中的参数次；
//		System.out.println("SingleVariableDeclaration： " + node);
		return true;

	}
}
