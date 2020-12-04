package student.avansti.hueapp.parts;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PartUrl {

    private String url;

    private Map<String, String> queryParams;
    private List<String> subPathList;

    public PartUrl(String url) {
        this.url = url;

        this.queryParams = new HashMap<>();
        this.subPathList = new LinkedList<>();
    }

    public void addSubPath(String subPathName) {
        this.subPathList.add(subPathName);
    }

    public boolean containsSubPath(String subPathName) {
        return this.subPathList.contains(subPathName);
    }

    public void removeSubPath(String subPathName) {
        this.subPathList.remove(subPathName);
    }

    public void addQueryParam(String key, String value) {
        this.queryParams.put(key, value);
    }

    public boolean containsQueryParam(String key) {
        return this.queryParams.containsKey(key);
    }

    public void removeQueryParam(String key) {
        this.queryParams.remove(key);
    }

    public String result() {
        // Create StringBuilder based on base url
        StringBuilder sb = new StringBuilder(this.url);

        // Add subpaths
        for(String subPath : this.subPathList) {
            if(sb.charAt(sb.length() - 1) != '/') sb.append('/');
            sb.append(subPath);
        }

        // remove last /
        if(sb.charAt(sb.length() - 1) == '/') sb.deleteCharAt(sb.length() - 1);

        // Add Query Parameters
        boolean isFirst = true;
        for(Map.Entry<String, String> queryParam : this.queryParams.entrySet()) {
            if(isFirst) { sb.append('?'); isFirst = false; }
            else sb.append('&');

            sb.append(queryParam.getKey());
            sb.append('=');
            sb.append(queryParam.getValue());
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return this.result();
    }

}
