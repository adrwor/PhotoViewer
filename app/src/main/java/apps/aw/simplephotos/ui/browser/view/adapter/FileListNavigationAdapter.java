package apps.aw.simplephotos.ui.browser.view.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import apps.aw.simplephotos.R;
import apps.aw.simplephotos.domain.FileList;
import apps.aw.simplephotos.ui.browser.view.customviews.NavigationItemView;

public class FileListNavigationAdapter extends RecyclerView.Adapter<FileListNavigationAdapter.ViewHolder> {

    private FileList fileList; //contains the filelist as well as the current selection
    private Context context;
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
            NavigationItemView.NavigationKeyHandler navigationKeyHandler,
            Context context
            ) {
        this.fileList = fileList;
        this.itemFocusedListener = itemFocusedListener;
        this.itemClickListener = itemClickListener;
        this.navigationKeyHandler = navigationKeyHandler;
        this.context = context;
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
        holder.text.setText(fileList.getFileList().get(p));
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
        if(position == fileList.getFocus()) {
            boolean f = holder.view.requestFocus();
            Log.i("FileListNavigationAdpt", "view took focus: " + f);
        }
    }

    @Override
    public int getItemCount() {
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
