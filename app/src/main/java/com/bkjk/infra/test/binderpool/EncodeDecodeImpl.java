package com.bkjk.infra.test.binderpool;

import android.os.RemoteException;

import com.bkjk.infra.test.IEncodeDecode;

/**
 * Author: zhouzhenhua
 * Date: 2019/3/24
 * Version: 1.0.0
 * Description:
 */
public class EncodeDecodeImpl extends IEncodeDecode.Stub {

    private static final char SECRET_CODE = '^';

    @Override
    public String encode(String password) throws RemoteException {
        char[] chars = password.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= SECRET_CODE;
        }
        return new String(chars);
    }

    @Override
    public String decode(String password) throws RemoteException {
        return encode(password);
    }
}
