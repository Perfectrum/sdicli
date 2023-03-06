package pro.karagodin;

import static java.util.Map.entry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import pro.karagodin.exceptions.CLIException;

public class Scanner {

    private static final Map<String, LexemeType> STR_TO_LEXEME_TYPE = Map.ofEntries(
            entry("eq", LexemeType.ASSIGN),
            entry("dq", LexemeType.DQ),
            entry("sq", LexemeType.SQ),
            entry("pipe", LexemeType.PIPE),
            entry("space", LexemeType.SPACE),
            entry("str", LexemeType.STR));

    public List<Lexeme> scan(String str) throws CLIException {
        var strRegex = "(?<str>[^=\\|\\s\'\"]+)";
        var equalRegex = "(?<eq>=)";
        var pipeRegex = "(?<pipe>\\|)";
        var dqRegex = "\"(?<dq>[^\"]+)\"";
        var sqRegex = "'(?<sq>[^']+)'";
        var spaceRegex = "(?<space>[\\s]+)";
        var fullRegex = "^" + strRegex + "|" + equalRegex + "|" + pipeRegex + "|" + dqRegex + "|" + sqRegex + "|"
                + spaceRegex;

        return scan(str, fullRegex, STR_TO_LEXEME_TYPE.keySet());

    }

    public static List<Lexeme> scan(String text, String regex, Collection<String> groupsNames) throws CLIException {
        //System.out.println("regex: " + regex);
        //System.out.println("text: " + text);
        var pattern = Pattern.compile(regex);
        var matcher = pattern.matcher(text);
        var lexemes = new ArrayList<Lexeme>();
        while (matcher.find()) {
            for (var groupName : groupsNames)
                if (matcher.group(groupName) != null) {
                    lexemes.add(new Lexeme(matcher.group(groupName), STR_TO_LEXEME_TYPE.get(groupName)));
                    //System.out.println(groupName + ": " + matcher.group(groupName));
                    break;

                }
            matcher.region(matcher.end(), text.length());
        }

        if (matcher.regionStart() != matcher.regionEnd()) {
            throw new CLIException("Parse error at pos " + matcher.regionStart());
        }
        return lexemes;
    }
}
