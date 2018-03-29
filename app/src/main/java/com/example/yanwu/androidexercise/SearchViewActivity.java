package com.example.yanwu.androidexercise;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.yanwu.androidexercise.widget.SearchEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * APIs:
 *  1.android:iconifiedByDefault: the default state of SearchView
 *  2.android:imeOptions: the IME options to set on the query text field
 *  3.android:inputType: the input type to set on the query text field
 *  4.android:maxWidth: an option maximum width of the searchview
 *  5.android:queryHint: an option query hint string to be displayed in the empty query field
 *
 * Listener:
 *  1.SearchView.OnCloseListener
 *  2.SearchView.OnQueryTextListener
 *  3.SearchView.onSuggestionListener
 *
 */
public class SearchViewActivity extends BaseActivity {
    private static final String TAG = "SearchView";
    private String[] mDatas = {"aaa", "bbb", "ccc", "airsaid"};
    private ArrayAdapter adapter;

    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.listView)
    ListView mListView;

//    @BindView(R.id.search_edit_text)
//    SearchEditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        ButterKnife.bind(this);

//        searchEditText.setOnSearchClickListener(new SearchEditText.OnSearchClickListener() {
//            @Override
//            public void onSearchClick(View view) {
//                Log.d(TAG, "going to search");
//                search(searchEditText.getText().toString());
//            }
//        });
//    }
//
//    private void search(String s) {
//        System.out.println(s);
//    }


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
        mListView.setAdapter(adapter);
        mListView.setTextFilterEnabled(true);

        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}
