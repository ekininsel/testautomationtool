package cs402project;

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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

public class Generator {

	@SuppressWarnings("rawtypes")
	static Class findClassName = null;
	static ArrayList<Method> methodsUnordered = new ArrayList<Method>();
	static ArrayList<Method> methodsOrdered = new ArrayList<Method>();
	static ArrayList<String> methodNamesList = new ArrayList<String>();
	static ArrayList<String> parameters = new ArrayList<String>();
	static ArrayList<Integer> parametersNumbersForMethods = new ArrayList<Integer>();
	static ArrayList<String> methodsTypes = new ArrayList<String>();
	static int maxIntObjectNumber = 0;
	static int maxStringObjectNumber = 0;
	static String packName;
	static String stringTXTFILE = "/Users/ekininsel1/Documents/workspace/CS/cs401File.txt";
	static String intTXTFILE = "/Users/ekininsel1/Documents/workspace/CS/cs401intFile.txt";
	static String intintTXTFILE = "/Users/ekininsel1/Documents/workspace/CS/2parameterInt-Int-nist.txt";
	static String intstrTXTFILE = "/Users/ekininsel1/Documents/workspace/CS/2parameterInt-Str-nist.txt";
	static String strstrTXTFILE = "/Users/ekininsel1/Documents/workspace/CS/2parameterStr-Str-nist.txt";
	static List<String> stringList = new ArrayList<String>();
	static List<Integer> intList = new ArrayList<Integer>();
	static List<Integer> intintList = new ArrayList<Integer>();
	static Scanner intScanner = null;
	static Scanner stringScanner = null;
	static Scanner scanner = null;
	static File intFile = new File(intTXTFILE);
	static File stringFile = new File(stringTXTFILE);
	static File file = new File(intintTXTFILE);
	static ArrayList<String> resultList = new ArrayList<String>();
	static ArrayList<String> returnTypeOfMethods = new ArrayList<String>();
	static ArrayList<String> parameterList = new ArrayList<String>();
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
		Object classObject = className.newInstance();
		methodsOfClassUnordered(classObject);
		methodCall(orderReflection(), classObject);
		return classObject;
	}

	public static ArrayList<Method> methodsOfClassUnordered(Object classObject) {
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
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException,
			InstantiationException, NoSuchMethodException, SecurityException {

		for (int i = 0; i < method.size(); i++) {
			List methodsParametersTypes = Arrays.asList(method.get(i).getParameterTypes());
			returnTypeOfMethods.add((method.get(i).getReturnType().toString()));
			parameterMethod(method, object, i, methodsParametersTypes);
		}
	}

	public static ArrayList<String> parameterMethod(ArrayList<Method> method, Object object, int i,
			List methodsParametersTypes)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		ArrayList<String> expectedResults = new ArrayList<String>();
		ArrayList<String> parametersForTests = new ArrayList<String>();

		if (method.get(i).getParameterCount() == 0) {
			expectedResults.add(method.get(i).invoke(object).toString());
			parametersForTests.add("/");
			resultList.add(expectedResults.toString());
			parameterList.add(parametersForTests.toString());
		} else if (method.get(i).getParameterCount() == 1) {
			if (methodsParametersTypes.toString().contains("int")) {
				for (int j = 0; j < intList.size(); j++) {
					expectedResults.add(method.get(i).invoke(object, intList.get(j)).toString());
					parametersForTests.add(intList.get(j).toString());
				}
			}
			if (methodsParametersTypes.toString().contains("String")) {
				for (int j = 0; j < stringList.size(); j++) {
					expectedResults.add(method.get(i).invoke(object, stringList.get(j)).toString());
					parametersForTests.add(stringList.get(j).toString());
				}
			}
			resultList.add(expectedResults.toString());
			parameterList.add(parametersForTests.toString());
		} else {
			// System.out.println(methodsParametersTypes);
			List<String> lis = new ArrayList<String>();
			String[] combines = new String[methodsParametersTypes.size()];
			ArrayList<String> comb = new ArrayList<String>();
			for (int j = 0; j < methodsParametersTypes.size(); j++) {
				// System.out.println(methodsParametersTypes.get(j));
				if (methodsParametersTypes.get(j).toString().contains("int")) {
					lis.add(intList.toString());
				} else if (methodsParametersTypes.get(j).toString().contains("String")) {
					lis.add(stringList.toString());
				}
				// combines = lis.toString().replace("[[", "").replace("]]",
				// "").replace("[", "").split("], ");
				// comb.add(Arrays.asList(combines[j].toString().split(",")).get(j));
				// System.out.println(lis.get(j));
				for (int s = 0; s < Arrays.asList(lis.get(j).toString().split(",")).size(); s++) {
					combines = lis.get(j).toString().replace("[", "").replace("]", "").split(",");
					// System.out.println(combines[s]);
					comb.add(Arrays.asList(combines).get(s).toString());
					// System.out.println(comb);
					// System.out.println(Arrays.asList(lis.get(j).toString().split(",")).size());
				}
				// System.out.println(Arrays.asList(deneme));
				// System.out.println(Arrays.asList(combines));
				// System.out.println(Arrays.asList(comb));
			}

			// System.out.println(Arrays.asList(lis.toString().split("], ")));
			// for (int s = 0; s < lis.get(); s++) {
			// }
			Object[] ob = {};
			ob = (Object[]) comb.toString().replace("[", "").replace("]", "").split(", ");
			// System.out.println(Arrays.asList(ob).toString());
			// expectedResults.add(method.get(i).invoke(object, ob).toString());
			// for (int p = 0; p < ob.length; p++) {
			// System.out.print(Arrays.asList(ob));
			// }
			parametersForTests.add(comb.toString().replace(", ", "-").replace("[", "").replace("]", ""));

			// System.out.println();

			// System.out.println(parametersForTests);
			parameterList.add(parametersForTests.toString());
			resultList.add(expectedResults.toString());

		}
		return expectedResults;

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

	// generating the Testclasses with javapoet
	@SuppressWarnings({ "unchecked", "deprecation", "null" })
	private static void classGenerator(String classDirectory) throws Exception {
		String objectName = getClassName(classDirectory).toString().toLowerCase();

		for (int p = 0; p < parameters.size(); p++) {
			List<String> objects = Arrays.asList(parameters.get(p).toString().split(","));
			for (int f = 0; f < objects.size(); f++) {
				if (objects.get(f).toString().contains("ınt ")) {
					maxIntObjectNumber++;
				}
				if (objects.get(f).toString().contains("String ")) {
					maxStringObjectNumber++;
				}
			}
		}

		FieldSpec[] intObjects = new FieldSpec[maxIntObjectNumber];
		FieldSpec[] strObjects = new FieldSpec[maxStringObjectNumber];
		List<FieldSpec> ints = null;
		List<FieldSpec> strs = null;
		List<String> constructorParameters = new ArrayList<String>();
		for (int t = 0; t < maxIntObjectNumber; t++) {
			intObjects[t] = FieldSpec.builder(int.class, "int" + t, Modifier.PRIVATE, Modifier.FINAL).build();
			constructorParameters.add("int" + t);
		}
		for (int h = 0; h < maxStringObjectNumber; h++) {
			strObjects[h] = FieldSpec.builder(String.class, "str" + h, Modifier.PRIVATE, Modifier.FINAL).build();
			constructorParameters.add("str" + h);
		}
		ints = Arrays.asList(intObjects);
		strs = Arrays.asList(strObjects);

		FieldSpec classObject = FieldSpec.builder(classMaker(classDirectory, packName), objectName)
				.addModifiers(Modifier.PUBLIC).build();

		FieldSpec[] expectedObjects = new FieldSpec[methodNamesList.size()];
		List<FieldSpec> expecteds = null;
		for (int y = 0; y < methodNamesList.size(); y++) {
			if (methodsTypes.get(y).equals("String")) {
				expectedObjects[y] = FieldSpec.builder(String.class, "StringExpectedFor" + methodNamesList.get(y),
						Modifier.PRIVATE, Modifier.FINAL).build();
				constructorParameters.add("StringExpectedFor" + methodNamesList.get(y));
			} else if (methodsTypes.get(y).equals("ınt")) {
				expectedObjects[y] = FieldSpec
						.builder(int.class, "intExpectedFor" + methodNamesList.get(y), Modifier.PRIVATE, Modifier.FINAL)
						.build();
				constructorParameters.add("intExpectedFor" + methodNamesList.get(y));
			} else if (methodsTypes.get(y).equals("Boolean")) {
				expectedObjects[y] = FieldSpec.builder(Boolean.class, "BooleanExpectedFor" + methodNamesList.get(y),
						Modifier.PRIVATE, Modifier.FINAL).build();
				constructorParameters.add("BooleanExpectedFor" + methodNamesList.get(y));
			} else {
				expectedObjects[y] = FieldSpec.builder(Object.class, "ObjectExpectedFor" + methodNamesList.get(y),
						Modifier.PRIVATE, Modifier.FINAL).build();
				constructorParameters.add("ObjectExpectedFor" + methodNamesList.get(y));
			}
		}
		expecteds = Arrays.asList(expectedObjects);

		MethodSpec[] testMethods = new MethodSpec[methodNamesList.size()];
		List<MethodSpec> testMethodList = null;
		MethodSpec[] parameterizedMethods = new MethodSpec[methodNamesList.size()];
		List<MethodSpec> testParameterizedList = null;

		ParameterSpec[] conParameters = new ParameterSpec[constructorParameters.size()];
		List<ParameterSpec> constructors = null;
		CodeBlock[] constructorInside = new CodeBlock[constructorParameters.size()];
		List<CodeBlock> lists = new ArrayList<CodeBlock>();
		for (int m = 0; m < constructorParameters.size(); m++) {
			if (constructorParameters.get(m).contains("int")) {
				conParameters[m] = ParameterSpec.builder(int.class, constructorParameters.get(m)).build();
				constructorInside[m] = CodeBlock.builder()
						.addStatement("this." + conParameters[m].name + " = " + conParameters[m].name).build();
			} else if (constructorParameters.get(m).contains("str")
					|| constructorParameters.get(m).contains("String")) {
				conParameters[m] = ParameterSpec.builder(String.class, constructorParameters.get(m)).build();
				constructorInside[m] = CodeBlock.builder()
						.addStatement("this." + conParameters[m].name + " = " + conParameters[m].name).build();
			} else if (constructorParameters.get(m).contains("Boolean")) {
				conParameters[m] = ParameterSpec.builder(Boolean.class, constructorParameters.get(m)).build();
				constructorInside[m] = CodeBlock.builder()
						.addStatement("this." + conParameters[m].name + " = " + conParameters[m].name).build();
			} else {
				conParameters[m] = ParameterSpec.builder(Object.class, constructorParameters.get(m)).build();
				constructorInside[m] = CodeBlock.builder()
						.addStatement("this." + conParameters[m].name + " = " + conParameters[m].name).build();
			}
		}
		constructors = Arrays.asList(conParameters);
		lists = Arrays.asList(constructorInside);

		MethodSpec constructor = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC)
				.addParameters(constructors)
				.addStatement(lists.toString().replace("[", "").replace("]", "").replace(", ", "")).build();

		List<CodeBlock> assertList = null;
		List<String> parameterizedList = null;

		int i = 0;
		int d = 0;
		int f = 0;
		while (i < methodNamesList.size()) {
			String[] Asserts = new String[Arrays.asList(parameters.get(i).toString().split(", ")).size()];

			for (int e = 0; e < Arrays.asList(parameters.get(i).toString().split(", ")).size(); e++) {
				if (Arrays.asList(parameters.get(i).toString().split(",")).get(e).contains("ınt")) {
					Asserts[e] = "int" + d;
					d++;
				} else if (Arrays.asList(parameters.get(i).toString().split(", ")).get(e).contains("String")) {
					Asserts[e] = "str" + f;
					f++;
				} else {
					Asserts[e] = "/";
				}
			}
			List<String> tt = Arrays.asList(Asserts);

			CodeBlock asserting = CodeBlock.builder()
					.addStatement(
							"assertEquals(" + expecteds.get(i).name + ", " + objectName + "." + methodNamesList.get(i)
									+ "(" + tt.toString().replaceAll("(^\\[|\\]$)", "").replace("/", "") + "))")
					.build();
			String[] expectedsArray = resultList.get(i).split(", ");
			String[] parametersArray = parameterList.get(i).split(",");

			int assertSize = Arrays.asList(expectedsArray).size();
			String[] parameterizedString = new String[assertSize];
			String testName = methodNamesList.get(i).substring(0, 1).toUpperCase()
					+ methodNamesList.get(i).substring(1);

			int j = 0;
			while (j < assertSize) {
				String expected = Arrays.asList(expectedsArray).get(j);
				String input = Arrays.asList(parametersArray).get(j);

				if (methodsTypes.get(i).equals("ınt") || methodsTypes.get(i).equals("String")
						|| methodsTypes.get(i).equals("Boolean")) {

					parameterizedString[j] = "{"
							+ input.replaceAll("(^\\[|\\]$)", "").replace("/", "null").replace("-", ",") + ","
							+ expected.replaceAll("(^\\[|\\]$)", "") + "} ";
				} else {
					parameterizedString[j] = "{"
							+ input.replaceAll("(^\\[|\\]$)", "").replace("/", "null").replace("-", ",") + "} ";
				}
				j++;
			}

			assertList = Arrays.asList(asserting);
			parameterizedList = Arrays.asList(parameterizedString);

			parameterizedMethods[i] = MethodSpec.methodBuilder("dataSetFor" + testName)
					.addAnnotation(Parameterized.Parameters.class)
					.returns(List.class)
					.addStatement("return Arrays.asList( new Object[][] { "
							+ parameterizedList.toString().replaceAll("(^\\[|\\]$)", "") + " })")
					.addModifiers(Modifier.PUBLIC, Modifier.STATIC).build();
			testParameterizedList = Arrays.asList(parameterizedMethods);

			testMethods[i] = MethodSpec.methodBuilder("test" + testName).addAnnotation(Test.class)
					.addModifiers(Modifier.PUBLIC, Modifier.FINAL)
					.addCode(assertList.toString().replaceAll("(^\\[|\\]$)", "").replaceAll("\n,", "\n")).build();
			testMethodList = Arrays.asList(testMethods);

			i++;
		}
		MethodSpec beforeTests = MethodSpec.methodBuilder("initialize").addAnnotation(Before.class)
				.addModifiers(Modifier.PUBLIC)
				.addStatement(objectName + " = new " + getClassName(classDirectory) + "()").build();

		MethodSpec data = MethodSpec.methodBuilder("dataForParameterized").addAnnotation(Parameterized.Parameters.class)
				.returns(List.class).addStatement("return Arrays.asList( new Object[][] {  })")
				.addModifiers(Modifier.PUBLIC, Modifier.STATIC).build();

		AnnotationSpec run = AnnotationSpec.builder(RunWith.class).addMember("", "Parameterized.class").build();

		TypeSpec generatedTestClass = TypeSpec.classBuilder(getClassName(classDirectory) + "Test").addFields(ints)
				.addFields(strs).addFields(expecteds).addField(classObject).addMethod(beforeTests)
				.addMethod(constructor).addMethod(data)
				// .addMethods(testParameterizedList)
				.addMethods(testMethodList).addAnnotation(run).addModifiers(Modifier.PUBLIC).build();

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
			if (n.getTypeAsString() != "void") {
				methodNamesList.add(n.getNameAsString());
				parameters.add(n.getParameters().toString().replaceAll("(^\\[|\\]$)", ""));
				parametersNumbersForMethods.add(n.getParameters().size());
				methodsTypes.add(n.getTypeAsString());
			}
		}
	}

	public static String getClassName(String classDirectory) {
		int start = lastIndexFinder(classDirectory, '/');
		int end = lastIndexFinder(classDirectory, '.');
		String className = classDirectory.substring(start + 1, end);
		return className;
	}

	public static int lastIndexFinder(String classDirectory, Character c) {
		int index = -1;
		for (int i = 0; i < classDirectory.length(); i++) {
			if (classDirectory.charAt(i) == c) {
				index = i;
			}
		}
		return index;
	}

	public static int beforeLasIndexFinder(String classDirectory, Character c) {
		int index = -1;
		int beforeLast = -1;
		for (int i = 0; i < classDirectory.length(); i++) {
			if (classDirectory.charAt(i) == c) {
				beforeLast = index;
				index = i;
			}
		}
		return beforeLast;
	}

	@SuppressWarnings("rawtypes")
	public static Class classMaker(String classDirectory, String packName) throws ClassNotFoundException {
		findClassName = Class.forName(packageNameFinder(classDirectory) + "." + getClassName(classDirectory));
		return findClassName;
	}

	public static String packageNameFinder(String classDirectory) {
		int start = beforeLasIndexFinder(classDirectory, '/');
		int end = lastIndexFinder(classDirectory, '/');
		packName = classDirectory.substring(start + 1, end);
		return packName;
	}
}