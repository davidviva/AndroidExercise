package com.example.yanwu.androidexercise;

import android.Manifest;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class BleActivity extends AppCompatActivity{
    private static final String TAG = "BleActivity";
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bleAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private ScanCallback scanCallback;
    private BluetoothGattCallback mGattCallback;
    private List<BluetoothGattService> serviceList;

    private String button = "IoTButton - 214DD2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);
        ButterKnife.bind(this);

        if(!isBleSupport()) {
            Toast.makeText(this, R.string.activity_ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        initBle();

        scanCallback = new LeScanCallback();
        mGattCallback = new BluetoothGattCallback() {
            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                System.out.println("onConnectionStateChange status:" + status + "  newState:" + newState);
                if (status == 0) {
                    gatt.discoverServices();
                }
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                System.out.println("onServicesDiscovered status" + status);
                serviceList = gatt.getServices();
                if (serviceList != null) {
                    System.out.println(serviceList);
                    System.out.println("Services num:" + serviceList.size());
                }

                for (BluetoothGattService service : serviceList){
                    List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
                    System.out.println("扫描到Service：" + service.getUuid());

                    for (BluetoothGattCharacteristic characteristic : characteristics) {
                        System.out.println("characteristic: " + characteristic.getUuid() );
                    }
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        startScanLeDevices();
    }

    /**
     * Check if the device supports BLE
     * @return if the device supports BLE
     */
    private boolean isBleSupport() {
        boolean flag = getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
        System.out.println(String.valueOf(flag));
        return flag;
    }

    /**
     * check if bluetooth is enabled
     */
    private void initBle() {
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        if (bluetoothManager != null) {
            bleAdapter = bluetoothManager.getAdapter();
        }
        if (bluetoothManager == null) {//platform not support bluetooth
            Log.d(TAG, "Bluetooth is not support");
        }
        else{
            int status = bleAdapter.getState();
            System.out.println("bleAdapter status: " + status);
            //bluetooth is disabled
            if (status == BluetoothAdapter.STATE_OFF) {
                // enable bluetooth
                bleAdapter.enable();
            }
        }
        checkPermissions();
        System.out.println(String.valueOf(bleAdapter.getBondedDevices().isEmpty()));
    }

    private void checkPermissions() {
        int permissionState = PermissionChecker.checkSelfPermission(this,
                Manifest.permission.BLUETOOTH);
        boolean flag = permissionState == PackageManager.PERMISSION_GRANTED;
        System.out.println("bluetooth permission: " + String.valueOf(flag));

        int permissionState1 = PermissionChecker.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        boolean flag1 = permissionState1 == PackageManager.PERMISSION_GRANTED;
        System.out.println("fine location permission: " + String.valueOf(flag1));

        int permissionState2 = PermissionChecker.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        boolean flag2 = permissionState2 == PackageManager.PERMISSION_GRANTED;
        System.out.println("coarse location permission: " + String.valueOf(flag2));
    }

    /**
     * start Ble scan
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void startScanLeDevices() {
        System.out.println("start scan");
        //Android 4.3以上，Android 5.0以下
        //mBluetoothAdapter.startLeScan(BluetoothAdapter.LeScanCallback)

        //Android 5.0以上，扫描的结果在mScanCallback中进行处理
        bluetoothLeScanner = bleAdapter.getBluetoothLeScanner();
//        System.out.println("bluetoothLeScanner is null? " + String.valueOf(bluetoothLeScanner == null));
        bluetoothLeScanner.startScan(scanCallback);
    }

    /**
     * LE设备扫描结果返回
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private class LeScanCallback  extends ScanCallback {
        /**
         * 扫描结果的回调，每次扫描到一个设备，就调用一次。
         * @param callbackType
         * @param result
         */
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            //Log.d(Tag, "onScanResult");
            if(result != null && result.getDevice() != null && !TextUtils.isEmpty(result.getDevice().getName())){
                String name = result.getDevice().getName();
                System.out.println("扫面到设备：" + name + "  " + result.getDevice().getAddress());
//
//                //此处，我们尝试连接MI 设备
//                if (result.getDevice().getName() != null && mTargetDeviceName.equals(result.getDevice().getName())) {
//                    //扫描到我们想要的设备后，立即停止扫描
//                    result.getDevice().connectGatt(MainActivity.this, false, mGattCallback);
//                    mBluetoothLeScanner.stopScan(mScanCallback);
//                }
//                bluetoothLeScanner.stopScan(scanCallback);
                if (name.equals("IoTButton - 214DD2")) {
                    System.out.println("stop to scan");
                    bluetoothLeScanner.stopScan(scanCallback);
                    System.out.println("start to connect");
                    result.getDevice().connectGatt(BleActivity.this, false, mGattCallback);
                }
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            System.out.println("Batch scanned: ");
            for (ScanResult result: results) {
                System.out.println("Batch Scanned devices：" + result.getDevice().getName() + "  " + result.getDevice().getAddress());
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            System.out.println("Fail to start scanning, error code is: " + errorCode);
        }
    }

}
