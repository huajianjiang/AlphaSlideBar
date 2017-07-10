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
            final ExpandableTextView tv = h.tv;
            h.more.setVisibility(item.expandable ? View.VISIBLE : View.GONE);
            if (item.expanded) {
                tv.setMaxLines(Integer.MAX_VALUE);
            } else {
                tv.setMaxLines(tv.getOriginalMaxLines());
            }
            tv.setText(item.txt);
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
            ExpandableTextView tv;
            View more;

            public Holder(View itemView) {
                super(itemView);
                tv = (ExpandableTextView) itemView.findViewById(R.id.txt);
                more = itemView.findViewById(R.id.view);

                tv.setOnExpandableStateChangeListener(
                        new ExpandableTextView.OnExpandableStateChangeListener() {
                            @Override
                            public void onExpandableStateChanged(ExpandableTextView who,
                                    boolean expandable)
                            {
                                Item item = mItems.get(getAdapterPosition());
                                item.expandable = expandable;
                                more.setVisibility(item.expandable ? View.VISIBLE : View.GONE);
                            }
                        });
                tv.setExpandListener(new ExpandableTextView.OnExpandListener() {
                    @Override
                    public void onExpand(ExpandableTextView who) {
                        Item item = mItems.get(getAdapterPosition());
                        item.expanded = true;
                    }
                });

                tv.setCollapseListener(new ExpandableTextView.OnCollapseListener() {
                    @Override
                    public void onCollpase(ExpandableTextView who) {
                        Item item = mItems.get(getAdapterPosition());
                        item.expanded = false;
                    }
                });

                more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Item item = mItems.get(getAdapterPosition());
                        final boolean expanded = item.expanded;
                        if (expanded) {
                            Logger.e(TAG, "request collapse");
                            tv.collapse(true);
                        } else {
                            Logger.e(TAG, "request expand");
                            tv.expand(true);
                        }
                    }
                });
            }
        }
    }

}
