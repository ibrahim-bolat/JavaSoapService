package org.example.client.security;

import org.apache.wss4j.common.ext.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClientPasswordCallback implements CallbackHandler {

    private Map<String, String> passwords = new HashMap<>();

    public ClientPasswordCallback() {
        passwords.put("ibo", "123456");
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            WSPasswordCallback pc = (WSPasswordCallback) callback;

            String password = passwords.get(pc.getIdentifier());
            if (password != null) {
                pc.setPassword(password);
                return;
            }
        }
    }
}