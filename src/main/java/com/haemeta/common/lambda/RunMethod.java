package com.haemeta.common.lambda;

public class RunMethod {

    public static interface Run{
        void run();
    }

    public static interface RunBack<Return>{
        Return run();
    }

    public static interface RunArray{

    }

}
