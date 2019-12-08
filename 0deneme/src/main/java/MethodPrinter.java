package cs401caseClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	static ArrayList<String> methodNamesList = new ArrayList<String>();
	static ArrayList<String> parameters = new ArrayList<String>();
	static ArrayList<Integer> parameterNumbers = new ArrayList<Integer>();
	static String packName;
	static String stringTXTFILE = "/Users/ekininsel1/Documents/workspace/0deneme/cs401File.txt";
	static String intTXTFILE = "/Users/ekininsel1/Documents/workspace/0deneme/cs401intFile.txt";
	static ArrayList<String> inputsTypes = new ArrayList<String>();
	static String parametersForEveryList = null;
	static int max = 0;
	static int typeNum = 0;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		// creates an input stream for the file to be parsed
		System.out.println("Please enter the class directory that will be tested.");
		Scanner inputReader = new Scanner(System.in);
		String classDirectory = inputReader.nextLine();
		FileInputStream in = new FileInputStream(classDirectory);

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

		String objectName = getClassName(classDirectory).toString().toLowerCase();

		FieldSpec strList = FieldSpec
				.builder(ParameterizedTypeName.get(ClassName.get(List.class), TypeName.get(String.class)), "stringList")
				.initializer("new ArrayList<>()").addModifiers(Modifier.STATIC).build();
		FieldSpec integerList = FieldSpec
				.builder(ParameterizedTypeName.get(ClassName.get(List.class), TypeName.get(Integer.class)), "intList")
				.initializer("new ArrayList<>()").addModifiers(Modifier.STATIC).build();

		FieldSpec strFileTXT = FieldSpec.builder(File.class, "stringFile")
				.initializer("new File (\"" + stringTXTFILE + "\")").build();
		FieldSpec integerFileTXT = FieldSpec.builder(File.class, "intFile")
				.initializer("new File (\"" + intTXTFILE + "\")").build();

		FieldSpec scanner = FieldSpec.builder(Scanner.class, "stringScanner").initializer("null").build();
		FieldSpec scnr = FieldSpec.builder(Scanner.class, "intScanner").initializer("null").build();

		String[] intParametersNames = new String[maxParameterNum()];
		String[] strParametersNames = new String[maxParameterNum()];

		FieldSpec[] strParameterObject = new FieldSpec[maxParameterNum()];
		List<FieldSpec> strParameters = null;
		FieldSpec[] intParameterObject = new FieldSpec[maxParameterNum()];
		List<FieldSpec> intParameters = null;

		for (int j = 0; j < maxParameterNum(); j++) {
			intParametersNames[j] = "num" + j;
			strParametersNames[j] = "str" + j;

			strParameterObject[j] = FieldSpec.builder(String.class, strParametersNames[j]).initializer("null").build();
			intParameterObject[j] = FieldSpec.builder(Integer.class, intParametersNames[j]).initializer("null").build();
			strParameters = Arrays.asList(strParameterObject);
			intParameters = Arrays.asList(intParameterObject);
		}

		CodeBlock strListRegister = CodeBlock.builder().beginControlFlow("while(stringScanner.hasNext())")
				.addStatement("stringList.add(stringScanner.next().toString())").endControlFlow().build();
		CodeBlock intListRegister = CodeBlock.builder().beginControlFlow("while(intScanner.hasNext())")
				.addStatement("intList.add(intScanner.nextInt())").endControlFlow().build();

		MethodSpec initMethod = MethodSpec.methodBuilder("init").addAnnotation(Before.class)
				.addException(FileNotFoundException.class).addModifiers(Modifier.PUBLIC)
				.addStatement("stringScanner = new Scanner(stringFile)")
				.addStatement("intScanner = new Scanner(intFile)").addCode(strListRegister)
				.addStatement("stringScanner.close()").addCode(intListRegister).addStatement("intScanner.close()")
				.build();

		FieldSpec object = FieldSpec.builder(classMaker(classDirectory, packName), objectName)
				.addModifiers(Modifier.PRIVATE).initializer("new $T()", classMaker(classDirectory, packName)).build();

		MethodSpec[] testMethods = new MethodSpec[methodNamesList.size()];
		List<MethodSpec> testMethodList = null;
		List<CodeBlock> fors = null;

		int i = 0;
		while (i < methodNamesList.size()) {
			parameterTypeCounter(parameterNumbers.get(i));
			parametersForEveryList = parameters.get(i);

			CodeBlock assertCode = CodeBlock.builder()
					.addStatement("assertEquals(" + objectName + "." + methodNamesList.get(i) + "("
							+ parameterInTest(parameterNumbers.get(i), intParametersNames, strParametersNames,
									parametersForEveryList)
							+ ") , " + objectName + "." + methodNamesList.get(i) + "("
							+ parameterInTest(parameterNumbers.get(i), intParametersNames, strParametersNames,
									parametersForEveryList)
							+ "))")
					.build();

			char[] letters = "abcdefghijklmnopqrstuvwxyz".toCharArray();

			CodeBlock[] intForLoop = loopCreator(letters, parameterNumbers.get(i), intParametersNames,
					strParametersNames, parameters, assertCode, parameterInTest(parameterNumbers.get(i),
							intParametersNames, strParametersNames, parametersForEveryList),
					returnInputType());
			fors = Arrays.asList(intForLoop);

			String testName = methodNamesList.get(i).substring(0, 1).toUpperCase()
					+ methodNamesList.get(i).substring(1);

			testMethods[i] = MethodSpec.methodBuilder("test" + testName).addAnnotation(Test.class)
					.addModifiers(Modifier.PUBLIC, Modifier.FINAL).addCode(fors.get(fors.size() - 1)).build();
			testMethodList = Arrays.asList(testMethods);

			i++;
		}

		TypeSpec generatedTestClass = TypeSpec.classBuilder(getClassName(classDirectory) + "Test").addField(scanner)
				.addField(scnr).addField(strFileTXT).addField(integerFileTXT).addField(integerList).addField(strList)
				.addFields(strParameters).addFields(intParameters).addField(object).addMethod(initMethod)
				.addMethods(testMethodList).addModifiers(Modifier.PUBLIC).build();

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

	private static String parameterInTest(Integer parameterNumber, String[] intparametersNames,
			String[] strparametersNames, String parametersForEveryList) {
		String result = "";
		// System.out.println(parametersForEveryList);

		int i = 0;
		while (i < parameterNumber) {
			if (parametersForEveryList.startsWith("String")) {
				if (parametersForEveryList.contains("String")) {
					if (i == 0)
						result = strparametersNames[i];
					else
						result += "," + strparametersNames[i];
					i++;
				}
				if (parametersForEveryList.contains("ınt")) {
					if (i == 0)
						result = intparametersNames[i];
					else
						result += "," + intparametersNames[i];
					i++;
				}
			} else if (parametersForEveryList.startsWith("ınt")) {
				if (parametersForEveryList.contains("ınt")) {
					if (i == 0)
						result = intparametersNames[i];
					else
						result += "," + intparametersNames[i];
					i++;
				}
				if (parametersForEveryList.contains("String")) {
					if (i == 0)
						result = strparametersNames[i];
					else
						result += "," + strparametersNames[i];
					i++;
				}
			}
		}
		return result;
	}

	public static CodeBlock[] loopCreator(char[] letters, Integer parameterNumber, String[] strParameter,
			String[] intParameter, ArrayList<String> parameters, CodeBlock assertCode, String result,
			String inputType) {

		CodeBlock[] codes = new CodeBlock[typeNum];
		List<CodeBlock> insides = null;
		String[] size = inputType.split("--");
		int resultSize = 0;
		for (int k = 0; k < result.length(); k++) {
			if (result.charAt(k) == ',')
				resultSize++;
		}

		String sizeStr = "";
		String strSize = "";
		String intSize = "";

		for (int i = 0; i < typeNum; i++) {
			if (resultSize == 0) {
				strSize = "<stringList.size();";
				intSize = "<intList.size();";
			} else {
				strSize = "<stringList.size() - " + resultSize + "; ";
				intSize = "<intList.size() - " + resultSize + "; ";
			}

			List inputSize = (Arrays.asList(size));
			CodeBlock[] inside = loopInside(parameterNumber, result, letters[i]);
			insides = Arrays.asList(inside);
			if (inputSize.get(i).toString().contains("String")) {
				sizeStr = strSize;
			}
			if (inputSize.get(i).toString().contains("ınt")) {
				sizeStr = intSize;
			}
			codes[i] = CodeBlock.builder()
					.beginControlFlow("for(int " + letters[i] + " = 0; " + letters[i] + sizeStr + letters[i] + "++)")
					.add(insides.toString().replaceAll("(^\\[|\\]$)", "").replace(",", "").replace(" ", ""))
					.add(assertCode).endControlFlow().build();
		}
		return codes;
	}

	public static CodeBlock[] loopInside(int parameterNumber, String result, char letters) {
		CodeBlock[] inside = new CodeBlock[parameterNumber];
		List<String> insides = Arrays.asList(result.split(","));

		String listGet = "";

		for (int i = 0; i < parameterNumber; i++) {

			String plus = "+" + insides.get(i).charAt(3);
			if (i == 0)
				plus = "";
			String strListGet = " = stringList.get(" + letters + plus + ")";
			String intListGet = " = intList.get(" + letters + plus + ")";
			if (insides.get(i).contains("num")) {
				listGet = intListGet;
			}
			if (insides.get(i).contains("str")) {
				listGet = strListGet;
			}

			inside[i] = CodeBlock.builder().addStatement(insides.get(i) + listGet).build();

		}
		return inside;
	}

	public static int parameterTypeCounter(Integer parameterNumber) {
		for (int i = 0; i < parameterNumber; i++) {
			if (parameters.get(i).contains("ınt")) {
				typeNum++;
				break;
			}
			if (parameters.get(i).contains("String")) {
				typeNum++;
				break;
			}
		}
		return typeNum;
	}

	// ikiden fazla str, int, str şekilde parametre varsa çalışmaz
	public static String returnInputType() {
		String result = "";
		for (int i = 0; i < parameters.size(); i++) {
			if (parameters.get(i).contains("String")) {
				inputsTypes.add(i, "-String-");
			}
			if (parameters.get(i).contains("ınt")) {
				inputsTypes.add(i, "-ınt-");
			}
			if (parameters.get(i).startsWith("String") && parameters.get(i).contains("ınt")) {
				inputsTypes.add(i, "-String, ınt-");
			}
			if (parameters.get(i).startsWith("ınt") && parameters.get(i).contains("String")) {
				inputsTypes.add(i, "-ınt, String-");
			}
			result += inputsTypes.get(i).toString();
		}
		return result;
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

			methodNamesList.add(n.getNameAsString());
			parameters.add(n.getParameters().toString().replaceAll("(^\\[|\\]$)", ""));
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

	public static int maxParameterNum() {
		for (int i = 0; i < parameterNumbers.size(); i++) {
			if (parameterNumbers.get(i) > max)
				max = parameterNumbers.get(i);
		}
		return max;
	}
}