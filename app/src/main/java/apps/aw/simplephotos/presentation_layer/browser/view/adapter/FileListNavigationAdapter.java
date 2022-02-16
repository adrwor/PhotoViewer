package apps.aw.simplephotos.presentation_layer.browser.view.adapter;


import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import apps.aw.simplephotos.R;
import apps.aw.simplephotos.presentation_layer.browser.utils.FileList;
import apps.aw.simplephotos.presentation_layer.browser.view.views.NavigationItemView;

public class FileListNavigationAdapter extends RecyclerView.Adapter<FileListNavigationAdapter.ViewHolder> {

    private FileList fileList; //contains the filelist as well as the current selection
    private ItemFocusedListener itemFocusedListener;    //handles focus events
    private ItemClickListener itemClickListener;        //handles the click events
    private NavigationItemView.NavigationKeyHandler navigationKeyHandler;

    /**
     * Constructor.
     * @param fileList
     */
    public FileListNavigationAdapter(
            FileList fileList,
            ItemFocusedListener itemFocusedListener,
            ItemClickListener itemClickListener,
            NavigationItemView.NavigationKeyHandler navigationKeyHandler
            ) {
        this.fileList = fileList;
        this.itemFocusedListener = itemFocusedListener;
        this.itemClickListener = itemClickListener;
        this.navigationKeyHandler = navigationKeyHandler;
    }

    /**
     * Sets the file list (with selection and state).
     * @param fileList FileListSelection object
     */
    public void setFileListSelection(FileList fileList) {
        Log.i("FileListAdapter", "setFileListSelection() length: " + fileList.getListSize());
        this.fileList = fileList;
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
        holder.text.setText(fileList.getFileList().get(p)); //set text
//        holder.view.setOnClickListener(view -> itemClickListener.onItemClick(p));
        holder.view.setOnFocusChangeListener(
                (v, hasFocus) -> {
                    itemFocusedListener.onItemFocusChanged(p, hasFocus);
                    if(hasFocus) {
//                        holder.view.setBackgroundColor(Color.GRAY);
                        holder.view.requestFocus();
                        holder.view.findFocus().requestFocus();
                    }
                    else {
//                        holder.view.setBackgroundColor(Color.TRANSPARENT);
                    }
                }
        );
        holder.view.setKeyPressHandler(navigationKeyHandler);
        if(position == fileList.getFocus()) {
            boolean f = holder.view.requestFocus();
            Log.i("FileListNavigationAdpt", "view took focus: " + f);
        }
    }

    @Override
    public int getItemCount() {
//        Log.i("FileListAdapter", "getItemCount()");
        return fileList.getListSize();
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
