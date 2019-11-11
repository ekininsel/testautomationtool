package cs401caseClasses;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.lang.model.element.Modifier;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public class MethodPrinter {

	static SimpleName methodNames = new SimpleName();
	static NodeList<Parameter> parameters;

	public static void main(String[] args) throws Exception {
		// creates an input stream for the file to be parsed
		System.out.println("Please enter the class directory that will be tested.");
		Scanner inputReader = new Scanner(System.in);
		String classDirectory = inputReader.nextLine();
		FileInputStream in = new FileInputStream(classDirectory);

		methodParser(in, classDirectory);
	}

	// parsing the classes methods with javaparser
	private static void methodParser(FileInputStream in, String classDirectory)
			throws Exception {
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
	private static void classGenerator(String classDirectory, CompilationUnit cu) throws Exception {
		// visit and print the methods names
		new MethodVisitor().visit(cu, null);

		for (int i = 0; i < methodNames.asString().length(); i++) {

			FieldSpec object = FieldSpec
					.builder(WithOneMethod.class,
							// kullanılan classın ismi ile obje oluşturma
							getClassName(classDirectory).toString().toLowerCase())
					.addModifiers(Modifier.PRIVATE).build();

			MethodSpec beforeGeneration = MethodSpec.methodBuilder("initialize").addAnnotation(Before.class)
					.addStatement(
							object.name + " = new " + getClassName(classDirectory) + "( )")
					.addModifiers(Modifier.PUBLIC).build();

			MethodSpec constructorSpec = MethodSpec.constructorBuilder()
					.addParameter(TypeName.INT, "aa", Modifier.FINAL)
					// parametrelere constructor olarak o classın parametresini
					// al
					.addStatement(" ").addModifiers(Modifier.PUBLIC).build();

			MethodSpec testGenerator = MethodSpec.methodBuilder("test" + methodNames.toString().charAt(i))
					.addAnnotation(Test.class)
					.addModifiers(Modifier.PUBLIC).build();

			MethodSpec parameterizedData = MethodSpec.methodBuilder("data").addAnnotation(Parameters.class)
					.addModifiers(Modifier.STATIC).addModifiers(Modifier.PUBLIC).build();

			TypeSpec generatedTestClass = TypeSpec.classBuilder(getClassName(classDirectory) + "TestClass")
					.addField(object)
					.addMethod(beforeGeneration).addMethod(constructorSpec).addMethod(testGenerator)
					.addMethod(parameterizedData)
					.addModifiers(Modifier.PUBLIC).build();

			JavaFile javaFile = JavaFile.builder("junit.generate.test", generatedTestClass)
					.addFileComment("AUTO_GENERATED BY TestGenerationTool \n")
					.addFileComment("Test class for " + getClassName(classDirectory) + " Class")
					.addStaticImport(Assert.class, "*")
					.build();

			try {
				javaFile.writeTo(Paths.get("./src/test/java"));// root maven
																// source
			} catch (IOException ex) {
				System.out.println("An exception ... Really!?! " + ex.getMessage());
			}
		}

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

			methodNames = n.getName();

			parameters = n.getParameters();
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
			if (classDirectory.charAt(i) == c)
				index = i;
		}
		return index;
	}
}