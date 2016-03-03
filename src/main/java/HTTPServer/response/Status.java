package httpserver.response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 A status object doesn't have any behavior it only has state,
    - a code
    - a reason statement
 */
public class Status {

    public static Map<Integer, String> statusCodes;
    static  {
        Map<Integer, String> aMap = new HashMap<Integer, String>();
        aMap.put(100,"Continue");
        aMap.put(101,"Switching Protocols");
        aMap.put(200, "OK");
        aMap.put(201, "Created");
        aMap.put(202, "Accepted");
        aMap.put(203, "Non-Authoritative Information");
        aMap.put(204, "No Content");
        aMap.put(205, "Reset Content");
        aMap.put(206, "Partial Content");
        aMap.put(207, "Multi-Status");
        aMap.put(300, "Multpile Choices");
        aMap.put(301, "Moved Permanently");
        aMap.put(302, "Found");
        aMap.put(303, "See Other");
        aMap.put(304, "Not Modified");
        aMap.put(305, "Use Proxy");
        aMap.put(307, "Temporary Redirect");
        aMap.put(400, "Bad Request");
        aMap.put(401, "Unauthorized");
        aMap.put(402, "Payment Required");
        aMap.put(403, "Forbidden");
        aMap.put(404, "Not Found");
        aMap.put(405, "Method Not Allowed");
        aMap.put(406, "Not Acceptable");
        aMap.put(407, "Proxy Authentication Required");
        aMap.put(408, "Request Timeout");
        aMap.put(409, "Conflict");
        aMap.put(410, "Gone");
        aMap.put(411, "Length Required");
        aMap.put(412, "Precondition failed");
        aMap.put(413, "Request Entity Too Large");
        aMap.put(414, "Request-URI Too Large");
        aMap.put(415, "Unsupported Media Type");
        aMap.put(416, "Request Range Not Satisfiable");
        aMap.put(417, "Expectation Failed");
        aMap.put(422, "Unprocessable Entity");
        aMap.put(423, "Locked");
        aMap.put(424, "Failed Dependency");
        aMap.put(426, "Upgrade Required");
        aMap.put(428, "Precondition Required");
        aMap.put(429, "Too Many Requests");
        aMap.put(431, "Request Header Fields Too Large");
        aMap.put(451, "Unavailable For Legal Reasons");
        aMap.put(500, "Internal Server Error");
        aMap.put(501, "Not Implemented");
        aMap.put(502, "Bad Gateway");
        aMap.put(503, "Service Unavailable");
        aMap.put(504, "Gateway Timeout");
        aMap.put(505, "HTTP Version Not Supported");
        aMap.put(507, "Insufficient Storage");
        aMap.put(511, "Network Authentication Required");
        statusCodes = Collections.unmodifiableMap(aMap);
    }
}

