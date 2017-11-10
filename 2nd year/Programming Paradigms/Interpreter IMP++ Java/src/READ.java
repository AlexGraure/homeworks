import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * 
 * @author Graure Marius-Alexandru 
 *
 */
public class READ {
	public void Read(String inputFileName, String outputFileName) {

		try {
			BufferedReader reader = null;
			FileInputStream input = new FileInputStream(inputFileName);
			reader = new BufferedReader(new InputStreamReader(input));
			PrintWriter output = new PrintWriter(outputFileName);

			String line = null;
			String[] tokens = null;

			String delimiters = "[\\t\\n.\\[.\\]. ]+";
			String text = null;
			while ((line = reader.readLine()) != null) {
				if(text == null) {
					text = line;
				} else {
					text = new StringBuilder()
							.append(text)
							.append(line)
							.toString();
				}
				line = null;
			}
			tokens = null;
			tokens = text.split(delimiters);
			Evaluate.getInstance().eval(tokens, output);
			reader.close();
			input.close();
			output.close();
		}catch(

	FileNotFoundException e)
	{
		System.out.println("FILE_NOT_FOUND");
	}catch(
	IOException e)
	{
		e.printStackTrace();
	}
}

}
