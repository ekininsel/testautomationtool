package cs401caseClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.lang.model.element.Modifier;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public class MethodPrinter {

	@SuppressWarnings("rawtypes")
	static Class findClassName = null;
	static ArrayList<String> array = new ArrayList<String>();
	static ArrayList<String> typeName = new ArrayList<String>();
	static ArrayList<String> parameters = new ArrayList<String>();
	//new
	static ArrayList<Integer> parameterNumbers = new ArrayList<Integer>();
	
	
	
	static String packName;
	static String fileTXT = null;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		// creates an input stream for the file to be parsed
		System.out.println("Please enter the class directory that will be tested.");
		Scanner inputReader = new Scanner(System.in);
		String classDirectory = inputReader.nextLine();
		FileInputStream in = new FileInputStream(classDirectory);
		System.out.println("Please enter the txt file that will be used.");
		Scanner fileReader = new Scanner(System.in);
		fileTXT = fileReader.nextLine();

		methodParser(in, classDirectory);
	}

	// parsing the classes methods with javaparser
	private static void methodParser(FileInputStream in, String classDirectory) throws Exception {
		SourceRoot sourceRoot = new SourceRoot(
				CodeGenerationUtils.mavenModuleRoot(MethodPrinter.class).resolve("src/main/java"));
		CompilationUnit cu;
		try {
			// parse the file
			cu = sourceRoot.parse("", classDirectory);
		} finally {
			in.close();
		}
		classGenerator(classDirectory, cu);
	}

	// generating the Testclasses with javapoet
	@SuppressWarnings({ "unchecked" })
	private static void classGenerator(String classDirectory, CompilationUnit cu) throws Exception {
		// visit the methods names
		new MethodVisitor().visit(cu, null);
		// testCreator();
		String objectName = getClassName(classDirectory).toString().toLowerCase();

		FieldSpec file = FieldSpec.builder(File.class, "file").initializer("new File (\"" + fileTXT + "\")").build();
		FieldSpec scanner = FieldSpec.builder(Scanner.class, "scanner").initializer("null").build();
		FieldSpec fileList = FieldSpec
				.builder(ParameterizedTypeName.get(ClassName.get(ArrayList.class), TypeName.get(Object.class)),
						"fileList")
				.initializer("new ArrayList<>()").build();

		CodeBlock listRegister = CodeBlock.builder().beginControlFlow("while(scanner.hasNextLine())")
				.addStatement("fileList.add(scanner.hasNext())").endControlFlow().build();

		MethodSpec initMethod = MethodSpec.methodBuilder("init").addAnnotation(Before.class)
				.addException(Exception.class).addModifiers(Modifier.PUBLIC)
				.addStatement("scanner = new Scanner(file)")
				// .addCode(listRegister)
				.build();

		FieldSpec object = FieldSpec.builder(classMaker(classDirectory, packName), objectName)
				.addModifiers(Modifier.PRIVATE).initializer("new $T()", classMaker(classDirectory, packName)).build();

		MethodSpec[] testMet = new MethodSpec[array.size()];
		List<MethodSpec> list = null;
		List<String> parameterList = null;
		//new
		List<String> stringList = new ArrayList<>();
		stringList.add("ekin");
		stringList.add("ekin1");
		stringList.add("ekin2");
		stringList.add("ekin3");
		
		List<Integer> intList = new ArrayList<>();
		intList.add(1);
		intList.add(2);
		intList.add(3);
		intList.add(4);
		intList.add(5);
		intList.add(6);
		
		int i = 0;
		while (i < array.size()) {
			parameterList = Arrays.asList(parameters.get(i));
			CodeBlock code = CodeBlock.builder()
					// .addStatement(typeName.get(i) + " actual = " +
					// getClassName(classDirectory).toString().toLowerCase()
					// + "." + array.get(i) + "(" +")")
				//new
					.addStatement(
							"assertEquals(7, " + objectName + "." + array.get(i) + "(" + parameterNumbers.get(i) + ")")
					.build();
			
			String testName = array.get(i).substring(0, 1).toUpperCase() + array.get(i).substring(1);

			testMet[i] = MethodSpec.methodBuilder("test" + testName).addAnnotation(Test.class)
					.addModifiers(Modifier.PUBLIC, Modifier.FINAL).addCode(code).build();
			list = Arrays.asList(testMet);

			// System.out.println(methodInside(testMet[i], array.get(i)));
			i++;
		}

		TypeSpec generatedTestClass = TypeSpec.classBuilder(getClassName(classDirectory) + "Test").addField(object)
				.addField(file).addField(scanner).addField(fileList).addMethod(initMethod).addMethods(list)
				.addModifiers(Modifier.PUBLIC)
				.build();

		JavaFile javaFile = JavaFile.builder("junit.generate.test", generatedTestClass)
				.addFileComment("AUTO_GENERATED BY TestGenerationTool \n")
				.addFileComment("Test class for " + getClassName(classDirectory) + " Class")
				.addStaticImport(Assert.class, "*").build();
		try {
			javaFile.writeTo(Paths.get("./src/test/java"));// root maven
															// source
		} catch (IOException ex) {
			System.out.println("An exception ...! " + ex.getMessage());
		}
	}

	public static TypeName getType(MethodSpec method) {
		TypeName type = method.returnType;
		return type;
	}

	// printing the parsed methods of a class
	@SuppressWarnings("rawtypes")
	private static class MethodVisitor extends VoidVisitorAdapter {
		@Override
		public void visit(MethodDeclaration n, Object arg) {
			// we can use any of the method elements in this method n is the
			// name of the method
			// System.out.print(n.getTypeAsString() + " " + n.getName() + "(" +
			// n.getParameters() + ")");

			// parameters.addAll(n.getParameters());
			array.add(n.getNameAsString());
			parameters.add(n.getParameters().toString().replaceAll("(^\\[|\\]$)", ""));
			//new
			parameterNumbers.add(n.getParameters().size());
		}
	}

	public static String getClassName(String classDirectory) {
		int slashIndex = substringMaker(classDirectory, '/');
		int dotIndex = substringMaker(classDirectory, '.');
		String className = classDirectory.substring(slashIndex + 1, dotIndex);
		return className;
	}

	public static int substringMaker(String classDirectory, Character c) {
		int index = -1;
		for (int i = 0; i < classDirectory.length(); i++) {
			if (classDirectory.charAt(i) == c) {
				index = i;
			}
		}
		return index;
	}

	public static int startIndexFinder(String classDirectory, Character c) {
		int index = -1;
		int otherIndex = -1;
		for (int i = 0; i < classDirectory.length(); i++) {
			if (classDirectory.charAt(i) == c) {
				otherIndex = index;
				index = i;
			}
		}
		return otherIndex;
	}

	@SuppressWarnings("rawtypes")
	public static Class classMaker(String classDirectory, String packName) throws ClassNotFoundException {
		findClassName = Class.forName(getPackageName(classDirectory) + "." + getClassName(classDirectory));
		return findClassName;
	}

	public static String getPackageName(String classDirectory) {
		int slashStart = startIndexFinder(classDirectory, '/');
		int slashEnd = substringMaker(classDirectory, '/');
		packName = classDirectory.substring(slashStart + 1, slashEnd);
		return packName;
	}

	public static String testCreator() {
		String method = null;
		for (int i = 0; i < array.size(); i++) {
			method = array.get(i);
		}
		return method;
	}
}
