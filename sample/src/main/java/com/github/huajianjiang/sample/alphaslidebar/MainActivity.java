package com.github.huajianjiang.sample.alphaslidebar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.github.huajianjiang.alphaslidebar.ExpandableTextView;
import com.github.huajianjiang.alphaslidebar.Logger;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.addItemDecoration(
                new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        rv.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends RecyclerView.Adapter {

        private List<Item> mItems = Dummy.getItems();

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(getLayoutInflater().inflate(viewType, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Item item = mItems.get(position);
            Holder h = (Holder) holder;
            Logger.e(TAG, "onBindViewHolder==>" + h.mTextView.getCompatMaxLines());
            h.mView.setVisibility(item.expandable ? View.VISIBLE : View.GONE);
            if (item.expanded) {
                h.mTextView.setMaxLines(Integer.MAX_VALUE);
            } else {
                h.mTextView.setMaxLines(3);
            }

            h.mTextView.setText(item.txt);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        @Override
        public int getItemViewType(int position) {
            return R.layout.item_text;
        }

        private class Holder extends RecyclerView.ViewHolder {
            ExpandableTextView mTextView;
            View mView;

            public Holder(View itemView) {
                super(itemView);
                mTextView = (ExpandableTextView) itemView.findViewById(R.id.txt);
                mView = itemView.findViewById(R.id.view);

                mTextView.setOnExpandableStateChangeListener(
                        new ExpandableTextView.OnExpandableStateChangeListener() {
                            @Override
                            public void onExpandableStateChanged(ExpandableTextView who,
                                    boolean expandable)
                            {
                                Item item = mItems.get(getAdapterPosition());
                                item.expandable = expandable;
                                mView.setVisibility(item.expandable ? View.VISIBLE : View.GONE);
                            }
                        });

                mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Item item = mItems.get(getAdapterPosition());
                        if (item.expanded) {
                            mTextView.setMaxLines(mTextView.getCompatMaxLines());
                        } else {
                            mTextView.setMaxLines(Integer.MAX_VALUE);
                        }
                        item.expanded = !item.expanded;
                    }
                });
            }
        }
    }

}
