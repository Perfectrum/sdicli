package pro.karagodin.commands;

import pro.karagodin.exceptions.CLIException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;


/**
 * TODO Implement custom reader to reduce memory usage
 */
public class CatCommand extends Command {
    @Override
    public Reader run(Reader reader) throws CLIException {
        if (this.arguments.isEmpty()) {
            return reader == null ? new StringReader("") : reader;
        } else {
            StringBuffer sb = new StringBuffer();
            for (String fileName : arguments) {
                try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))){
                    String line = null;
                    while ((line = fileReader.readLine()) != null) {
                        sb.append(line);
                    }
                } catch (Exception e) {
                    throw new CLIException("Exception happened wile reading file " + fileName , e);
                }
            }
            StringReader outputReader = new StringReader(sb.toString());
            return outputReader;
        }
    }
}
