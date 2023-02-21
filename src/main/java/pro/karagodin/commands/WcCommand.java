package pro.karagodin.commands;

import pro.karagodin.exceptions.CLIException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class WcCommand extends Command {

    private long lines = 0;
    private long words = 0;
    private long bytes = 0;

    @Override
    public Reader run(Reader reader) throws CLIException {

        if (this.arguments.isEmpty()) {
            try(BufferedReader bufferedReader = new BufferedReader(reader)) {
                read(bufferedReader);
            } catch (Exception e) {
                throw new CLIException("Error while reading data", e);
            }
        } else {
            for (String fileName : arguments) {
                try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))){
                    read(fileReader);
                } catch (Exception e) {
                    throw new CLIException("Exception happened wile reading file " + fileName , e);
                }
            }
        }
        String output = lines + " " + words + " " + bytes;
        return new StringReader(output);
    }

    private void read(BufferedReader br) throws IOException {
        String line = null;
        while ((line = br.readLine()) != null) {
            lines++;
            bytes += line.getBytes().length;
            words += line.split(" ").length;
        }
    }
}
