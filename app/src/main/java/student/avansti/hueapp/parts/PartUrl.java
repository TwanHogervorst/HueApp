package student.avansti.hueapp.parts;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PartUrl {

    private String url;

    private Map<String, String> queryParams;
    private List<String> subDirList;

    public PartUrl(String url) {
        this.url = url;

        this.queryParams = new HashMap<>();
        this.subDirList = new LinkedList<>();
    }

    public void addSubDir(String dirName) {
        this.subDirList.add(dirName);
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

        // Add subdirs
        for(String subdir : this.subDirList) {
            if(sb.charAt(sb.length() - 1) != '/') sb.append('/');
            sb.append(subdir);
        }

        // Add Query Parameters
        if(sb.charAt(sb.length() - 1) == '/') sb.deleteCharAt(sb.length() - 1);
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
