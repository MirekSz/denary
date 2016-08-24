import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Main {
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 5000; i++) {
			File file = new File(
					"C:\\Users\\Mirek\\WebstormProjects\\sys\\app\\mods\\mod"
							+ i + ".js");
			file.createNewFile();
			List<String> lines = new ArrayList<String>();
			lines.add("import mod" + (i - 1) + " from './mod" + (i - 1) + "';");
			lines.add("var value=mod" + (i - 1) + "+1;");
			lines.add("export default value;");

			FileUtils.writeLines(file, lines);
		}
	}
}
