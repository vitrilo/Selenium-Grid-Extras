package com.groupon.seleniumgridextras.videorecording;

import com.groupon.seleniumgridextras.config.RuntimeConfig;
import com.groupon.seleniumgridextras.grid.proxies.SetupTeardownProxy;
import com.groupon.seleniumgridextras.tasks.config.TaskDescriptions;
import com.groupon.seleniumgridextras.utilities.HttpUtility;
import com.groupon.seleniumgridextras.utilities.json.JsonCodec;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.openqa.grid.internal.TestSession;


public class RemoteVideoRecorderHelper {
    private static Logger logger = Logger.getLogger(RemoteVideoRecorderHelper.class);

    public static String startVideoRecording(TestSession session) {

        String host = session.getSlot().getRemoteURL().getHost();
        int port = SetupTeardownProxy.calcGridExtractPort(session);
        String sessionKey = session.getExternalKey().getKey();
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http");
        builder.setHost(host);
        builder.setPort(port);
        builder.setPath(TaskDescriptions.Endpoints.VIDEO);

        Map<String, String> params = getBlankParams(sessionKey, JsonCodec.Video.START);

        for (String p : params.keySet()) {
            builder.addParameter(p, params.get(p));
        }
        URI uri;
        String errorMessage = String.format("Error building URI for host: %s, port: %s, session: %s, action: %s, params: %s",
                host,
                port,
                sessionKey,
                JsonCodec.Video.START,
                params.toString());
        try {
            uri = builder.build();
            return HttpUtility.getRequestAsString(uri);
        } catch (URISyntaxException e) {
            logger.error(errorMessage, e);
            return "";
        } catch (IOException e) {
            logger.error(errorMessage, e);
            return "";
        }


    }

    public static String stopVideoRecording(TestSession session) {

        String host = session.getSlot().getRemoteURL().getHost();
        int port = SetupTeardownProxy.calcGridExtractPort(session);
        String sessionKey = session.getExternalKey().getKey();
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http");
        builder.setHost(host);
        builder.setPort(port);
        builder.setPath(TaskDescriptions.Endpoints.VIDEO);

        Map<String, String> params = getBlankParams(sessionKey, JsonCodec.Video.STOP);
        for (String p : params.keySet()) {
            builder.addParameter(p, params.get(p));
        }
        URI uri;
        String errorMessage = String.format("Error building URI for host: %s, port: %s, session: %s, action: %s, params: %s",
                host,
                port,
                sessionKey,
                JsonCodec.Video.STOP,
                params.toString());
        try {
            uri = builder.build();

            return HttpUtility.getRequestAsString(uri);
        } catch (URISyntaxException e) {
            logger.error(errorMessage, e);
            return "";
        } catch (IOException e) {
            logger.error(errorMessage, e);
            return "";
        }

    }

    public static String updateLastAction(TestSession session, String action) {
        String host = session.getSlot().getRemoteURL().getHost();
        int port = SetupTeardownProxy.calcGridExtractPort(session);
        String sessionKey = session.getExternalKey().getKey();
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http");
        builder.setHost(host);
        builder.setPort(port);
        builder.setPath(TaskDescriptions.Endpoints.VIDEO);

        Map<String, String> params = getBlankParams(sessionKey, JsonCodec.Video.HEARTBEAT);
        params.put(JsonCodec.Video.DESCRIPTION, action);

        for (String p : params.keySet()) {
            builder.addParameter(p, params.get(p));
        }
        URI uri;
        String errorMessage = String.format("Error building URI for host: %s, port: %s, session: %s, action: %s, params: %s",
                host,
                port,
                sessionKey,
                JsonCodec.Video.HEARTBEAT,
                params.toString());
        try {
            uri = builder.build();
            return HttpUtility.getRequestAsString(uri);
        } catch (URISyntaxException e) {
            logger.error(errorMessage, e);
            return "";
        } catch (IOException e) {
            logger.error(errorMessage, e);
            return "";
        }

    }

    protected static Map<String, String> getBlankParams(String session, String action) {
        Map<String, String> params = new HashMap<String, String>();

        params.put(JsonCodec.Video.SESSION, session);
        params.put(JsonCodec.Video.ACTION, action);

        return params;
    }
}
