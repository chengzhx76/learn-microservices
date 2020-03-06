package com.github.chengzhx76.rpc.framework.test;

import com.github.chengzhx76.rpc.framework.RpcFramework;

/**
 * Desc:
 * Author: 光灿
 * Date: 2019/5/26
 */
public class RpcProvider {

    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        RpcFramework.export(service, 1234);
    }

}
