package apps.aw.photoviewer.android.ui.browser.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import apps.aw.photoviewer.R;
import apps.aw.photoviewer.java.ItemListWithFocus;
import apps.aw.photoviewer.android.ui.browser.custom_views.NavigationItemView;
import apps.aw.photoviewer.java.ListItem;

public class FileListNavigationAdapter extends RecyclerView.Adapter<FileListNavigationAdapter.ViewHolder> {

    private ItemListWithFocus itemListWithFocus; //contains the filelist as well as the current selection
    private Context context;
    private ItemFocusedListener itemFocusedListener;    //handles focus events
    private ItemClickListener itemClickListener;        //handles the click events
    private NavigationItemView.NavigationKeyHandler navigationKeyHandler;

    /**
     * Constructor.
     * @param itemListWithFocus
     */
    public FileListNavigationAdapter(
            ItemListWithFocus itemListWithFocus,
            ItemFocusedListener itemFocusedListener,
            ItemClickListener itemClickListener,
            NavigationItemView.NavigationKeyHandler navigationKeyHandler,
            Context context
            ) {
        this.itemListWithFocus = itemListWithFocus;
        this.itemFocusedListener = itemFocusedListener;
        this.itemClickListener = itemClickListener;
        this.navigationKeyHandler = navigationKeyHandler;
        this.context = context;
    }

    /**
     * Sets the file list (with selection and state).
     * @param itemListWithFocus FileListSelection object
     */
    public void setFileListSelection(ItemListWithFocus itemListWithFocus) {
        Log.i("FileListAdapter", "setFileListSelection() length: " + itemListWithFocus.getListSize());
        this.itemListWithFocus = itemListWithFocus;
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
        int p = holder.getAbsoluteAdapterPosition();
        ListItem listItem = itemListWithFocus.getItemList().get(p);
        holder.text.setText(listItem.name); //set text
        switch (listItem.type) {    // set icon
            case ROOT:
                holder.icon.setImageResource(R.drawable.ic_root);
                break;
            case ACTION:
                holder.icon.setImageResource(R.drawable.ic_add);
                break;
            case DIRECTORY:
                holder.icon.setImageResource(R.drawable.ic_folder);
                break;
            case IMAGE:
                holder.icon.setImageResource(R.drawable.ic_image);
                break;
            case BROKEN_IMAGE:
                holder.icon.setImageResource(R.drawable.ic_broken_image);
                break;
            case FILE:
                holder.icon.setImageResource(R.drawable.ic_file);
                break;
        }
        holder.view.setOnFocusChangeListener(
                (v, hasFocus) -> {
                    itemFocusedListener.onItemFocusChanged(p, hasFocus);
                }
        );
        holder.view.setOnClickListener(v -> {
            itemClickListener.onItemClick(p);
//            if(v.hasFocus()) { // is it necessary to check this?
//                itemClickListener.onItemClick(p);
//            }
        });
        holder.view.setKeyPressHandler(navigationKeyHandler);
        if(position == itemListWithFocus.getFocus()) {
            boolean f = holder.view.requestFocus();
            Log.i("FileListNavigationAdpt", "view took focus: " + f);
        }
    }

    @Override
    public int getItemCount() {
        return itemListWithFocus.getListSize();
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
            icon = (ImageView) itemView.findViewById(R.id.iconImageView);
            text = (TextView) itemView.findViewById(R.id.fileNameTextView);
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
