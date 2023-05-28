package com.parsley.constant;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ResponseCodeConst {

    static {
        ResponseCodeContainer.register(OrdinaryResponseCodeConst.class,0,200);
    }

    protected int code;

    protected String msg;

    protected boolean success;

    public ResponseCodeConst() {
    }

    protected ResponseCodeConst(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
        ResponseCodeContainer.put(this);
    }

    protected ResponseCodeConst(int code, String msg, boolean success) {
        super();
        this.code = code;
        this.msg = msg;
        this.success = success;
        ResponseCodeContainer.put(this);
    }

    protected ResponseCodeConst(int code) {
        super();
        this.code = code;
        ResponseCodeContainer.put(this);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private static class ResponseCodeContainer{

        private static final Map<Integer, ResponseCodeConst> RESPONSE_CODE_MAP = new HashMap<>();
        private static final Map<Class<? extends  ResponseCodeConst>,int []> RESPONSE_CODE_RANGE_MAP = new HashMap<>();

        private static void register(Class<? extends ResponseCodeConst> clazz,int start,int end){
            if (start>end){
                throw new IllegalArgumentException("<ResponseDTO> start > end!");
            }
            if (RESPONSE_CODE_RANGE_MAP.containsKey(clazz)){
                throw new IllegalArgumentException(String.format("<ResponseDTO> Class:%s already exist!", clazz.getSimpleName()));
            }
            RESPONSE_CODE_RANGE_MAP.forEach((k,v)->{
                if((start >= v[0] && start <= v[1])||(end>=v[0]&&end<=v[1])){
                    throw new IllegalArgumentException(String.format("<ResponseDTO> Class:%s 's id range[%d,%d] has " + "intersection with " + "class:%s", clazz.getSimpleName(), start, end,
                            k.getSimpleName()));
                }
            });

            RESPONSE_CODE_RANGE_MAP.put(clazz,new int[]{start,end});

            Field[] fields = clazz.getFields();
            if (fields.length!=0){
                try {
                    fields[0].get(clazz);
                }catch (IllegalArgumentException | IllegalAccessException e){

                }
            }
        }
        private static void put(ResponseCodeConst codeConst){
            int[] idRange = RESPONSE_CODE_RANGE_MAP.get(codeConst.getClass());
            if(idRange == null){
                throw new IllegalArgumentException(String.format("<ResponseDTO> Class:%s has not been registered!", codeConst.getClass().getSimpleName()));
            }
            int code = codeConst.code;
            if(code<idRange[0]||code>idRange[1]){
                throw new IllegalArgumentException(String.format("<ResponseDTO> Id(%d) out of range[%d,%d], " + "class:%s", code, idRange[0], idRange[1], codeConst.getClass().getSimpleName()));
            }
            if(RESPONSE_CODE_MAP.keySet().contains(code)){
                System.out.println("重复了");
                System.exit(0);
            }
            RESPONSE_CODE_MAP.put(code,codeConst);
        }
    }

}
