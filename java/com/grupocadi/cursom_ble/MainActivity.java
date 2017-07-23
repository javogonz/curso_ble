package com.grupocadi.cursom_ble;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    BluetoothManager btManager;
    BluetoothAdapter btAdapter;
    BluetoothLeScanner btScanner;
    Button inicioEscaneoButton;
    Button detenerEscaneoButton;
    TextView perifericoTextView;
    private final static int REQUEST_ENABLE_BT = 1;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        perifericoTextView = (TextView) findViewById(R.id.PerifericoTextView);
        perifericoTextView.setMovementMethod(new ScrollingMovementMethod());

        inicioEscaneoButton = (Button) findViewById(R.id.InicioEscaneoButton);
        inicioEscaneoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                iniciarEscaneo();
            }
        });

        detenerEscaneoButton = (Button) findViewById(R.id.DetenerEscaneoButton);
        detenerEscaneoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                detenerEscaneo();
            }
        });

        detenerEscaneoButton.setVisibility(View.INVISIBLE);

        btManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        btScanner = btAdapter.getBluetoothLeScanner();

        if (btAdapter != null && !btAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }

        // Debemos estar seguros de tener acceso a - coarse location. Si no, entonces que el usuario la habilite
        if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Esta app requiere acceso a la ubicación");
            builder.setMessage("Por favor habilite el acceso a la ubicación para que esta app detecte los periféricos.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                }
            });
            builder.show();
        }
    }

    private ScanCallback leScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            //perifericoTextView.append("Nombre del dispositivo: " + result.getDevice().getName() + " RSSI: " + result.getRssi() + "\n");
            ;


            if (result == null
                    || result.getDevice() == null
                    || TextUtils.isEmpty(result.getDevice().getName()))
                return;

            if (result.getScanRecord() != null) {
                // EJEMPLO 01
                perifericoTextView.append("Dispositivo: " + result.getDevice().getName() + " RSSI: " + result.getRssi() + "\n");
                perifericoTextView.append("Mac: " + result.getDevice().getAddress() + "\n\n");

                // EJEMPLO 02
                /*perifericoTextView.append("Dispositivo: " + result.getDevice().getName() + " RSSI: " + result.getRssi() + "\n");
                perifericoTextView.append("Mac: " + result.getDevice().getAddress() + "\n");
                perifericoTextView.append("ScanRecord: " + result.getScanRecord().toString() + "\n");*/

                // EJEMPLO 03
                /*perifericoTextView.append("Dispositivo: " + result.getDevice().getName() + " RSSI: " + result.getRssi() + "\n");
                perifericoTextView.append("Mac: " + result.getDevice().getAddress() + "\n");
                perifericoTextView.append("UUID: " + getUUID(result) + "\n\n");*/

                // EJEMPLO 04
                /*perifericoTextView.append("Dispositivo: " + result.getDevice().getName() + " RSSI: " + result.getRssi() + "\n");
                perifericoTextView.append("Mac: " + result.getDevice().getAddress() + "\n");
                perifericoTextView.append("UUID: " + result.getDevice().getUuids().toString() + "\n\n");*/

                // EJEMPLO 05
                /*perifericoTextView.append("Dispositivo: " + result.getDevice().getName() + " RSSI: " + result.getRssi() + "\n");
                perifericoTextView.append("Mac: " + result.getDevice().getAddress() + "\n");
                List<ParcelUuid> uuids = result.getScanRecord().getServiceUuids();
                perifericoTextView.append("UUID: " + uuids.toString() + "\n\n");*/

                // EJEMPLO 06
                /*ScanRecord record = result.getScanRecord();
                UUID uuid = null;
                //// https://www.bluetooth.com/specifications/assigned-numbers/company-identifiers
                byte[] appleData = record.getManufacturerSpecificData(0x004c);
                if (appleData != null) {
                    ByteBuffer bb = ByteBuffer.wrap(appleData, 2, 16);
                    uuid = new UUID(bb.getLong(), bb.getLong());
                }*/

                /*perifericoTextView.append("Dispositivo: " + result.getDevice().getName() + " RSSI: " + result.getRssi() + "\n");
                perifericoTextView.append("Mac: " + result.getDevice().getAddress() + "\n");
                perifericoTextView.append("UUID: " + uuid.toString() + "\n\n");*/

                // EJEMPLO 07
                /*perifericoTextView.append("Dispositivo: " + result.getDevice().getName() + " RSSI: " + result.getRssi() + "\n");
                perifericoTextView.append("Mac: " + result.getDevice().getAddress() + "\n");
                perifericoTextView.append("UUID: " + result.getScanRecord().getServiceData(result.getScanRecord().getServiceUuids().get(0)) + "\n\n");*/



                // auto scroll para TextView
                final int scrollTam = perifericoTextView.getLayout().getLineTop(perifericoTextView.getLineCount()) - perifericoTextView.getHeight();
                // si no se requiere del scroll, scrollCantidad sera <=0
                if (scrollTam > 0)
                    perifericoTextView.scrollTo(0, scrollTam);

            } else {
                return;
            }

        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            Toast.makeText(getApplicationContext(),"onScanResult error: "+errorCode,Toast.LENGTH_SHORT).show();
            super.onScanFailed(errorCode);
        }

    };

    public String getUUID(ScanResult result) {
        String UUIDx = UUID
                .nameUUIDFromBytes(result.getScanRecord().getBytes()).toString();

        // String UUIDx = result.getScanRecord().getServiceUuids().toString();
        return UUIDx;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("Permisos de ubicación habilitados");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Funcionalidad limitada");
                    builder.setMessage("Los permisos de ubicación no han sido habilitados, esta app no podrá encontrar los beacons cuando este en ejecución.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }

    public void iniciarEscaneo() {
        System.out.println("Iniciar escaneo");
        perifericoTextView.setText("");
        inicioEscaneoButton.setVisibility(View.INVISIBLE);
        detenerEscaneoButton.setVisibility(View.VISIBLE);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btScanner.startScan(leScanCallback);
            }
        });
    }

    public void detenerEscaneo() {
        System.out.println("Detener escaneo");
        perifericoTextView.append("Escaneo terminado");
        inicioEscaneoButton.setVisibility(View.VISIBLE);
        detenerEscaneoButton.setVisibility(View.INVISIBLE);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btScanner.stopScan(leScanCallback);
            }
        });
    }

}
