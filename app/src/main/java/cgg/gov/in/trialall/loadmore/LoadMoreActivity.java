package cgg.gov.in.trialall.loadmore;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cgg.gov.in.trialall.R;

public class LoadMoreActivity extends AppCompatActivity {

    private List<Contact> contacts;
    private ContactAdapter contactAdapter;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_main);

        contacts = new ArrayList<>();
        random = new Random();

        //set dummy data
        for (int i = 0; i < 10; i++) {
            Contact contact = new Contact();
            contact.setPhone(phoneNumberGenerating());
            contact.setEmail("DevExchanges" + i + "@gmail.com");
            contacts.add(contact);
        }

        //find view by id and attaching adapter for the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactAdapter = new ContactAdapter(recyclerView, contacts, this);
        recyclerView.setAdapter(contactAdapter);

        //set load more listener for the RecyclerView adapter
        contactAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (contacts.size() <= 20) {
                    contacts.add(null);
                    contactAdapter.notifyItemInserted(contacts.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            contacts.remove(contacts.size() - 1);
                            contactAdapter.notifyItemRemoved(contacts.size());

                            //Generating more data
                            int index = contacts.size();
                            int end = index + 10;
                            for (int i = index; i < end; i++) {
                                Contact contact = new Contact();
                                contact.setPhone(phoneNumberGenerating());
                                contact.setEmail("DevExchanges" + i + "@gmail.com");
                                contacts.add(contact);
                            }
                            contactAdapter.notifyDataSetChanged();
                            contactAdapter.setLoaded();
                        }
                    }, 5000);
                } else {
                    Toast.makeText(LoadMoreActivity.this, "Loading data completed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String phoneNumberGenerating() {
        int low = 100000000;
        int high = 999999999;
        int randomNumber = random.nextInt(high - low) + low;

        return "0" + randomNumber;
    }
}