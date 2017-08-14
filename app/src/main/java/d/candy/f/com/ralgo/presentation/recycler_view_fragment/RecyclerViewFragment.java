package d.candy.f.com.ralgo.presentation.recycler_view_fragment;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import d.candy.f.com.ralgo.presentation.recycler_view_fragment.RecyclerVIewFragmentAdapter;

/**
 * Created by daichi on 17/08/14.
 */

abstract public class RecyclerViewFragment<VH extends RecyclerView.ViewHolder> extends Fragment
        implements RecyclerVIewFragmentAdapter.DispatchMethodCallListener<VH> {

    private RecyclerVIewFragmentAdapter<VH> mAdapter;

    /**
     * TODO; Call this method in onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState).
     */
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
    abstract protected RecyclerView.LayoutManager getLayoutManager();
}
