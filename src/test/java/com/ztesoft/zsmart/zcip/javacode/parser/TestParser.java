package com.ztesoft.zsmart.zcip.javacode.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ztesoft.zsmart.zcip.javacode.ClassDto;
import com.ztesoft.zsmart.zcip.javacode.MethodDto;

public class TestParser {
	
	private static File file;
	
	
	@BeforeClass
	public static void init() {
		file = new File("./Person.java");
		
		if (file.exists()) {
			return;
		}
		
		try {
			StringBuffer fileContent = new StringBuffer(1024);
			fileContent.append("package com.ztesoft.zsmart.testjunitmaven;").append("\n")
			.append("\n").append("import java.util.Calendar;").append("\n")
			.append("import java.util.Date;").append("\n").append("public class Person {").append("\n").append("	\n")
			.append("	static {\n").append("		System.out.println(111);\n").append("		int i = 1+4;\n").append("		System.out.println(i);\n")
			.append("	}\n").append("	private Date birthDate;\n").append("	\n").append("	/**\n").append("	 * name\n").append("	 */\n").append("	private String name;\n")
			.append("	\n").append("	public String getName() {\n").append("		return name;\n").append("	}\n").append("\n\n")
			.append("	public void setName(String name) {\n").append("		this.name = name;\n").append("	}\n").append("	/**\n").append("	 * test\n")
			.append("	 * @return\n").append("	 * @throws Exception\n").append("	 */\n").append("    public  int getAge() throws Exception {  \n")
			.append("        Calendar cal = Calendar.getInstance();  \n").append("  \n").append("        if (cal.before(birthDate)) {  \n")
			.append("            throw new IllegalArgumentException(  \n").append("                    \"The birthDay is before Now.It's unbelievable!\");  \n")
			.append("        }  \n").append("        int yearNow = cal.get(Calendar.YEAR);  \n").append("        int monthNow = cal.get(Calendar.MONTH);  \n")
			.append("        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);\n").append("        cal.setTime(birthDate);  \n")
			.append("  \n").append("        int yearBirth = cal.get(Calendar.YEAR);  \n").append("        int monthBirth = cal.get(Calendar.MONTH);  \n")
			.append("        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  \n").append("  \n").append("        int age = yearNow - yearBirth;  ")
			.append("  \n").append("        if (monthNow <= monthBirth) {  \n").append("            if (monthNow == monthBirth) {  \n")
			.append("                if (dayOfMonthNow < dayOfMonthBirth) age--; \n")
			.append("            }else{  \n").append("                age--;  \n")
			.append("            }  \n").append("        }  \n").append("        return age;  \n").append("}\n").append("}\n");
			
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			out.write(fileContent.toString());
			out.flush();
			out.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testParserClass() throws IOException {
		CodeParser codeParser = new CodeParser();
		ClassDto classDto = codeParser.parser4Class(file);
		
		System.out.println(classDto);
		
		assertThat(classDto.getClassName()).isEqualTo("Person");
		assertThat(classDto.getMethodList()).extracting("methodName").contains("getName", "setName", "getAge");
		assertThat(classDto.getMethodList()).extracting("methodStartLineNumber").contains(24, 27);
		assertThat(classDto.getMethodList()).extracting("methodEndLineNumber").contains(26, 57);
		
	}
	
	@Test
	public void testParserClassByFilePath() throws IOException {
		CodeParser codeParser = new CodeParser();
		ClassDto classDto = codeParser.parser4Class(file.getAbsolutePath());
		assertThat(classDto.getClassName()).isEqualTo("Person");
		assertThat(classDto.getMethodList()).extracting("methodName").contains("getName", "setName", "getAge");
	}
	
	
	@Test
	public void testParserMethodByLineNumber() throws IOException {
		CodeParser codeParser = new CodeParser();
		MethodDto method = codeParser.parser4Method(file, 50);
		assertThat(method.getMethodName()).isEqualTo("getAge");
	}
	
	
	@Test
	public void testParserMethodByLineNumberTwoSteps() throws IOException {
		CodeParser codeParser = new CodeParser();
		codeParser.parser4Class(file.getAbsolutePath());
		MethodDto method = codeParser.parser4Method(50);
		assertThat(method.getMethodName()).isEqualTo("getAge");
	}
	
	
	@Test(expected = NullPointerException.class)
	public void testParserMethodByLineNumberTwoStepsExecption() throws IOException {
		CodeParser codeParser = new CodeParser();
		MethodDto method = codeParser.parser4Method(50);
		assertThat(method.getMethodName()).isEqualTo("getAge");
	}
	
	
	@Test
	public void testParserMethodListByLineNumber() throws IOException {
		
		List<Integer> lineNumberList = new ArrayList<Integer>();
		lineNumberList.add(25);
		lineNumberList.add(50);
		CodeParser codeParser = new CodeParser();
		assertThat(codeParser.parser4MethodList(file, lineNumberList)).containsKeys(25, 50);
		
		lineNumberList.add(3);
		assertThat(codeParser.parser4MethodList(file, lineNumberList)).containsOnlyKeys(25, 50);
		
		assertThat(codeParser.parser4MethodList(file, lineNumberList).values()).extracting("methodName").contains("setName", "getAge");
		
		
	}
	
	
	@AfterClass
	public static void destory() {
		if (file.exists()) {
			file.delete();
		}
	}
}
