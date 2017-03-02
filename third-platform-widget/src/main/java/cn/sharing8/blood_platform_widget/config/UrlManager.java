package cn.sharing8.blood_platform_widget.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by hufei on 2017/2/6.
 * url请求调度管理器
 */

public class UrlManager {

    private Map<String, List<Call>> callListMap = new HashMap<>();

    private static UrlManager instance = new UrlManager();

    private UrlManager() {
    }

    public static UrlManager getInstance() {
        return instance;
    }

    public void putCallList(String tagName, Call... calls) {
        if (callListMap.containsKey(tagName)) {
            callListMap.get(tagName).addAll(new LinkedList<>(Arrays.asList(calls)));
        } else {
            callListMap.put(tagName, new LinkedList<>(Arrays.asList(calls)));
        }
    }

    public void removeCall(String tagName, Call call) {
        if (callListMap.containsKey(tagName)) {
            List<Call> callList = callListMap.get(tagName);
            if (callList.contains(call)) {
                callList.remove(call);
            }
            if (callList.size() == 0) {
                callListMap.remove(tagName);
            }
        }
    }

    public List<Call> getCallListByTagName(String tagName) {
        if (callListMap.containsKey(tagName)) {
            return callListMap.get(tagName);
        }
        return null;
    }

    public void cancelCallByTagName(String tagName, boolean isRemoveInReqsManager) {
        if (callListMap.containsKey(tagName)) {
            List<Call> callList = callListMap.get(tagName);
            if (callList != null && !callList.isEmpty()) {
                for (Call call : callList) {
                    if (!call.isCanceled()) {
                        call.cancel();
                    }
                }
            }
            if (isRemoveInReqsManager) {
                callListMap.remove(tagName);
            }
        }
    }
}
