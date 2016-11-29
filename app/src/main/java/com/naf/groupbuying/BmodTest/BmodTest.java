package com.naf.groupbuying.BmodTest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.naf.groupbuying.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/11/29.
 */

public class BmodTest extends AppCompatActivity {
    @BindView(R.id.name)
    EditText mName;
    @BindView(R.id.age)
    EditText mAge;
    @BindView(R.id.save)
    Button mSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmob);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.save)
    public void onClick() {
        User user=new User();
        user.setName("Willian");
        user.setAge(20);

        Student student=new Student();
        student.setHobby("football");
        student.setNum("22");

        Worker worker=new Worker();
        worker.setFoot("apple");
        worker.setMovie("lie");
        user.setStudent(student);
        user.setWorker(worker);

        user.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Toast.makeText(BmodTest.this, "success", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(BmodTest.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
