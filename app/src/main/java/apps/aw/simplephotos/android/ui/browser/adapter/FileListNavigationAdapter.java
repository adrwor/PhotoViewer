package apps.aw.simplephotos.android.ui.browser.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import apps.aw.simplephotos.R;
import apps.aw.simplephotos.java.ItemList;
import apps.aw.simplephotos.android.ui.browser.custom_views.NavigationItemView;

public class FileListNavigationAdapter extends RecyclerView.Adapter<FileListNavigationAdapter.ViewHolder> {

    private ItemList itemList; //contains the filelist as well as the current selection
    private Context context;
    private ItemFocusedListener itemFocusedListener;    //handles focus events
    private ItemClickListener itemClickListener;        //handles the click events
    private NavigationItemView.NavigationKeyHandler navigationKeyHandler;

    /**
     * Constructor.
     * @param itemList
     */
    public FileListNavigationAdapter(
            ItemList itemList,
            ItemFocusedListener itemFocusedListener,
            ItemClickListener itemClickListener,
            NavigationItemView.NavigationKeyHandler navigationKeyHandler,
            Context context
            ) {
        this.itemList = itemList;
        this.itemFocusedListener = itemFocusedListener;
        this.itemClickListener = itemClickListener;
        this.navigationKeyHandler = navigationKeyHandler;
        this.context = context;
    }

    /**
     * Sets the file list (with selection and state).
     * @param itemList FileListSelection object
     */
    public void setFileListSelection(ItemList itemList) {
        Log.i("FileListAdapter", "setFileListSelection() length: " + itemList.getListSize());
        this.itemList = itemList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("FileListAdapter", "onCreateViewHolder()");
        // create a new view
        NavigationItemView v = (NavigationItemView) LayoutInflater.from(parent.getContext()).inflate(R.layout.file_view, parent, false);
        v.setFocusable(true);
        return new ViewHolder(v);   //return a new viewholder
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i("FileListAdapter", "onBindViewHolder()");
        //holder.icon.setImageDrawable(null);   //TODO: set drawable according to file type
        int p = holder.getAdapterPosition();
        holder.text.setText(itemList.getFileList().get(p));
        holder.view.setOnFocusChangeListener(
                (v, hasFocus) -> {
                    itemFocusedListener.onItemFocusChanged(p, hasFocus);
                }
        );
        holder.view.setOnClickListener(v -> {
            if(v.hasFocus()) {
                itemClickListener.onItemClick(p);
            }
        });
        holder.view.setKeyPressHandler(navigationKeyHandler);
        if(position == itemList.getFocus()) {
            boolean f = holder.view.requestFocus();
            Log.i("FileListNavigationAdpt", "view took focus: " + f);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.getListSize();
    }




    /**
     * ViewHolder
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView text;
        public NavigationItemView view;

        public ViewHolder(NavigationItemView itemView) {
            super(itemView);
            Log.i("FileListAdapter", "ViewHolder()");
            icon = (ImageView) itemView.findViewById(R.id.imageView2);
            text = (TextView) itemView.findViewById(R.id.textView4);
            view = itemView;
        }
    }


    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public interface ItemFocusedListener {
        void onItemFocusChanged(int position, boolean hasFocus);
    }

}
