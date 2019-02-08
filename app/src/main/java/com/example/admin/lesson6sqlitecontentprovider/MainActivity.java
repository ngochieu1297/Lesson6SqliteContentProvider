package com.example.admin.lesson6sqlitecontentprovider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickContactItem {
    private RecyclerView mRecyclerContact;
    private List<Contact> mContacts = new ArrayList<>();
    private ContactAdapter mContactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, R.string.string_permission_granted,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, R.string.string_permission_denied,
                        Toast.LENGTH_SHORT).show();

            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onClickCall(int index) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                + mContacts.get(index).getPhoneNumber()));
        startActivity(intent);
    }

    @Override
    public void onClickFavorite(int index) {
        if (mContacts.get(index).isFavorite()) {
            mContacts.get(index).setFavorite(false);
        } else {
            mContacts.get(index).setFavorite(true);
        }
        mContactAdapter.notifyItemChanged(index);
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
            } else {
                getAllContactFromDevice();
            }
        }
    }

    private void getAllContactFromDevice() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null,
                null, null);
        mContacts.clear();
        while (cursor.moveToNext()) {
            String colName = ContactsContract.Contacts.DISPLAY_NAME;
            String colPhone = ContactsContract.CommonDataKinds.Phone.NUMBER;
            int indexColName = cursor.getColumnIndex(colName);
            int indexColPhone = cursor.getColumnIndex(colPhone);
            String name = cursor.getString(indexColName);
            String phone = cursor.getString(indexColPhone);
            Contact contact = new Contact(name, phone, false);
            mContacts.add(contact);
        }
        cursor.close();
        mContactAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mRecyclerContact = findViewById(R.id.recycler_contact);
        mContactAdapter = new ContactAdapter(this, mContacts, this);
        mRecyclerContact.setAdapter(mContactAdapter);
        mRecyclerContact.setLayoutManager(new LinearLayoutManager(this,
                LinearLayout.VERTICAL, false));
    }
}
