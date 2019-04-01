package com.example.android_hinofi_prototype.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.android_hinofi_prototype.R;
import com.example.android_hinofi_prototype.adapters.DatabaseAdapter;
import com.example.android_hinofi_prototype.adapters.SearchAdapter;
import com.mancj.materialsearchbar.MaterialSearchBar;


public class SearchActivity extends AppCompatActivity {



    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter searchAdapter;
    MaterialSearchBar materialSearchBar;
//    List<String> suggestList  = new ArrayList<>();

    DatabaseAdapter databaseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //init view
        recyclerView = findViewById(R.id.recycler_search);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();

        getSupportActionBar().setTitle("Search");
        materialSearchBar = findViewById(R.id.search_bar);


        //init DB
        databaseAdapter = new DatabaseAdapter(this);

        //Sets up the search bar
        materialSearchBar.setHint("Search");
        materialSearchBar.setCardViewElevation(10);
//        loadSuggestList();
//        materialSearchBar.addTextChangeListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                List<String> suggest = new ArrayList<>();
////                for(String search:suggestList)
////                {
////                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
////
////                }
////                materialSearchBar.setLastSuggestions(suggest);
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled)
                {
//                    searchAdapter = new SearchAdapter(getBaseContext(), databaseAdapter.getMusicArtists());
                    recyclerView.setAdapter(searchAdapter);
                }

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


        //Init adapter default set all result
//        searchAdapter = new SearchAdapter(this, databaseAdapter.getMusicArtists());
        recyclerView.setAdapter(searchAdapter);
    }


    private void startSearch(String text)
    {
        searchAdapter = new SearchAdapter(this, databaseAdapter.getMusicArtistByName(text));
        recyclerView.setAdapter(searchAdapter);
    }

//    private void loadSuggestList()
//    {
//        suggestList = databaseAdapter.getArtistName();
//        materialSearchBar.setLastSuggestions(suggestList);
//    }
}
