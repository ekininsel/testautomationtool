package CSseniorProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.*;

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
	static int maxDoubleObjectNumber = 0;
	static String packName;
	static String stringTXTFILE = "/Users/ekininsel1/Documents/workspace/seniorProject/cs401File.txt";
	static String intTXTFILE = "/Users/ekininsel1/Documents/workspace/seniorProject/cs401intFile.txt";
	static String booleanTXTFILE = "/Users/ekininsel1/Documents/workspace/seniorProject/cs401booleanFile";
	static String doubleTXTFILE = "/Users/ekininsel1/Documents/workspace/seniorProject/cs401doubleFile";

	static List<String> stringList = new ArrayList<String>();
	static List<Integer> intList = new ArrayList<Integer>();
	static List<Boolean> booleanList = new ArrayList<Boolean>();
	static List<Double> doubleList = new ArrayList<Double>();
	static Scanner intScanner = null;
	static Scanner stringScanner = null;
	static Scanner boolScanner = null;
	static Scanner doubleScanner = null;

	static File intFile = new File(intTXTFILE);
	static File stringFile = new File(stringTXTFILE);
	static File boolFile = new File(booleanTXTFILE);
	static File doubleFile = new File(doubleTXTFILE);
	static Map<Integer, List<Object>> resultList = new HashMap<> ();
	static ArrayList<String> returnTypeOfMethods = new ArrayList<String>();

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
		doubleListScanner();
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

	public static Object[] doubleListScanner() throws FileNotFoundException {
		doubleScanner = new Scanner(doubleFile);
		while (doubleScanner.hasNextDouble ()) {
			doubleList.add(doubleScanner.nextDouble ());
		}
		doubleScanner.close();
		return doubleList.toArray();
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
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException {

		for (int i = 0; i < method.size(); i++) {
			List methodsParametersTypes = Arrays.asList(method.get(i).getParameterTypes());
			returnTypeOfMethods.add((method.get(i).getReturnType().toString()));
			parameterMethod(method, object, i, methodsParametersTypes);
		}
	}

	public static void parameterMethod(ArrayList<Method> method, Object object, int i,
			List methodsParametersTypes)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Map<Integer, List<Object>> ekin = new HashMap<> ();
        for (int index = 0 ; index< stringList.size (); index++) {
            List<Object> liste = Arrays.asList (intList.get (index), stringList.get (index), booleanList.get (index), doubleList.get (index));
            ekin.put (index, liste);
        }

        for(int index = 0 ; index<ekin.size (); index++){

            List<Object> objects = ekin.get(index);
            List<Object> objectList = new ArrayList<>();

            for (int j = 0; j < methodsParametersTypes.size(); j++) {
                if (methodsParametersTypes.get (j).toString ().contains ("int")) {
                    objectList.add (objects.get (0));
                } else if (methodsParametersTypes.get (j).toString ().contains ("String")) {
                    objectList.add (objects.get (1));
                } else if (methodsParametersTypes.get (j).toString ().contains ("boolean")) {
                    objectList.add (objects.get (2));
                }else if (methodsParametersTypes.get (j).toString ().contains ("double")){
					objectList.add (objects.get (3));
				}
            }

            Object result = new Object();
            if(methodsParametersTypes.size() == 0 )
                result = method.get(i).invoke(object);
            else if(methodsParametersTypes.size() == 1 )
                result = method.get(i).invoke(object, objectList.get (0));
            else if(methodsParametersTypes.size() == 2 )
                result = method.get(i).invoke(object, objectList.get (0), objectList.get (1));
            else if(methodsParametersTypes.size() == 3 )
                result = method.get(i).invoke(object, objectList.get (0), objectList.get (1), objectList.get (2));
            else if(methodsParametersTypes.size() == 4 )
                result = method.get(i).invoke(object, objectList.get (0), objectList.get (1), objectList.get (2), objectList.get (3));
            else if(methodsParametersTypes.size() == 5 )
                result = method.get(i).invoke(object, objectList.get (0), objectList.get (1), objectList.get (2), objectList.get (3), objectList.get (4));

            addResultToList(result, index);
        }
	}

	private static void addResultToList(Object result, int index) {
	    List<Object> results = resultList.get (index);
	    if(results == null)
        {
            results = new ArrayList<> ();
            resultList.put(index, results);
        }
	    results.add (result);
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
		String objectName = getClassName(classDirectory).toLowerCase();
		FieldSpec classObject = FieldSpec.builder(classMaker(classDirectory, packName), objectName)
				.addModifiers(Modifier.PUBLIC).build();


		objectNumberFinder();

		FieldSpec[] intObjects = new FieldSpec[maxIntObjectNumber];
		FieldSpec[] strObjects = new FieldSpec[maxStringObjectNumber];
		FieldSpec[] boolObjects = new FieldSpec[maxBooleanObjectNumber];
		FieldSpec[] doubleObjects = new FieldSpec[maxDoubleObjectNumber];
		List<FieldSpec> ints = null;
		List<FieldSpec> strs = null;
		List<FieldSpec> bools = null;
		List<FieldSpec> doubles = null;
		List<String> constructorParameters = new ArrayList<String>();
		testClassObjectsDefiner(intObjects, strObjects, boolObjects, doubleObjects, constructorParameters);
		ints = Arrays.asList(intObjects);
		strs = Arrays.asList(strObjects);
		bools = Arrays.asList(boolObjects);
		doubles = Arrays.asList (doubleObjects);

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
        Map<Integer,List<Object>> datas = null;
		int i = 0;
		while (i < methodNamesList.size()) {
			String[] Asserts = new String[Arrays.asList(parameters.get(i).split(", ")).size()];
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

            datas = parameterizedDataCreator (constructors);
            i++;
		}

		MethodSpec beforeTests = initializerForTestClass(classDirectory, objectName);

		String content = dataForParameterizedFuncContent(datas);
		MethodSpec data = parameterizedMaker(content);

		AnnotationSpec run = runWithMaker();

		TypeSpec generatedTestClass = testClassGenerator(classDirectory, ints, strs, bools, doubles, classObject, expecteds,
				testMethodList, constructor, beforeTests, data, run);

		JavaFile javaFile = javaFileCreator(classDirectory, generatedTestClass);
		try {
			javaFile.writeTo(Paths.get("./src/test/java"));// root maven
															// source
		} catch (IOException ex) {
			System.out.println("An exception ...! " + ex.getMessage());
		}
	}

    private static Map<Integer,List<Object>> parameterizedDataCreator(List<ParameterSpec> constructors) {
        Map<Integer, List<Object>> ekin = new HashMap<> ();
        Map<Integer, List<Object>> objectMap = new HashMap<> ();
        for (int index = 0 ; index< stringList.size (); index++) {
            List<Object> liste = Arrays.asList (intList.get (index), stringList.get (index), booleanList.get (index), doubleList.get (index), resultList.get (index));
            ekin.put (index, liste);
        }

        for(int index = 0 ; index<ekin.size (); index++){

            List<Object> objects = ekin.get(index);
            List<Object> results = resultList.get(index);
            List<Object> objectList = new ArrayList<> ();
            int expecedParamCount =  0;

            for (int j = 0; j < constructors.size(); j++) {
                if(constructors.get (j).toString ().contains ("Expected")){
                    objectList.add (results.get (expecedParamCount));
                    expecedParamCount++;
                }
                else if (constructors.get (j).toString ().contains ("int")) {
                    objectList.add (objects.get (0));
                } else if (constructors.get (j).toString ().contains ("String")) {
                    objectList.add (objects.get (1));
                } else if (constructors.get (j).toString ().contains ("boolean")) {
                    objectList.add (objects.get (2));
                }else if (constructors.get (j).toString ().contains ("double")) {
					objectList.add (objects.get (3));
				}
            }

            objectMap.put (index, objectList);
        }
        return objectMap;
    }

    public static TypeSpec testClassGenerator(String classDirectory, List<FieldSpec> ints, List<FieldSpec> strs,
			List<FieldSpec> bools, List<FieldSpec> doubles, FieldSpec classObject, List<FieldSpec> expecteds, List<MethodSpec> testMethodList,
			MethodSpec constructor, MethodSpec beforeTests,MethodSpec data, AnnotationSpec run) {
		TypeSpec generatedTestClass = javaTypeSpecCreator(classDirectory, ints, strs, bools, doubles, classObject, expecteds,
				testMethodList, constructor, beforeTests, data, run);
		return generatedTestClass;
	}

	public static AnnotationSpec runWithMaker() {
		CodeBlock parameterized = CodeBlock.builder().addStatement("Parameterized.class").build();
		AnnotationSpec run = AnnotationSpec.builder(RunWith.class).addMember("value", "Parameterized.class").build();
		return run;
	}

	public static MethodSpec parameterizedMaker(String statement) {
		MethodSpec data = MethodSpec.methodBuilder("dataForParameterized").addAnnotation(Parameterized.Parameters.class)
				.returns(List.class)
				.addStatement(statement)
				.addModifiers(Modifier.PUBLIC, Modifier.STATIC).build();
		return data;
	}

	private static String dataForParameterizedFuncContent(Map<Integer, List<Object>> datas)
    {
        StringBuilder statement = new StringBuilder ();
        statement.append ("return Arrays.asList( new Object[][] {");

        for(int index =0 ; index < datas.size (); index++) {
            List<Object> dataList = datas.get (index);
            statement.append ("{");
            for(int j= 0 ; j < dataList.size () ; j++) {

                if(dataList.get (j) instanceof List){
                    statement.append ("Arrays.asList");
                    statement.append (dataList.get (j).toString ().replace ("[", "(").replace ("]", ")"));
                } else {
                    statement.append (dataList.get (j));
                }

                if(j+1 != dataList.size ())
                    statement.append (", ");
            }
            statement.append ("}");
            if(index+1 != datas.size ())
                statement.append (" , ");
        }

        statement.append ("})");
        return statement.toString ();
    }

	public static void objectsInsideAssertions(int i, String[] Asserts) {
		int d = 0;
		int f = 0;
		int r = 0;
		int s = 0;
		for (int e = 0; e < Arrays.asList(parameters.get(i).toString().split(", ")).size(); e++) {
			if (Arrays.asList(parameters.get(i).toString().split(",")).get(e).contains("ınt")) {
				Asserts[e] = "int" + d;
				d++;
			} else if (Arrays.asList(parameters.get(i).toString().split(", ")).get(e).contains("String")) {
				Asserts[e] = "str" + f;
				f++;
			} else if (Arrays.asList(parameters.get(i).toString().split(", ")).get(e).contains("boolean")) {
				Asserts[e] = "boolean" + r;
				r++;
			} else if (Arrays.asList(parameters.get(i).toString().split(", ")).get(e).contains("double")) {
				Asserts[e] = "double" + s;
				s++;
			}
			else {
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
			List<FieldSpec> bools, List<FieldSpec> doubles, FieldSpec classObject, List<FieldSpec> expecteds, List<MethodSpec> testMethodList,
			MethodSpec constructor, MethodSpec beforeTests, MethodSpec data, AnnotationSpec run) {
		TypeSpec generatedTestClass = TypeSpec.classBuilder(getClassName(classDirectory) + "Test").addFields(ints)
				.addFields(strs).addFields(bools).addFields (doubles).addFields(expecteds).addField(classObject).addMethod(beforeTests).addMethod (data)
				.addMethod(constructor).addMethods(testMethodList).addAnnotation(run)
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
				conParameters[m] = ParameterSpec.builder(boolean.class, constructorParameters.get(m)).build();
				constructorInside[m] = CodeBlock.builder()
						.addStatement("this." + conParameters[m].name + " = " + conParameters[m].name).build();
			}else if (constructorParameters.get(m).contains("Double")
					|| constructorParameters.get(m).contains("double")) {
				conParameters[m] = ParameterSpec.builder(double.class, constructorParameters.get(m)).build();
				constructorInside[m] = CodeBlock.builder()
						.addStatement("this." + conParameters[m].name + " = " + conParameters[m].name).build();
			}else if (constructorParameters.get(m).contains("List")) {
                conParameters[m] = ParameterSpec.builder(List.class, constructorParameters.get(m)).build();
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
				expectedObjects[y] = FieldSpec.builder(String.class, "StringExpectedFor" + methodNamesList.get(y).substring(0, 1).toUpperCase()
                                + methodNamesList.get(y).substring(1),
						Modifier.PRIVATE, Modifier.FINAL).build();
				constructorParameters.add("StringExpectedFor" + methodNamesList.get(y).substring(0, 1).toUpperCase()
                        + methodNamesList.get(y).substring(1));
			} else if (methodsTypes.get(y).equals("ınt")) {
				expectedObjects[y] = FieldSpec
						.builder(int.class, "intExpectedFor" + methodNamesList.get(y).substring(0, 1).toUpperCase()
                                + methodNamesList.get(y).substring(1), Modifier.PRIVATE, Modifier.FINAL)
						.build();
				constructorParameters.add("intExpectedFor" + methodNamesList.get(y).substring(0, 1).toUpperCase()
                        + methodNamesList.get(y).substring(1));
			} else if (methodsTypes.get(y).equals("Boolean")) {
				expectedObjects[y] = FieldSpec.builder(boolean.class, "BooleanExpectedFor" + methodNamesList.get(y).substring(0, 1).toUpperCase()
                                + methodNamesList.get(y).substring(1),
						Modifier.PRIVATE, Modifier.FINAL).build();
				constructorParameters.add("BooleanExpectedFor" + methodNamesList.get(y).substring(0, 1).toUpperCase()
                        + methodNamesList.get(y).substring(1));
			} else if (methodsTypes.get(y).equals("double")) {
				expectedObjects[y] = FieldSpec.builder(double.class, "DoubleExpectedFor" + methodNamesList.get(y).substring(0, 1).toUpperCase()
                                + methodNamesList.get(y).substring(1),
						Modifier.PRIVATE, Modifier.FINAL).build();
				constructorParameters.add("DoubleExpectedFor" + methodNamesList.get(y).substring(0, 1).toUpperCase()
                        + methodNamesList.get(y).substring(1));
			} else if (methodsTypes.get(y).contains("List")) {
                expectedObjects[y] = FieldSpec.builder(List.class, "ListExpectedFor" + methodNamesList.get(y).substring(0, 1).toUpperCase() + methodNamesList.get(y).substring(1),
                        Modifier.PRIVATE, Modifier.FINAL).build();
                constructorParameters.add("ListExpectedFor" + methodNamesList.get(y).substring(0, 1).toUpperCase()
                        + methodNamesList.get(y).substring(1));
            } else {
				expectedObjects[y] = FieldSpec.builder(Object.class, "ObjectExpectedFor" + methodNamesList.get(y).substring(0, 1).toUpperCase()
                                + methodNamesList.get(y).substring(1),
						Modifier.PRIVATE, Modifier.FINAL).build();
				constructorParameters.add("ObjectExpectedFor" + methodNamesList.get(y).substring(0, 1).toUpperCase()
                        + methodNamesList.get(y).substring(1));
			}
		}
	}

	public static void testClassObjectsDefiner(FieldSpec[] intObjects, FieldSpec[] strObjects, FieldSpec[] boolObjects, FieldSpec[] doubleObjects,
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
		for (int g = 0; g < maxDoubleObjectNumber; g++) {
			doubleObjects[g] = FieldSpec.builder(double.class, "double" + g, Modifier.PRIVATE, Modifier.FINAL).build();
			constructorParameters.add("double" + g);
		}
	}

	public static void objectNumberFinder() {
		for (int p = 0; p < parameters.size(); p++) {
			List<String> objects = Arrays.asList(parameters.get(p).toString().split(","));
			int intNum = 0;
			int strNum = 0;
			int boolNum = 0;
			int doubleNum = 0;
			for (int f = 0; f < objects.size(); f++) {
				if (objects.get(f).toString().contains("ınt ")) {
				    intNum++;
				    if(intNum > maxIntObjectNumber)
				        maxIntObjectNumber = intNum;
				}
				if (objects.get(f).toString().contains("String ")) {
				    strNum++;
				    if(strNum > maxStringObjectNumber)
				        maxStringObjectNumber = strNum;
				}
				if (objects.get(f).toString().contains("boolean ")) {
				    boolNum++;
				    if(boolNum > maxBooleanObjectNumber)
				        maxBooleanObjectNumber = boolNum;
				}
				if (objects.get(f).toString().contains("double ")) {
				    doubleNum++;
				    if(doubleNum > maxDoubleObjectNumber)
				        maxDoubleObjectNumber = doubleNum;
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