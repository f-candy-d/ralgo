package d.candy.f.com.ralgo.presentation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import d.candy.f.com.ralgo.R;

/**
 * Created by daichi on 17/08/14.
 */

abstract public class RecyclerViewFragment<VH extends RecyclerView.ViewHolder> extends Fragment
        implements RecyclerVIewFragmentAdapter.DispatchMethodCallListener<VH> {

    private RecyclerVIewFragmentAdapter<VH> mAdapter;

    protected View onCreateView(LayoutInflater inflater, ViewGroup container, int layoutId, int recyclerViewId) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(layoutId, container, false);
        RecyclerView recyclerView = view.findViewById(recyclerViewId);
        init(recyclerView);
        return view;
    }

    private void init(RecyclerView recyclerView) {
        mAdapter = new RecyclerVIewFragmentAdapter<>(this);
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setAdapter(mAdapter);
    }

    public RecyclerVIewFragmentAdapter<VH> getAdapter() {
        return mAdapter;
    }

    @NonNull
    abstract RecyclerView.LayoutManager getLayoutManager();
}
