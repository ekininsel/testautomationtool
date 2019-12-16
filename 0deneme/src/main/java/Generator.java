package cs401caseClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.lang.model.element.Modifier;

import org.junit.Assert;
import org.junit.Test;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class Generator {

	@SuppressWarnings("rawtypes")
	static Class findClassName = null;
	static ArrayList<Method> methodsUnordered = new ArrayList<Method>();
	static ArrayList<Method> methodsOrdered = new ArrayList<Method>();
	static ArrayList<String> methodNamesList = new ArrayList<String>();
	static ArrayList<String> parameters = new ArrayList<String>();
	static ArrayList<Integer> parameterNumbers = new ArrayList<Integer>();
	static ArrayList<String> methodsTypes = new ArrayList<String>();
	static String packName;
	static String stringTXTFILE = "/Users/ekininsel1/Documents/workspace/0deneme/cs401File.txt";
	static String intTXTFILE = "/Users/ekininsel1/Documents/workspace/0deneme/cs401intFile.txt";
	static List<String> stringList = new ArrayList<String>();
	static List<Integer> intList = new ArrayList<Integer>();
	static Scanner intScanner = null;
	static Scanner stringScanner = null;
	static File intFile = new File(intTXTFILE);
	static File stringFile = new File(stringTXTFILE);
	static ArrayList<String> resultList = new ArrayList<>();
	static ArrayList<String> returnType = new ArrayList<>();
	static ArrayList<String> parameterList = new ArrayList<>();
	static ArrayList<String> inputsTypes = new ArrayList<String>();

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		// creates an input stream for the file to be parsed
		System.out.println("Please enter the class directory that will be tested.");
		Scanner inputReader = new Scanner(System.in);
		String classDirectory = inputReader.nextLine();
		FileInputStream in = new FileInputStream(classDirectory);

		intListsScanner();
		stringListScanner();
		methodParser(in, classDirectory);
		objectCreator(classMaker(classDirectory, packName));
		classGenerator(classDirectory);

	}

	public static Object[] intListsScanner() throws FileNotFoundException {
		intScanner = new Scanner(intFile);
		while (intScanner.hasNext()) {
			intList.add(intScanner.nextInt());
		}
		intScanner.close();
		return intList.toArray();
	}

	public static Object[] stringListScanner() throws FileNotFoundException {
		stringScanner = new Scanner(stringFile);
		while (stringScanner.hasNext()) {
			stringList.add(stringScanner.next().toString());
		}
		stringScanner.close();
		return stringList.toArray();
	}

	public static Object objectCreator(Class className) throws Exception {
		Object classObject = null;
		classObject = className.newInstance();
		methodsOfClass(classObject);
		methodCall(orderReflection(), classObject);
		return classObject;
	}

	public static ArrayList<Method> methodsOfClass(Object classObject) {
		int i = 0;
		while (i < classObject.getClass().getDeclaredMethods().length) {
			if (!classObject.getClass().getDeclaredMethods()[i].toString().contains("void")) {
				methodsUnordered.add(classObject.getClass().getDeclaredMethods()[i]);
			}
			i++;
		}
		return methodsUnordered;
	}

	public static void methodCall(ArrayList<Method> method, Object object)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		for (int i = 0; i < method.size(); i++) {
			List methodsParameters = Arrays.asList(method.get(i).getParameterTypes());
			returnType.add((method.get(i).getReturnType().toString()));
			zeroParameterMethods(method, object, i);
			oneParameterMethod(method, object, i, methodsParameters);
			twoParameterMethod(method, object, i, methodsParameters);
		}
	}

	public static ArrayList<String> twoParameterMethod(ArrayList<Method> method, Object object, int i,
			List methodsParameters) throws IllegalAccessException, InvocationTargetException {
		ArrayList<String> results = new ArrayList<>();
		ArrayList<String> parameter = new ArrayList<>();
		if (method.get(i).getParameterCount() == 2) {
			if (methodsParameters.toString().startsWith("[int")) {
				if (methodsParameters.toString().contains("int]")) {
					for (int j = 0; j < intList.size(); j++) {
						for (int k = 0; k < intList.size(); k++) {
							results.add(method.get(i).invoke(object, intList.get(j), intList.get(k)).toString());
							parameter.add(intList.get(j).toString() + "-" + intList.get(k).toString());
						}
					}
				}
				if (methodsParameters.toString().contains("String]")) {
					for (int j = 0; j < intList.size(); j++) {
						for (int k = 0; k < stringList.size(); k++) {
							if (returnType.get(i).contains("String")) {
								results.add(
										'"' + method.get(i).invoke(object, intList.get(j), stringList.get(k)).toString()
									+ '"');
							} else {
								results.add(method.get(i).invoke(object, intList.get(j), stringList.get(k)).toString());
							}

							parameter.add(intList.get(j).toString() + "-" + '"' + stringList.get(k).toString() + '"');
						}
					}
				}
			}
			if (methodsParameters.toString().startsWith("[class java.lang.String")) {
				if (methodsParameters.toString().contains("int]")) {
					for (int j = 0; j < stringList.size(); j++) {
						for (int k = 0; k < intList.size(); k++) {
							if (returnType.get(i).contains("String")) {
								results.add(
										'"' + method.get(i).invoke(object, stringList.get(j), intList.get(k)).toString()
									+ '"');
							} else {
								results.add(method.get(i).invoke(object, stringList.get(j), intList.get(k)).toString());
							}

							parameter.add('"' + stringList.get(j).toString() + '"' + "-" + intList.get(k).toString());
						}
					}
				}
				if (methodsParameters.toString().contains("String]")) {
					for (int j = 0; j < stringList.size(); j++) {
						for (int k = 0; k < stringList.size(); k++) {
							if (returnType.get(i).contains("String")) {
								results.add(
									'"' + method.get(i).invoke(object, stringList.get(j), stringList.get(k)).toString()
											+ '"');
							} else {
								results.add(
										method.get(i).invoke(object, stringList.get(j), stringList.get(k)).toString());
							}
							parameter.add('"' + stringList.get(j).toString() + '"' + "-" + '"'
									+ stringList.get(k).toString() + '"');
						}
					}
				}
			}
			resultList.add(results.toString());
			parameterList.add(parameter.toString());
		}
		return results;
	}

	public static ArrayList<String> oneParameterMethod(ArrayList<Method> method, Object object, int i,
			List methodsParameters) throws IllegalAccessException, InvocationTargetException {
		ArrayList<String> results = new ArrayList<>();
		ArrayList<String> parameter = new ArrayList<>();
		if (method.get(i).getParameterCount() == 1) {
			if (methodsParameters.toString().contains("int")) {
				for (int j = 0; j < intList.size(); j++) {
					results.add(method.get(i).invoke(object, intList.get(j)).toString());
					parameter.add(intList.get(j).toString());
				}
			}
			if (methodsParameters.toString().contains("String")) {
				for (int j = 0; j < stringList.size(); j++) {
					if (returnType.get(i).contains("String")) {
						results.add('"' + method.get(i).invoke(object, stringList.get(j)).toString() + '"');
					} else {
						results.add(method.get(i).invoke(object, stringList.get(j)).toString());
					}
					parameter.add('"' + stringList.get(j).toString() + '"');
				}
			}
			resultList.add(results.toString());
			parameterList.add(parameter.toString());
		}
		return results;
	}

	public static ArrayList<String> zeroParameterMethods(ArrayList<Method> method, Object object, int i)
			throws IllegalAccessException, InvocationTargetException {
		ArrayList<String> results = new ArrayList<>();
		ArrayList<String> parameter = new ArrayList<>();
		if (method.get(i).getParameterCount() == 0) {
			results.add(method.get(i).invoke(object).toString());
			resultList.add(results.toString());
			parameter.add("/");
			parameterList.add(parameter.toString());
		}
		return results;
	}

	// parsing the classes methods with javaparser
	private static void methodParser(FileInputStream in, String classDirectory) throws Exception {
		SourceRoot sourceRoot = new SourceRoot(
				CodeGenerationUtils.mavenModuleRoot(Generator.class).resolve("src/main/java"));
		CompilationUnit cu;
		try {
			// parse the file
			cu = sourceRoot.parse("", classDirectory);
		} finally {
			in.close();
		}
		new MethodVisitor().visit(cu, null);

	}

	public static ArrayList<Method> orderReflection() {
		for (int i = 0; i < methodNamesList.size(); i++) {
			for (int j = 0; j < methodNamesList.size(); j++) {
				if (methodNamesList.get(i).equals(methodsUnordered.get(j).getName())) {
					methodsOrdered.add(methodsUnordered.get(j));
				}
			}
		}
		return methodsOrdered;
	}

	// generating the Testclasses with javapoet
	@SuppressWarnings({ "unchecked" })
	private static void classGenerator(String classDirectory) throws Exception {
		// visit the methods names

		String objectName = getClassName(classDirectory).toString().toLowerCase();

		FieldSpec object = FieldSpec.builder(classMaker(classDirectory, packName), objectName)
				.addModifiers(Modifier.PRIVATE).initializer("new $T()", classMaker(classDirectory, packName)).build();

		MethodSpec[] testMethods = new MethodSpec[methodNamesList.size()];
		List<MethodSpec> testMethodList = null;

		List<CodeBlock> assertList = null;
		int i = 0;
		while (i < methodNamesList.size()) {
			String[] s = resultList.get(i).split(", ");
			String[] p = parameterList.get(i).split(",");

			int assertSize = Arrays.asList(s).size();
			CodeBlock assertCode[] = new CodeBlock[assertSize];

			int j = 0;
			while (j < assertSize) {
				String result = Arrays.asList(s).get(j);
				String input = Arrays.asList(p).get(j);
				if (methodsTypes.get(i).equals("ınt") || methodsTypes.get(i).equals("String")
						|| methodsTypes.get(i).equals("Boolean")) {

					assertCode[j] = CodeBlock.builder()
							.addStatement("assertEquals(" + result.replaceAll("(^\\[|\\]$)", "") + ", " + objectName
									+ "." + methodNamesList.get(i) + "("
									+ input.replace("-", ", ").replaceAll("(^\\[|\\]$)", "").replace("/", "") + "))")
							.build();

				} else {
					assertCode[j] = CodeBlock.builder()
							.addStatement("assertNotNull(" + objectName + "." + methodNamesList.get(i) + "("
									+ input.replace("-", ", ").replaceAll("(^\\[|\\]$)", "") + "))")
							.build();
				}
				j++;
			}

			assertList = Arrays.asList(assertCode);

			String testName = methodNamesList.get(i).substring(0, 1).toUpperCase()
					+ methodNamesList.get(i).substring(1);

			testMethods[i] = MethodSpec.methodBuilder("test" + testName).addAnnotation(Test.class)
					.addModifiers(Modifier.PUBLIC, Modifier.FINAL)
					.addCode(assertList.toString().replaceAll("(^\\[|\\]$)", "").replaceAll("\n,", "\n")).build();
			testMethodList = Arrays.asList(testMethods);
			i++;
		}

		TypeSpec generatedTestClass = TypeSpec.classBuilder(getClassName(classDirectory) + "Test").addField(object)
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

	// printing the parsed methods of a class
	@SuppressWarnings("rawtypes")
	private static class MethodVisitor extends VoidVisitorAdapter {
		@Override
		public void visit(MethodDeclaration n, Object arg) {
			// we can use any of the method elements in this method n is the
			// name of the method

			if (n.getTypeAsString() != "void") {
				methodNamesList.add(n.getNameAsString());
				parameters.add(n.getParameters().toString().replaceAll("(^\\[|\\]$)", ""));
				parameterNumbers.add(n.getParameters().size());
				methodsTypes.add(n.getTypeAsString());
			}
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
}