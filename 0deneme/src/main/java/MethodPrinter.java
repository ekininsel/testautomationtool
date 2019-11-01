import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.lang.model.element.Modifier;

import org.junit.Before;
import org.junit.Test;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import com.google.common.base.Strings;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class MethodPrinter {

	public static void main(String[] args) throws Exception {
		// creates an input stream for the file to be parsed
		System.out.println("Please enter the class directory that will be tested.");
		Scanner inputReader = new Scanner(System.in);
		String classDirectory = inputReader.nextLine();
		FileInputStream in = new FileInputStream(classDirectory);

		methodParser(in, classDirectory);
		classGenerator(classDirectory);
	}

	// parsing the classes methods with javaparser
	private static void methodParser(FileInputStream in, String classDirectory) throws IOException {
		SourceRoot sourceRoot = new SourceRoot(
				CodeGenerationUtils.mavenModuleRoot(MethodPrinter.class).resolve("src/main/java"));
		CompilationUnit cu;
		try {
			// parse the file
			cu = sourceRoot.parse("", classDirectory);
		} finally {
			in.close();
		}
	}

	// generating the Testclasses with javapoet
	private static void classGenerator(String classDirectory) {
		// visit and print the methods names
		// new MethodVisitor().visit(cu, null);

		MethodSpec beforeGeneration = MethodSpec.methodBuilder("initialize").addAnnotation(Before.class)
				.addModifiers(Modifier.PUBLIC).build();

		MethodSpec testGenerator = MethodSpec.methodBuilder("test").addAnnotation(Test.class)
				.addModifiers(Modifier.PUBLIC).build();

		TypeSpec generatedTestClass = TypeSpec.classBuilder(getClassName(classDirectory) + "TestClass")
				.addMethod(beforeGeneration).addMethod(testGenerator).addModifiers(Modifier.PUBLIC).build();

		JavaFile javaFile = JavaFile.builder("junit.generate.test", generatedTestClass)
				.addFileComment("AUTO_GENERATED BY TestGenerationTool").build();

		try {
			javaFile.writeTo(Paths.get("./src/test/java"));// root maven source
		} catch (IOException ex) {
			System.out.println("An exception ... Really!?! " + ex.getMessage());
		}
	}

	// printing the parsed methods of a class
	@SuppressWarnings("rawtypes")
	private static class MethodVisitor extends VoidVisitorAdapter {
		@Override
		public void visit(MethodDeclaration n, Object arg) {
			// we can use any of the method elements in this method n is the
			// name of the method
			System.out.println(Strings.repeat("=", 10));
			System.out.print(n.getTypeAsString() + " " + n.getName() + "(" + n.getParameters() + ")");
			System.out.println(" [L " + n.getBegin().get().line + " - " + n.getEnd().get().line + "]");
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