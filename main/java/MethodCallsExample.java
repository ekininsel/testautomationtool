import java.io.File;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;


public class MethodCallsExample {

	public static void listMethodCalls(File projectDir) {
		SourceRoot sourceRoot = new SourceRoot(
				CodeGenerationUtils.mavenModuleRoot(MethodCallsExample.class).resolve("src/main/java"));
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			// System.out.println(path);
			// System.out.println(Strings.repeat("=", path.length()));
			new VoidVisitorAdapter<Object>() {
				@Override
				public void visit(MethodCallExpr n, Object arg) {
					super.visit(n, arg);
					System.out.println(" [L " + n.getBegin().get().line + "] " + n);
				}
			}.visit(sourceRoot.parse("", "Math.java"), null);
			System.out.println(); // empty line
		}).explore(projectDir);
	}

	public static void main(String[] args) {
		File projectDir = new File("source_to_parse/junit-master");
		listMethodCalls(projectDir);
	}
}