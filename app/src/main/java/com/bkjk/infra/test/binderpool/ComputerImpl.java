package com.bkjk.infra.test.binderpool;

import android.os.RemoteException;

import com.bkjk.infra.test.IComputer;

/**
 * Author: zhouzhenhua
 * Date: 2019/3/24
 * Version: 1.0.0
 * Description:
 */
public class ComputerImpl extends IComputer.Stub {
    @Override
    public int calculate(int a, int b) throws RemoteException {
        return a + b;
    }
}
