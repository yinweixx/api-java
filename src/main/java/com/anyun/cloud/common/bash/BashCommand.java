package com.anyun.cloud.common.bash;

import org.buildobjects.process.*;

import java.io.ByteArrayOutputStream;

public class BashCommand  implements LinuxCommand{
    private final String command;
    private ProcResult result;
    private Exception exception;
    private long timeout = Long.MAX_VALUE;

    public BashCommand(String command) {
        this.command = command;
    }

    @Override
    public String exec() throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            String export = "export PATH=$PATH:/bin:/sbin/:/usr/bin:/usr/sbin";
            ProcBuilder pb = new ProcBuilder("/bin/bash")
                    .withArgs("-c", export + " && " + command)
                    .withOutputStream(output)
                    .withTimeoutMillis(Long.MAX_VALUE);
            result = pb.run();
            return output.toString().trim();
        } catch (StartupException | TimeoutException | ExternalProcessFailureException e) {
            exception = e;
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getCommand() {
        return command;
    }

    public ProcResult getResult() {
        return result;
    }

    public Exception getException() {
        return exception;
    }
}
