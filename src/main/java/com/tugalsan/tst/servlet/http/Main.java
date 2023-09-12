package com.tugalsan.tst.servlet.http;

import com.tugalsan.api.file.client.*;
import com.tugalsan.api.log.server.*;
import com.tugalsan.api.servlet.http.server.*;
import com.tugalsan.api.string.client.*;
import com.tugalsan.api.tuple.client.*;
import com.tugalsan.api.validator.client.*;

public class Main {

    final private static TS_Log d = TS_Log.of(true, Main.class);

    //HOW TO EXECUTE
    //WHEN RUNNING IN NETBEANS, ALL DEPENDENCIES SHOULD HAVE TARGET FOLDER!
    //cd C:\me\codes\com.tugalsan\tst\com.tugalsan.tst.serialcom
    //java --enable-preview --add-modules jdk.incubator.vector -jar target/com.tugalsan.tst.serialcom-1.0-SNAPSHOT-jar-with-dependencies.jar    
    public static void main(String[] args) {
        var settings = Settings.of(Settings.pathDefault());
        TGS_ValidatorType1<TS_SHttpHandlerRequest> allow = request -> {
            if (!request.isLocal()) {
                request.sendError404("ERROR: Will work only localhost 😠");
                return false;
            }
            return true;
        };
        var customTextHandler = TS_SHttpHandlerText.of("/", allow, request -> {
            return TGS_Tuple2.of(TGS_FileTypes.htm_utf8, TGS_StringUtils.concat(
                    "<html><head><script>location.reload();</script></head><body>",
                    request.url.toString(),
                    "<body></html>"
            ));
        });
        TS_SHttpServer.of(
                TS_SHttpConfigNetwork.of(settings.ip, settings.sslPort),
                TS_SHttpConfigSSL.of(settings.sslPath, settings.sslPass, settings.redirectToSSL),
                allow, settings.pathFileServer, customTextHandler
        );
    }
}
