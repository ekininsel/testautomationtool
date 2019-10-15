import java.io.FileInputStream;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import com.google.common.base.Strings;

public class MethodPrinter {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		SourceRoot sourceRoot = new SourceRoot(
				CodeGenerationUtils.mavenModuleRoot(MethodPrinter.class).resolve("src/main/java"));
		// creates an input stream for the file to be parsed
		FileInputStream in = new FileInputStream(
				"/Users/ekininsel1/Documents/workspace/0deneme/src/main/java/Math.java");

		CompilationUnit cu;
		try {
			// parse the file
			cu = sourceRoot.parse("", "/Users/ekininsel1/Documents/workspace/0deneme/src/main/java/Math.java");
		} finally {
			in.close();
		}

		// visit and print the methods names
		new MethodVisitor().visit(cu, null);
	}

	/**
	 * Simple visitor implementation for visiting MethodDeclaration nodes.
	 */
	@SuppressWarnings("rawtypes")
	private static class MethodVisitor extends VoidVisitorAdapter {

		@Override
		public void visit(MethodDeclaration n, Object arg) {
			// here you can access the attributes of the method.
			// this method will be called for all methods in this
			// CompilationUnit, including inner class methods
			System.out.println(Strings.repeat("=", 10));
			System.out.print(n.getTypeAsString() + " " + n.getName() + "(" + n.getParameters() + ")");
			System.out.println(" [L " + n.getBegin().get().line + " - " + n.getEnd().get().line + "]");
		}
	}
}