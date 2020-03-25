package CSseniorProject;

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
	static int maxBooleanObjectNumber = 0;
	static String packName;
	static String stringTXTFILE = "/Users/ekininsel1/Documents/workspace/seniorProject/cs401File.txt";
	static String intTXTFILE = "/Users/ekininsel1/Documents/workspace/seniorProject/cs401intFile.txt";
	static String booleanTXTFILE = "/Users/ekininsel1/Documents/workspace/seniorProject/cs401booleanFile";

	// static String bool_boolFile =
	// "/Users/ekininsel1/Documents/workspace/seniorProject/bool-bool File";
	// static List<String> bool_boolList = new ArrayList<String>();
	// static File bool_boolFiles = new File(bool_boolFile);
	// static String bool_strFile =
	// "/Users/ekininsel1/Documents/workspace/seniorProject/bool-str File";
	// static List<String> bool_strList = new ArrayList<String>();
	// static File bool_strFiles = new File(bool_strFile);
	// static String int_boolFile =
	// "/Users/ekininsel1/Documents/workspace/seniorProject/int-bool File";
	// static List<String> int_boolList = new ArrayList<String>();
	// static File int_boolFiles = new File(int_boolFile);
	// static String int_intFile =
	// "/Users/ekininsel1/Documents/workspace/seniorProject/int-int File";
	// static List<String> int_intList = new ArrayList<String>();
	// static File int_intFiles = new File(int_intFile);
	// static String int_strFile =
	// "/Users/ekininsel1/Documents/workspace/seniorProject/int-str File";
	// static List<String> int_strList = new ArrayList<String>();
	// static File int_strFiles = new File(int_strFile);
	// static String str_strFile =
	// "/Users/ekininsel1/Documents/workspace/seniorProject/str-str File";
	// static List<String> str_strList = new ArrayList<String>();
	// static File str_strFiles = new File(str_strFile);
	// static Scanner listScanner = null;

	static List<String> stringList = new ArrayList<String>();
	static List<Integer> intList = new ArrayList<Integer>();
	static List<Boolean> booleanList = new ArrayList<Boolean>();
	static Scanner intScanner = null;
	static Scanner stringScanner = null;
	static Scanner boolScanner = null;

	static File intFile = new File(intTXTFILE);
	static File stringFile = new File(stringTXTFILE);
	static File boolFile = new File(booleanTXTFILE);
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
		booleanListScanner();
		// twoParametersListCreator();
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

	public static Object[] booleanListScanner() throws FileNotFoundException {
		boolScanner = new Scanner(boolFile);
		while (boolScanner.hasNextBoolean()) {
			booleanList.add(boolScanner.nextBoolean());
		}
		boolScanner.close();
		return booleanList.toArray();
	}

	// public static Object[] listScanner(Scanner scanner, File file,
	// List<String> list) throws FileNotFoundException {
	// scanner = new Scanner(file);
	// while (scanner.hasNext()) {
	// list.add(scanner.next());
	// }
	// scanner.close();
	// return list.toArray();
	// }
	//
	// public static void twoParametersListCreator() throws
	// FileNotFoundException {
	// listScanner(listScanner, bool_boolFiles, bool_boolList);
	// listScanner(listScanner, bool_strFiles, bool_strList);
	// listScanner(listScanner, int_boolFiles, int_boolList);
	// listScanner(listScanner, int_intFiles, int_intList);
	// listScanner(listScanner, int_strFiles, int_strList);
	// listScanner(listScanner, str_strFiles, str_strList);
	// }

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
			oneParameterMethod(method, object, i, methodsParametersTypes, expectedResults, parametersForTests);
			resultList.add(expectedResults.toString());
			parameterList.add(parametersForTests.toString());
		} else {
			List<String> lis = new ArrayList<String>();
			for (int j = 0; j < methodsParametersTypes.size(); j++) {
				if (methodsParametersTypes.get(j).toString().contains("int")) {
					lis.add(intList.toString());
				} else if (methodsParametersTypes.get(j).toString().contains("String")) {
					lis.add(stringList.toString());
				} else if (methodsParametersTypes.get(j).toString().contains("Boolean")) {
					lis.add(booleanList.toString());
				}
			}

			ArrayList<String> inputParameters = new ArrayList<String>();

			// [0] kısmını değiştir!
			for (int p = 0; p < lis.size(); p++) {
				String[][] deneme = new String[lis.size()][Arrays.asList(lis.get(p).toString().split(",")).size()];
				for (int h = 0; h < Arrays.asList(lis.get(p).toString().split(",")).size(); h++) {
					deneme[p][h] = Arrays.asList(
							lis.get(p).toString().replace("[", "").replace("]", "").split(","))
							.get(h);

					System.out.println(deneme[p][h]);

				}


				// inputParameters.add();

				// inputParameters.add(deneme[0][1]);
				// inputParameters.add(deneme[1][1]);
			}

			
			parametersForTests.add(inputParameters.toString().replace(", ", "-").replace("[", "").replace("]", ""));

			// System.out.println(parametersForTests);
			// expectedResults.add(method.get(i).invoke(object,
			// inputParameters.toArray()).toString());

			parameterList.add(parametersForTests.toString());
			resultList.add(expectedResults.toString());
		}
		return expectedResults;
	}

	public static void oneParameterMethod(ArrayList<Method> method, Object object, int i,
			List<String> methodsParametersTypes, ArrayList<String> expectedResults,
			ArrayList<String> parametersForTests) throws IllegalAccessException, InvocationTargetException {
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
		if (methodsParametersTypes.toString().contains("Boolean")) {
			for (int j = 0; j < booleanList.size(); j++) {
				expectedResults.add(method.get(i).invoke(object, booleanList.get(j)).toString());
				parametersForTests.add(booleanList.get(j).toString());
			}
		}
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
	private static void classGenerator(String classDirectory) throws Exception {
		// System.out.println(parameterList);
		String objectName = getClassName(classDirectory).toString().toLowerCase();
		FieldSpec classObject = FieldSpec.builder(classMaker(classDirectory, packName), objectName)
				.addModifiers(Modifier.PUBLIC).build();

		List<String> datas = null;
		objectNumberFinder();

		FieldSpec[] intObjects = new FieldSpec[maxIntObjectNumber];
		FieldSpec[] strObjects = new FieldSpec[maxStringObjectNumber];
		FieldSpec[] boolObjects = new FieldSpec[maxBooleanObjectNumber];
		List<FieldSpec> ints = null;
		List<FieldSpec> strs = null;
		List<FieldSpec> bools = null;
		List<String> constructorParameters = new ArrayList<String>();
		testClassObjectsDefiner(intObjects, strObjects, boolObjects, constructorParameters);
		ints = Arrays.asList(intObjects);
		strs = Arrays.asList(strObjects);
		bools = Arrays.asList(boolObjects);

		FieldSpec[] expectedObjects = new FieldSpec[methodNamesList.size()];
		List<FieldSpec> expecteds = null;
		testExpectedsCreator(constructorParameters, expectedObjects);
		expecteds = Arrays.asList(expectedObjects);

		MethodSpec[] testMethods = new MethodSpec[methodNamesList.size()];
		List<MethodSpec> testMethodList = null;

		ParameterSpec[] conParameters = new ParameterSpec[constructorParameters.size()];
		List<ParameterSpec> constructors = null;
		CodeBlock[] constructorInside = new CodeBlock[constructorParameters.size()];
		List<CodeBlock> lists = new ArrayList<CodeBlock>();
		constructorDefiner(constructorParameters, conParameters, constructorInside);
		constructors = Arrays.asList(conParameters);
		lists = Arrays.asList(constructorInside);
		MethodSpec constructor = constructorMakerForTest(constructors, lists);

		List<CodeBlock> assertList = null;

		int i = 0;
		while (i < methodNamesList.size()) {
			String[] Asserts = new String[Arrays.asList(parameters.get(i).toString().split(", ")).size()];
			objectsInsideAssertions(i, Asserts);
			List<String> tt = Arrays.asList(Asserts);
			CodeBlock asserting = methodForAssertion(objectName, expecteds, i, tt);
			assertList = Arrays.asList(asserting);

			String testName = methodNamesList.get(i).substring(0, 1).toUpperCase()
					+ methodNamesList.get(i).substring(1);

			testMethods[i] = MethodSpec.methodBuilder("test" + testName).addAnnotation(Test.class)
					.addModifiers(Modifier.PUBLIC, Modifier.FINAL)
					.addCode(assertList.toString().replaceAll("(^\\[|\\]$)", "").replaceAll("\n,", "\n")).build();
			testMethodList = Arrays.asList(testMethods);

			List<String> results = new ArrayList<String>();

			// [0] kısmı değişmeli!
			for (int p = 0; p < resultList.size(); p++) {
				String[][] resultDimension = new String[resultList.size()][Arrays
						.asList(resultList.get(p).toString().split(",")).size()];
				for (int h = 0; h < Arrays.asList(resultList.get(p).toString().split(",")).size(); h++) {
					resultDimension[p][h] = Arrays
							.asList(resultList.get(p).toString().replace("[", "").replace("]", "").split(",")).get(h);
				}
				results.add(resultDimension[p][0]);
			}

			// DEĞİŞMELİ her seçeneğe göre bir data listesi oluşturmalı
			String[] dataList = new String[constructors.size()];
			int z = 0;
			while (z < results.size()) {
				for (int y = 0; y < constructors.size(); y++) {
					if (constructors.get(y).toString().contains("Expected")) {
						dataList[y] = results.get(z);
						z++;
					} else if (constructors.get(y).toString().contains("str")) {
						dataList[y] = stringList.get(0);
					} else if (constructors.get(y).toString().contains("int")) {
						dataList[y] = intList.get(0).toString();
					} else if (constructors.get(y).toString().contains("boolean")) {
						dataList[y] = booleanList.get(0).toString();
					}
					datas = Arrays.asList(dataList);
				}
			}
			i++;
		}

		MethodSpec beforeTests = initializerForTestClass(classDirectory, objectName);

		MethodSpec data = parameterizedMaker(datas);

		AnnotationSpec run = runWithMaker();

		TypeSpec generatedTestClass = testClassGenerator(classDirectory, ints, strs, bools, classObject, expecteds,
				testMethodList, constructor, beforeTests, data, run);

		JavaFile javaFile = javaFileCreator(classDirectory, generatedTestClass);
		try {
			javaFile.writeTo(Paths.get("./src/test/java"));// root maven
															// source
		} catch (IOException ex) {
			System.out.println("An exception ...! " + ex.getMessage());
		}
	}

	public static TypeSpec testClassGenerator(String classDirectory, List<FieldSpec> ints, List<FieldSpec> strs,
			List<FieldSpec> bools, FieldSpec classObject, List<FieldSpec> expecteds, List<MethodSpec> testMethodList,
			MethodSpec constructor, MethodSpec beforeTests, MethodSpec data, AnnotationSpec run) {
		TypeSpec generatedTestClass = javaTypeSpecCreator(classDirectory, ints, strs, bools, classObject, expecteds,
				testMethodList, constructor, beforeTests, data, run);
		return generatedTestClass;
	}

	public static AnnotationSpec runWithMaker() {
		CodeBlock parameterized = CodeBlock.builder().addStatement("Parameterized.class").build();
		AnnotationSpec run = AnnotationSpec.builder(RunWith.class).addMember("value", "Parameterized.class").build();
		return run;
	}

	// Data.toString() kısmı değişecek
	public static MethodSpec parameterizedMaker(List<String> datas) {
		MethodSpec data = MethodSpec.methodBuilder("dataForParameterized").addAnnotation(Parameterized.Parameters.class)
				.returns(List.class)
				.addStatement("return Arrays.asList( new Object[][] { {"
						+ datas.toString().replace("[", "").replace("]", "") + "} })")
				.addModifiers(Modifier.PUBLIC, Modifier.STATIC).build();
		return data;
	}

	public static void objectsInsideAssertions(int i, String[] Asserts) {
		int d = 0;
		int f = 0;
		int r = 0;
		for (int e = 0; e < Arrays.asList(parameters.get(i).toString().split(", ")).size(); e++) {
			if (Arrays.asList(parameters.get(i).toString().split(",")).get(e).contains("ınt")) {
				Asserts[e] = "int" + d;
				d++;
			} else if (Arrays.asList(parameters.get(i).toString().split(", ")).get(e).contains("String")) {
				Asserts[e] = "str" + f;
				f++;
			} else if (Arrays.asList(parameters.get(i).toString().split(", ")).get(e).contains("Boolean")) {
				Asserts[e] = "boolean" + r;
				r++;
			} else {
				Asserts[e] = "/";
			}
		}
	}

	public static MethodSpec constructorMakerForTest(List<ParameterSpec> constructors, List<CodeBlock> lists) {
		MethodSpec constructor = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC)
				.addParameters(constructors)
				.addStatement(lists.toString().replace("[", "").replace("]", "").replace(", ", "")).build();
		return constructor;
	}

	public static MethodSpec initializerForTestClass(String classDirectory, String objectName) {
		MethodSpec beforeTests = MethodSpec.methodBuilder("initialize").addAnnotation(Before.class)
				.addModifiers(Modifier.PUBLIC)
				.addStatement(objectName + " = new " + getClassName(classDirectory) + "()").build();
		return beforeTests;
	}

	public static CodeBlock methodForAssertion(String objectName, List<FieldSpec> expecteds, int i, List<String> tt) {
		CodeBlock asserting = CodeBlock.builder()
				.addStatement("assertEquals(" + expecteds.get(i).name + ", " + objectName + "." + methodNamesList.get(i)
						+ "(" + tt.toString().replaceAll("(^\\[|\\]$)", "").replace("/", "") + "))")
				.build();
		return asserting;
	}

	public static TypeSpec javaTypeSpecCreator(String classDirectory, List<FieldSpec> ints, List<FieldSpec> strs,
			List<FieldSpec> bools, FieldSpec classObject, List<FieldSpec> expecteds, List<MethodSpec> testMethodList,
			MethodSpec constructor, MethodSpec beforeTests, MethodSpec data, AnnotationSpec run) {
		TypeSpec generatedTestClass = TypeSpec.classBuilder(getClassName(classDirectory) + "Test").addFields(ints)
				.addFields(strs).addFields(bools).addFields(expecteds).addField(classObject).addMethod(beforeTests)
				.addMethod(constructor).addMethod(data).addMethods(testMethodList).addAnnotation(run)
				.addModifiers(Modifier.PUBLIC).build();
		return generatedTestClass;
	}

	public static JavaFile javaFileCreator(String classDirectory, TypeSpec generatedTestClass) {
		JavaFile javaFile = JavaFile.builder("junit.generate.test", generatedTestClass)
				.addFileComment("AUTO_GENERATED BY TestGenerationTool \n")
				.addFileComment("Test class for " + getClassName(classDirectory) + " Class")
				.addStaticImport(Assert.class, "*").build();
		return javaFile;
	}

	public static void constructorDefiner(List<String> constructorParameters, ParameterSpec[] conParameters,
			CodeBlock[] constructorInside) {
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
			} else if (constructorParameters.get(m).contains("Boolean")
					|| constructorParameters.get(m).contains("boolean")) {
				conParameters[m] = ParameterSpec.builder(Boolean.class, constructorParameters.get(m)).build();
				constructorInside[m] = CodeBlock.builder()
						.addStatement("this." + conParameters[m].name + " = " + conParameters[m].name).build();
			} else {
				conParameters[m] = ParameterSpec.builder(Object.class, constructorParameters.get(m)).build();
				constructorInside[m] = CodeBlock.builder()
						.addStatement("this." + conParameters[m].name + " = " + conParameters[m].name).build();
			}
		}
	}

	public static void testExpectedsCreator(List<String> constructorParameters, FieldSpec[] expectedObjects) {
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
	}

	public static void testClassObjectsDefiner(FieldSpec[] intObjects, FieldSpec[] strObjects, FieldSpec[] boolObjects,
			List<String> constructorParameters) {
		for (int t = 0; t < maxIntObjectNumber; t++) {
			intObjects[t] = FieldSpec.builder(int.class, "int" + t, Modifier.PRIVATE, Modifier.FINAL).build();
			constructorParameters.add("int" + t);
		}
		for (int h = 0; h < maxStringObjectNumber; h++) {
			strObjects[h] = FieldSpec.builder(String.class, "str" + h, Modifier.PRIVATE, Modifier.FINAL).build();
			constructorParameters.add("str" + h);
		}
		for (int k = 0; k < maxBooleanObjectNumber; k++) {
			boolObjects[k] = FieldSpec.builder(boolean.class, "boolean" + k, Modifier.PRIVATE, Modifier.FINAL).build();
			constructorParameters.add("boolean" + k);
		}
	}

	public static void objectNumberFinder() {
		for (int p = 0; p < parameters.size(); p++) {
			List<String> objects = Arrays.asList(parameters.get(p).toString().split(","));
			for (int f = 0; f < objects.size(); f++) {
				if (objects.get(f).toString().contains("ınt ")) {
					maxIntObjectNumber++;
				}
				if (objects.get(f).toString().contains("String ")) {
					maxStringObjectNumber++;
				}
				if (objects.get(f).toString().contains("Boolean ")) {
					maxBooleanObjectNumber++;
				}
			}
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