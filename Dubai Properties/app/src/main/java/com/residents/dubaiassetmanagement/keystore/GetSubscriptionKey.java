package com.residents.dubaiassetmanagement.keystore;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.residents.dubaiassetmanagement.DeCryptor;
import com.residents.dubaiassetmanagement.EnCryptor;

public class GetSubscriptionKey extends Activity
{
    private EnCryptor encryptor;
    private DeCryptor decryptor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
