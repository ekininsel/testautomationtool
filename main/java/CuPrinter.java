import java.io.FileInputStream;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;

public class CuPrinter {
	public static void main(String[] args) throws Exception {
		SourceRoot sourceRoot = new SourceRoot(
				CodeGenerationUtils.mavenModuleRoot(CuPrinter.class).resolve("src/main/java"));
		// creates an input stream for the file to be parsed
		FileInputStream in = new FileInputStream(
				"/Users/ekininsel1/Documents/workspace/0deneme/src/main/java/Math.java");

		CompilationUnit cu;
		try {
			// parse the file
			cu = sourceRoot.parse("", "Math.java");
		} finally {
			in.close();
		}
		cu.accept(new ClassVisitor(), null);
}

	private static class ClassVisitor extends VoidVisitorAdapter<Void> {
		@Override
		public void visit(ClassOrInterfaceDeclaration n, Void arg) {
			/*
			 * here you can access the attributes of the method. this method
			 * will be called for all methods in this CompilationUnit, including
			 * inner class methods
			 */

			System.out.println(n.getName());
			super.visit(n, arg);
			// System.out.println(" [L " + n.getBegin().get().line + "] " + n);
		}
	}
}