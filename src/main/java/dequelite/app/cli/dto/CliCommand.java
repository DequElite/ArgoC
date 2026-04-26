package dequelite.app.cli.dto;

import java.util.HashMap;
import java.util.Map;

public abstract class CliCommand {
    public abstract String name();
    public abstract String commandFlag();

    public abstract void execute(String[] args);

    protected Map<String, String> parseParams(String[] args){
        Map<String, String> params = new HashMap<>();
        for(int i = 0; i < args.length; i++) {
            String arg = args[i];
            if(arg.startsWith("--") || arg.startsWith("p")) {
                String key = arg.substring(2);
                String value = null;
                if(i + 1 >= args.length) {
                    throw new RuntimeException("Param value cannot be empty");
                }

                value = args[i + 1];
                params.put(key, value);

                i++;
            } else {
                throw new RuntimeException("You must use format: --param_name");
            }
        }
        return params;
    }
}
