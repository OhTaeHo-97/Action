package com.example.action;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditInfoActivity extends AppCompatActivity {
    Button close;
    Button edit_info;
    Button info;
    EditText phone;

    private ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editinfo_activity);

        close=(Button)findViewById(R.id.close);
        edit_info=(Button)findViewById(R.id.edit_info);
        info=(Button)findViewById(R.id.info);
        phone=(EditText)findViewById(R.id.phone);
        progressDialog=new ProgressDialog(this);

        String email="";
        String uid="";

        progressDialog.setMessage("처리중입니다. 잠시 기다려 주세요...");
        progressDialog.show();

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if(user!=null){
//            email=user.getEmail();
//            uid=user.getUid();
//        }
//
//        mDatabase=FirebaseDatabase.getInstance();
//        mReference=mDatabase.getReference("Users/"+uid+"/phone");
//        mReference.addValueEventListener(new ValueEventListener(){
//            public void onDataChange(DataSnapshot dataSnapshot){
//                String phone_num=dataSnapshot.getValue().toString();
//                phone.setText(phone_num);
//            }
//
//            public void onCancelled(DatabaseError databaseError){
//
//            }
//        });

        progressDialog.dismiss();

        info.setText(email);

        final String uid_final=uid;
        edit_info.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String phone_number=phone.getText().toString().trim();
//                reference=mDatabase.getReference();
//                reference.child("Users").child(uid_final).child("phone").setValue(phone_number);
//
//                new AlertDialog.Builder(EditInfoActivity.this)
//                        .setTitle("정보 변경 완료")
//                        .setMessage("입력하신 정보로 수정이 완료되었습니다.")
//                        .setNeutralButton("확인", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dlg, int sumthin) {
//                                Intent intent=new Intent(getApplicationContext(), BottomBarActivity.class);
//                                startActivity(intent);
//                            }
//                        })
//                        .show(); // 팝업창 보여줌
            }
        });

        close.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(), BottomBarActivity.class);
                startActivity(intent);
            }
        });
    }
}
