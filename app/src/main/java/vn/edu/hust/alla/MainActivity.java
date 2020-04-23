package vn.edu.hust.alla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import io.bloco.faker.Faker;

public class MainActivity extends AppCompatActivity {

    List<EmailModel> emails;
    RecyclerView.Adapter adapter;
    EditText editText;
    Button btnFavorite;
    boolean state; //bien trang thai , false: chua an favorite , true: da an

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_search);
        btnFavorite = findViewById(R.id.btn_favorite);
        state = false;

        emails = new ArrayList<>();
        Faker fake = new Faker();
        for(int i = 0; i < 20; i++) {
            emails.add(new EmailModel(fake.color.hexColor(), fake.name.name(), fake.lorem.sentence(),fake.lorem.paragraph(),"12:03 AM"));
        }

        //lay view hien thi
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //thiet lap cach hien thi
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager); //noi voi view

        //ket noi toi adapter
        adapter = new emailAdapter(emails);
        recyclerView.setAdapter(adapter);

        //tim kiem
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<EmailModel> searchList = new ArrayList<>();
                if(s.length() < 3) {
                    adapter = new emailAdapter(emails);
                    recyclerView.setAdapter(adapter);
                }
                else {
                    for(int i = 0 ; i < emails.size() ; i++) {
                        if(emails.get(i).getName().indexOf(s.toString()) != -1 || emails.get(i).getSubject().indexOf(s.toString()) != -1 || emails.get(i).getSubject().indexOf(s.toString()) != -1) {
                            searchList.add(emails.get(i));
                        }
                    }
                    adapter = new emailAdapter(searchList);
                    recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = !state; //doi lai trang thai

                if( !state ) { //neu la xen list full

                    adapter = new emailAdapter(emails);
                    recyclerView.setAdapter(adapter);
                }
                else { //xem list favorite

                    List<EmailModel> favoriteList = new ArrayList<>();
                    for(int i = 0 ; i < emails.size() ; i++) {
                        if( emails.get(i).isFavorite() ) { //neu la favorite thi them vao list
                            favoriteList.add(emails.get(i));
                        }
                    }
                    adapter = new emailAdapter(favoriteList,state);
                    recyclerView.setAdapter(adapter);
                }

            }
        });

    }
}
