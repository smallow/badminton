package smallow.api;

/**
 * Created by smallow on 15/12/1.
 */
public class ApiResponse<T> {

    private String event;
    private String msg;
    private T obj;
    private T objList;
    private int currentPage;
    private int pageSize;
    private int maxCount;
    private int maxPage;

    // 构造函数，初始化code和msg
    public ApiResponse(String event, String msg) {
        this.event = event;
        this.msg = msg;
    }

    // 判断结果是否成功
    public boolean isSuccess() {
        return event.equals("0");
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public T getObjList() {
        return objList;
    }

    public void setObjList(T objList) {
        this.objList = objList;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }
}
